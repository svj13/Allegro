package seng302.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import jdk.nashorn.internal.parser.JSONParser;
import seng302.Environment;
import javafx.scene.control.Button;



/**
 * Created by svj13 on 2/09/16.
 */
public class StageMapController {

    @FXML
    private Button scaleRecognitionTutorButton;

    @FXML
    private Button keySignaturesTutorButton;

    @FXML
    private AnchorPane stageMap;

    @FXML
    private Button diatonicChordsTutorButton;

    @FXML
    private Button scaleRecognitionAdvancedTutorButton;

    @FXML
    private Button majorModesTutorButton;

    @FXML
    private Button musicalTermsTutorButton;

    @FXML
    private Button chordRecognitionTutorButton;

    @FXML
    private Button pitchTutorButton;

    @FXML
    private Button chordSpellingTutorButton;

    @FXML
    private Button chordRecognitionAdvancedTutorButton;

    @FXML
    private Button intervalRecognitionButton;

    UserPageController userPageController; //creates an insance of user page controller so we can access its methods

    Environment env; //declare the environment

    private JSONParser parser; //used to parser the tutor records


    /**
     * Initializes the environment and the user page controller instance
     *
     * @param env
     */
    public void setEnvironment(Environment env) {
        this.env = env;
        userPageController = env.getUserPageController();
    }


    public StageMapController() {


    }

    /**
     * Fetches tutor score file of tutor of interest and parses items of interest. Will terminate if it cannot
     * source more than 5 entries
     */
    private void fetchTutorFile() {
        //JSONParser parser = new JSONParser();
        boolean enoughEntries = false; //must be at least 5 entries for their to be a valid entry


        if (enoughEntries) {
            System.out.println("You need to attempt this tutor 5x consecutively with an " +
                    "average score of 90% to proceed to the next tutor. Soz");
        } else {

        }


        //project of interest
        //tutor of interest

        //-> follow brrm brrm path the the tutor record
        //->parse into tree


    }


    /**
     * Takes the tutor score file, finds the scores of the last 5 entries and calculates the average over the last
     * 5 scores
     */
    private void calculateAverageScore() {

        //find last x scores
        //calculate last x scores
        //return average


    }





    /*
    //FXML methods below give actions to the buttons on the stage map. When a stage on the stage map is selected,
    //the corresponding tutor will launch
     */
    @FXML
    private void launchMusicalTermsTutor() {
//        userPageController.setCurrentTutor("Musical Terms Tutor");
        env.getRootController().getTutorFactory().openTutor("Musical Terms Tutor");
    }

    @FXML
    private void launchPitchTutor() {
        //userPageController.setCurrentTutor("Pitch Comparison Tutor");
        env.getRootController().getTutorFactory().openTutor("Pitch Comparison Tutor");
    }

    @FXML
    private void launchScaleRecognitionTutor() {
        //userPageController.setCurrentTutor("Scale Recognition Tutor");
        env.getRootController().getTutorFactory().openTutor("Scale Recognition Tutor");
    }

    @FXML
    private void launchChordRecognitionTutor() {
        //userPageController.setCurrentTutor("Chord Recognition Tutor");
        env.getRootController().getTutorFactory().openTutor("Chord Recognition Tutor");
    }

    @FXML
    private void launchIntervalRecognitionTutor() {
        env.getRootController().getTutorFactory().openTutor("Interval Recognition Tutor");
    }

    @FXML
    private void launchScaleRecognitionAdvancedTutor() {

        System.out.println("Advanced scale tutor");
    }

    @FXML
    private void launchChordRecognitionAdvancedTutor() {

        System.out.println("Chord Recognition Advanced");
    }

    @FXML
    private void launchChordSpellingTutor() {
        env.getRootController().getTutorFactory().openTutor("Chord Spelling Tutor");
    }

    @FXML
    private void launchKeySignaturesTutor() {
        env.getRootController().getTutorFactory().openTutor("Key Signatures Tutor");
    }
    @FXML
    private void launchDiatonicChordsTutor() {
        env.getRootController().getTutorFactory().openTutor("Diatonic Chords Tutor");

    }
    @FXML
    private void launchMajorModesTutor() {

        env.getRootController().getTutorFactory().openTutor("Major Modes Tutor");
    }



}
