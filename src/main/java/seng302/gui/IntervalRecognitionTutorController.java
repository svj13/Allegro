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

    private ComboBox<String> generateChoices() {
        ComboBox<String> options = new ComboBox<String>();
        for (Interval interval:Interval.intervals.values()) {
            options.getItems().add(interval.getName());
        }
        return options;
    }


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


    private Interval generateInterval() {
        Random rand = new Random();
        // There are 8 different intervals
        return Interval.intervals.get(rand.nextInt(8));
    }

}