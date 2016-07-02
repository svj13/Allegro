package seng302.gui;


import java.util.ArrayList;
import java.util.Collection;
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
import seng302.data.Note;
import seng302.utility.TutorRecord;
import seng302.utility.musicNotation.ChordUtil;


/**
 * Created by Elliot on 16/05/16 Controller class for the chord recognition tutor.
 */
public class ChordRecognitionTutorController extends TutorController {
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
        playChords.getItems().addAll("Unison", "Arpeggio", "Both");
        playChords.getSelectionModel().selectFirst();
        octaves.getItems().addAll(1, 2, 3, 4);
        octaves.getSelectionModel().selectFirst();

    }

    /**
     * Prepares a new question
     *
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
//        return generateQuestionPane(randNote, chordType);
        return generateQuestionPane(new Pair<Note, String>(randNote, chordType));
    }

    /**
     * Generates a note in the octave of middle C
     *
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
     *
     * @param noteAndChord pair containing first note and type of scale to play
     */
    public HBox generateQuestionPane(Pair noteAndChord) {
        final Pair<Note, String> noteAndChordType = noteAndChord;
        final HBox questionRow = new HBox();
        formatQuestionRow(questionRow);
        final Label correctAnswer = correctAnswer(noteAndChordType.getValue());
        final Note startNote = noteAndChordType.getKey();
//        System.out.println("startNote" + startNote);
        final String chordType = noteAndChordType.getValue();

        final Collection<Note> theChord = new ArrayList<Note>(ChordUtil.getChord(startNote, chordType));
//        final Pair<Collection<Note>, String> answer = new Pair<Collection<Note>, String>(theChord, chordType);
//        final ChordUtil myChord;
//        System.out.println("theChord?: " + theChord);

        Button play = new Button();
        stylePlayButton(play);

        play.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //Play the scale
                int currentTempo = env.getPlayer().getTempo();
                if (playChords.getValue().equals("Unison")) {
                    env.getPlayer().playSimultaneousNotes(theChord);
                } else if (playChords.getValue().equals("Arpeggio")) {
                    env.getPlayer().playNotes((ArrayList) theChord);
                } else {
                    env.getPlayer().playNotes((ArrayList) theChord);
                    try {
                        //Calculates how long three crotchets is at the current tempo
                        int wait = 1000 * 180 / currentTempo + 50;
                        Thread.sleep(wait);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    env.getPlayer().playSimultaneousNotes(theChord);
                }
            }
        });


        final ComboBox<String> options = generateChoices();
        options.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
//                System.out.println("Options.getVal: " + options.getValue());
                handleQuestionAnswer(options.getValue().toLowerCase(), noteAndChordType, questionRow);
            }
        });


        Button skip = new Button("Skip");
        styleSkipButton(skip);

        skip.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Disables only input buttons
                disableButtons(questionRow, 1, 3);
                formatSkippedQuestion(questionRow);
                manager.questions -= 1;
                manager.add(noteAndChordType, 2);
                String[] question = new String[]{
                        String.format("%s scale from %s", chordType, startNote.getNote()),
                        chordType
                };
                projectHandler.saveTutorRecords("chord", record.addSkippedQuestion(question));
                env.getRootController().setTabTitle("chordTutor", true);
                if (manager.answered == manager.questions) {
                    finished();
                }
            }
        });

        questionRow.getChildren().add(0, play);
        questionRow.getChildren().add(1, options);
        questionRow.getChildren().add(2, skip);
        questionRow.getChildren().add(3, correctAnswer);

        questionRow.prefWidthProperty().bind(paneQuestions.prefWidthProperty());
        return questionRow;
    }

    /**
     * Creates a JavaFX combo box containing the lexical names of all scales.
     *
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
     *
     * @param userAnswer    The user's selection, as text
     * @param correctAnswer The correct chord, as text
     * @param questionRow   The HBox containing GUI question data
     */
    public void handleQuestionAnswer(String userAnswer, Pair correctAnswer, HBox questionRow) {
        manager.answered += 1;
        boolean correct;
        disableButtons(questionRow, 1, 3);
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
                String.format("%s chord from %s",
                        correctAnswer.getValue(),
                        startingNote.getNote()),
                userAnswer,
                Boolean.toString(correct)
        };
        projectHandler.saveTutorRecords("chord", record.addQuestionAnswer(question));
        env.getRootController().setTabTitle("chordTutor", true);

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

    /**
     * This function is run once a tutoring session has been completed.
     */
    public void finished() {
        env.getPlayer().stop();
        userScore = getScore(manager.correct, manager.answered);
        outputText = String.format("You have finished the tutor.\n" +
                        "You answered %d questions, and skipped %d questions.\n" +
                        "You answered %d questions correctly, %d questions incorrectly.\n" +
                        "This gives a score of %.2f percent.",
                manager.questions, manager.skipped,
                manager.correct, manager.incorrect, userScore);
        if (projectHandler.currentProjectPath != null) {
            projectHandler.saveSessionStat("chord", record.setStats(manager.correct, manager.getTempIncorrectResponses().size(), userScore));
            projectHandler.saveCurrentProject();
            outputText += "\nSession auto saved.";
        }
        env.getRootController().setTabTitle("chordTutor", false);
        // Sets the finished view
        resultsContent.setText(outputText);

        paneQuestions.setVisible(false);
        paneResults.setVisible(true);
        questionRows.getChildren().clear();

        Button retestBtn = new Button("Retest");
        Button clearBtn = new Button("Clear");
        Button saveBtn = new Button("Save");

        clearBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                manager.saveTempIncorrect();
                paneResults.setVisible(false);
                paneQuestions.setVisible(true);
            }
        });

        paneResults.setPadding(new Insets(10, 10, 10, 10));
        retestBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                paneResults.setVisible(false);
                paneQuestions.setVisible(true);
                retest();

            }
        });
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                saveRecord();
            }
        });

        if (manager.getTempIncorrectResponses().size() > 0) {
            //Can re-test
            buttons.getChildren().setAll(retestBtn, clearBtn, saveBtn);
        } else {
            //Perfect score
            buttons.getChildren().setAll(clearBtn, saveBtn);
        }

        buttons.setMargin(retestBtn, new Insets(10, 10, 10, 10));
        buttons.setMargin(clearBtn, new Insets(10, 10, 10, 10));
        buttons.setMargin(saveBtn, new Insets(10, 10, 10, 10));
        // Clear the current session
        manager.resetStats();
    }
}
