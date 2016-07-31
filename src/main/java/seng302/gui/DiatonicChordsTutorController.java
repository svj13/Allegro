package seng302.gui;

import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import seng302.Environment;
import seng302.data.Note;
import seng302.utility.TutorRecord;
import seng302.utility.musicNotation.ChordUtil;

/**
 * Created by isabelle on 30/07/16.
 */
public class DiatonicChordsTutorController extends TutorController {
    private Random rand;
    private final String typeOneText = "What is the %s chord of %s?";
    private final String typeTwoText = "In %s, what is %s?";


    public void create(Environment env) {
        super.create(env);
        initialiseQuestionSelector();
        paneQuestions.setVisible(true);
        paneResults.setVisible(false);
        manager.resetEverything();
        manager.questions = selectedQuestions;
        rand = new Random();

        questionRows.getChildren().clear();
        for (int i = 0; i < manager.questions; i++) {
            HBox questionRow = generateQuestionPane(generateQuestionTypeOne());
            questionRows.getChildren().add(questionRow);
            questionRows.setMargin(questionRow, new Insets(10, 10, 10, 10));
        }


    }

    private Pair generateQuestionTypeOne() {
        Integer function = rand.nextInt(7) + 1;
        String functionInRomanNumeral = ChordUtil.integerToRomanNumeral(function);
        Note randomNote = Note.getRandomNote();
        Pair question = new Pair(functionInRomanNumeral, randomNote);
        return question;
    }



    @Override
    HBox generateQuestionPane(Pair data) {
        final Pair<String, Note> functionAndNote = data;
        String scaleType = "major"; // Diatonics only accept major scales at this point.
        String answer = ChordUtil.getChordFunction(functionAndNote.getKey(),
                functionAndNote.getValue().getNote(), scaleType);
        final HBox questionRow = new HBox();
        formatCorrectQuestion(questionRow);

        final Label correctAnswer = correctAnswer(answer);
        final ComboBox<String> options = generateChoices();
        options.setOnAction(event ->
                handleQuestionAnswer(options.getValue().toLowerCase(), answer, questionRow)
        );

        Button skip = new Button("Skip");
        styleSkipButton(skip);

        questionRow.getChildren().add(0, options);
        questionRow.getChildren().add(1, skip);
        questionRow.getChildren().add(1, correctAnswer);

        questionRow.prefWidthProperty().bind(paneQuestions.prefWidthProperty());
        return questionRow;
    }

    /**
     * Creates a JavaFX combo box containing the lexical names of all scales.
     *
     * @return a combo box of scale options
     */
    private ComboBox<String> generateChoices() {
        ComboBox<String> options = new ComboBox<>();
        options.setPrefHeight(30);

// Type 2 questions
//        for(int i = 1; i<8; i++){
//            options.getItems().add(ChordUtil.integerToRomanNumeral(i));
//        }
//        options.getItems().add("Non Functional");
        options.getItems().add("to do");


        return options;
    }

    /**
     * Reacts accordingly to a user's input
     *
     * @param userAnswer    The user's selection, as text
     * @param correctAnswer A pair containing the starting note and scale type
     * @param questionRow   The HBox containing GUI question data
     */
    public void handleQuestionAnswer(String userAnswer, String correctAnswer, HBox questionRow) {

    }

    @Override
    void resetInputs() {

    }

    @FXML
    /**
     * When the go button is pressed, a new tutoring session is launched
     */
    private void goAction(ActionEvent event) {
        record = new TutorRecord();

    }

}
