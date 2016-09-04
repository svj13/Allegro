package seng302.Users;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.image.Image;
import seng302.Environment;
import seng302.data.Term;
import seng302.utility.FileHandler;

/**
 * Stores information about a single user.
 * Includes getter/setter functions for updating all this information
 */
public class User {

    private String userFullName, userPassword, themeColor;

    private String userName;

    private Path profilePicPath;

    private ProjectHandler projectHandler;

    private Environment env;

    private JSONObject properties;

    private Date lastSignIn;

    private Path userDirectory;

    private String userFirstName;

    private String userLastName;


    /**
     *  User constructor used for generating new users.
     * @param userName username
     * @param password password to set for the corresponding user.
     * @param env
     */
    public User(String userName, String password, Environment env){
        userDirectory = Paths.get("UserData/"+userName);
        this.userName = userName;
        this.userPassword = password;
        this.env = env;
        properties = new JSONObject();

        createUserFiles();
        loadBasicProperties();
        saveProperties();

        Path filePath = Paths.get(this.userDirectory.toString()+"/profilePicture");
        try {
            URI defaultPath = getClass().getResource("/images/testDP.jpg").toURI();
            FileHandler.copyFolder(new File(defaultPath), filePath.toFile());
        }
        catch (Exception e){
            e.printStackTrace();

        }

        profilePicPath = Paths.get(userDirectory.toString() + "/profilePicture");

        projectHandler = new ProjectHandler(env, userName);

        //loadFullProperties();
    }

    /**
     * Loads basic user properties (Picture, Name, Password etc.)
     * Used when loading a collection of users (Login screen)
     * This should only load properties which NEED to be loaded before the user logs in.
     * @param env
     * @param user user name
     */
    public User(Environment env, String user){
        userDirectory = Paths.get("UserData/"+user);
        this.env = env;
        this.userName = user;
        properties = new JSONObject();
        loadBasicProperties();
        profilePicPath = Paths.get(userDirectory.toString() + "/profilePicture");


    }

    /**
     * loads extensive user properties (after user login)
     * This should load all properties which aren't neccessary before the user logs in.
     */
    public void loadFullProperties(){
        /**
         * Current Theme
         * Musical Terms
         * Full name
         * Project Handler
         * Theme
         */

        //Load musical terms property
        Gson gson = new Gson();
        Type termsType = new TypeToken<ArrayList<Term>>() {
        }.getType();
        ArrayList<Term> terms = gson.fromJson((String) properties.get("musicalTerms"), termsType);

        if (terms != null) {
            env.getMttDataManager().setTerms(terms);
        }

        try {
            userFirstName = (properties.get("firstName")).toString();
        } catch (NullPointerException e) {
            userFirstName = "";
        }

        try {
            userLastName = (properties.get("lastName")).toString();
        } catch (NullPointerException e) {
            userLastName = "";
        }

        try {
            //Theme
            themeColor = (properties.get("themeColor")).toString();
        } catch (NullPointerException e) {
            themeColor = "white";
        }



        projectHandler = new ProjectHandler(env, userName);




    }

    /**
     * This needs to be called to unlock the project folders to allow them to be deleted.
     */
    public void delete() {

        this.projectHandler = null;


    }


    public ProjectHandler getProjectHandler(){

        return projectHandler;}

    /**
     * Loads basic properties which need be read by the login screen.
     */
    private void loadBasicProperties() {
        /**
         * Basic properties:
         *  PhotoID - Stored as default 'userPicture.png'
         *  Last sign in time
         *  username
         *  Password
         *
         */
        Gson gson = new Gson();
        Path userDirectory = Paths.get("UserData/"+userName); //Default user path for now, before user compatibility is set up.
        JSONParser parser = new JSONParser(); //parser for reading project
        try {
            properties = (JSONObject) parser.parse(new FileReader(userDirectory + "/user_properties.json"));
        }catch(Exception e){

        }

        try {
            Type dateType = new TypeToken<Date>() {
            }.getType();
            lastSignIn = gson.fromJson((String) properties.get("signInTime"), dateType);

            if(lastSignIn == null)  lastSignIn = new Date();
        }catch(Exception e){
            lastSignIn = new Date();

        }


        //Password
        userPassword = (properties.get("password")).toString();



        try {
            //Theme
            themeColor = (properties.get("themeColor")).toString();
        }catch(NullPointerException e){
            themeColor = "white";
        }


    }


    /**
     * Updates project property JSON files to be written to disc.
     */
    public void updateProperties() {
        Gson gson = new Gson();
        properties.put("userName", userName);
        properties.put("fullName", userFullName);
        properties.put("password", this.userPassword);
        properties.put("themeColor", this.themeColor);
        properties.put("firstName", this.userFirstName);
        properties.put("lastName", this.userLastName);


        String musicalTermsJSON = gson.toJson(env.getMttDataManager().getTerms());
        properties.put("musicalTerms", musicalTermsJSON);

        String lastSignInJSON = gson.toJson(lastSignIn);
        properties.put("signInTime", lastSignInJSON);


    }


    /**
     * Writes JSON properties to disc
     */
    public void saveProperties() {
        try {
            Gson gson = new Gson();
            updateProperties();


            FileWriter file = new FileWriter(userDirectory + "/user_properties.json");
            file.write(properties.toJSONString());
            file.flush();
            file.close();


        } catch (IOException e) {

            e.printStackTrace();
        }


    }


    /**
     * Creates user directory files.
     */
    private void createUserFiles(){
        //Add all settings to such as tempo speed to the project here.


        Path path;
        try {
            //path = Paths.get("UserData/" + userName + "/" + resultString);

            if (!Files.isDirectory(userDirectory)) {
                try {

                    Files.createDirectories(userDirectory);

                    saveProperties();



                } catch (IOException e) {
                    //Failed to create the directory.
                    e.printStackTrace();
                }

            } else {
                env.getRootController().errorAlert("The user " + userName + " already exists.");
                //createNewProject();
            }

        } catch (InvalidPathException invPath) {
            //invalid path (Poor project naming)
            env.getRootController().errorAlert("Invalid file name - try again.");
            //createNewProject();
        }


    }


    /**
     * Checking functionality specifically for musical saved musical terms.
     */
    public void checkmusicTerms() {
        //String saveName = (projectName == null || projectName.length() < 1) ? "No Project" : this.projectName;
        if (properties.containsKey("musicalTerms")) {
            Type termsType = new TypeToken<ArrayList<Term>>() {
            }.getType();
            if (!properties.get("musicalTerms").equals(new Gson().fromJson((String) properties.get("muscalTerms"), termsType))) {
                env.getRootController().setWindowTitle(getProjectHandler().getCurrentProject().projectName + "*");
                getProjectHandler().getCurrentProject().saved = false;
            }
        } else {
            if (env.getRootController() != null) {
                env.getRootController().setWindowTitle(getProjectHandler().getCurrentProject().projectName + "*");
                getProjectHandler().getCurrentProject().saved = false;
            }

        }


    }


    public String getUserPassword() {
        return userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserPicture(Path imagePath) {
        this.profilePicPath = imagePath;
    }

    public Image getUserPicture() {
        return new Image(profilePicPath.toUri().toString());
    }

    public void setUserFirstName(String name) {
        userFirstName = name;
    }

    public void setUserLastName(String name) {
        userLastName = name;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public int getUserExperience() {
        Integer experience = getProjectHandler().getCurrentProject().getExperience();
        return experience;
    }

    public int getUserLevel() {
        Integer level = getProjectHandler().getCurrentProject().getLevel();
        return level;
    }

}