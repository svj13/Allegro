package seng302.gui;


import org.controlsfx.control.RangeSlider;
import org.controlsfx.control.spreadsheet.StringConverterWithFormat;

import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import seng302.Environment;
import seng302.data.Interval;
import seng302.data.KeySignature;
import seng302.data.Note;
import seng302.data.Term;
import seng302.utility.TutorRecord;

public class KeySignaturesTutorController extends TutorController {

    @FXML
    AnchorPane KeySignaturesTab;

    @FXML
    Button btnGo;

    @FXML
    ComboBox<String> scaleBox;

    @FXML
    ComboBox<String> formBox;

    @FXML
    ComboBox<String> answerBox;

    private Random rand;

    private ArrayList<String> majorSharps = new ArrayList<String>(Arrays.asList("C","G","D","A","E","B","F#","C#"));
    private ArrayList<String> majorFlats = new ArrayList<String>(Arrays.asList("Cb","Gb","Db","Ab","Eb","Bb","F","C"));
    private ArrayList<String> minorSharps = new ArrayList<String>(Arrays.asList("A","E","B","F#","C#","G#","D#","A#"));
    private ArrayList<String> minorFlats = new ArrayList<String>(Arrays.asList("Ab","Eb","Bb","F","C","G","D","A"));



    @FXML
    /**
     * Run when the go button is pressed. Creates a new tutoring session.
     */
    private void goAction(ActionEvent event) {
        record = new TutorRecord();
        paneQuestions.setVisible(true);
        paneResults.setVisible(false);
        manager.resetEverything();
        manager.questions = selectedQuestions;

        questionRows.getChildren().clear();
        for (int i = 0; i < manager.questions; i++) {
            HBox questionRow = setUpQuestion();
            questionRows.getChildren().add(questionRow);
            questionRows.setMargin(questionRow, new Insets(10, 10, 10, 10));
        }
    }


    /**
     * Initialises certain GUI elements
     */
    public void create(Environment env) {
        super.create(env);
        initialiseQuestionSelector();
        rand = new Random();
        scaleBox.getItems().addAll("Major", "Minor", "Both");
        scaleBox.getSelectionModel().selectFirst();
        formBox.getItems().addAll("Number of sharps/flats", "Listing sharps/flats");
        formBox.getSelectionModel().selectFirst();
        answerBox.getItems().addAll("Show Key Signature", "Show Name");
        answerBox.getSelectionModel().selectFirst();
    }

    /**
     * Prepares a new question
     *
     * @return a question pane containing the question information
     */
    public HBox setUpQuestion() {
        String scaletype;
        int questionType;


        //figure out the scale is wanted to be tested
        if(scaleBox.getValue().equals("Major")){
            scaletype = "major";
        }else if(scaleBox.getValue().equals("Minor")){
            scaletype = "minor";
        }else{
            scaletype = "both";
        }

        //figure out the type of question wanted
        if(formBox.getValue().equals("Show Key Signature")){
            questionType = 0;
        }else{
            questionType = 1;
        }





        return generateQuestionPane(new Pair<String, Integer>(scaletype, questionType));
    }



    /**
     * Reacts accordingly to a user's input
     *
     * @param userAnswer    The user's selection, as text
     * @param correctAnswer A pair containing the starting note and scale type
     * @param questionRow   The HBox containing GUI question data
     */
    public void handleQuestionAnswer(String userAnswer, Pair correctAnswer, HBox questionRow) {


    }

    /**
     * Creates a GUI pane for a single question
     *y
     */
    //@Override
    public HBox generateQuestionPane(Pair pair) {
        final HBox questionRow = new HBox();
        formatQuestionRow(questionRow);
        final ComboBox<String> options;
        Random rand = new Random();

        Button skip = new Button("Skip");
        styleSkipButton(skip);
        Label questionText = new Label();
        List<String> keysAsArray;
        String question;

        if(pair.getKey().equals("major")) {
            keysAsArray = new ArrayList<String>(KeySignature.getMajorKeySignatures().keySet());
            questionText.setText(" Major");
            question = keysAsArray.get(rand.nextInt(keysAsArray.size()));
            options = generateMajorChoices(question,true);

        }else if(pair.getKey().equals("minor")) {
            keysAsArray = new ArrayList<String>(KeySignature.getMinorKeySignatures().keySet());
            question = keysAsArray.get(rand.nextInt(keysAsArray.size()));
            questionText.setText(" Minor");
            options = generateMinorChoices(question,true);

        }else{

            /// random generation from both
            keysAsArray = new ArrayList<String>(KeySignature.getMinorKeySignatures().keySet());
            question = keysAsArray.get(rand.nextInt(keysAsArray.size()));
            options = generateMajorChoices(question,true);
        }

        questionText.setText(question.concat(questionText.getText()));
        options.setPrefHeight(30);



        skip.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

            }
        });

        options.setOnAction(new EventHandler<ActionEvent>() {
            // This handler colors the GUI depending on the user's input
            public void handle(ActionEvent event) {

            }
        });

        questionRow.getChildren().add(0,questionText);
        questionRow.getChildren().add(1, options);
        questionRow.getChildren().add(2, skip);

        questionRow.prefWidthProperty().bind(paneQuestions.prefWidthProperty());
        return questionRow;
    }



    private ComboBox<String> generateMajorChoices(String scale, Boolean keysignature) {
        ComboBox<String> options = new ComboBox<String>();
        options.setPrefHeight(30);

        ArrayList<List> optionsList = new ArrayList<List>();
        if((KeySignature.getMajorKeySignatures().get(scale)).getNotes().get(0).contains("#")){

            for(String keySig : majorSharps){
                optionsList.add((KeySignature.getMajorKeySignatures().get(keySig)).getNotes());
            }
        }else{
            for(String keySig : majorFlats){
                optionsList.add((KeySignature.getMajorKeySignatures().get(keySig)).getNotes());
            }
        }

        Collections.shuffle(optionsList);
        for(List option:optionsList){
            options.getItems().add(option.toString());
        }

        return options;
    }


    private ComboBox<String> generateMinorChoices(String scale, Boolean keysignature) {
        ComboBox<String> options = new ComboBox<String>();
        options.setPrefHeight(30);

        ArrayList<List> optionsList = new ArrayList<List>();
        if((KeySignature.getMinorKeySignatures().get(scale)).getNotes().get(0).contains("#")){

            for(String keySig : minorSharps){
                optionsList.add((KeySignature.getMinorKeySignatures().get(keySig)).getNotes());
            }
        }else{
            for(String keySig : minorFlats){
                optionsList.add((KeySignature.getMinorKeySignatures().get(keySig)).getNotes());
            }
        }

        Collections.shuffle(optionsList);
        for(List option:optionsList){
            options.getItems().add(option.toString());
        }

        return options;
    }

    private void generateScaleOptions(){


    }

    private void generateKeySignatureOptions(){
//        if(startNote.contains("#")){
//
//        }

    }

    /**
     * Returns the option combo boxes to their default states.
     */
    public void resetInputs() {
        scaleBox.getSelectionModel().selectFirst();
        formBox.getSelectionModel().selectFirst();
    }


}
