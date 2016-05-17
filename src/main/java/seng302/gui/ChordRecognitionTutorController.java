package seng302.gui;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import seng302.Environment;
import seng302.command.Chord;
import seng302.data.Note;
import seng302.utility.TutorRecord;


import java.util.Collections;

/**
 * Created by Elliot on 16/05/16
 * Controller class for the chord recognition tutor.
 */
public class ChordRecognitionTutorController extends TutorController{
    @FXML
    HBox settings;

    @FXML
    AnchorPane chordTutorAnchor;

    @FXML
    Button btnGo;

    @FXML
    ComboBox<String> playChords;

    @FXML
    ComboBox<Integer> octaves;

    private Random rand;

    @FXML
    /**
     * Run when the go button is pressed. Creates a new tutoring session.
     */
    private void goAction(ActionEvent event) {
        record = new TutorRecord(new Date(), "Chord Recognition");
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
     * @param env
     */
    public void create(Environment env) {
        super.create(env);
        initialiseQuestionSelector();
        rand = new Random();
        playChords.getItems().addAll("Unison", "Arpeggio", "Both");
        playChords.getSelectionModel().selectFirst();
        octaves.getItems().addAll(1,2,3,4);
        octaves.getSelectionModel().selectFirst();

    }

    /**
     * Prepares a new question
     * @return a question pane containing the question information
     */
    public HBox setUpQuestion() {
        int type = rand.nextInt(2);
        String chordType;
        if (type == 0) {
            chordType = "major";
        } else {
            chordType = "minor";
        }
        Note randNote = getRandomNote();
        return generateQuestionPane(randNote, chordType);
    }

    /**
     * Generates a note in the octave of middle C
     * @return the random note
     */
    public Note getRandomNote() {
        return Note.lookup(Integer.toString(rand.nextInt(11) + 60));
    }

    /**
     * Given a type of scale (major or minor) and a starting note, returns a list of notes of scale
     * @param startNote The first note in the scale
     * @param scaleType Either major or minor
     * @return Arraylist of notes in a scale
     */
//    public ArrayList<Note> getScale(Note startNote, String scaleType) {
//        // Add # octaves and up/down selection here.
//        ArrayList<Note> scale;
//        if (playChords.getValue().equals("Up")) {
//            scale = startNote.getOctaveScale(scaleType, octaves.getValue(), true);
//        } else if (playChords.getValue().equals("UpDown")) {
//            scale = startNote.getOctaveScale(scaleType, octaves.getValue(), true);
//            ArrayList<Note> notes = new ArrayList<Note>(scale);
//            Collections.reverse(notes);
//            scale.addAll(notes);
//        } else {
//            scale = startNote.getOctaveScale(scaleType, octaves.getValue(), false);
//        }
//
//        return scale;
//    }


    /**
     * Creates a GUI pane for a single question
     * @param startNote the first note in the chord
     * @param chordType the type of chord, being major or minor
     * @return
     */
    public HBox generateQuestionPane(Note startNote, final String chordType) {
        final HBox questionRow = new HBox();
        formatQuestionRow(questionRow);
        final Label correctAnswer = correctAnswer(chordType);

        final Collection<Note> theChord = new ArrayList<Note>(startNote.getChord(chordType));
        final Chord myChord;
//        System.out.println("theChord?: " + theChord);

        Button play = new Button();
        stylePlayButton(play);

        play.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //Play the scale
                env.getPlayer().playSimultaneousNotes(theChord);
            }
        });

        final ComboBox<String> options = generateChoices();
//        final Pair<Chord, String> answer = (theChord, chordType);
        options.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("Options.getVal: " + options.getValue());
                handleQuestionAnswer(options.getValue(), new Pair<Collection<Note>, String>(theChord, chordType), questionRow);
            }
        });


        Button skip = new Button("Skip");
        styleSkipButton(skip);

//        skip.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent event) {
//                // Disables only input buttons
//                disableButtons(questionRow, 1, 3);
//                formatSkippedQuestion(questionRow);
//                manager.questions -= 1;
//                manager.add(noteAndScaleType, 2);
//                String[] question = new String[]{
//                        String.format("%s scale from %s",scaleType, startNote.getNote()),
//                        scaleType
//                };
//                record.addSkippedQuestion(question);
//                if (manager.answered == manager.questions) {
//                    finished();
//                }
//            }
//        });

        questionRow.getChildren().add(0, play);
        questionRow.getChildren().add(1, options);
        questionRow.getChildren().add(2, skip);
        questionRow.getChildren().add(3, correctAnswer);

        questionRow.prefWidthProperty().bind(paneQuestions.prefWidthProperty());
        return questionRow;
    }

    /**
     * Creates a JavaFX combo box containing the lexical names of all scales.
     * @return a combo box of scale options
     */
    private ComboBox<String> generateChoices() {
        ComboBox<String> options = new ComboBox<String>();
        options.setPrefHeight(30);
        options.getItems().add("major");
        options.getItems().add("minor");
        return options;
    }

    /**
     * Reacts accordingly to a user's input
     * @param userAnswer The user's selection, as text
     * @param correctAnswer The correct chord, as text
     * @param questionRow The HBox containing GUI question data
     */
    public void handleQuestionAnswer(String userAnswer, Pair<Collection<Note>, String> correctAnswer, HBox questionRow) {
        manager.answered += 1;
        boolean correct;
        disableButtons(questionRow, 1, 3);
//        System.out.println("User answer and 'correctAnswer': " + userAnswer + " and " + correctAnswer.getValue());
        if (userAnswer.equals(correctAnswer.getValue())) {
            correct = true;
            manager.add(correctAnswer, 1);
            formatCorrectQuestion(questionRow);
        } else {
            correct = false;
            manager.add(correctAnswer, 0);
            formatIncorrectQuestion(questionRow);
            //Shows the correct answer
            questionRow.getChildren().get(3).setVisible(true);
        }
        Note startingNote = (Note) correctAnswer.getKey();
        String[] question = new String[]{
                String.format("%s scale from %s",
                        correctAnswer.getValue(),
                        startingNote.getNote()),
                userAnswer,
                Boolean.toString(correct)
        };
        record.addQuestionAnswer(question);

        if (manager.answered == manager.questions) {
            finished();
        }
    }

    /**
     * Returns the option combo boxes to their default states.
     */
    public void resetInputs() {
        playChords.getSelectionModel().selectFirst();
        octaves.getSelectionModel().selectFirst();
    }


}
