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
import seng302.utility.OutputTuple;

public class ProjectHandler {

    JSONObject projectSettings;
    JSONParser parser = new JSONParser(); //parser for reading project

    JSONArray projectList;

    JSONObject projectsInfo = new JSONObject();
    Path userDirectory = Paths.get("UserData"); //Default user path for now, before user compatibility is set up.

    String currentProjectPath;

    String projName; //delete this testing for commit fix.
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


            projectSettings.put("tempo", env.getPlayer().getTempo());


            String transcriptString = new Gson().toJson(env.getTranscriptManager().getTranscriptTuples());
            projectSettings.put("transcript", transcriptString);

            FileWriter file = new FileWriter(projectAddress+".json");
            file.write(projectSettings.toJSONString());
            file.flush();
            file.close();
            String projectName = projectAddress.substring(projectAddress.lastIndexOf("/") + 1);
            this.projName = projectName;
            env.getRootController().setWindowTitle(projName);
            System.out.println("project name" +projectAddress);
            currentProjectPath = projectAddress;
            //Check if it isn't an exisiting stored project
            if(!projectList.contains(projectName)){
                System.out.println("Saved project not found in projects.json - adding it");
                projectList.add(projectName);
                System.out.println(projectList.size());

                try {
                    projectsInfo.put("projects", projectList);
                    FileWriter projectsJson = new FileWriter(userDirectory+"/projects.json");
                    projectsJson.write(projectsInfo.toJSONString());
                    projectsJson.flush();
                    projectsJson.close();


                } catch (Exception e2) {
                    e2.printStackTrace();
                }

            }

        } catch (IOException e) {
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
        String saveName = (propName.length() < 1) ? "New Project" : projName;

        if(propName.equals("tempo")){


            if(projectSettings.containsKey("tempo") && !(projectSettings.get("tempo").equals(String.valueOf(env.getPlayer().getTempo())))){ //If not equal

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

            String path = userDirectory+"/Projects/"+pName+"/"+pName;
            projectSettings = (JSONObject) parser.parse(new FileReader(path+".json"));
            this.projName = pName;

            int tempo = ((Long)projectSettings.get("tempo")).intValue();
            ArrayList<OutputTuple> transcript;
            Type fooType = new TypeToken<ArrayList<OutputTuple>>() {}.getType();
            transcript = new Gson().fromJson((String)projectSettings.get("transcript"), fooType);


            env.getTranscriptManager().setTranscriptContent(transcript);
            System.out.println(env.getTranscriptManager().convertToText());
            env.getRootController().setTranscriptPaneText(env.getTranscriptManager().convertToText());
            for(OutputTuple t : transcript){
                System.out.println("command: " + t.getInput() + " res: " + t.getResult());
            }

            //SetTempo
            env.getPlayer().setTempo(tempo);

            currentProjectPath = path;

            env.getRootController().setWindowTitle(pName);
            //ignore




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public boolean isSaved(){
        return saved;
    }
    public JSONArray getProjectList(){
        return this.projectList;
    }

}
