package seng302.gui;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringJoiner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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

    }


    public void create(Environment env) {
        super.create(env);
        rand = new Random();
    }

    public ArrayList<Note> getRandomScale() {
        Note startNote = Note.lookup(Integer.toString(rand.nextInt(11) + 60));
        int type = rand.nextInt(1);
        String scaleType;
        if (type == 0) {
            scaleType = "major";
        } else {
            scaleType = "minor";
        }

        // Add # octaves and up/down selection here.
        ArrayList<Note> scale = startNote.getOctaveScale(scaleType, 1, true);
        return scale;
    }


}
