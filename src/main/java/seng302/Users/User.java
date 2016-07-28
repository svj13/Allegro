package seng302.Users;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.image.Image;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import seng302.Environment;
import seng302.data.Term;
import seng302.utility.OutputTuple;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jmw280 on 22/07/16.
 */
public class User {

    private String userFullName, userPassword, themeColor;


    private String userName;

    private String password;

    private Image profilePic;

    private ProjectHandler projectHandler;

    private Environment env;


    private JSONObject properties;

    private Date lastSignIn;






    public User(String userName, String password, Environment env){
        this.userName = userName;
        this.password = password;
        this.env = env;
        projectHandler = new ProjectHandler(env, userName);
        loadBasicProperties();
        //loadFullProperties();
    }

    /**
     * Loads basic user properties (Picture, Name, Password etc.)
     * @param env
     * @param user user name
     */
    public User(Environment env, String user){
        this.env = env;
        this.userName = user;
        loadBasicProperties();
    }

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



        //env.getTranscriptManager().unsavedChanges = false;
        Type dateType = new TypeToken<Date>() {
        }.getType();
        lastSignIn = gson.fromJson((String) properties.get("signInTime"), dateType);

        //name
        userFullName = ((String) properties.get("fullName")).toString();


        //Password
        userPassword = ((String) properties.get("password")).toString();

        //Theme
        themeColor = ((String) properties.get("themeColor")).toString();





    }





















}