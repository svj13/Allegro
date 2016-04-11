package seng302.gui;

import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.Environment;
import seng302.data.Interval;
import seng302.data.Note;

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
    }

    @FXML
    void goAction(ActionEvent event) {
        int numberIntervals = Integer.parseInt(txtNumIntervals.getText());
        if (numberIntervals >= 1){
            // Run the tutor
            questionRows.getChildren().clear();
            for (int i = 0; i < numberIntervals; i++) {
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
        for (Interval interval:Interval.intervals.values()) {
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
        final Interval thisInterval = generateInterval();
        Note firstNote = getStartingNote();
        Note secondNote = getFinalNote(firstNote, thisInterval);

        options.setOnAction(new EventHandler<ActionEvent>() {
            // This handler colors the GUI depending on the user's input
            public void handle(ActionEvent event) {
                if (options.getValue().equals(thisInterval.getName())) {
                    questionRow.setStyle("-fx-background-color: green;");
                } else {
                    questionRow.setStyle("-fx-background-color: red;");
                }
            }
        });

        questionRow.getChildren().add(play);
        questionRow.getChildren().add(skip);
        questionRow.getChildren().add(cancel);
        questionRow.getChildren().add(options);

        questionRow.prefWidthProperty().bind(paneQuestions.prefWidthProperty());
        return questionRow;
    }

    /**
     * Randomly selects a note for the interval.
     * @return A Note object, for playing an interval.
     */
    private Note getStartingNote() {
        Random randNote = new Random();
        return Note.lookup(String.valueOf(randNote.nextInt(128)));
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
        return Interval.intervals.get(rand.nextInt(8));
    }

}