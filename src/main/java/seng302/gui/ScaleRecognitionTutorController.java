package seng302.gui;

import org.controlsfx.control.CheckComboBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;
import seng302.Environment;
import seng302.data.Note;
import seng302.utility.TutorRecord;

/**
 * Controller class for the scale recognition tutor.
 */
public class ScaleRecognitionTutorController extends TutorController {
    @FXML
    HBox settings;

    @FXML
    AnchorPane scaleTutorAnchor;

    @FXML
    Button btnGo;

    @FXML
    ComboBox<String> direction;

    @FXML
    CheckComboBox<String> ccbScales;

    @FXML
    ComboBox<Integer> octaves;

    @FXML
    Text scaleError;

    private Random rand;


    String playDirection;
    int playOctaves;
    ObservableList<String> playScaleType;

    @FXML
    /**
     * Run when the go button is pressed. Creates a new tutoring session.
     */
    private void goAction(ActionEvent event) {
        if (ccbScales.getCheckModel().getCheckedIndices().size() != 0) {
            scaleError.setVisible(false);
            record = new TutorRecord();
            paneQuestions.setVisible(true);
            paneResults.setVisible(false);
            manager.resetEverything();
            manager.questions = selectedQuestions;

            this.playDirection = direction.getSelectionModel().getSelectedItem();
            this.playOctaves = octaves.getSelectionModel().getSelectedItem();
            this.playScaleType = ccbScales.getCheckModel().getCheckedItems();

            questionRows.getChildren().clear();
            for (int i = 0; i < manager.questions; i++) {
                HBox questionRow = setUpQuestion();
                questionRows.getChildren().add(questionRow);
                VBox.setMargin(questionRow, new Insets(10, 10, 10, 10));
            }
        } else {
            scaleError.setVisible(true);
        }
    }


    /**
     * Initialises certain GUI elements
     */
    public void create(Environment env) {
        super.create(env);
        initialiseQuestionSelector();
        rand = new Random();
        direction.getItems().addAll("Up", "Down", "UpDown");
        ccbScales.getItems().addAll("Major", "Minor", "Melodic Minor", "Major Pentatonic", "Minor Pentatonic");

        ccbScales.getCheckModel().check(0); //Only major/minor selected by default
        ccbScales.getCheckModel().check(1);
        direction.getSelectionModel().selectFirst();
        octaves.getItems().addAll(1, 2, 3, 4);
        octaves.getSelectionModel().selectFirst();

    }

    /**
     * Prepares a new question
     *
     * @return a question pane containing the question information
     */
    public HBox setUpQuestion() {
        int type = rand.nextInt(playScaleType.size());
        String scaleType = playScaleType.get(type).toLowerCase();
        return generateQuestionPane(new Pair<>(Note.getRandomNote(), scaleType.toLowerCase()));
    }


    /**
     * Given a type of scale (major or minor) and a starting note, returns a list of notes of scale
     *
     * @param startNote The first note in the scale
     * @param scaleType Either major or minor
     * @return Arraylist of notes in a scale
     */
    public ArrayList<Note> getScale(Note startNote, String scaleType) {
        // Add # octaves and up/down selection here.
        ArrayList<Note> scale;
        if (playDirection.equals("Up")) {
            scale = startNote.getOctaveScale(scaleType, playOctaves, true);
        } else if (playDirection.equals("UpDown")) {
            scale = startNote.getOctaveScale(scaleType, playOctaves, true);
            ArrayList<Note> notes = new ArrayList<>(scale);
            Collections.reverse(notes);
            scale.addAll(notes);
        } else {
            scale = startNote.getOctaveScale(scaleType, playOctaves, false);
        }

        return scale;
    }

