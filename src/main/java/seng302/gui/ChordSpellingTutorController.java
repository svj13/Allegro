package seng302.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
 * This class is responsible for the GUI and logic of the Chord Spelling Tutor.
 * It works similarly to all other tutors, and is based off the abstract TutorController class.
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

    @FXML
    CheckBox allowFalseChords;

    private Random rand;

    private final String typeOneText = "Spell the chord with the name %s";

    private final String typeTwoText = "Name the chord with the notes %s";

    private String[] validChordNames = {"major", "minor", "minor 7th",
            "major 7th", "seventh", "diminished", "half diminished 7th", "diminished 7th"};

    /**
     * What type the generated chords are, i.e. major, minor
     */
    private String validChords = "all";

    /**
     * Sets up the tutoring environment
     *
     * @param env The environment the tutor is being run in
     */
    public void create(Environment env) {
        super.create(env);
        initialiseQuestionSelector();
        chordTypes.getItems().add("all");
        chordTypes.getItems().addAll(validChordNames);
        chordTypes.setOnAction(event -> {
            validChords = (String) chordTypes.getValue();
        });
        chordTypes.getSelectionModel().selectFirst();
        numEnharmonics.getItems().addAll("only one", "all");
        numEnharmonics.getSelectionModel().selectFirst();
        rand = new Random();
    }

    @FXML
    /**
     * When the go button is pressed, a new tutoring session is launched
     */
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

        //Generates either 1 or 2: The type of question
        int questionType = rand.nextInt(2) + 1;

        Pair question = new Pair(randomChord, questionType);

        return generateQuestionPane(question);
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
    /**
     * Generates a GUI containing question data.
     * Needs to be broken up into parts - currently 160 lines long
     */
    HBox generateQuestionPane(Pair data) {
        final HBox questionRow = new HBox();
        formatQuestionRow(questionRow);
        Label correctAnswer = new Label();
        Label question = new Label();

        final HBox inputs = new HBox();

        int questionType = (int) data.getValue();

        if (questionType == 2) {
            //Use 'fake chords' with a ~0.25 probability for type 2 questions
            if (allowFalseChords.isSelected() && rand.nextInt(4) == 0) {
                ArrayList<Note> randomNotes = new ArrayList<>();
                ArrayList<Integer> noteMidis = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    Note randomNote = getRandomNote();
                    randomNotes.add(randomNote);
                    noteMidis.add(randomNote.getMidi());
                }
                try {
                    ChordUtil.getChordName(noteMidis, true);
                } catch (IllegalArgumentException notAChord) {
                    //Only use it if it's not a valid chord
                    data = new Pair("No Chord", randomNotes);
                }
            }
        }

        final Pair finalData = (Pair) data.getKey();
        final String chordName = (String) finalData.getKey();
        final ArrayList<Note> chordNotes = (ArrayList<Note>) finalData.getValue();

        Button skip = new Button("Skip");
        styleSkipButton(skip);

        if (questionType == 1) {
            //Type one questions
            String[] selectedNotes = new String[3];
            String[] correctNotes = new String[3];

            for (int i = 0; i < 3; i++) {
                correctNotes[i] = OctaveUtil.removeOctaveSpecifier(chordNotes.get(i).getNote());
            }


            skip.setOnAction(event -> {
                String[] questionInfo = new String[]{
                        String.format(typeOneText, chordName),
                        chordAsString(chordNotes)

                };

                handleSkippedQuestion(questionInfo, questionRow, finalData, questionType);
            });


            //Type A question
            correctAnswer = correctAnswer(chordAsString(chordNotes));
            question.setText(chordName);

            ComboBox<String> note1 = new ComboBox<String>();
            note1.getItems().addAll(generateOptions(chordNotes.get(0)));
            note1.setOnAction(event -> {
                //Store the answer
                String selectedNote = note1.getValue();
                selectedNotes[0] = selectedNote;

                //Check if it's correct and style appropriately
                boolean answeredCorrectly = isNoteCorrect(correctNotes[0], selectedNote);
                styleNoteInput(note1, answeredCorrectly);

                //check entire question
                if (isQuestionCompletelyAnswered(inputs)) {
                    //Style whole question as done
                    String selection = String.join(" ", selectedNotes);
                    handleCompletedQuestion(questionRow, 1, finalData, selection);
                }
            });
            ComboBox<String> note2 = new ComboBox<String>();
            note2.getItems().addAll(generateOptions(chordNotes.get(1)));
            note2.setOnAction(event -> {
                //Store the answer
                String selectedNote = note2.getValue();
                selectedNotes[1] = selectedNote;

                //Check if it's correct and style appropriately
                boolean answeredCorrectly = isNoteCorrect(correctNotes[1], selectedNote);
                styleNoteInput(note2, answeredCorrectly);

                //check entire question
                if (isQuestionCompletelyAnswered(inputs)) {
                    //Style whole question as done
                    String selection = String.join(" ", selectedNotes);
                    handleCompletedQuestion(questionRow, 1, finalData, selection);
                }
            });
            ComboBox<String> note3 = new ComboBox<String>();
            note3.getItems().addAll(generateOptions(chordNotes.get(2)));
            note3.setOnAction(event -> {
                //Store the answer
                String selectedNote = note3.getValue();
                selectedNotes[2] = selectedNote;

                //Check if it's correct and style appropriately
                boolean answeredCorrectly = isNoteCorrect(correctNotes[2], selectedNote);
                styleNoteInput(note3, answeredCorrectly);

                //check entire question
                if (isQuestionCompletelyAnswered(inputs)) {
                    //Style whole question as done
                    String selection = String.join(" ", selectedNotes);
                    handleCompletedQuestion(questionRow, questionType, finalData, selection);
                }
            });

            inputs.getChildren().add(note1);
            inputs.getChildren().add(note2);
            inputs.getChildren().add(note3);


        } else {
            //Type 2 questions
            skip.setOnAction(event -> {
                String[] questionInfo = new String[]{
                        String.format(typeTwoText, chordAsString(chordNotes)),
                        chordName

                };
                handleSkippedQuestion(questionInfo, questionRow, finalData, questionType);
            });

            correctAnswer = correctAnswer(chordName);
            question.setText(chordAsString(chordNotes));

            ComboBox<String> possibleNames = new ComboBox<String>();

            for (String chord : generateTypeTwoOptions(chordName)) {
                possibleNames.getItems().add(chord);
            }

            possibleNames.setOnAction(event -> {
                //Check if the answer is correct
                String selection = possibleNames.getValue();
                boolean answeredCorrectly = selection.equals(chordName);
                styleNoteInput(possibleNames, answeredCorrectly);
                handleCompletedQuestion(questionRow, questionType, finalData, selection);
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
            int chordType = rand.nextInt(validChordNames.length);
            return validChordNames[chordType];
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

        //"No Chord" is always an option, if false chords is 'turned on'.
        if (allowFalseChords.isSelected()) {
            chordNames.add("No Chord");
        }

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
     * @return 0 if all inputs are wrong, 1 if all are correct, and 2 if some inputs are correct
     */
    private int typeOneQuestionCorrectness(HBox inputs) {
        int correctParts = 0;
        for (Object input : inputs.getChildren()) {
            ComboBox<String> thisInput = (ComboBox<String>) input;
            if (thisInput.getStyle().contains("green")) {
                //this part was correct
                correctParts += 1;
            }

        }
        if (correctParts == 3) {
            //All correct
            return 1;
        } else if (correctParts == 0) {
            //All incorrect
            return 0;
        } else {
            //Some correct
            return 2;
        }

    }

    /**
     * Checks whether the user correctly answered a type 2 question or not
     * @param inputs The HBox containing the user's input
     * @return true if the user was correct, false otherwise
     */
    private boolean isTypeTwoQuestionCorrect(HBox inputs) {
        return inputs.getChildren().get(0).getStyle().contains("green");
    }


    /**
     * This method is called when a question is completed.
     * It styles the GUI based on the correctness of the question, and also saves the question's
     * information.
     * @param completedQuestion The HBox containing the GUI of the question
     * @param questionType Whether it was a type 1 or type 2 question
     * @param data The question data - chord name and notes
     * @param selectedAnswer - The answer that the user input. For saving
     */
    private void handleCompletedQuestion(HBox completedQuestion, int questionType, Pair data, String selectedAnswer) {
        HBox inputs = (HBox) completedQuestion.getChildren().get(1);
        String questionText;
        Boolean answeredCorrectly = false;

        //0 for wrong, 1 for right, 2 for partially correct
        int correctnessValue;

        if (questionType == 1) {
            //check question of type 1
            correctnessValue = typeOneQuestionCorrectness(inputs);
            questionText = String.format(typeOneText, data.getKey());
        } else {
            //check question of type 2
            if (isTypeTwoQuestionCorrect(inputs)) {
                correctnessValue = 1;
            } else {
                correctnessValue = 0;
            }
            questionText = String.format(typeTwoText, chordAsString((ArrayList<Note>) data.getValue()));
        }

        applyFormatting(completedQuestion, correctnessValue);

        if (correctnessValue == 0 || correctnessValue == 2) {
            manager.add(new Pair<Pair, Integer>(data, questionType), 0);

            //Shows the correct answer
            completedQuestion.getChildren().get(3).setVisible(true);
        } else {
            answeredCorrectly = true;
            manager.add(new Pair<Pair, Integer>(data, questionType), 1);
        }

        //Disables skip button
        completedQuestion.getChildren().get(2).setDisable(true);

        String[] question = new String[]{
                questionText,
                selectedAnswer,
                answeredCorrectly.toString()
        };
        projectHandler.saveTutorRecords("spelling", record.addQuestionAnswer(question));

        updateManagerCompletedQuestion();
    }

    /**
     * @param question    The HBox to be styled
     * @param formatStyle 0 for wrong, 1 for right, 2 for partially correct
     */
    private void applyFormatting(HBox question, int formatStyle) {
        switch (formatStyle) {
            case 0:
                formatIncorrectQuestion(question);
                break;
            case 1:
                formatCorrectQuestion(question);
                break;
            case 2:
                formatPartiallyCorrectQuestion(question);
                break;
        }

    }

    /**
     * Once a question has been completely answered, the question manager is updated.
     */
    private void updateManagerCompletedQuestion() {
        manager.answered += 1;

        if (manager.answered == manager.questions) {
            finished();
        }
    }

    /**
     * Runs once the tutoring session has finished.
     * Shows the statistics of the session, and saves information.
     */
    private void finished() {
        userScore = getScore(manager.correct, manager.answered);
        outputText = String.format("You have finished the tutor.\n" +
                        "You answered %d questions, and skipped %d questions.\n" +
                        "You answered %d questions correctly, %d questions incorrectly.\n" +
                        "This gives a score of %.2f percent.",
                manager.questions, manager.skipped,
                manager.correct, manager.incorrect, userScore);

        if (projectHandler.currentProjectPath != null) {
            projectHandler.saveSessionStat("spelling", record.setStats(manager.correct, manager.getTempIncorrectResponses().size(), userScore));
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

    /**
     * Run when a user elects to skip a question. Saves that question to the manager as skipped, and
     * writes it to the project.
     *
     * @param questionInfo The textual representation of the skipped question
     * @param questionRow  The GUI element of the question, to be styled
     * @param finalData    The information about the question, to be stored.
     * @param questionType Whether the question was of type 1 or type 2.
     */
    private void handleSkippedQuestion(String[] questionInfo, HBox questionRow, Pair finalData, int questionType) {
        // Disables only input buttons
        disableButtons(questionRow, 1, 3);
        formatSkippedQuestion(questionRow);
        manager.questions -= 1;
        manager.add(new Pair<Pair, Integer>(finalData, questionType), 2);

        projectHandler.saveTutorRecords("spelling", record.addSkippedQuestion(questionInfo));

        env.getRootController().setTabTitle("chordSpellingTutor", true);
        if (manager.answered == manager.questions) {
            finished();
        }
    }


    @Override
    /**
     * Resets the settings inputs
     */
    void resetInputs() {
        chordTypes.getSelectionModel().selectFirst();
        numEnharmonics.getSelectionModel().selectFirst();
        allowFalseChords.setSelected(false);
    }
}
