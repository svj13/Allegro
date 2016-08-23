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

    private Image profilePic;

    private ProjectHandler projectHandler;

    private Environment env;

    private JSONObject properties;

    private Date lastSignIn;

    private Path userDirectory;

    private String userFirstName;

    private String userLastName;

    private int experience;

    private int level;


    /**
     *  User constructor used for generating new users.
     * @param userName
     * @param password
     * @param env
     */
    public User(String userName, String password, Environment env){
        userDirectory = Paths.get("UserData/"+userName);
        this.userName = userName;
        this.userPassword = password;
        this.experience = 0;
        this.level = 1;
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
        this.profilePic = new Image(userDirectory.toUri() + "/profilePicture");

        projectHandler = new ProjectHandler(env, userName);
        //loadFullProperties();
    }

    /**
     * Loads basic user properties (Picture, Name, Password etc.)
     * Used when loading a collection of users (Login screen)
     * @param env
     * @param user user name
     */
    public User(Environment env, String user){
        userDirectory = Paths.get("UserData/"+user);
        this.env = env;
        this.userName = user;
        properties = new JSONObject();
        loadBasicProperties();
        this.profilePic = new Image(userDirectory.toUri() + "/profilePicture");


    }

    /**
     * loads extensive user properties (after user login)
     */
    public void loadFullProperties(){
        /**
         * Current Theme
         * Musical Terms
         * Project Handler
         *
         */

        //Load musical terms property
        Gson gson = new Gson();
        Type termsType = new TypeToken<ArrayList<Term>>() {
        }.getType();
        ArrayList<Term> terms = gson.fromJson((String) properties.get("musicalTerms"), termsType);

        if (terms != null) {

            env.getMttDataManager().setTerms(terms);
        }

        projectHandler = new ProjectHandler(env, userName);




    }


    public ProjectHandler getProjectHandler(){

        return projectHandler;}

    /**
     * Loads basic properties which need be readed in login screen.
     */
    private void loadBasicProperties() {
        /**
         * Properties:
         *  PhotoID - Stored as default 'userPicture.png'
         *  Last sign in time
         *  name
         *  Password
         *  Musical terms
         *  Maybe default tempo?
         *  Theme
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

        try {
            //name
            userFullName = (properties.get("fullName")).toString();
        }catch (NullPointerException e){
            userFullName = userName;
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


        //Password
        userPassword = (properties.get("password")).toString();


        try {
            //Theme
            themeColor = (properties.get("themeColor")).toString();
        }catch(NullPointerException e){
            themeColor = "white";
        }

        //User experience
        try {
            experience = Integer.parseInt(properties.get("experience").toString());
        } catch (NullPointerException e) {
            //If XP has never been set (ie old account), default to 0
            experience = 0;
        }

        //Level
        try {
            level = ((Long) properties.get("level")).intValue();
        } catch (NullPointerException e) {
            //If level has never been set, (ie old account), default to 1
            level = 1;
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
        properties.put("experience", this.experience);
        properties.put("level", this.level);


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


    public String getUserPassword() {
        return userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserPicture(Image image) {
        this.profilePic = image;
    }

    public Image getUserPicture() {
        return this.profilePic;
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
        return experience;
    }

    public int getUserLevel() {
        return level;
    }

    /**
     * When a user gains XP, eg by completing a tutoring session, this function is called. It adds
     * their newly gained experience to their overall experience, and saves this info to their user
     * file.
     *
     * @param addedExperience The integer amount of gained experience, to be added to the user
     */
    public void addExperience(int addedExperience) {
        experience += addedExperience;
        saveProperties();
    }








}