package seng302.gui;

//import com.sun.xml.internal.bind.v2.TODO;
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

    @FXML
    /**
     * Run when the go button is pressed. Creates a new tutoring session.
     */
    private void goAction(ActionEvent event) {
        record = new TutorRecord(new Date(), "Scale Recognition");
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
        int answerForm;
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

//        //figure out the type of answer wanted
//        if(answerBox.getValue().equals("Number of sharps/flats")){
//            answerForm = 0;
//        }else{
//            answerForm = 1;
//        }




        return generateQuestionPane(new Pair<String, String>("", ""));
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


        Button skip = new Button("Skip");
        styleSkipButton(skip);

        final ComboBox<String> options = generateChoices("specific scale",true);
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

        questionRow.getChildren().add(0, options);
        questionRow.getChildren().add(1, skip);

        questionRow.prefWidthProperty().bind(paneQuestions.prefWidthProperty());
        return questionRow;
    }


    /**
     * Creates a JavaFX combo box containing the lexical names of all scales.
     *
     * @return a combo box of scale options
     */
    private ComboBox<String> generateChoices(String scale, Boolean keysignature) {
        ComboBox<String> options = new ComboBox<String>();
        options.setPrefHeight(30);



        //TODO add randomly generated choices
        //if keysignature = true then generate options with key signatures
        //else display names
        return options;
    }

    /**
     * Returns the option combo boxes to their default states.
     */
    public void resetInputs() {
        scaleBox.getSelectionModel().selectFirst();
        formBox.getSelectionModel().selectFirst();
    }


}
