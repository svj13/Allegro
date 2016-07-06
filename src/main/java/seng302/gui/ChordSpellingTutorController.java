package seng302.gui;

import java.util.ArrayList;
import java.util.Collections;
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

    /**
     * What type the generated chords are, i.e. major, minor
     */
    private String validChords = "all";

    public void create(Environment env) {
        super.create(env);
        initialiseQuestionSelector();
        chordTypes.getItems().addAll("all", "major", "minor");
        chordTypes.setOnAction(event -> {
            validChords = (String) chordTypes.getValue();
        });
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
    HBox generateQuestionPane(Pair data) {
        final HBox questionRow = new HBox();
        formatQuestionRow(questionRow);
        Label correctAnswer = new Label();
        Label question = new Label();

        final HBox inputs = new HBox();

        int questionType = rand.nextInt(2);

        if (questionType == 1) {
            //Use 'fake chords' with a ~0.25 probability
            if (rand.nextInt(4) == 0) {
                System.out.println("random chord!");
                ArrayList<Note> randomNotes = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    randomNotes.add(getRandomNote());
                }
                //have to somehow ensure that the generated notes are not a real chord
                data = new Pair("No Chord", randomNotes);
            }
        }

        final Pair finalData = data;
        final String chordName = (String) finalData.getKey();
        final ArrayList<Note> chordNotes = (ArrayList<Note>) finalData.getValue();

        Button skip = new Button("Skip");
        styleSkipButton(skip);

        skip.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Disables only input buttons
                disableButtons(questionRow, 1, 3);
                formatSkippedQuestion(questionRow);
                manager.questions -= 1;
                manager.add(finalData, 2);
                env.getRootController().setTabTitle("chordSpellingTutor", true);
                if (manager.answered == manager.questions) {
                    finished();
                }
            }
        });

        if (questionType == 0) {
            //Type A question
            correctAnswer = correctAnswer(chordAsString(chordNotes));
            question.setText(chordName);

            ComboBox<String> note1 = new ComboBox<String>();
            note1.getItems().addAll(generateOptions(chordNotes.get(0)));
            note1.setOnAction(event -> {
                String correctNote = OctaveUtil.removeOctaveSpecifier(chordNotes.get(0).getNote());
                boolean answeredCorrectly = isNoteCorrect(correctNote, note1.getValue());
                styleNoteInput(note1, answeredCorrectly);
                //check entire question
                if (isQuestionCompletelyAnswered(inputs)) {
                    //Style whole question as done
                    handleCompletedQuestion(questionRow, 1, finalData);
                }
            });
            ComboBox<String> note2 = new ComboBox<String>();
            note2.getItems().addAll(generateOptions(chordNotes.get(1)));
            note2.setOnAction(event -> {
                String correctNote = OctaveUtil.removeOctaveSpecifier(chordNotes.get(1).getNote());
                boolean answeredCorrectly = isNoteCorrect(correctNote, note2.getValue());
                styleNoteInput(note2, answeredCorrectly);
                //check entire question
                if (isQuestionCompletelyAnswered(inputs)) {
                    //Style whole question as done
                    handleCompletedQuestion(questionRow, 1, finalData);
                }
            });
            ComboBox<String> note3 = new ComboBox<String>();
            note3.getItems().addAll(generateOptions(chordNotes.get(2)));
            note3.setOnAction(event -> {
                String correctNote = OctaveUtil.removeOctaveSpecifier(chordNotes.get(2).getNote());
                boolean answeredCorrectly = isNoteCorrect(correctNote, note3.getValue());
                styleNoteInput(note3, answeredCorrectly);
                //check entire question
                if (isQuestionCompletelyAnswered(inputs)) {
                    //Style whole question as done
                    handleCompletedQuestion(questionRow, 1, finalData);
                }
            });

            inputs.getChildren().add(note1);
            inputs.getChildren().add(note2);
            inputs.getChildren().add(note3);


        } else {
            //Type B question
            correctAnswer = correctAnswer(chordName);
            question.setText(chordAsString(chordNotes));

            //Use 'fake chords' with a ~0.25 probability
            if (rand.nextInt(4) == 0) {
                ArrayList<Note> randomNotes = new ArrayList<>();
                randomNotes.add(getRandomNote());
                data = new Pair("No Chord", randomNotes);
            }

            ComboBox<String> possibleNames = new ComboBox<String>();

            for (String chord : generateTypeTwoOptions(chordName)) {
                possibleNames.getItems().add(chord);
            }

            possibleNames.setOnAction(event -> {
                //Check if the answer is correct
                boolean answeredCorrectly = possibleNames.getValue().equals(chordName);
                styleNoteInput(possibleNames, answeredCorrectly);
                handleCompletedQuestion(questionRow, 2, finalData);
            });

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
        if (validChords.equals("all")) {
            int majorOrMinor = rand.nextInt(2);
            if (majorOrMinor == 0) {
                return "major";
            } else {
                return "minor";
            }
        } else {
            return validChords;
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
     * Randomly selects 7 chord names, and creates a list of them along with the real chord name.
     *
     * @param correctAnswer The correct chord name, to be included in the list
     * @return An arraylist of chord names
     */
    private ArrayList<String> generateTypeTwoOptions(String correctAnswer) {
        ArrayList<String> chordNames = new ArrayList<String>();
        chordNames.add(correctAnswer);
        while (chordNames.size() < 8) {
            String chordName = generateValidChord().getKey();
            if (!chordNames.contains(chordName)) {
                chordNames.add(chordName);
            }
        }
        Collections.shuffle(chordNames);
        return chordNames;
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
     * @param isCorrect Whether or not the correct answer was selected
     */
    private void styleNoteInput(ComboBox<String> note, boolean isCorrect) {
        //check if the note is correct, style accordingly
        String selectedNote = note.getValue();
        if (isCorrect) {
            //style green
            note.setStyle("-fx-background-color: green");
        } else {
            //style red
            note.setStyle("-fx-background-color: red");
        }
        note.setDisable(true);
    }

    /**
     * Checks that all parts of a question have been answered.
     *
     * @param inputs The HBox containing the question parts
     * @return True if all parts are answered, false otherwise.
     */
    private boolean isQuestionCompletelyAnswered(HBox inputs) {
        boolean isAnswered = true;
        for (Object input : inputs.getChildren()) {
            ComboBox<String> inputCombo = (ComboBox<String>) input;
            if (inputCombo.getValue() == null) {
                isAnswered = false;
            }
        }
        return isAnswered;
    }

    /**
     * Checks if all inputs to a type 1 question were correct.
     * @param inputs The HBox containing the combo box inputs
     * @return True if all inputs were correct, false otherwise
     */
    private boolean isTypeOneQuestionCorrect(HBox inputs) {
        int correctParts = 0;
        for (Object input : inputs.getChildren()) {
            ComboBox<String> thisInput = (ComboBox<String>) input;
            if (thisInput.getStyle().contains("green")) {
                //this part was correct
                correctParts += 1;
            }

        }
        if (correctParts == 3) {
            return true;
        } else {
            return false;
        }

    }

    private boolean isTypeTwoQuestionCorrect(HBox inputs) {
        return inputs.getChildren().get(0).getStyle().contains("green");
    }

    private void handleCompletedQuestion(HBox completedQuestion, int questionType, Pair data) {
        HBox inputs = (HBox) completedQuestion.getChildren().get(1);
        Boolean wasAnsweredCorrectly;
        if (questionType == 1) {
            //check question of type 1
            wasAnsweredCorrectly = isTypeOneQuestionCorrect(inputs);
        } else {
            //check question of type 2
            //Placeholder, currently.
            wasAnsweredCorrectly = isTypeTwoQuestionCorrect(inputs);
        }

        if (wasAnsweredCorrectly) {
            manager.add(data, 1);
            formatCorrectQuestion(completedQuestion);
        } else {
            manager.add(data, 0);
            formatIncorrectQuestion(completedQuestion);

            //Shows the correct answer
            completedQuestion.getChildren().get(3).setVisible(true);
        }
        manager.answered += 1;
        //Disables skip button
        completedQuestion.getChildren().get(2).setDisable(true);
        if (manager.answered == manager.questions) {
            finished();
        }

    }

    private void finished() {
        userScore = getScore(manager.correct, manager.answered);
        outputText = String.format("You have finished the tutor.\n" +
                        "You answered %d questions, and skipped %d questions.\n" +
                        "You answered %d questions correctly, %d questions incorrectly.\n" +
                        "This gives a score of %.2f percent.",
                manager.questions, manager.skipped,
                manager.correct, manager.incorrect, userScore);

        if (projectHandler.currentProjectPath != null) {
            projectHandler.saveSessionStat("chordSpelling", record.setStats(manager.correct, manager.getTempIncorrectResponses().size(), userScore));
            projectHandler.saveCurrentProject();
            outputText += "\nSession auto saved";
        }
        env.getRootController().setTabTitle("chordSpellingTutor", false);
        // Sets the finished view
        resultsContent.setText(outputText);

        paneQuestions.setVisible(false);
        paneResults.setVisible(true);
        questionRows.getChildren().clear();

        Button retestBtn = new Button("Retest");
        Button clearBtn = new Button("Clear");
        final Button saveBtn = new Button("Save");


        clearBtn.setOnAction(event -> {
            manager.saveTempIncorrect();
            paneResults.setVisible(false);
            paneQuestions.setVisible(true);

        });
        paneResults.setPadding(new Insets(10, 10, 10, 10));
        retestBtn.setOnAction(event -> {
            paneResults.setVisible(false);
            paneQuestions.setVisible(true);
            retest();

        });
        saveBtn.setOnAction(event -> saveRecord());


        if (manager.getTempIncorrectResponses().size() > 0) {
            //Can re-test
            buttons.getChildren().setAll(retestBtn, clearBtn, saveBtn);
        } else {
            //Perfect score
            buttons.getChildren().setAll(clearBtn, saveBtn);
        }

        HBox.setMargin(retestBtn, new Insets(10, 10, 10, 10));
        HBox.setMargin(clearBtn, new Insets(10, 10, 10, 10));
        HBox.setMargin(saveBtn, new Insets(10, 10, 10, 10));
        // Clear the current session
        manager.resetStats();
    }


    @Override
    /**
     * Resets the settings input combo boxes
     * To be implemented
     */
    void resetInputs() {

    }
}
