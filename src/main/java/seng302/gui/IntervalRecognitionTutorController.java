package seng302.gui;

import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class IntervalRecognitionTutorController {

    @FXML
    private TextField txtNumIntervals;

    @FXML
    private VBox questionRows;

    @FXML
    private AnchorPane IntervalRecognitionTab;

    @FXML
    private ScrollPane paneQuestions;

    @FXML
    private Button btnGo;

    // hash map where the key is the number of semitones and the value is the name of that interval
    private HashMap intervals = new HashMap(8);


    @FXML
    void goAction(ActionEvent event) {
        int numberIntervals = Integer.parseInt(txtNumIntervals.getText());
        if (numberIntervals >= 1){
            // Run the tutor
            populateIntervals();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Number of Intervals");
            alert.setContentText("Please select a number of intervals that is 1 or greater");
            alert.setResizable(false);
            alert.showAndWait();
        }

    }

    private ComboBox<String> generateChoices() {
        ComboBox<String> options = new ComboBox<String>();
        options.getItems().addAll(intervals.values());
        return options;
    }



    void populateIntervals() {
        intervals.put(0, "unison");
        intervals.put(2, "major second");
        intervals.put(4, "major third");
        intervals.put(5, "perfect fourth");
        intervals.put(7, "perfect fifth");
        intervals.put(9, "major sixth");
        intervals.put(11, "major seventh");
        intervals.put(12, "perfect octave");
    }

}