package seng302.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import seng302.Environment;

/**
 * Created by emily on 2/07/16.
 */
public class ChordSpellingTutorController extends TutorController {
    @FXML
    HBox settings;

    @FXML
    AnchorPane chordSpellingAnchor;

    @FXML
    Button btnGo;

    public void create(Environment env) {
        super.create(env);
        initialiseQuestionSelector();
    }

    @FXML
    private void goAction(ActionEvent event) {
    }



    @Override
    HBox generateQuestionPane(Pair data) {
        return null;
    }

    @Override
    void resetInputs() {

    }
}
