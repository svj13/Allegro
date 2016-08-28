package seng302.gui;

import org.controlsfx.control.CheckComboBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javafx.collections.ListChangeListener;
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
import seng302.data.Note;

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

    private String[] validScaleNames = {"Major", "Minor", "Melodic Minor", "Blues",
            "Major Pentatonic", "Minor Pentatonic", "Harmonic Minor"};

    private ArrayList<String> selectedScaleTypes = new ArrayList<String>();

    Random rand;

    public void create(Environment env) {
        super.create(env);
        rand = new Random();
        initialiseQuestionSelector();
        initialiseScaleTypeSelector();
    }

    /**
     * Uses the selected scale types to generate a random scale. Creates a hash map with a start
     * note and a scale type.
     *
     * @return A hash map of start note and type of a random scale.
     */
    private Map generateRandomScale() {
        Map scaleInfo = new HashMap<String, Object>();

        scaleInfo.put("startNote", Note.getRandomNote());
        scaleInfo.put("scaleType", selectedScaleTypes.get(rand.nextInt(selectedScaleTypes.size())));

        return scaleInfo;
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

    /**
     * Sets up the scale type CheckComboBox for selecting which scale types to be tested on.
     */
    private void initialiseScaleTypeSelector() {
        scaleTypes.setMaxWidth(100);

        for (String validChordName : validScaleNames) {
            scaleTypes.getItems().add(validChordName);
        }

        // Listener to keep track of which scale types are selected
        scaleTypes.getCheckModel().getCheckedIndices().addListener((ListChangeListener<Integer>) c -> {
            selectedScaleTypes.clear();
            selectedScaleTypes.addAll(scaleTypes.getCheckModel().getCheckedIndices().stream().map(index -> validScaleNames[index]).collect(Collectors.toList()));
        });

        //Select all scale types by default
        for (int i = 0; i < scaleTypes.getItems().size(); i++) {
            scaleTypes.getCheckModel().checkIndices(i);
        }

        //Adds to the settings, after its label
        settingsBox.getChildren().add(1, scaleTypes);
    }


}
