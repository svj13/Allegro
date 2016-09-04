package seng302.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
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

    //sets the current tutor so the tutor can be launched upon the button click
    String currentTutor;

    UserPageController userPageController;


    Environment env;

    public void setEnvironment(Environment env) {
        this.env = env;
        userPageController = env.getUserPageController();
    }


    public StageMapController() {




    }

    private void openTutorTab(String tutor) {

    }


    @FXML
    private void launchMusicalTermsTutor() {
        userPageController.setCurrentTutor("Musical Terms Tutor");
        userPageController.loadTutor();
    }

    @FXML
    private void launchPitchTutor() {
        userPageController.setCurrentTutor("Pitch Comparison Tutor");
        userPageController.loadTutor();
    }

    @FXML
    private void launchScaleRecognitionTutor() {
        userPageController.setCurrentTutor("Scale Recognition Tutor");
        userPageController.loadTutor();
    }

    @FXML
    private void launchChordRecognitionTutor() {
        userPageController.setCurrentTutor("Chord Recognition Tutor");
        userPageController.loadTutor();
    }

    @FXML
    private void launchIntervalRecognitionTutor() {
        userPageController.setCurrentTutor("Interval Recognition Tutor");
        userPageController.loadTutor();
    }

    @FXML
    private void launchScaleReconitionAdvancedTutor() {
        System.out.println("ERRORORORORORORORORORA  A");
    }

    @FXML
    private void launchChordRecognitionAdvancedTutor() {
        System.out.println("ERRORORORORORORORORORA  A");
    }

    @FXML
    private void launchChordSpellingTutor() {
        userPageController.setCurrentTutor("Chord Spelling Tutor");
        userPageController.loadTutor();
    }

    @FXML
    private void launchKeySignaturesTutor() {
        userPageController.setCurrentTutor("Key Signature Tutor");
        userPageController.loadTutor();
    }
    @FXML
    private void launchDiatonicChordsTutor() {
        userPageController.setCurrentTutor("Diatonic Chord Tutor");
        userPageController.loadTutor();
    }
    @FXML
    private void launchMajorModesTutor() {
        System.out.println("ERRORORORORORORORORORA  A");
    }



}
