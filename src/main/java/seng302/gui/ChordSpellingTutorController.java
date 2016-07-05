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

            ComboBox<String> note1 = new ComboBox<String>();
            note1.getItems().addAll(generateOptions(chordNotes.get(0)));
            note1.setOnAction(event -> {
                String correctNote = OctaveUtil.removeOctaveSpecifier(chordNotes.get(0).getNote());
                styleNoteInput(note1, correctNote);
            });
            ComboBox<String> note2 = new ComboBox<String>();
            note2.getItems().addAll(generateOptions(chordNotes.get(1)));
            note2.setOnAction(event -> {
                String correctNote = OctaveUtil.removeOctaveSpecifier(chordNotes.get(1).getNote());
                styleNoteInput(note2, correctNote);
            });
            ComboBox<String> note3 = new ComboBox<String>();
            note3.getItems().addAll(generateOptions(chordNotes.get(2)));
            note3.setOnAction(event -> {
                String correctNote = OctaveUtil.removeOctaveSpecifier(chordNotes.get(2).getNote());
                styleNoteInput(note3, correctNote);
            });

            inputs.getChildren().add(note1);
            inputs.getChildren().add(note2);
            inputs.getChildren().add(note3);


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

    /**
     * Given a note to include, generates a set of 8 notes around it.
     *
     * @param correctNote The note that must be included
     * @return A list of 8 notes, with the required note included somewhere.
     */
    private ArrayList<String> generateOptions(Note correctNote) {
        ArrayList<String> surroundingNotes = new ArrayList<String>();
        int correctNoteMidi = correctNote.getMidi();

        //Generates a starting point in the range -7 to +7
        int randomStartingDifference = rand.nextInt(15) - 7;

        String startingNoteMidi = Integer.toString(correctNoteMidi + randomStartingDifference);

        Note startingNote = Note.lookup(startingNoteMidi);

        if (randomStartingDifference > 0) {
            //goes to above the note
            //example, if you're +7 above the note, you take that one and go down 7 semitones?
            for (int i = 0; i < 8; i++) {
                Note thisNote = startingNote.semitoneDown(i);
                surroundingNotes.add(randomiseNoteName(thisNote, correctNote));
            }
        } else {
            //goes to below the note
            //so, if you're -7 below the note, you take that and go up 7 semitones
            for (int i = 0; i < 8; i++) {
                Note thisNote = startingNote.semitoneUp(i);
                surroundingNotes.add(randomiseNoteName(thisNote, correctNote));
            }
        }

        return surroundingNotes;

    }

    /**
     * Using random numbers, "randomises" whether a note will display with a sharp or flat. Only
     * uses sharps/flats when applicable.
     *
     * @param noteToRandomise A Note which is being "randomised"
     * @param correctNote     A note we don't want to randomise, ever
     * @return A 'randomised' string representation of a note.
     */
    private String randomiseNoteName(Note noteToRandomise, Note correctNote) {
        String noteName = OctaveUtil.removeOctaveSpecifier(noteToRandomise.getNote());

        //As the default is sharp, we randomise to get some flats
        if (!noteToRandomise.equals(correctNote) && rand.nextInt(2) != 0) {
            if (!noteToRandomise.simpleEnharmonic().equals("")) {
                noteName = OctaveUtil.removeOctaveSpecifier(noteToRandomise.simpleEnharmonic());
            }
        }
        return noteName;
    }

    /**
     * Checks if the note a user has selected is the correct note.
     * Essentially just a nice comparison function.
     * @param correctNote The correct value
     * @param selectedNote The value the user has selected
     * @return True if the user selected the right note, False otherwise
     */
    private Boolean isNoteCorrect(String correctNote, String selectedNote) {
        return correctNote.equals(selectedNote);
    }

    /**
     * Once a user has selected a note (type 1 questions), it is styled.
     * The relevant combo box is styled green if the answer was correct, red if it was incorrect.
     * @param note The combo box a note was selected from
     * @param correctNote The value that should have been selected
     */
    private void styleNoteInput(ComboBox<String> note, String correctNote) {
        //check if the note is correct, style accordingly
        String selectedNote = note.getValue();
        if (isNoteCorrect(correctNote, selectedNote)) {
            //style green
            note.setStyle("-fx-background-color: green");
            note.setDisable(true);
        } else {
            //style red
            note.setStyle("-fx-background-color: red");
            note.setDisable(true);
        }

    }


    @Override
    void resetInputs() {

    }
}
