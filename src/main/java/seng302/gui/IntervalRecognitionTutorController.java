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
import javafx.util.Pair;
import seng302.Environment;
import seng302.data.Interval;
import seng302.data.Note;
import seng302.utility.TutorManager;

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

    TutorManager manager;

    @FXML
    void goAction(ActionEvent event) {
        manager.questions = Integer.parseInt(txtNumIntervals.getText());
        if (manager.questions >= 1){
            // Run the tutor
            questionRows.getChildren().clear();
            for (int i = 0; i < manager.questions; i++) {
                HBox questionRow = setUpQuestion();
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

    private void disableButtons(HBox questionRow) {
        for (int i = 0; i < questionRow.getChildren().size(); i++) {
            questionRow.getChildren().get(i).setDisable(true);
        }
    }

    private HBox setUpQuestion() {
        // Key is the interval, value is the note
        Interval thisInterval = generateInterval();
        Note firstNote = getStartingNote(thisInterval.getSemitones());
        Pair<Interval, Note> pair = new Pair<Interval, Note>(thisInterval, firstNote);
        return generateQuestionRow(pair);
    }


    /**
     * Creates a GUI section for one question.
     * @return a JavaFX HBox containing controls and info about one question.
     */
    private HBox generateQuestionRow(Pair intervalAndNote) {
        final HBox questionRow = new HBox();

        questionRow.setPadding(new Insets(10, 10, 10, 10));
        questionRow.setSpacing(10);
        questionRow.setStyle("-fx-border-color: #336699; -fx-border-width: 2px;");

        //Add buttons for play and skip
        Button play = new Button("Play");
        Button skip = new Button("Skip");
        final ComboBox<String> options = generateChoices();
        final Label correctAnswer = new Label();

        final Pair pair = intervalAndNote;
        final Interval thisInterval = (Interval) pair.getKey();
        final Note firstNote = (Note) pair.getValue();
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
                //Disables inputs
                disableButtons(questionRow);
                questionRow.setStyle("-fx-border-color: grey; -fx-border-width: 2px;");
                manager.questions -= 1;
                manager.add(pair, 0);
                if (manager.answered == manager.questions) {
                    finished();
                }
            }
        });

        options.setOnAction(new EventHandler<ActionEvent>() {
            // This handler colors the GUI depending on the user's input
            public void handle(ActionEvent event) {
                disableButtons(questionRow);
                if (options.getValue().equals(thisInterval.getName())) {
                    questionRow.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
                    manager.add(pair, 1);
                } else {
                    questionRow.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                    manager.add(pair, 0);
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


        ButtonType retestBtn = new ButtonType("Retest");
        ButtonType clearBtn  = new ButtonType("Clear");

        if (manager.getTempIncorrectResponses().size() > 0) {
            //Can re-test
            alert.getButtonTypes().setAll(retestBtn, clearBtn);
        } else {
            //Perfect score
            alert.getButtonTypes().setAll(clearBtn);
        }
        Optional<ButtonType> result = alert.showAndWait();
        questionRows.getChildren().clear();

        if (result.get() == clearBtn) {
            manager.saveTempIncorrect();
        } else if (result.get() == retestBtn) {
            retest();
        }

        // Clear the current session
        manager.answered = 0;
        manager.correct = 0;
    }

    private void retest() {
        ArrayList<Pair> tempIncorrectResponses = new ArrayList<Pair>(manager.getTempIncorrectResponses());
        manager.clearTempIncorrect();
        manager.questions = tempIncorrectResponses.size();
        for(Pair<Interval, Note> pair : tempIncorrectResponses){
            HBox questionRow = generateQuestionRow(pair);
            questionRows.getChildren().add(questionRow);
            questionRows.setMargin(questionRow, new Insets(10, 10, 10, 10));
        }

    }


}