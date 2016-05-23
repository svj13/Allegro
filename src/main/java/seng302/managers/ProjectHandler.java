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
import javafx.scene.control.TextInputDialog;
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
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import seng302.Environment;
import seng302.data.Term;
import seng302.utility.OutputTuple;

public class ProjectHandler {
    private String[] propertyNames = {"tempo"};

    JSONObject projectSettings;


    JSONObject overalOveralObject;
    JSONObject overalSessionObject;
    Collection<JSONObject> pitchTutorRecordsList = new ArrayList<JSONObject>();
    String pitchTutorRecordStats = "";

    JSONObject intervalTutorRecords;
    JSONObject musicalTermsTutorRecords;
    JSONObject scaleTutorRecords;
    JSONObject chordTutorRecords;

    JSONParser parser = new JSONParser(); //parser for reading project

    JSONArray projectList;

    JSONObject projectsInfo = new JSONObject();
    Path userDirectory = Paths.get("UserData"); //Default user path for now, before user compatibility is set up.

    private String currentProjectPath, projectName;

    boolean saved = true;
    Environment env;

    public ProjectHandler(Environment env){

        projectSettings = new JSONObject();
        //pitchTutorRecords = new JSONObject();
        intervalTutorRecords = new JSONObject();
        musicalTermsTutorRecords = new JSONObject();
        scaleTutorRecords = new JSONObject();
        chordTutorRecords = new JSONObject();
        overalOveralObject = new JSONObject();
        overalSessionObject = new JSONObject();


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
        Gson gson = new Gson();
        projectSettings.put("tempo", env.getPlayer().getTempo());
        String transcriptString = gson.toJson(env.getTranscriptManager().getTranscriptTuples());
        projectSettings.put("transcript", transcriptString);

        String musicalTermsJSON = gson.toJson(env.getMttDataManager().getTerms());
        projectSettings.put("musicalTerms", musicalTermsJSON);

        projectSettings.put("rhythm", gson.toJson(env.getPlayer().getRhythmHandler().getRhythmTimings()));

    }


    public void saveSessionStat(String tutorType, String statString){
        if(tutorType.equals("pitch")) {

            pitchTutorRecordStats += (statString);

        }
//        }else if(tutorType.equals("interval")) {
//
//            intervalTutorRecords.put("Record", record);
//
//        }else if(tutorType.equals("musicalTerm")){
//            musicalTermsTutorRecords.put("Record", record);
//
//        }else if(tutorType.equals("scale")){
//
//            scaleTutorRecords.put("Record", record);
//
//        }else if(tutorType.equals("chord")){
//
//            chordTutorRecords.put("Record", record);
//        }



    }

    public void saveTutorRecords(String tutorType, String record){

        if(tutorType.equals("pitch")){

            JSONObject jasonOFQuestion = new JSONObject();

            jasonOFQuestion.put("QuestionInfo", record);
            pitchTutorRecordsList.add(jasonOFQuestion);
            System.out.println(pitchTutorRecordsList);

        }else if(tutorType.equals("interval")) {

            intervalTutorRecords.put("Record", record);

        }else if(tutorType.equals("musicalTerm")){
        musicalTermsTutorRecords.put("Record", record);

        }else if(tutorType.equals("scale")){

        scaleTutorRecords.put("Record", record);

        }else if(tutorType.equals("chord")){

            chordTutorRecords.put("Record", record);
        }
    }


