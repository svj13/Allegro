package seng302.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import jdk.nashorn.internal.parser.JSONParser;
import seng302.Environment;
import javafx.scene.control.Button;
import seng302.Users.Project;
import seng302.Users.TutorHandler;
import seng302.utility.TutorRecord;

import java.util.ArrayList;


/**
 * Created by svj13 on 2/09/16.
 */
public class StageMapController {

    public Project currentProject;


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

    private TutorHandler tutorHandler;


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
        currentProject = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject();
        tutorHandler = currentProject.getTutorHandler();

        userPageController = env.getUserPageController();
    }


    public StageMapController() {




    }

    /**
     * Fetches tutor score file of tutor of interest and parses items of interest. Will terminate if it cannot
     * source more than 3 entries
     */
    private void fetchTutorFile(String tutorId) {
        boolean enoughEntries = false; //must be at least 3 entries for their to be a valid entry
        System.out.println(tutorHandler);
        ArrayList<TutorRecord> td = tutorHandler.getTutorData(tutorId);
        System.out.println(td);



        if (enoughEntries) {
            System.out.println("You need to attempt this tutor 5x consecutively with an " +
                    "average score of 90% to proceed to the next tutor. Soz");
//        } else {

        }



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
        env.getRootController().getTutorFactory().openTutor("Musical Terms Tutor");
        fetchTutorFile("musicalTermTutor");

    }

    @FXML
    private void launchPitchTutor() {
        env.getRootController().getTutorFactory().openTutor("Pitch Comparison Tutor");
    }

    @FXML
    private void launchScaleRecognitionTutor() {
        env.getRootController().getTutorFactory().openTutor("Scale Recognition Tutor");
    }

    @FXML
    private void launchChordRecognitionTutor() {
        env.getRootController().getTutorFactory().openTutor("Chord Recognition Tutor");
    }

    @FXML
    private void launchIntervalRecognitionTutor() {
        env.getRootController().getTutorFactory().openTutor("Interval Recognition Tutor");
    }

    @FXML
    private void launchScaleRecognitionAdvancedTutor() {
        System.out.println("Advanced scale tutor");
        env.getRootController().getTutorFactory().openTutor("Scale Recognition Tutor");
    }

    @FXML
    private void launchChordRecognitionAdvancedTutor() {

        System.out.println("Chord Recognition Advanced Tutor");
        env.getRootController().getTutorFactory().openTutor("Chord Recognition Tutor");
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

    //Getters for data of each tutor

//    private ArrayList<TutorRecord> getMusicalTermsData() {
//        ArrayList<TutorRecord> musicalTermsTutorData = new ArrayList<>();
//        musicalTermsTutorData.add(0, tutorHandler.getTutorData("musicalTermsTutor"));
//        System.out.println(musicalTermsTutorData);
//        return musicalTermsTutorData;
//    }



}
