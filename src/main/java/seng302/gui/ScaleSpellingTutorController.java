package seng302.gui;

import org.controlsfx.control.CheckComboBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;
import seng302.Environment;
import seng302.command.Scale;
import seng302.data.Note;
import seng302.utility.musicNotation.OctaveUtil;

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

    public HBox createNewQuestion() {
        // Every question needs a random scale
        Map scaleInfo = generateRandomScale();

        // Randomly decides whether question will be type 1, 2, or 3
        int questionType = rand.nextInt(3) + 1;

        // Creates a pair with question type, and the scale info
        Pair question = new Pair(questionType, scaleInfo);

        // Delegates actual generation of question
        return generateQuestionPane(question);
    }

    /**
     * Creates a GUI HBox for type one questions. Type one questions display the scale name, and the
     * user must select all notes of the scale.
     *
     * @param scaleInfo A map containing the start note and scale type
     * @return An HBox, containing the entire question
     */
    private HBox generateQuestionTypeOne(Map scaleInfo) {
        final HBox questionRow = new HBox();
        formatQuestionRow(questionRow);

        // Set up textual representation of question
        Label question = new Label();
        Note startNote = (Note) scaleInfo.get("startNote");
        String scaleType = (String) scaleInfo.get("scaleType");
        question.setText(OctaveUtil.removeOctaveSpecifier(startNote.getNote()) + " " + scaleType);

        // Set up answer and textual representation of answer
        ArrayList<Note> correctNotes = startNote.getScale(scaleType, true);
        ArrayList<String> correctNoteNames = Scale.scaleNameList(OctaveUtil.removeOctaveSpecifier(startNote.getNote()), correctNotes, true, scaleType.toLowerCase());
        Label correctAnswer = correctAnswer(String.join(" ", correctNoteNames));

        // Creates a selection of ComboBoxes to pick notes from
        final HBox inputs = new HBox();
        for (String note : correctNoteNames) {
            ComboBox<String> noteOptions = new ComboBox<>();

            // Create a list of potential answers, include the real answer
            List<String> textualOptions = new ArrayList<>();
            textualOptions.add(note);

            // Create 7 fictional options
            for (int i = 0; i < 7; i++) {
                // Randomly generate notes until we find one currently unused
                String randomNote = OctaveUtil.removeOctaveSpecifier(Note.getRandomNote().getNote());
                while (textualOptions.contains(randomNote)) {
                    randomNote = OctaveUtil.removeOctaveSpecifier(Note.getRandomNote().getNote());
                }
                textualOptions.add(randomNote);
            }

            // Randomise the order of options and add all to combobox
            Collections.shuffle(textualOptions);
            noteOptions.getItems().addAll(textualOptions);
            inputs.getChildren().add(noteOptions);
        }

        Button skip = new Button("Skip");
        styleSkipButton(skip);
        skip.setOnAction(event -> {
            formatSkippedQuestion(questionRow);
            disableButtons(questionRow, 1, questionRow.getChildren().size() - 1);
        });


        questionRow.getChildren().add(0, question);
        questionRow.getChildren().add(1, inputs);
        questionRow.getChildren().add(2, skip);
        questionRow.getChildren().add(3, correctAnswer);

        questionRow.prefWidthProperty().bind(paneQuestions.prefWidthProperty());
        return questionRow;
    }

    private HBox generateQuestionTypeTwo(Map scaleInfo) {
        final HBox questionRow = new HBox();
        formatQuestionRow(questionRow);

        // Get scale info
        Note startNote = (Note) scaleInfo.get("startNote");
        String scaleType = (String) scaleInfo.get("scaleType");

        // Set up textual representation of question
        Label question = new Label();
        ArrayList<Note> correctNotes = startNote.getScale(scaleType, true);
        ArrayList<String> correctNoteNames = Scale.scaleNameList(OctaveUtil.removeOctaveSpecifier(startNote.getNote()), correctNotes, true, scaleType.toLowerCase());
        question.setText(String.join(" ", correctNoteNames));

        // Set up answer and textual representation of answer
        String answer = OctaveUtil.removeOctaveSpecifier(startNote.getNote()) + " " + scaleType;
        Label correctAnswer = correctAnswer(answer);

        // This question type only has two inputs
        final HBox inputs = new HBox();
        ComboBox<String> noteOptions = new ComboBox<>();
        ComboBox<String> scaleTypeOptions = new ComboBox<>();

        List<String> textNoteOptions = new ArrayList<>();

        // Generate the note options
        textNoteOptions.add(OctaveUtil.removeOctaveSpecifier(startNote.getNote()));

        for (int i = 0; i < 7; i++) {
            String randomNote = OctaveUtil.removeOctaveSpecifier(Note.getRandomNote().getNote());
            while (textNoteOptions.contains(randomNote)) {
                randomNote = OctaveUtil.removeOctaveSpecifier(Note.getRandomNote().getNote());
            }
            textNoteOptions.add(randomNote);

        }
        Collections.shuffle(textNoteOptions);
        noteOptions.getItems().addAll(textNoteOptions);

        // Generate the scale options - all the selected scale types.
        scaleTypeOptions.getItems().addAll(selectedScaleTypes);

        inputs.getChildren().add(noteOptions);
        inputs.getChildren().add(scaleTypeOptions);


        Button skip = new Button("Skip");
        styleSkipButton(skip);
        skip.setOnAction(event -> {
            formatSkippedQuestion(questionRow);
            disableButtons(questionRow, 1, questionRow.getChildren().size() - 1);
        });


        questionRow.getChildren().add(0, question);
        questionRow.getChildren().add(1, inputs);
        questionRow.getChildren().add(2, skip);
        questionRow.getChildren().add(3, correctAnswer);

        questionRow.prefWidthProperty().bind(paneQuestions.prefWidthProperty());
        return questionRow;
    }

    private HBox generateQuestionTypeThree(Map scaleInfo) {
        final HBox questionRow = new HBox();
        formatQuestionRow(questionRow);

        // Get scale info
        Note startNote = (Note) scaleInfo.get("startNote");
        String scaleType = (String) scaleInfo.get("scaleType");

        Button play = new Button();
        stylePlayButton(play);
        play.setOnAction(event -> {
            if (scaleType.equalsIgnoreCase("blues")) {
                env.getPlayer().playBluesNotes(startNote.getScale(scaleType, true));
            } else {
                env.getPlayer().playNotes(startNote.getScale(scaleType, true));
            }
        });

        ArrayList<String> correctNoteNames = Scale.scaleNameList(
                OctaveUtil.removeOctaveSpecifier(startNote.getNote()),
                startNote.getScale(scaleType, true),
                true,
                scaleType.toLowerCase());

        Button skip = new Button("Skip");
        styleSkipButton(skip);
        skip.setOnAction(event -> {
            formatSkippedQuestion(questionRow);
            disableButtons(questionRow, 1, questionRow.getChildren().size() - 1);
        });

        Label correctAnswer = correctAnswer(String.join(" ", correctNoteNames));

        questionRow.getChildren().add(0, play);
        questionRow.getChildren().add(1, skip);
        questionRow.getChildren().add(2, correctAnswer);

        return questionRow;
    }

    @Override
    HBox generateQuestionPane(Pair data) {
        int questionType = (int) data.getKey();
        if (questionType == 1) {
            return generateQuestionTypeOne((Map) data.getValue());
        } else if (questionType == 2) {
            return generateQuestionTypeTwo((Map) data.getValue());
        } else {
            return generateQuestionTypeThree((Map) data.getValue());
        }
    }

    @Override
    void resetInputs() {

    }

    @FXML
    void goAction(ActionEvent event) {
        if (scaleTypes.getCheckModel().getCheckedIndices().size() != 0) {
            scaleError.setVisible(false);
            paneQuestions.setVisible(true);
            paneResults.setVisible(false);

            questionRows.getChildren().clear();
            for (int i = 0; i < selectedQuestions; i++) {
                HBox questionRow = createNewQuestion();
                questionRows.getChildren().add(questionRow);
                questionRows.setMargin(questionRow, new Insets(10, 10, 10, 10));
            }
        } else {
            scaleError.setVisible(true);
        }

    }


}
