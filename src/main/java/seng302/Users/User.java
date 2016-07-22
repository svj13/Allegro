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
import java.util.ArrayList;

/**
 * Created by jmw280 on 22/07/16.
 */
public class User {



    private String userName;

    private String password;

    private Image profilePic;

    private ProjectHandler projectHandler;

    private Environment env;

    private JSONObject properties;



    public User(String userName, String password, Environment env){
        this.userName = userName;
        this.password = password;
        this.env = env;
        projectHandler = new ProjectHandler(env, userName);
    }

    private void loadProperties() {

        Path userDirectory = Paths.get("UserData/"+userName); //Default user path for now, before user compatibility is set up.
        JSONParser parser = new JSONParser(); //parser for reading project
        try {
            properties = (JSONObject) parser.parse(new FileReader(userDirectory + "/user_properties.json"));
        }catch(Exception e){

        }

        int tempo;
        Gson gson = new Gson();

        try {
            tempo = ((Long) properties.get("tempo")).intValue();
        } catch (Exception e) {
            tempo = 120;
        }
        env.getPlayer().setTempo(tempo);


        //Transcript
        ArrayList<OutputTuple> transcript;
        Type transcriptType = new TypeToken<ArrayList<OutputTuple>>() {
        }.getType();
        transcript = gson.fromJson((String) properties.get("transcript"), transcriptType);

        env.getTranscriptManager().setTranscriptContent(transcript);
        env.getRootController().setTranscriptPaneText(env.getTranscriptManager().convertToText());

        //Musical Terms
        Type termsType = new TypeToken<ArrayList<Term>>() {
        }.getType();
        ArrayList<Term> terms = gson.fromJson((String) properties.get("musicalTerms"), termsType);

        if (terms != null) {

            env.getMttDataManager().setTerms(terms);
        }

        //Rhythm
        int[] rhythms;


        try {
            rhythms = ((int[]) gson.fromJson((String) properties.get("rhythm"), int[].class));
            rhythms = rhythms == null ? new int[]{12} : rhythms;
        } catch (Exception e) {
            rhythms = new int[]{12};
        }
        env.getPlayer().getRhythmHandler().setRhythmTimings(rhythms);


        env.getTranscriptManager().unsavedChanges = false;


    }





















}