    /**
     * Reacts accordingly to a user's input
     *
     * @param userAnswer    The user's selection, as text
     * @param correctAnswer A pair containing the starting note and scale type
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
                String.format("%s scale from %s",
                        correctAnswer.getValue(),
                        startingNote.getNote()),
                userAnswer,
                Boolean.toString(correct)
        };
        projectHandler.saveTutorRecords("scale", record.addQuestionAnswer(question));
        env.getRootController().setTabTitle("scaleTutor", true);

        if (manager.answered == manager.questions) {
            finished();
        }
    }

    /**
     * Creates a GUI pane for a single question
     *
     * @param noteAndScale pair containing first note and type of scale to play
     */
    public HBox generateQuestionPane(Pair noteAndScale) {
        final Pair<Note, String> noteAndScaleType = noteAndScale;
        final HBox questionRow = new HBox();
        formatQuestionRow(questionRow);
        final Label correctAnswer = correctAnswer(noteAndScaleType.getValue());

        final Note startNote = noteAndScaleType.getKey();
        final String scaleType = noteAndScaleType.getValue();

        Button play = new Button();
        stylePlayButton(play);

        play.setOnAction(event -> {
            //Play the scale
            env.getPlayer().playNotes(getScale(startNote, scaleType));
        });

        final ComboBox<String> options = generateChoices();
        options.setOnAction(event ->
                handleQuestionAnswer(options.getValue().toLowerCase(), noteAndScaleType, questionRow)
        );


        Button skip = new Button("Skip");
        styleSkipButton(skip);

        skip.setOnAction(event -> {
            // Disables only input buttons
            disableButtons(questionRow, 1, 3);
            formatSkippedQuestion(questionRow);
            manager.questions -= 1;
            manager.add(noteAndScaleType, 2);
            String[] question = new String[]{
                    String.format("%s scale from %s", scaleType, startNote.getNote()),
                    scaleType
            };
            projectHandler.saveTutorRecords("scale", record.addSkippedQuestion(question));
            env.getRootController().setTabTitle("scaleTutor", true);
            if (manager.answered == manager.questions) {
                finished();
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
        ComboBox<String> options = new ComboBox<>();
        options.setPrefHeight(30);
        options.getItems().addAll(playScaleType);
        return options;
    }

    /**
     * Returns the option combo boxes to their default states.
     */
    public void resetInputs() {
        direction.getSelectionModel().selectFirst();
        octaves.getSelectionModel().selectFirst();
        ccbScales.getCheckModel().clearChecks();
        ccbScales.getCheckModel().check(0);
        ccbScales.getCheckModel().check(1);
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
            projectHandler.saveSessionStat("scale", record.setStats(manager.correct, manager.getTempIncorrectResponses().size(), userScore));
            projectHandler.saveCurrentProject();
            outputText += "\nSession auto saved";
        }
        env.getRootController().setTabTitle("scaleTutor", false);
        // Sets the finished view
        resultsContent.setText(outputText);

        paneQuestions.setVisible(false);
        paneResults.setVisible(true);
        questionRows.getChildren().clear();

        Button retestBtn = new Button("Retest");
        Button clearBtn = new Button("Clear");
        final Button saveBtn = new Button("Save");

        clearBtn.setOnAction(event -> {
            manager.saveTempIncorrect();
            paneResults.setVisible(false);
            paneQuestions.setVisible(true);
        });
        paneResults.setPadding(new Insets(10, 10, 10, 10));
        retestBtn.setOnAction(event -> {
            paneResults.setVisible(false);
            paneQuestions.setVisible(true);
            retest();
        });
        saveBtn.setOnAction(event -> saveRecord());

        if (manager.getTempIncorrectResponses().size() > 0) {
            //Can re-test
            buttons.getChildren().setAll(retestBtn, clearBtn, saveBtn);
        } else {
            //Perfect score
            buttons.getChildren().setAll(clearBtn, saveBtn);
        }

        HBox.setMargin(retestBtn, new Insets(10, 10, 10, 10));
        HBox.setMargin(clearBtn, new Insets(10, 10, 10, 10));
        HBox.setMargin(saveBtn, new Insets(10, 10, 10, 10));
        // Clear the current session
        manager.resetStats();
    }

}
