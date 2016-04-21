package seng302.gui;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;
import seng302.Environment;
import seng302.data.Interval;
import seng302.data.Note;
import seng302.data.Term;
import seng302.utility.TutorManager;
import seng302.utility.TutorRecord;

public class TutorController {

    public Environment env;

    public TutorManager manager;

    public TutorRecord record;

    public float userScore;

    public String outputText;

    @FXML
    VBox questionRows;

    @FXML
    ScrollPane paneQuestions;

    @FXML
    ScrollPane paneResults;

    @FXML
    Text resultsTitle;

    @FXML
    Text resultsContent;

    @FXML
    VBox resultsBox;

    @FXML
    HBox buttons;

    public TutorController() {

    }

    public void create(Environment env){
        this.env = env;
        manager = new TutorManager();
    }

    /**
     * If the user chooses to re-test their self on their failed questions, this function
     * sets up the tutoring environment for that.
     */
    public void retest() {
        ArrayList<Pair> tempIncorrectResponses = new ArrayList<Pair>(manager.getTempIncorrectResponses());
        manager.clearTempIncorrect();
        manager.questions = tempIncorrectResponses.size();
        for(Pair pair : tempIncorrectResponses){
            HBox questionRow = generateQuestionPane(pair);
            questionRows.getChildren().add(questionRow);
            questionRows.setMargin(questionRow, new Insets(10, 10, 10, 10));
        }
    }

    /**
     * An empty function which is overridden by each tutor
     */
    public HBox generateQuestionPane(Pair data) {
        System.out.println("using blank method instead");
        return new HBox();
    }

    /**
     * A function for disabling the buttons in an HBox
     * @param questionRow the HBox containing children to be disabled
     */
    public void disableButtons(HBox questionRow) {
        for (int i = 0; i < questionRow.getChildren().size(); i++) {
            questionRow.getChildren().get(i).setDisable(true);
        }
    }

    /**
     * Saves a record of the tutoring session to a file.
     */
    public void saveRecord() {
        if (env.getRecordLocation() != null) {
            record.writeToFile(env.getRecordLocation());
        } else {
            //show a file picker
            env.setRecordLocation("new-file.txt");
            record.writeToFile("new-file.txt");
        }
    }

    /**
     * Calculates a user's score after a tutoring session
     *
     * @param correct  The number of questions the user answered correctly
     * @param answered The number of questions the user answered, correctly or incorrectly
     * @return the user's score as a percentage value
     */
    public float getScore(int correct, int answered) {
        float score = 0;
        if (answered > 0) {
            score = (float) correct / (float) answered * 100;
        }
        return score;

    }

    /**
     * This function is run once a tutoring session has been completed.
     */
    public void finished() {
        record.setStats(manager.correct, manager.getTempIncorrectResponses().size());
        userScore = getScore(manager.correct, manager.answered);
        outputText = String.format("You have finished the tutor.\n" +
                "You answered %d questions, and skipped %d questions.\n" +
                "You answered %d questions correctly, %d questions incorrectly.\n" +
                "This gives a score of %.2f percent",
                manager.questions, manager.skipped,
                manager.correct, manager.incorrect, userScore);
        // Sets the finished view
        resultsContent.setText(outputText);
        paneQuestions.setVisible(false);
        paneResults.setVisible(true);
        questionRows.getChildren().clear();

        Button retestBtn = new Button("Retest");
        Button clearBtn  = new Button("Clear");

        clearBtn.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            public void handle(javafx.event.ActionEvent event) {
                saveRecord();
                manager.saveTempIncorrect();
                paneResults.setVisible(false);
                paneQuestions.setVisible(true);
            }
        });

        retestBtn.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            public void handle(javafx.event.ActionEvent event) {
                paneResults.setVisible(false);
                paneQuestions.setVisible(true);
                retest();
            }
        });

        if (manager.getTempIncorrectResponses().size() > 0) {
            //Can re-test
            buttons.getChildren().setAll(retestBtn, clearBtn);
        } else {
            //Perfect score
            buttons.getChildren().setAll(clearBtn);
        }

        // Clear the current session
        manager.resetStats();
    }


    public void formatQuestionRow(HBox questionRow) {
        questionRow.setPadding(new Insets(10, 10, 10, 10));
        questionRow.setSpacing(10);
        questionRow.setStyle("-fx-border-color: #336699; -fx-border-width: 2px;");
    }
}