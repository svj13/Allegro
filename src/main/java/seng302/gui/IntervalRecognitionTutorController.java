package seng302.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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


    @FXML
    void goAction(ActionEvent event) {
        int numberIntervals = Integer.parseInt(txtNumIntervals.getText());
        if (numberIntervals >= 1){
            // Run the tutor
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Number of Intervals");
            alert.setContentText("Please select a number of intervals that is 1 or greater");
            alert.setResizable(false);
            alert.showAndWait();
        }

    }

}