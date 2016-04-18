package seng302.JSON;

/**
 * Created by Jonty on 12-Apr-16.
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import seng302.Environment;

public class jsonHandler {

    JSONObject projectSettings = new JSONObject();
    JSONParser parser = new JSONParser(); //parser for reading project

    JSONArray projectList;

    JSONObject projectsInfo = new JSONObject();
    Path userDirectory = Paths.get("UserData"); //Default user path for now, before user compatibility is set up.

    String currentProjectPath;

    String projName; //delete this testing for commit fix.

    Environment env;
    public jsonHandler(Environment env){

        this.env = env;
        try {
            this.projectsInfo = (JSONObject) parser.parse(new FileReader(userDirectory+"/projects.JSON"));
            this.projectList = (JSONArray) projectsInfo.get("projects");

        } catch (FileNotFoundException e) {
            try {
                System.err.println("projects.JSON Does not exist! - Creating new one");
                projectList = new JSONArray();


                projectsInfo.put("projects",projectList);

                FileWriter file = new FileWriter(userDirectory+"/projects.JSON");
                file.write(projectsInfo.toJSONString());
                file.flush();
                file.close();

            } catch (IOException e2) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void saveCurrentProject(){
        if(currentProjectPath.length() > 0){
            saveProject(currentProjectPath);
        }
        else System.err.println("Project could not be saved!!");
        //TODO make a pop up window if the project cannot be saved.

    }
    public void saveProject(String projectAddress){

        //Add all settings to such as tempo speed to the project here.


        try {


            projectSettings.put("tempo", (int) env.getPlayer().getTempo());

            FileWriter file = new FileWriter(projectAddress+".JSON");
            file.write(projectSettings.toJSONString());
            file.flush();
            file.close();
            String projectName = projectAddress.substring(projectAddress.lastIndexOf("/") + 1);
            this.projName = projectName;
            env.getRootController().setWindowTitle(projName);

            //Check if it isn't an exisiting stored project
            if(!projectList.contains(projectName)){
                System.out.println("Saved project not found in projects.JSON - adding it");
                projectList.add(projectName);
                System.out.println(projectList.size());

                try {
                    projectsInfo.put("projects", projectList);
                    FileWriter projectsJson = new FileWriter(userDirectory+"/projects.JSON");
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

        if(propName == "tempo"){

            if(!(projectSettings.get("tempo") == String.valueOf(env.getPlayer().getTempo()))){ //If not equal

                env.getRootController().setWindowTitle(projName + "*");
            }
        }



    }



    public  void loadProject(String pName){
        try {

            String path = userDirectory+"/Projects/"+pName+"/"+pName;
            projectSettings = (JSONObject) parser.parse(new FileReader(path+".JSON"));
            this.projName = pName;

            int tempo = ((Long)projectSettings.get("tempo")).intValue();
            System.out.println(tempo);

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

    public JSONArray getProjectList(){
        return this.projectList;
    }

}