    /**
     * load all saved project properties from the project json file.
     * This currently supports Tempo, working transcript, musical terms and rhythm setting.
     *
     */
    private void loadProperties(){
        int tempo;
        Gson gson = new Gson();

        try {
            tempo = ((Long) projectSettings.get("tempo")).intValue();
        }catch(Exception e){
            tempo = 120;
        }
        env.getPlayer().setTempo(tempo);


        //Transcript
        ArrayList<OutputTuple> transcript;
        Type transcriptType = new TypeToken<ArrayList<OutputTuple>>() {}.getType();
        transcript = gson.fromJson((String)projectSettings.get("transcript"), transcriptType);

        env.getTranscriptManager().setTranscriptContent(transcript);
        env.getRootController().setTranscriptPaneText(env.getTranscriptManager().convertToText());

        //Musical Terms
        Type termsType = new TypeToken<ArrayList<Term>>() {}.getType();
        ArrayList<Term> terms = gson.fromJson((String)projectSettings.get("musicalTerms"), termsType);

        if(terms != null){

            env.getMttDataManager().setTerms(terms);
        }

        //Rhythm
        int[] rhythms;


        try {
            rhythms = ((int[]) gson.fromJson((String)projectSettings.get("rhythm"), int[].class));
            rhythms = rhythms == null ? new int[]{12} : rhythms;
        }catch(Exception e){
            rhythms = new int[]{12};
        }
        env.getPlayer().getRhythmHandler().setRhythmTimings(rhythms);




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
            createNewProject();
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
            Gson gson = new Gson();

            saveProperties();
            projectName = projectAddress.substring(projectAddress.lastIndexOf("/") + 1);

            FileWriter file = new FileWriter(projectAddress+"/"+projectName+".json");
            file.write(projectSettings.toJSONString());
            file.flush();
            file.close();

            FileWriter file1 = new FileWriter(projectAddress+"/PitchComparisonRecords.json",true);
            projectSettings.put("tempo", env.getPlayer().getTempo());


            //System.out.print("pitchTutorRecordList: " + pitchTutorRecordsList);
            overalSessionObject.put("Questions", pitchTutorRecordsList);
            overalSessionObject.put("SessionStats", pitchTutorRecordStats);
            System.out.println(overalSessionObject);


            overalOveralObject.put("Session_" + new Date().toString(),overalSessionObject);
            file1.write(overalOveralObject.toJSONString());
            //file1.write(overalSessionObject.toJSONString());
            pitchTutorRecordsList.clear();
            pitchTutorRecordStats = "";
            overalSessionObject.clear();
            overalOveralObject.clear();

            file1.flush();
            file1.close();

            FileWriter file2 = new FileWriter(projectAddress+"/IntervalRecognitionRecords.json");
            file2.write(intervalTutorRecords.toJSONString());
            file2.flush();
            file2.close();

            FileWriter file3 = new FileWriter(projectAddress+"/MusicalTermsRecords.json");
            file3.write(musicalTermsTutorRecords.toJSONString());
            file3.flush();
            file3.close();

            FileWriter file4 = new FileWriter(projectAddress+"/ScaleRecognitionRecords.json");
            file4.write(scaleTutorRecords.toJSONString());
            file4.flush();
            file4.close();

            FileWriter file5 = new FileWriter(projectAddress+"/ChordRecognitionRecords.json");
            file5.write(chordTutorRecords.toJSONString());
            file5.flush();
            file5.close();




            env.getRootController().setWindowTitle(projectName);

            currentProjectPath = projectAddress;

            //Check if it isn't an exisiting stored project
            updateProjectList();

        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Updates the json list of project names, used to fill the quick load list.
     *
     */
    private  void updateProjectList(){
        if(!projectList.contains(projectName)) {
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
        else if(propName.equals("rhythm")){


            if(projectSettings.containsKey("rhythm") && !(projectSettings.get("rhythm").equals(env.getPlayer().getRhythmHandler().getRhythmTimings()))){ //If not equal

                env.getRootController().setWindowTitle(saveName + "*");
                saved = false;
            }
        }

    }

    /**
     * Checking functionality specifically for musical saved musical terms.
     */
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

            env.resetEnvironment();
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
                    env.getRootController().errorAlert("Project properties are corrupt - resetting values.");
                    saveProject(path);
                }

            }

            this.projectName = pName;

            loadProperties();

            currentProjectPath = path;
            updateProjectList();

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

    /**
     * Creates a new project.
     */
    public void createNewProject() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("New Project");
        dialog.setHeaderText("New Project");
        dialog.setContentText("Please enter the project name:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            String resultString = result.get().toString();
            Path path;
            try{
                path = Paths.get("UserData/Projects/" + resultString);

                if(!Files.isDirectory(path)){
                    try{

                        Files.createDirectories(path);

                        env.getProjectHandler().saveProject(path.toString().replace("\\", "/"));
                        //setWindowTitle(resultString);

                    }
                    catch(IOException e){
                        //Failed to create the directory.
                        e.printStackTrace();
                    }

                }
                else{
                    env.getRootController().errorAlert("The project: "  +resultString+" already exists.");
                }

            }
            catch(InvalidPathException invPath){
                //invalid path (Poor project naming)
                env.getRootController().errorAlert("Invalid file name - try again.");
                createNewProject();
            }

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
