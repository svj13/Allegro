package seng302.json;

/**
 * Created by Jonty on 12-Apr-16.
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import seng302.Environment;

public class jsonHandler {

    JSONObject projectSettings = new JSONObject();
    JSONParser parser = new JSONParser(); //parser for reading project


    Environment env;
    public jsonHandler(Environment env){
        this.env = env;
    }

    public void saveProject(String projectName){

        //Add all settings to such as tempo speed to the project here.


        try {

            projectSettings.put("tempo", env.getPlayer().getTempo());


            FileWriter file = new FileWriter(projectName+".json");
            file.write(projectSettings.toJSONString());
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void loadProject(String path){
        try {

            JSONObject obj = (JSONObject) parser.parse(new FileReader(path));



            String tempo = (String) projectSettings.get("tempo");
            System.out.println(tempo);





        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
