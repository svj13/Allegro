package seng302.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    ComboBox chordTypes;

    @FXML
    ComboBox numEnharmonics;

    @FXML
    Button btnGo;

    public void create(Environment env) {
        super.create(env);
        initialiseQuestionSelector();
        chordTypes.getItems().addAll("all", "major", "minor");
        chordTypes.getSelectionModel().selectFirst();
        numEnharmonics.getItems().addAll("only one", "all");
        numEnharmonics.getSelectionModel().selectFirst();
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
