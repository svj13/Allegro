package seng302.gui;

import org.controlsfx.control.CheckComboBox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;
import seng302.Environment;

public class ScaleSpellingTutorController extends TutorController {

    @FXML
    private HBox settings;

    @FXML
    private VBox resultsBox;

    @FXML
    private VBox questionRows;

    @FXML
    private HBox buttons;

    @FXML
    private Label questions;

    @FXML
    private ScrollPane paneResults;

    @FXML
    private HBox settingsBox;

    @FXML
    private Slider numQuestions;

    @FXML
    private AnchorPane chordSpellingAnchor;

    @FXML
    private Text resultsTitle;

    @FXML
    private Text scaleError;

    @FXML
    private ScrollPane paneQuestions;

    @FXML
    private Text resultsContent;

    @FXML
    private Button btnGo;

    CheckComboBox<String> scaleTypes = new CheckComboBox<String>();

    public void create(Environment env) {
        super.create(env);
        //initialiseQuestionSelector();
    }

    @Override
    HBox generateQuestionPane(Pair data) {
        return null;
    }

    @Override
    void resetInputs() {

    }

    @FXML
    void goAction(ActionEvent event) {

    }

    private void initialiseScaleTypeSelector() {
        scaleTypes.setMaxWidth(100);
        //Adds to the settings, after its label
        settingsBox.getChildren().add(1, scaleTypes);
    }


}
