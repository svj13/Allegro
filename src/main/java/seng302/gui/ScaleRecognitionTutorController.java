package seng302.gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.StringJoiner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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

    private Random rand;

    @FXML
    private void goAction(ActionEvent event) {
        record = new TutorRecord(new Date(), "Scale Recognition");
        paneQuestions.setVisible(true);
        paneResults.setVisible(false);

        //Alter once a slider has been implemented
        manager.questions = 5;

        questionRows.getChildren().clear();
        for (int i = 0; i < manager.questions; i++) {
            HBox questionRow = setUpQuestion();
            questionRows.getChildren().add(questionRow);
            questionRows.setMargin(questionRow, new Insets(10, 10, 10, 10));
        }
    }


    public void create(Environment env) {
        super.create(env);
        rand = new Random();
    }

    /**
     * Prepares a new question
     * @return a question pane containing the question information
     */
    public HBox setUpQuestion() {
        int type = rand.nextInt(2);
        String scaleType;
        if (type == 0) {
            scaleType = "major";
        } else {
            scaleType = "minor";
        }
        return generateQuestionPane(getRandomScale(scaleType), scaleType);
    }

    public ArrayList<Note> getRandomScale(String scaleType) {
        Note startNote = Note.lookup(Integer.toString(rand.nextInt(11) + 60));
        // Add # octaves and up/down selection here.
        ArrayList<Note> scale = startNote.getOctaveScale(scaleType, 1, true);
        return scale;
    }

    public void handleQuestionAnswer(String userAnswer, Pair correctAnswer, HBox questionRow) {
        manager.answered += 1;
        disableButtons(questionRow, 1, 4);
        if (userAnswer.equals(correctAnswer.getValue())) {
            manager.add(correctAnswer, 1);
            formatCorrectQuestion(questionRow);
        } else {
            manager.add(correctAnswer, 0);
            formatIncorrectQuestion(questionRow);
            //Shows the correct answer
            questionRow.getChildren().get(4).setVisible(true);
        }
        if (manager.answered == manager.questions) {
            finished();
        }
    }

    /**
     * Creates a GUI pane for a single question
     * @param scale The array list of notes to be played
     * @param scaleType Whether it is a major or minor scale
     * @return
     */
    public HBox generateQuestionPane(final ArrayList<Note> scale, final String scaleType) {
        final HBox questionRow = new HBox();
        formatQuestionRow(questionRow);
        final Label correctAnswer = correctAnswer(scaleType);

        //Saves the first note and scale type - C Major, B Minor, etc
        final Pair<Note, String> noteAndScaleType = new Pair<Note, String>(scale.get(0), scaleType);

        Button play = new Button();
        Image imagePlay = new Image(getClass().getResourceAsStream("/images/play-icon.png"), 20, 20, true, true);
        play.setGraphic(new ImageView(imagePlay));
        play.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //Play the scale
                env.getPlayer().playNotes(scale);
            }
        });

        Button major = new Button("Major");
        major.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                handleQuestionAnswer("major", noteAndScaleType, questionRow);
            }
        });

        Button minor = new Button("Minor");
        minor.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                handleQuestionAnswer("minor", noteAndScaleType, questionRow);
            }
        });

        Button skip = new Button("Skip");
        Image imageSkip = new Image(getClass().getResourceAsStream("/images/right-arrow.png"), 20, 20, true, true);
        skip.setGraphic(new ImageView(imageSkip));
        skip.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Disables only input buttons
                disableButtons(questionRow, 1, 4);
                formatSkippedQuestion(questionRow);
                manager.questions -= 1;
                manager.add(noteAndScaleType, 2);
                if (manager.answered == manager.questions) {
                    finished();
                }
            }
        });

        questionRow.getChildren().add(0, play);
        questionRow.getChildren().add(1, major);
        questionRow.getChildren().add(2, minor);
        questionRow.getChildren().add(3, skip);
        questionRow.getChildren().add(4, correctAnswer);

        questionRow.prefWidthProperty().bind(paneQuestions.prefWidthProperty());
        return questionRow;
    }


}
