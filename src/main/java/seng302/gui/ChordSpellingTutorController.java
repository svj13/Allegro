package seng302.gui;

import java.util.ArrayList;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import seng302.Environment;
import seng302.data.Note;
import seng302.utility.TutorRecord;
import seng302.utility.musicNotation.ChordUtil;
import seng302.utility.musicNotation.OctaveUtil;

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

    private Random rand;

    public void create(Environment env) {
        super.create(env);
        initialiseQuestionSelector();
        chordTypes.getItems().addAll("all", "major", "minor");
        chordTypes.getSelectionModel().selectFirst();
        numEnharmonics.getItems().addAll("only one", "all");
        numEnharmonics.getSelectionModel().selectFirst();
        rand = new Random();
    }

    @FXML
    private void goAction(ActionEvent event) {
        record = new TutorRecord();
        paneQuestions.setVisible(true);
        paneResults.setVisible(false);
        manager.resetEverything();
        manager.questions = selectedQuestions;

        questionRows.getChildren().clear();
        for (int i = 0; i < manager.questions; i++) {
            HBox questionRow = setUpQuestion();
            questionRows.getChildren().add(questionRow);
            questionRows.setMargin(questionRow, new Insets(10, 10, 10, 10));
        }
    }

    /**
     * Prepares a new question
     *
     * @return a question pane containing the question information
     */
    public HBox setUpQuestion() {
        //Both questions just need a chord
        Pair<String, ArrayList<Note>> randomChord = generateValidChord();
        return generateQuestionPane(randomChord);
    }

    /**
     * Generates a note in the octave of middle C
     *
     * @return the random note
     */
    public Note getRandomNote() {
        return Note.lookup(Integer.toString(rand.nextInt(11) + 60));
    }

    @Override
    HBox generateQuestionPane(final Pair data) {
        final HBox questionRow = new HBox();
        formatQuestionRow(questionRow);
        Label correctAnswer = new Label();
        Label question = new Label();

        final HBox inputs = new HBox();


        Button skip = new Button("Skip");
        styleSkipButton(skip);

        skip.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Disables only input buttons
                disableButtons(questionRow, 1, 3);
                formatSkippedQuestion(questionRow);
                manager.questions -= 1;
                manager.add(data, 2);
                env.getRootController().setTabTitle("chordSpellingTutor", true);
                if (manager.answered == manager.questions) {
                    //finished();
                }
            }
        });

        final String chordName = (String) data.getKey();
        final ArrayList<Note> chordNotes = (ArrayList<Note>) data.getValue();

        int questionType = rand.nextInt(2);

        if (questionType == 0) {
            //Type A question
            correctAnswer = correctAnswer(chordAsString(chordNotes));
            question.setText(chordName);

            //either 3 or 4 notes in the chord
            ComboBox<String> note1 = new ComboBox<String>();
            ComboBox<String> note2 = new ComboBox<String>();
            ComboBox<String> note3 = new ComboBox<String>();
            ComboBox<String> note4 = new ComboBox<String>();

            inputs.getChildren().add(note1);
            inputs.getChildren().add(note2);
            inputs.getChildren().add(note3);
            inputs.getChildren().add(note4);


        } else {
            //Type B question
            correctAnswer = correctAnswer(chordName);
            question.setText(chordAsString(chordNotes));

            ComboBox<String> possibleNames = new ComboBox<String>();
            inputs.getChildren().add(possibleNames);

        }

        questionRow.getChildren().add(0, question);
        questionRow.getChildren().add(1, inputs);
        questionRow.getChildren().add(2, skip);
        questionRow.getChildren().add(3, correctAnswer);

        questionRow.prefWidthProperty().bind(paneQuestions.prefWidthProperty());
        return questionRow;
    }


    /**
     * Turns a chord (array list of notes) into a pretty string.
     *
     * @param chord An Array List of Notes
     * @return the provided notes in a readable format
     */
    private String chordAsString(ArrayList<Note> chord) {
        String chordAsText = "";

        for (Note note : chord) {
            chordAsText += OctaveUtil.removeOctaveSpecifier(note.getNote()) + " ";
        }
        return chordAsText;
    }

    /**
     * Randomly decides whether the chord will be major or minor.
     * Will be extended for further chord types
     * @return either "major" or "minor" as a string
     */
    private String generateRandomChordType() {
        int majorOrMinor = rand.nextInt(2);
        if (majorOrMinor == 0) {
            return "major";
        } else {
            return "minor";
        }
    }

    /**
     * Generates a "valid chord". That is, its name is valid and its notes match its name.
     * @return A Pair object of Chord Name, Notes in Chord
     */
    private Pair<String, ArrayList<Note>> generateValidChord() {
        String chordType = generateRandomChordType();

        boolean validChord = false;
        String chordName = "";
        ArrayList<Note> chordNotes = null;

        while (!validChord) {
            Note startNote = getRandomNote();
            if (ChordUtil.getChord(startNote, chordType) != null) {
                validChord = true;
                chordNotes = ChordUtil.getChord(startNote, chordType);
                chordName = OctaveUtil.removeOctaveSpecifier(startNote.getNote()) + " " + chordType;
            }
        }

        return new Pair<String, ArrayList<Note>>(chordName, chordNotes);
    }


    @Override
    void resetInputs() {

    }
}
