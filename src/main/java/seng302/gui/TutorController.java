package seng302.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import seng302.Environment;
import seng302.managers.TutorManager;
import seng302.utility.TutorRecord;

public class TutorController {

    public Environment env;

    public TutorManager manager;

    public TutorRecord record;

    public float userScore;

    public String outputText;

    public int selectedQuestions;

    Stage stage;

    File fileDir;

    String path;

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

    @FXML
    Slider numQuestions;

    @FXML
    Label questions;

    /**
     * An empty constructor, required for sub-classes.
     */
    public TutorController() {}

    /**
     * The method called to initialise a tutor.
     * Sets up the environment and tutor manager
     * @param env
     */
    public void create(Environment env){
        this.env = env;
        manager = new TutorManager();
    }

    /**
     * Implements the settings of a slider used to select number of questions.
     */
    public void initialiseQuestionSelector() {
        System.out.println("numQuestions: " + numQuestions.getValue());
        selectedQuestions = (int) numQuestions.getValue();
        questions.setText(Integer.toString(selectedQuestions));

        // The listener for the number of questions selected
        numQuestions.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                selectedQuestions = newValue.intValue();
                questions.setText(Integer.toString(selectedQuestions));
            }
        });
    }

    /**
     * If the user chooses to re-test their self on their failed questions, this function
     * sets up the tutoring environment for that.
     */
    public void retest() {
        ArrayList<Pair> tempIncorrectResponses = new ArrayList<Pair>(manager.getTempIncorrectResponses());
        manager.clearTempIncorrect();
        Collections.shuffle(tempIncorrectResponses);
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
        return new HBox();
    }

    /**
     * A function for disabling a selection of buttons.
     * For example, disable all inputs but not the play button.
     * @param questionRow the HBox containing children to be disabled
     * @param firstChild the index of the first object to disable
     * @param lastChild the index at which to stop disabling items
     */
    public void disableButtons(HBox questionRow, int firstChild, int lastChild) {
        for (int i = firstChild; i < lastChild; i++) {
            questionRow.getChildren().get(i).setDisable(true);
        }
    }

    /**
     * Saves a record of the tutoring session to a file.
     */
    public void saveRecord() {
        if (env.getRecordLocation() != null) {
            // Appends to file already created in this session.
            record.writeToFile(env.getRecordLocation());
        } else {
            //show a file picker
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter textFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(textFilter);
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                fileDir = file.getParentFile();
                path = file.getAbsolutePath();
                env.setRecordLocation(path);
                record.writeToFile(path);
            }
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
        userScore = getScore(manager.correct, manager.answered);
        record.setStats(manager.correct, manager.getTempIncorrectResponses().size(), userScore);
        outputText = String.format("You have finished the tutor.\n" +
                "You answered %d questions, and skipped %d questions.\n" +
                "You answered %d questions correctly, %d questions incorrectly.\n" +
                        "This gives a score of %.2f percent.",
                manager.questions, manager.skipped,
                manager.correct, manager.incorrect, userScore);
        // Sets the finished view
        resultsContent.setText(outputText);

        paneQuestions.setVisible(false);
        paneResults.setVisible(true);
        questionRows.getChildren().clear();

        Button retestBtn = new Button("Retest");
        Button clearBtn  = new Button("Clear");

        clearBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                promptSaveRecord();
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

        if (manager.getTempIncorrectResponses().size() > 0) {
            //Can re-test
            buttons.getChildren().setAll(retestBtn, clearBtn);
        } else {
            //Perfect score
            buttons.getChildren().setAll(clearBtn);
        }

        buttons.setMargin(retestBtn, new Insets(10,10,10,10));
        buttons.setMargin(clearBtn, new Insets(10,10,10,10));
        // Clear the current session
        manager.resetStats();
    }


    /**
     * Styles a question row consistently
     * @param questionRow the row being styled
     */
    public void formatQuestionRow(HBox questionRow) {
        questionRow.setPadding(new Insets(10, 10, 10, 10));
        questionRow.setSpacing(10);
        questionRow.setStyle("-fx-border-color: #336699; -fx-border-width: 2px;");
    }


    /**
     * Creates an alert to ask the user whether or not to save a record to file.
     */
    public void promptSaveRecord() {
        Alert savePrompt = new Alert(Alert.AlertType.NONE);
        savePrompt.setContentText("Would you like to save this tutoring session?");
        savePrompt.setHeaderText("Save Record?");
        ButtonType save = new ButtonType("Save");
        ButtonType cancel = new ButtonType("Discard");
        savePrompt.getButtonTypes().setAll(save, cancel);
        ButtonType result = savePrompt.showAndWait().get();

        if (result.equals(save)) {
            saveRecord();
        }
    }

    /**
     * Formats a GUI question to indicate it was skipped
     * @param question The HBox containing info about a question
     */
    public void formatSkippedQuestion(HBox question) {
        question.setStyle("-fx-border-color: grey; -fx-border-width: 2px;");
    }

    /**
     * Formats a GUI question to indicate it was answered correctly
     * @param question The HBox containing info about a question
     */
    public void formatCorrectQuestion(HBox question) {
        question.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
    }

    /**
     * Formats a GUI question to indicate it was answered incorrectly
     * @param question The HBox containing info about a question
     */
    public void formatIncorrectQuestion(HBox question) {
        question.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
    }

    /**
     * Formats a GUI question to indicate it was answered partially correctly
     * @param question The HBox containing info about a question
     */
    public void formatPartiallyCorrectQuestion(HBox question) {
        question.setStyle("-fx-border-color: yellow; -fx-border-width: 2px;");
    }

    /**
     * Returns a new, hidden label containing the correct answer to a question.
     * @param answerToShow The correct answer to the question
     * @return A new hidden label
     */
    public Label correctAnswer(String answerToShow) {
        Label correctAnswerLabel = new Label(answerToShow);
        correctAnswerLabel.setVisible(false);
        return correctAnswerLabel;

    }
}