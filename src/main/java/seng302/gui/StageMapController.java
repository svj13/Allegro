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




    Environment env;

    public void setEnvironment(Environment env) {
        this.env = env;
    }

    public StageMapController() {


    }

    private void openTutorTab(String tutor) {

    }


    @FXML
    private void launchMusicalTermsTutor() {
        System.out.println("ERRORORORORORORORORORA  A");
    }

    @FXML
    private void launchPitchTutor() {
        RootController.openPitchTutor();
    }

    @FXML
    private void launchScaleRecognitionTutor() {
        System.out.println("ERRORORORORORORORORORA  A");
    }

    @FXML
    private void launchChordRecognitionTutor() {
        System.out.println("ERRORORORORORORORORORA  A");
    }

    @FXML
    private void launchIntervalRecognitionTutor() {
        System.out.println("ERRORORORORORORORORORA  A");
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
        System.out.println("ERRORORORORORORORORORA  A");
    }

    @FXML
    private void launchKeySignaturesTutor() {
        System.out.println("ERRORORORORORORORORORA  A");
    }
    @FXML
    private void launchDiatonicChordsTutor() {
        System.out.println("ERRORORORORORORORORORA  A");
    }
    @FXML
    private void launchMajorModesTutor() {
        System.out.println("ERRORORORORORORORORORA  A");
    }



}
