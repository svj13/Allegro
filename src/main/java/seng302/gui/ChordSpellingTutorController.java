package seng302.gui;

import java.util.ArrayList;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
        int type = rand.nextInt(2);
        if (type == 0) {
            //First type of question
            int majorOrMinor = rand.nextInt(2);
            String chordType;
            if (majorOrMinor == 0) {
                chordType = "major";
            } else {
                chordType = "minor";
            }

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

            return generateQuestionA(chordName, chordNotes);
        } else {
            //Second type of question
            return generateQuestionB();
        }

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
    HBox generateQuestionPane(Pair data) {
        return null;
    }

    HBox generateQuestionA(String chordName, ArrayList<Note> chordNotes) {
        System.out.println("Given the chord name " + chordName + ", the expected notes are");
        for (Note note : chordNotes) {
            System.out.println(OctaveUtil.removeOctaveSpecifier(note.getNote()));
        }
        return new HBox();
    }

    HBox generateQuestionB() {
        return new HBox();
    }

    @Override
    void resetInputs() {

    }
}
