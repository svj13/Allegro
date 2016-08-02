package seng302.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import seng302.command.Scale;
import seng302.data.Note;
import seng302.utility.TutorRecord;
import seng302.utility.musicNotation.ChordUtil;

/**
 * Created by isabelle on 30/07/16.
 */
public class DiatonicChordsTutorController extends TutorController {
    private Random rand;
    private final String typeOneText = "What is the %s chord of %s major?";
    private final String typeTwoText = "In %s major, what is %s?";
    Integer type = 1;


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
            HBox questionRow;
            if (rand.nextBoolean()) {
                type = 1;
                questionRow = generateQuestionPane(generateQuestionTypeOne());
            } else {
                type = 2;
                questionRow = generateQuestionPane(generateQuestionTypeTwo());
            }
            questionRows.getChildren().add(questionRow);
            questionRows.setMargin(questionRow, new Insets(10, 10, 10, 10));
        }


    }

    private Pair generateQuestionTypeOne() {
        Integer function = rand.nextInt(7) + 1;
        String functionInRomanNumeral = ChordUtil.integerToRomanNumeral(function);
        Note randomNote = Note.getRandomNote();
        String randomNoteName = randomiseNoteName(randomNote);
        Pair question = new Pair(functionInRomanNumeral, randomNoteName);
        return question;
    }

    private Pair generateQuestionTypeTwo() {
        Note randomNote = Note.getRandomNote();
        String randomNoteName = randomiseNoteName(randomNote);
        // Functional questions:
        ArrayList<Note> scale = randomNote.getOctaveScale("major", 1, true);
        Integer numInScale = rand.nextInt(7) + 1;
        List<String> scaleNames = Scale.scaleNameList(randomNoteName, scale, true);
        String chordNote = scaleNames.get(numInScale);

        // About a quarter of questions will be non-functional.
        Integer funcOrNon = rand.nextInt(4);
        String chordType;
        if (funcOrNon < 3) {
            chordType = ChordUtil.getDiatonicChordQuality(ChordUtil.integerToRomanNumeral(numInScale));
        } else {
            // Non Functional questions:
            Integer randomFunction = rand.nextInt(7) + 1;
            chordType = ChordUtil.getDiatonicChordQuality(ChordUtil.integerToRomanNumeral(randomFunction));
        }
        Pair<String, String> chord = new Pair(chordNote, chordType);
        return new Pair(randomNoteName, chord);
    }


    @Override
    HBox generateQuestionPane(Pair data) {
        String scaleType = "major"; // Diatonics only accept major scales at this point.
        final HBox questionRow = new HBox();
        Label question;
        String answer;
        if (type == 1) {
            question = new Label(String.format(typeOneText, data.getKey(), data.getValue()));
            answer = ChordUtil.getChordFunction((String) data.getKey(), (String) data.getValue(), scaleType);
        } else {
            Pair chord = (Pair) data.getValue();
            question = new Label(String.format(typeTwoText, data.getKey(), chord.getKey() + " " + chord.getValue()));
            answer = ChordUtil.getFunctionOf((String) data.getKey(), (String) chord.getKey(), (String) chord.getValue());
        }
        formatQuestionRow(questionRow);

        final Label correctAnswer = correctAnswer(answer);
        final ComboBox<String> options = generateChoices(data, answer);
        options.setOnAction(event ->
                handleQuestionAnswer(options.getValue().toLowerCase(), answer, questionRow)
        );

        Button skip = new Button("Skip");
        styleSkipButton(skip);

        questionRow.getChildren().add(0, question);
        questionRow.getChildren().add(1, options);
        questionRow.getChildren().add(2, skip);
        questionRow.getChildren().add(3, correctAnswer);

        questionRow.prefWidthProperty().bind(paneQuestions.prefWidthProperty());
        return questionRow;
    }

    /**
     * For type 2 questions: the answer options are the roman numerals from 1-7 and
     * 'Non-functional'. For type 1 questions: the answer options are 7 random choices that have the
     * correct letter + the correct answer.
     *
     * @return a combo box of scale options
     */
    private ComboBox<String> generateChoices(Pair functionAndData, String answer) {
        ComboBox<String> options = new ComboBox<>();
        options.setPrefHeight(30);

        if (type == 2) {
            for (int i = 1; i < 8; i++) {
                options.getItems().add(ChordUtil.integerToRomanNumeral(i));
            }
            options.getItems().add("Non Functional");
        } else {
            Integer numInScale = ChordUtil.romanNumeralToInteger((String) functionAndData.getKey());
            String noteName = (String) functionAndData.getValue();

            //Get the letter numInScale above the scale note
            char letter = ChordUtil.lettersUp(noteName, numInScale - 1);
            List<String> allOptions = new ArrayList<>();
            allOptions.add(letter + " major 7th");
            allOptions.add(letter + " minor 7th");
            allOptions.add(letter + " 7th");
            allOptions.add(letter + " half-diminished 7th");
            allOptions.add(letter + "b major 7th");
            allOptions.add(letter + "b minor 7th");
            allOptions.add(letter + "b 7th");
            allOptions.add(letter + "b half-diminished 7th");
            allOptions.add(letter + "# major 7th");
            allOptions.add(letter + "# minor 7th");
            allOptions.add(letter + "# 7th");
            allOptions.add(letter + "# half-diminished 7th");
            Collections.shuffle(allOptions);
            // We only want 8 of all the options.
            List<String> eight = allOptions.subList(0, 8);

            // Ensure the correct answer is included.
            if (!eight.contains(answer)) {
                eight.remove(rand.nextInt(7));
                eight.add(rand.nextInt(7), answer);
            }

            options.getItems().addAll(eight);
        }


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
