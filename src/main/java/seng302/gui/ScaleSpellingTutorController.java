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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Pair;
import seng302.Environment;
import seng302.command.Scale;
import seng302.data.Note;
import seng302.utility.TutorRecord;
import seng302.utility.musicNotation.OctaveUtil;

public class ScaleSpellingTutorController extends TutorController {

    @FXML
    private HBox settings;


    @FXML
    private HBox settingsBox;


    @FXML
    private AnchorPane scaleSpellingAnchor;


    @FXML
    private Text scaleError;


    @FXML
    private Button btnGo;

    CheckComboBox<String> scaleTypes = new CheckComboBox<String>();

    private String[] validScaleNames = {"Major", "Minor", "Melodic Minor", "Blues",
            "Major Pentatonic", "Minor Pentatonic", "Harmonic Minor"};

    private ArrayList<String> selectedScaleTypes = new ArrayList<String>();

    Random rand;

    private final String typeOneText = "Spell the scale with the name %s";

    private final String typeTwoText = "Name the scale with the notes %s";

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

    private boolean isTypeOneTwoComplete(HBox question) {
        // Check if all inputs have been entered
        boolean isComplete = true;
        for (Node comboBox : ((HBox) question.lookup("#inputs")).getChildren()) {
            if (!comboBox.isDisabled()) {
                isComplete = false;
            }
        }
        return isComplete;
    }

    private int gradeTypeOneTwoQuestion(HBox question) {
        int correct = 0;
        int incorrect = 0;
        question.lookup("#skip").setDisable(true);

        // Calculate how many were correct and incorrect
        for (Node comboBox : ((HBox) question.lookup("#inputs")).getChildren()) {
            if (comboBox.getStyle().contains("green")) {
                correct += 1;
            } else {
                incorrect += 1;
            }
        }

        // Style accordingly
        if (incorrect == 0) {
            // Answer was correct
            formatCorrectQuestion(question);
            return 1;
        } else if (correct == 0) {
            // Answer was incorrect
            formatIncorrectQuestion(question);
            return 0;
        } else {
            // Answer was partially correct
            formatPartiallyCorrectQuestion(question);
            return 2;
        }


    }

    private void styleTypeOneTwoQuestion(HBox question, int score) {
        if (score == 0) {
            formatIncorrectQuestion(question);
        } else if (score == 1) {
            formatCorrectQuestion(question);
        } else if (score == 2) {
            formatPartiallyCorrectQuestion(question);
        }


    }

    private void styleTypeOneTwoInput(ComboBox input, String answer) {
        input.setDisable(true);
        if (input.getValue().equals(answer)) {
            input.setStyle("-fx-background-color: green");
        } else {
            input.setStyle("-fx-background-color: red");
        }

    }

    private void handleTypeOneTwoInput(ComboBox input, Map scaleInfo, HBox questionRow, String answer, int questionType) {
        styleTypeOneTwoInput(input, answer);
        if (isTypeOneTwoComplete(questionRow)) {
            gradeTypeOneTwoQuestion(questionRow);
            int score = gradeTypeOneTwoQuestion(questionRow);

            styleTypeOneTwoQuestion(questionRow, score);


            if (score == 0) {
                //show the answer
                questionRow.lookup("#answer").setVisible(true);
            }
            if (score == 2) {
                // if question was partially correct, add it to the manager as incorrect
                score = 0;
            }

            String questionText;
            if (questionType == 1) {
                questionText = String.format(typeOneText, ((Label) questionRow.lookup("#question")).getText());
            } else {
                questionText = String.format(typeTwoText, ((Label) questionRow.lookup("#question")).getText());
            }

            String usersAnswer = "";

            for (Node part : ((HBox) questionRow.lookup("#inputs")).getChildren()) {
                usersAnswer += ((ComboBox) part).getValue() + " ";
            }
            usersAnswer = usersAnswer.trim();

            String[] question = new String[]{
                    questionText,
                    usersAnswer,
                    Integer.toString(score)
            };

            record.addQuestionAnswer(question);

            manager.add(new Pair(scaleInfo, 1), score);
            if (manager.answered == manager.questions) {
                finished();
            }
        }
    }

