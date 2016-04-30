package seng302.gui;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringJoiner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import seng302.Environment;
import seng302.data.Note;

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
        paneQuestions.setVisible(true);
        paneResults.setVisible(false);
        questionRows.getChildren().clear();

        int type = rand.nextInt(1);
        String scaleType;
        if (type == 0) {
            scaleType = "major";
        } else {
            scaleType = "minor";
        }
        HBox questionRow = generateQuestionPane(getRandomScale(scaleType), scaleType);
        questionRows.getChildren().add(questionRow);
        questionRows.setMargin(questionRow, new Insets(10, 10, 10, 10));
    }


    public void create(Environment env) {
        super.create(env);
        rand = new Random();
    }

    public ArrayList<Note> getRandomScale(String scaleType) {
        Note startNote = Note.lookup(Integer.toString(rand.nextInt(11) + 60));

        // Add # octaves and up/down selection here.
        ArrayList<Note> scale = startNote.getOctaveScale(scaleType, 1, true);
        return scale;
    }

    /**
     * Creates a GUI pane for a single question
     * @param scale The array list of notes to be played
     * @param scaleType Whether it is a major or minor scale
     * @return
     */
    public HBox generateQuestionPane(final ArrayList<Note> scale, String scaleType) {
        final HBox questionRow = new HBox();
        formatQuestionRow(questionRow);


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
        Button minor = new Button("Minor");

        Button skip = new Button("Skip");
        Image imageSkip = new Image(getClass().getResourceAsStream("/images/right-arrow.png"), 20, 20, true, true);
        skip.setGraphic(new ImageView(imageSkip));
        skip.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Disables only input buttons
                disableButtons(questionRow, 1, 4);
                formatSkippedQuestion(questionRow);
            }
        });

        questionRow.getChildren().addAll(play, major, minor, skip);

        questionRow.prefWidthProperty().bind(paneQuestions.prefWidthProperty());
        return questionRow;
    }


}
