package seng302.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.layout.AnchorPane;
import seng302.Environment;

import java.io.IOException;

/**
 * Created by Jonty on 04-Sep-16.
 */
public class TutorFactory {




    Environment env;
    AnchorPane parentNode;

    /**
     * opens the scale tutor when the scale menu option is pressed If there is already an open tutor
     * of the same form then it sets focus to the already open tutor
     */


    public TutorFactory(Environment env, AnchorPane parentNode){
        this.env =env;
        this.parentNode = parentNode;
    }

    public void openTutor(String tutorName){


        switch (tutorName) {
            case "Pitch Comparison Tutor":
                //userView.setVisible(false);
                openPitchTutor();
                break;
            case "Interval Recognition Tutor":
                //userView.setVisible(false);
                openIntervalTutor();
                break;
            case "Scale Recognition Tutor":
                //userView.setVisible(false);
                openScaleTutor();
                break;
            case "Musical Terms Tutor":
                //userView.setVisible(false);
                openMusicalTermTutor();
                break;
            case "Chord Recognition Tutor":
                //userView.setVisible(false);
                openChordTutor();
                break;
            case "Chord Spelling Tutor":
                //userView.setVisible(false);
                openSpellingTutor();
                break;
            case "Key Signature Tutor":
                //userView.setVisible(false);
                openKeySignatureTutor();
                break;
            case "Diatonic Chord Tutor":
                //userView.setVisible(false);
                openDiatonicChordTutor();
                break;
        }
    }

    /**
     * Expands the subject node to fill the parent.
     * @param node node to expand
     */
    private void fillParentAnchors(Node node){
        parentNode.setRightAnchor(node, 0.0);
        parentNode.setLeftAnchor(node, 0.0);
        parentNode.setBottomAnchor(node, 0.0);
        parentNode.setTopAnchor(node, 0.0);

    }


    /**
     * opens the pitch tutor when the pitch tutor menu option is pressed If there is already an open
     * tutor of the same form then it sets focus to the already open tutor
     */
    @FXML
    private void openPitchTutor() {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/PitchComparisonPane.fxml"));

            try {
                parentNode.getChildren().setAll((Node)loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }

            PitchComparisonTutorController pitchComparisonTutorController = loader.getController();
            pitchComparisonTutorController.create(env);
            pitchComparisonTutorController.setTabID("pitchTutor");


    }

    /**
     * opens the interval tutor when the interval menu option is pressed If there is already an open
     * tutor of the same form then it sets focus to the already open tutor
     */
    @FXML
    private void openIntervalTutor() {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/IntervalRecognitionPane.fxml"));

        try {
            parentNode.getChildren().setAll((Node)loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        IntervalRecognitionTutorController intervalRecognitionTabController = loader.getController();
        intervalRecognitionTabController.create(env);
        intervalRecognitionTabController.setTabID("intervalTutor");


    }

    /**
     * opens the musical terms tutor when the musical term tutor menu option is pressed If there is
     * already an open tutor of the same form then it sets focus to the already open tutor
     */
    @FXML
    private void openMusicalTermTutor() {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/MusicalTermsPane.fxml"));

        try {
            parentNode.getChildren().setAll((Node)loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        MusicalTermsTutorController musicalTermsTabController= loader.getController();
        musicalTermsTabController.create(env);
        musicalTermsTabController.setTabID("musicalTermTutor");


    }


    private void openScaleTutor() {


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/ScaleRecognitionPane.fxml"));
            try{
                Node tutor = loader.load();

                parentNode.getChildren().setAll(tutor);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ScaleRecognitionTutorController scaleRecognitionTutorController = loader.getController();
            scaleRecognitionTutorController.create(env);
            scaleRecognitionTutorController.setTabID("scaleTutor");


    }

    /**
     * opens the chord tutor when the chord tutor menu option is pressed If there is already an open
     * tutor of the same form then it sets focus to the already open tutor
     */
    @FXML
    private void openChordTutor() {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/ChordRecognitionPane.fxml"));

        try {
            parentNode.getChildren().setAll((Node)loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ChordRecognitionTutorController chordRecognitionTabController = loader.getController();
        chordRecognitionTabController.create(env);
        chordRecognitionTabController.setTabID("chordTutor");


    }

    /**
     * Opens the diatonic chord tutor when the diatonic chord tutor menu option is pressed. If there
     * is already an open tutor of the same form then it sets focus to the already open tutor.
     */
    @FXML
    private void openDiatonicChordTutor() {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/DiatonicChordPane.fxml"));

        try {
            parentNode.getChildren().setAll((Node)loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        DiatonicChordsTutorController diatonicChordsController = loader.getController();
        diatonicChordsController.create(env);
        diatonicChordsController.setTabID("diatonicChordTutor");


    }

    /**
     * opens the keySignatures tutor when the key signatures tutor menu option is pressed If there
     * is already an open tutor of the same form then it sets focus to the already open tutor
     */
    @FXML
    private void openKeySignatureTutor() {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/KeySignaturesPane.fxml"));

        try {
            parentNode.getChildren().setAll((Node) loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        KeySignaturesTutorController keySignaturesTabController= loader.getController();
        keySignaturesTabController.create(env);
        keySignaturesTabController.setTabID("keySignatureTutor");


    }

    /**
     * opens the scale modes tutor when the scale modes tutor menu option is pressed If there is
     * already an open tutor of the same form then it sets focus to the already open tutor
     */
    @FXML
    private void openScaleModesTutor() {


//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/Views/ScaleModesPane.fxml"));
//
//            try {
//                ScaleTab.setContent((Node) loader.load());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            TabPane.getTabs().add(ScaleTab);
//            TabPane.getSelectionModel().select(ScaleTab);
//             = loader.getController();
//            saleModesController.create(env);
//            scaleModesController.setTabID("scaleModesTutor");


    }

    /**
     * Opens the chord spelling tutor. If this tutor is already open, focus is transferred to it.
     */
    @FXML
    private void openSpellingTutor() {



            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/ChordSpellingPane.fxml"));

            try {
                parentNode.getChildren().setAll((Node) loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }

            ChordSpellingTutorController chordSpellingTutorController = loader.getController();
            chordSpellingTutorController.create(env);
            chordSpellingTutorController.setTabID("chordSpellingTutor");

    }
}
