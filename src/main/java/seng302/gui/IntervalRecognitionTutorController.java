package seng302.gui;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.Environment;
import seng302.data.Interval;
import seng302.data.Note;
import seng302.utility.PitchComparisonTutorManager;

public class IntervalRecognitionTutorController {

    @FXML
    TextField txtNumIntervals;

    @FXML
    VBox questionRows;

    @FXML
    AnchorPane IntervalRecognitionTab;

    @FXML
    ScrollPane paneQuestions;

    @FXML
    Button btnGo;

    Environment env;

    public void create(Environment env) {
        this.env = env;
        manager = env.getIrtManager();
    }

    PitchComparisonTutorManager manager;

    @FXML
    void goAction(ActionEvent event) {
        manager.questions = Integer.parseInt(txtNumIntervals.getText());
        if (manager.questions >= 1){
            // Run the tutor
            questionRows.getChildren().clear();
            for (int i = 0; i < manager.questions; i++) {
                HBox questionRow = generateQuestionRow();
                questionRows.getChildren().add(questionRow);
                questionRows.setMargin(questionRow, new Insets(10, 10, 10, 10));
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Number of Intervals");
            alert.setContentText("Please select a positive number of intervals");
            alert.setResizable(false);
            alert.showAndWait();
        }

    }

    /**
     * Creates a JavaFX combo box containing the lexical names of all intervals.
     * @return a combo box of interval options
     */
    private ComboBox<String> generateChoices() {
        ComboBox<String> options = new ComboBox<String>();
        for (Interval interval:Interval.intervals) {
            options.getItems().add(interval.getName());
        }
        return options;
    }


    /**
     * Creates a GUI section for one question.
     * @return a JavaFX HBox containing controls and info about one question.
     */
    private HBox generateQuestionRow() {
        final HBox questionRow = new HBox();

        questionRow.setPadding(new Insets(10, 10, 10, 10));
        questionRow.setSpacing(10);
        questionRow.setStyle("-fx-background-color: #336699;");

        //Add buttons for play, skip, and cancel
        Button play = new Button("Play");
        Button skip = new Button("Skip");
        Button cancel = new Button("Cancel");
        final ComboBox<String> options = generateChoices();
        final Label correctAnswer = new Label();


        final Interval thisInterval = generateInterval();
        final Note firstNote = getStartingNote(thisInterval.getSemitones());
        final Note secondNote = getFinalNote(firstNote, thisInterval);
        final ArrayList<Note> playNotes = new ArrayList<Note>();
        playNotes.add(firstNote);
        playNotes.add(secondNote);

        play.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                env.getPlayer().playNotes(playNotes, 48);
            }
        });

        skip.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //Disables skip and cancel buttons, combo box also
                for (int i = 0; i < questionRow.getChildren().size(); i++) {
                    questionRow.getChildren().get(i).setDisable(true);
                }


                manager.questions -= 1;
                manager.add(firstNote.getNote(), secondNote.getNote(), false);
                if (manager.answered == manager.questions) {
                    finished();
                }
            }
        });

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                manager.questions -= 1;
                manager.add(firstNote.getNote(), secondNote.getNote(), false);
                if (manager.answered == manager.questions) {
                    finished();
                }
            }
        });

        options.setOnAction(new EventHandler<ActionEvent>() {
            // This handler colors the GUI depending on the user's input
            public void handle(ActionEvent event) {
                if (options.getValue().equals(thisInterval.getName())) {
                    questionRow.setStyle("-fx-background-color: green;");
                    manager.add(firstNote.getNote(), secondNote.getNote(), true);
                } else {
                    questionRow.setStyle("-fx-background-color: red;");
                    manager.add(firstNote.getNote(), secondNote.getNote(), false);
                }
                manager.answered += 1;

                // Shows the correct answer
                correctAnswer.setText(thisInterval.getName());
                if (manager.answered == manager.questions) {
                    finished();
                }
            }
        });

        questionRow.getChildren().add(play);
        questionRow.getChildren().add(skip);
        questionRow.getChildren().add(cancel);
        questionRow.getChildren().add(options);
        questionRow.getChildren().add(correctAnswer);

        questionRow.prefWidthProperty().bind(paneQuestions.prefWidthProperty());
        return questionRow;
    }

    /**
     * Randomly selects a note for the interval.
     * @param numSemitones The generated interval, so the second note is not outside correct range
     * @return A Note object, for playing an interval.
     */
    private Note getStartingNote(int numSemitones) {
        Random randNote = new Random();
        return Note.lookup(String.valueOf(randNote.nextInt(128 - numSemitones)));
    }

    /**
     * Calculates the second note of an interval based on the first.
     * @param startingNote The first note of an interval
     * @param interval The number of semitones in an interval
     * @return The second note of the interval
     */
    private Note getFinalNote(Note startingNote, Interval interval) {
        return startingNote.semitoneUp(interval.getSemitones());
    }

    /**
     * Randomly selects an interval from the approved list
     * @return the randomly selected interval
     */
    private Interval generateInterval() {
        Random rand = new Random();
        // There are 8 different intervals
        return Interval.intervals[rand.nextInt(8)];
    }

    private float getScore(int correct, int answered) {
        float score = 0;
        if (answered > 0) {
            score = (float) correct / (float) answered * 100;
        }
        return score;

    }

    private void finished() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Finished");
        float userScore = getScore(manager.correct, manager.answered);
        String outputText = String.format("You have finished the tutor. You got %d out of %d. This is a score of %.2f percent", manager.correct, manager.answered, userScore);
        alert.setContentText(outputText);
        Optional<ButtonType> result = alert.showAndWait();

        // Clear the results
        manager.answered = 0;
        manager.correct = 0;
    }

}