package seng302.Users;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import seng302.Environment;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jmw280 on 22/07/16.
 */
public class UserHandler {



    private User currentUser;

    Environment env;
    JSONArray userList;
    JSONParser parser = new JSONParser(); //parser for reading project




    final Path userDirectory = Paths.get("UserData"); //Default user path for now, before user compatibility is set up.


    public UserHandler(Environment env){
        this.env = env;

        populateUsers();

    }

    public ArrayList<String> getUserNames(){
        return (ArrayList<String>) userList;
    }

    public HashMap<String, User> getUsers(){
        ArrayList<String> names = (ArrayList<String>) userList;
        HashMap<String, User> users = new HashMap<>();

        for(String un : names){
            users.put(un, new User(env, un));
        }
        return users;
    }



    private void populateUsers(){
        ArrayList<User> users = new ArrayList<User>();
        JSONObject UsersInfo = new JSONObject();

        try {
            UsersInfo = (JSONObject) parser.parse(new FileReader(userDirectory + "/projects.json"));
            this.userList = (JSONArray) UsersInfo.get("user");

        } catch (FileNotFoundException e) {
            try {
                System.err.println("projects.json Does not exist! - Creating new one");
                userList = new JSONArray();


                UsersInfo.put("projects", userList);

                if (!Files.isDirectory(userDirectory)) {
                    //Create Projects path doesn't exist.
                    try {
                        Files.createDirectories(userDirectory);


                    } catch (IOException eIO3) {
                        //Failed to create the directory.
                        System.err.println("Well UserData directory failed to create.. lost cause.");
                    }
                }

                FileWriter file = new FileWriter(userDirectory + "/projects.json");
                file.write(UsersInfo.toJSONString());
                file.flush();
                file.close();

            } catch (IOException e2) {
                System.err.println("Failed to create projects.json file.");


            }
        }catch (IOException e) {
                //e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }





    public void createUser(String user, String password){

    }


    public User getCurrentUser(){
        return currentUser;
    }

    public void setCurrentUser(String userName){
        this.currentUser = new User(env, userName);
        currentUser.loadFullProperties();

    }

}