    private void handleSkippedQuestion(Map scaleInfo, int questionType, HBox questionRow) {
        manager.add(new Pair(scaleInfo, questionType), 2);
        formatSkippedQuestion(questionRow);
        disableButtons(questionRow, 1, questionRow.getChildren().size() - 1);

        String questionText = "";
        if (questionType == 1) {
            questionText = String.format(typeOneText, ((Label) questionRow.lookup("#question")).getText());

        } else if (questionType == 2) {
            questionText = String.format(typeTwoText, ((Label) questionRow.lookup("#question")).getText());
        }
        String[] questionInfo = new String[]{
                questionText,
                "",
                "2"

        };
        record.addQuestionAnswer(questionInfo);
        handleAccordion();

        if (manager.answered == manager.questions) {
            finished();
        }

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
        String startNoteName = randomiseNoteName(startNote);

        String scaleType = (String) scaleInfo.get("scaleType");
        question.setText(startNoteName + " " + scaleType);
        question.setId("question");

        // Set up answer and textual representation of answer
        ArrayList<Note> correctNotes = startNote.getScale(scaleType, true);
        ArrayList<String> correctNoteNames = Scale.scaleNameList(startNoteName, correctNotes, true, scaleType.toLowerCase());
        Label correctAnswer = correctAnswer(String.join(" ", correctNoteNames));
        correctAnswer.setId("answer");

        // Creates a selection of ComboBoxes to pick notes from
        final HBox inputs = new HBox();
        inputs.setId("inputs");
        for (String note : correctNoteNames) {
            ComboBox<String> noteOptions = new ComboBox<>();

            // Create a list of potential answers, include the real answer
            List<String> textualOptions = new ArrayList<>();
            textualOptions.add(note);

            // Create 7 fictional options
            for (int i = 0; i < 7; i++) {
                // Randomly generate notes until we find one currently unused
                String randomNote = randomiseNoteName(Note.getRandomNote());
                while (textualOptions.contains(randomNote)) {
                    randomNote = randomiseNoteName(Note.getRandomNote());
                }
                textualOptions.add(randomNote);
            }

            // Randomise the order of options and add all to combobox
            Collections.shuffle(textualOptions);
            noteOptions.getItems().addAll(textualOptions);

            // Add handler to each ComboBox input
            noteOptions.setOnAction(event -> handleTypeOneTwoInput(noteOptions, scaleInfo, questionRow, note, 1));

            inputs.getChildren().add(noteOptions);
        }

        Button skip = new Button("Skip");
        skip.setId("skip");
        styleSkipButton(skip);
        skip.setOnAction(event -> handleSkippedQuestion(scaleInfo, 1, questionRow));


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

        String correctStartNote = randomiseNoteName(startNote);

        // Set up textual representation of question
        Label question = new Label();
        ArrayList<Note> correctNotes = startNote.getScale(scaleType, true);
        ArrayList<String> correctNoteNames = Scale.scaleNameList(correctStartNote, correctNotes, true, scaleType.toLowerCase());
        question.setText(String.join(" ", correctNoteNames));
        question.setId("question");

        // Set up answer and textual representation of answer
        String answer = correctStartNote + " " + scaleType;
        Label correctAnswer = correctAnswer(answer);
        correctAnswer.setId("answer");

        // This question type only has two inputs
        final HBox inputs = new HBox();
        inputs.setId("inputs");
        ComboBox<String> noteOptions = new ComboBox<>();
        ComboBox<String> scaleTypeOptions = new ComboBox<>();

        List<String> textNoteOptions = new ArrayList<>();

        // Generate the note options
        textNoteOptions.add(correctStartNote);

        for (int i = 0; i < 7; i++) {
            String randomNote = randomiseNoteName(Note.getRandomNote());
            while (textNoteOptions.contains(randomNote)) {
                randomNote = randomiseNoteName(Note.getRandomNote());
            }
            textNoteOptions.add(randomNote);

        }
        Collections.shuffle(textNoteOptions);
        noteOptions.getItems().addAll(textNoteOptions);
        // Add handler to each ComboBox input

        noteOptions.setOnAction(event -> handleTypeOneTwoInput(noteOptions, scaleInfo, questionRow, correctStartNote, 2));

        // Generate the scale options - all the selected scale types.
        scaleTypeOptions.getItems().addAll(selectedScaleTypes);

        scaleTypeOptions.setOnAction(event -> handleTypeOneTwoInput(scaleTypeOptions, scaleInfo, questionRow, scaleType, 2));

        inputs.getChildren().add(noteOptions);
        inputs.getChildren().add(scaleTypeOptions);


        Button skip = new Button("Skip");
        skip.setId("skip");
        styleSkipButton(skip);
        skip.setOnAction(event -> handleSkippedQuestion(scaleInfo, 2, questionRow));


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
        skip.setOnAction(event -> handleSkippedQuestion(scaleInfo, 3, questionRow));

        Label correctAnswer = correctAnswer(String.join(" ", correctNoteNames));
        correctAnswer.setId("answer");

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
            //scaleError.setVisible(false);
            record = new TutorRecord();
            paneInit.setVisible(false);
            paneQuestions.setVisible(true);

            manager.resetEverything();
            manager.questions = selectedQuestions;

            qPanes = new ArrayList<>();

            questionRows.getChildren().clear();
            for (int i = 0; i < manager.questions; i++) {
                HBox questionRow = createNewQuestion();
                TitledPane qPane = new TitledPane("Question " + (i + 1), questionRow);
                qPane.setPadding(new Insets(2, 2, 2, 2));
                qPanes.add(qPane);
                questionRows.setMargin(questionRow, new Insets(10, 10, 10, 10));
            }
            qAccordion.getPanes().addAll(qPanes);
            qAccordion.setExpandedPane(qAccordion.getPanes().get(0));
            questionRows.getChildren().add(qAccordion);
        } else {
            //scaleError.setVisible(true);
        }

    }


}
