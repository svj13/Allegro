package seng302.managers;

/**
 *  ProjectHandler
 *
 *  In charge of handling user project data, including saving, loading and validating.

 *
 * Created by Jonty on 12-Apr-16.
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import seng302.Environment;
import seng302.data.Term;
import seng302.utility.OutputTuple;

public class ProjectHandler {
    private String[] propertyNames = {"tempo"};

    JSONObject projectSettings;
    JSONParser parser = new JSONParser(); //parser for reading project

    JSONArray projectList;

    JSONObject projectsInfo = new JSONObject();
    Path userDirectory = Paths.get("UserData"); //Default user path for now, before user compatibility is set up.

    private String currentProjectPath, projectName;

    boolean saved = true;
    Environment env;

    public ProjectHandler(Environment env){

        projectSettings = new JSONObject();
        this.env = env;
        try {
            this.projectsInfo = (JSONObject) parser.parse(new FileReader(userDirectory+"/projects.json"));
            this.projectList = (JSONArray) projectsInfo.get("projects");

        } catch (FileNotFoundException e) {
            try {
                System.err.println("projects.json Does not exist! - Creating new one");
                projectList = new JSONArray();


                projectsInfo.put("projects",projectList);

                if(!Files.isDirectory(userDirectory)) {
                    //Create Projects path doesn't exist.
                    try {
                        Files.createDirectories(userDirectory);


                    } catch (IOException eIO3) {
                        //Failed to create the directory.
                        System.err.println("Well UserData directory failed to create.. lost cause.");
                    }
                }

                FileWriter file = new FileWriter(userDirectory+"/projects.json");
                file.write(projectsInfo.toJSONString());
                file.flush();
                file.close();

            } catch (IOException e2) {
                System.err.println("Failed to create projects.json file.");

            }


        } catch (IOException e) {
            //e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void saveProperties(){
        projectSettings.put("tempo", env.getPlayer().getTempo());
        String transcriptString = new Gson().toJson(env.getTranscriptManager().getTranscriptTuples());
        projectSettings.put("transcript", transcriptString);

        String musicalTermsJSON = new Gson().toJson(env.getMttDataManager().getTerms());
        projectSettings.put("musicalTerms", musicalTermsJSON);

    }

    private void loadProperties(){
        int tempo;

        try {
            tempo = ((Long) projectSettings.get("tempo")).intValue();
        }catch(Exception e){
            tempo = 120;
        }
        env.getPlayer().setTempo(tempo);


        //Transcript
        ArrayList<OutputTuple> transcript;
        Type transcriptType = new TypeToken<ArrayList<OutputTuple>>() {}.getType();
        transcript = new Gson().fromJson((String)projectSettings.get("transcript"), transcriptType);

        env.getTranscriptManager().setTranscriptContent(transcript);
        env.getRootController().setTranscriptPaneText(env.getTranscriptManager().convertToText());

        //Musical Terms
        Type termsType = new TypeToken<ArrayList<Term>>() {}.getType();
        ArrayList<Term> terms = new Gson().fromJson((String)projectSettings.get("musicalTerms"), termsType);

        if(terms != null){
            for(Term t : terms){
                System.out.println(t.getMusicalTermName());
            }
            env.getMttDataManager().setTerms(terms);
        }



        env.getTranscriptManager().unsavedChanges = false;



    }


    /**
     * Saves the current project, or if there is no current working project; launches the New project dialog.
     */
    public void saveCurrentProject(){
        if(currentProjectPath != null){
            saveProject(currentProjectPath);
        }
        else{
            env.getRootController().newProject();
        }
        saved = true;

    }


    /**
     * Handles Saving a .json Project file, for the specified project address
     * @param projectAddress Project directory address.
     */

    public void saveProject(String projectAddress){

        //Add all settings to such as tempo speed to the project here.

        try {

            saveProperties();
            projectName = projectAddress.substring(projectAddress.lastIndexOf("/") + 1);

            FileWriter file = new FileWriter(projectAddress+"/"+projectName+".json");
            file.write(projectSettings.toJSONString());
            file.flush();
            file.close();


            env.getRootController().setWindowTitle(projectName);

            currentProjectPath = projectAddress;

            //Check if it isn't an exisiting stored project
            if(!projectList.contains(projectName)) {
                System.out.println("Saved project not found in projects.json - adding it");
                projectList.add(projectName);
            }

            try { //Save list of projects.
                projectsInfo.put("projects", projectList);
                FileWriter projectsJson = new FileWriter(userDirectory+"/projects.json");
                projectsJson.write(projectsInfo.toJSONString());
                projectsJson.flush();
                projectsJson.close();


            } catch (Exception e2) {
                e2.printStackTrace();
            }




        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Compares all current project properties to the saved values
     * If there are any differences, adds an asterix indicator to the project title
     */
    public void checkChanges(){


    }

    /**
     * Compares a specified project property to the saved value
     * If there is a difference, adds an asterix indicator to the project title
     * @param propName property id which is stored in the Json project file.
     */
    public void checkChanges(String propName){

        //Accepted values: tempo
        String saveName = (projectName == null) ? "No Project" : this.projectName;

        if(propName.equals("tempo")){


            if(projectSettings.containsKey("tempo") && !(projectSettings.get("tempo").equals(String.valueOf(env.getPlayer().getTempo())))){ //If not equal

                env.getRootController().setWindowTitle(saveName + "*");
                saved = false;
            }
        }

    }
    public void checkmusicTerms(){
        String saveName = (projectName == null || projectName.length() < 1) ? "No Project" : this.projectName;
        if(projectSettings.containsKey("musicalTerms")){
            Type termsType = new TypeToken<ArrayList<Term>>() {}.getType();
            if(!projectSettings.get("musicalTerms").equals(new Gson().fromJson((String) projectSettings.get("muscalTerms"), termsType))){
                env.getRootController().setWindowTitle(saveName + "*");
                saved = false;
            }
        }
        else{
            if(env.getRootController() != null){
                env.getRootController().setWindowTitle(saveName + "*");
                saved = false;
            }

        }


    }


    /**
     * Loads a project, specifed by the project name.
     * All projects must be located in the user's projects directory to be correctly loaded.
     * @param pName project name string
     */
    public  void loadProject(String pName){
        try {

            String path = userDirectory + "/Projects/" + pName;
            try {
                projectSettings = (JSONObject) parser.parse(new FileReader(path +"/"+ pName + ".json"));
            } catch (FileNotFoundException f) {
                //Project doesn't exist? Create it.

                if(!Paths.get(path).toFile().isDirectory()){
                    //If the Project directory folder doesn't exist.
                    System.err.println("Project directory missing - Might have been moved, renamed or deleted.\n Will remove the project from the projects json");
                    env.getRootController().errorAlert("Project directory is missing - possibly moved, renamed or deleted.");
                    projectList.remove(pName);
                    return;
                }
                else{
                    //.json project files are corrupt.

                    saveProject(path);
                }

            }

            this.projectName = pName;

            loadProperties();

            currentProjectPath = path;

            env.getRootController().setWindowTitle(pName);
            //ignore


        } catch (FileNotFoundException e) {
            System.err.println("File not found, printing exception..");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException when trying to parse project .json");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("Parse exception. Project json is corrupt, cannot open.");
            env.getRootController().errorAlert("Project Corrupt - Cannot open.");


        }
    }


    public boolean isSaved(){
        return saved;
    }


    public JSONArray getProjectList(){
        return this.projectList;
    }

    public Boolean isProject(){
        return currentProjectPath != null;
    }

    public String getCurrentProjectPath(){
        return currentProjectPath;
    }

}
