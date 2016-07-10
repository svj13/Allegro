package seng302.gui;


import org.controlsfx.control.RangeSlider;
import org.controlsfx.control.spreadsheet.StringConverterWithFormat;

import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import seng302.Environment;
import seng302.data.Interval;
import seng302.data.KeySignature;
import seng302.data.Note;
import seng302.data.Term;
import seng302.utility.TutorRecord;

import javax.swing.text.StyledEditorKit;

public class KeySignaturesTutorController extends TutorController {

    @FXML
    AnchorPane KeySignaturesTab;

    @FXML
    Button btnGo;

    @FXML
    ComboBox<String> scaleBox;

    @FXML
    ComboBox<String> formBox;

    @FXML
    ComboBox<String> answerBox;

    private Random rand;

    private ArrayList<String> majorSharps = new ArrayList<String>(Arrays.asList("C", "G", "D", "A", "E", "B", "F#", "C#"));
    private ArrayList<String> majorFlats = new ArrayList<String>(Arrays.asList("Cb", "Gb", "Db", "Ab", "Eb", "Bb", "F", "C"));
    private ArrayList<String> minorSharps = new ArrayList<String>(Arrays.asList("A", "E", "B", "F#", "C#", "G#", "D#", "A#"));
    private ArrayList<String> minorFlats = new ArrayList<String>(Arrays.asList("Ab", "Eb", "Bb", "F", "C", "G", "D", "A"));


    @FXML
    /**
     * Run when the go button is pressed. Creates a new tutoring session.
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
     * Initialises certain GUI elements
     */
    public void create(Environment env) {
        super.create(env);
        initialiseQuestionSelector();
        rand = new Random();
        scaleBox.getItems().addAll("Major", "Minor", "Both");
        scaleBox.getSelectionModel().selectFirst();
        formBox.getItems().addAll("Listing sharps/flats", "Number of sharps/flats");
        formBox.getSelectionModel().selectFirst();
        answerBox.getItems().addAll("Show Key Signature", "Show Name");
        answerBox.getSelectionModel().selectFirst();
    }

    /**
     * Prepares a new question
     *
     * @return a question pane containing the question information
     */
    public HBox setUpQuestion() {
        String scaletype;
        int questionType;
        boolean answerType;


        //figure out the scale is wanted to be tested
        if (scaleBox.getValue().equals("Major")) {
            scaletype = "major";
        } else if (scaleBox.getValue().equals("Minor")) {
            scaletype = "minor";
        } else {
            scaletype = "both";
        }

        //figure out the type of question wanted
        if (answerBox.getValue().equals("Show Key Signature")) {
            questionType = 0;
        } else {
            questionType = 1;
        }

        if (formBox.getValue().equals("Listing sharps/flats")) {
            answerType = true;

        } else {
            answerType = false;
        }


        return generateQuestionPane(new Pair<String, Pair>(scaletype, new Pair<Integer, Boolean>(questionType, answerType)));
    }



    /**
     * Creates a GUI pane for a single question
     * y
     */
    //@Override
    public HBox generateQuestionPane( final Pair pair) {
        Boolean isMajor = false;

        final HBox questionRow = new HBox();
        formatQuestionRow(questionRow);
        final ComboBox<String> options;
        Random rand = new Random();

        Button skip = new Button("Skip");
        styleSkipButton(skip);
        Label questionText = new Label();
        List<String> keysAsArray;
        final String question;

        System.out.println(((Pair) pair.getValue()).getValue());
        Random bRand = new Random();

        int random = 2;
        if(pair.getKey().equals("both")) {
            random = bRand.nextInt(2);
        }

        if ((pair.getKey().equals("major")) || (random == 0) ) {
            isMajor = true;
            keysAsArray = new ArrayList<String>(KeySignature.getMajorKeySignatures().keySet());
            questionText.setText(" Major");
            question = keysAsArray.get(rand.nextInt(keysAsArray.size()));
            options = generateMajorChoices(question, (Boolean) (((Pair) pair.getValue()).getValue()));

        } else if ((pair.getKey().equals("minor")) || (random == 1)) {
            keysAsArray = new ArrayList<String>(KeySignature.getMinorKeySignatures().keySet());
            question = keysAsArray.get(rand.nextInt(keysAsArray.size()));
            questionText.setText(" Minor");
            options = generateMinorChoices(question, (Boolean) (((Pair) pair.getValue()).getValue()));

        } else {

            System.err.println("something is broken");
            options = null;
            question = null;
        }

        questionText.setText(question.concat(questionText.getText()));
        options.setPrefHeight(30);


        final Boolean fIsMajor = isMajor;

        skip.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Disables only input buttons
                disableButtons(questionRow, 1, 3);
                formatSkippedQuestion(questionRow);
                manager.questions -= 1;
                manager.add(pair, 2);
                String correctAnswer;

                if(fIsMajor){
                    correctAnswer = KeySignature.getMajorKeySignatures().get(question).getNotes().toString();

                }else {
                    correctAnswer = KeySignature.getMinorKeySignatures().get(question).getNotes().toString();
                }




                String[] recordQuestion = new String[]{
                        String.format("Keys signature of %s %s", question, pair.getKey()),
                        correctAnswer
                };
                projectHandler.saveTutorRecords("keySignature", record.addSkippedQuestion(recordQuestion));
                env.getRootController().setTabTitle("keySignatureTutor", true);
                if (manager.answered == manager.questions) {
                    finished();
                }
            }
        });

        options.setOnAction(new EventHandler<ActionEvent>() {
            // This handler colors the GUI depending on the user's input
            public void handle(ActionEvent event) {
                disableButtons(questionRow, 1, 3);
                boolean isCorrect = false;
                System.out.println(question);

                if(questionCorrectCheck(((Boolean) ((Pair) pair.getValue()).getValue()),fIsMajor,question,options.getValue())){
                    isCorrect = true;
                    formatCorrectQuestion(questionRow);
                    manager.add(pair, 1);

                }else{
                    //correctAnswer.setVisible(true);
                    formatIncorrectQuestion(questionRow);
                    manager.add(pair, 0);
                }

//                if(fIsMajor) {
//                    if (options.getValue().equals(KeySignature.getMajorKeySignatures().get(question).getNotes().toString())) {
//                        isCorrect = true;
//                        formatCorrectQuestion(questionRow);
//                        manager.add(pair, 1);
//                    }else{
//                        //correctAnswer.setVisible(true);
//                        formatIncorrectQuestion(questionRow);
//                        manager.add(pair, 0);
//                    }
//                }else{
//                    if (options.getValue().equals(KeySignature.getMinorKeySignatures().get(question).getNotes().toString())) {
//                        isCorrect = true;
//                        formatCorrectQuestion(questionRow);
//                        manager.add(pair, 1);
//                    }else{
//                        //correctAnswer.setVisible(true);
//                        formatIncorrectQuestion(questionRow);
//                        manager.add(pair, 0);
//                    }
//                }

                manager.answered += 1;
                // Sets up the question to be saved to the record
                String[] recordQuestion = new String[] {
                        String.format("Key signature of %s %s", question, pair.getKey()),
                        options.getValue(),
                        String.valueOf(isCorrect)
                };
                projectHandler.saveTutorRecords("keySignature", record.addQuestionAnswer(recordQuestion));
                env.getRootController().setTabTitle("keySignatureTutor", true);
                // Shows the correct answer
                if (manager.answered == manager.questions) {
                    finished();
                }
            }
        });

        questionRow.getChildren().add(0, questionText);
        questionRow.getChildren().add(1, options);
        questionRow.getChildren().add(2, skip);

        questionRow.prefWidthProperty().bind(paneQuestions.prefWidthProperty());
        return questionRow;
    }


    public Boolean questionCorrectCheck(Boolean showKeysignature, Boolean isMajor, String question, String givenAnswer){

//        System.out.println("keysign " + showKeysignature);
//        System.out.println("isMajor " + isMajor);
//        System.out.println("question " + question);
//        System.out.println("given answer " + givenAnswer);
//        System.out.println("--------------");

        //if display keySignatures in answer
        if (showKeysignature){
            if(isMajor){
                if(givenAnswer.equals(KeySignature.getMajorKeySignatures().get(question).getNotes().toString())){
                    return true;
                }

            }else{
                if(givenAnswer.equals(KeySignature.getMinorKeySignatures().get(question).getNotes().toString())){
                    return true;
                }
            }
        }
        //if displaying the number of sharps/flats in answer
        else{
            if(isMajor){
                if ((KeySignature.getMajorKeySignatures().get(question)).getNotes().get(0).contains("#")) {
                    if(givenAnswer.equals((KeySignature.getMajorKeySignatures().get(question)).getNumberOfSharps() + "#")){
                        return true;
                    }
                }else if((KeySignature.getMajorKeySignatures().get(question)).getNotes().get(0).contains("b")){
                    if(givenAnswer.equals((KeySignature.getMajorKeySignatures().get(question)).getNumberOfFlats() + "b")){
                        return true;
                    }
                }else {
                    if (givenAnswer.startsWith("0")) {
                        return true;
                    }
                }

            } else{
                if ((KeySignature.getMinorKeySignatures().get(question)).getNotes().get(0).contains("#")) {
                    if(givenAnswer.equals((KeySignature.getMinorKeySignatures().get(question)).getNumberOfSharps() + "#")){
                        return true;
                    }
                }else if((KeySignature.getMinorKeySignatures().get(question)).getNotes().get(0).contains("b")){
                    if(givenAnswer.equals((KeySignature.getMinorKeySignatures().get(question)).getNumberOfFlats() + "b")){
                        return true;
                    }
                }else{//if it is an A minor or C major
                    if(givenAnswer.startsWith("0")){
                        return true;
                    }
                }
            }

        }
        return false;

    }

    private ComboBox<String> generateMajorChoices(String scale, Boolean keysignature) {
        ComboBox<String> options = new ComboBox<String>();
        options.setPrefHeight(30);

        ArrayList<List> optionsList = new ArrayList<List>();
        ArrayList<String> optionsListNum = new ArrayList<String>();
        if ((KeySignature.getMajorKeySignatures().get(scale)).getNotes().get(0).contains("#")) {

            for (String keySig : majorSharps) {
                if (keysignature == true) {
                    optionsList.add((KeySignature.getMajorKeySignatures().get(keySig)).getNotes());
                } else {
                    optionsListNum.add((KeySignature.getMajorKeySignatures().get(keySig)).getNumberOfSharps() + "#");
                }

            }
        } else {
            for (String keySig : majorFlats) {
                if (keysignature == true) {
                    optionsList.add((KeySignature.getMajorKeySignatures().get(keySig)).getNotes());
                } else {
                    optionsListNum.add((KeySignature.getMajorKeySignatures().get(keySig)).getNumberOfFlats() + "b");
                }
            }
        }


        if (keysignature == true) {
            Collections.shuffle(optionsList);
            for (List option : optionsList) {
                options.getItems().add(option.toString());
            }
        } else {
            Collections.shuffle(optionsListNum);
            for (String option : optionsListNum) {
                options.getItems().add(option);
            }
        }

        return options;
    }




    private ComboBox<String> generateMinorChoices(String scale, Boolean keysignature) {
        ComboBox<String> options = new ComboBox<String>();
        options.setPrefHeight(30);

        ArrayList<List> optionsList = new ArrayList<List>();
        ArrayList<String> optionsListNum = new ArrayList<String>();
        if((KeySignature.getMinorKeySignatures().get(scale)).getNotes().get(0).contains("#")){

            for(String keySig : minorSharps){
                if(keysignature == true) {
                    optionsList.add((KeySignature.getMinorKeySignatures().get(keySig)).getNotes());
                }else{
                    optionsListNum.add((KeySignature.getMinorKeySignatures().get(keySig)).getNumberOfSharps() + "#");
                }
            }
        }else{
            for(String keySig : minorFlats){
                if(keysignature == true) {
                    optionsList.add((KeySignature.getMinorKeySignatures().get(keySig)).getNotes());
                }else{
                    optionsListNum.add((KeySignature.getMinorKeySignatures().get(keySig)).getNumberOfFlats() + "b");
                }
            }
        }

        if(keysignature == true) {
            Collections.shuffle(optionsList);
            for (List option : optionsList) {
                options.getItems().add(option.toString());
            }
        }else{
            Collections.shuffle(optionsListNum);
            for (String option : optionsListNum) {
                options.getItems().add(option);
            }
        }

        return options;
    }


    /**
     * This function is run once a tutoring session has been completed.
     */
    public void finished() {
        env.getPlayer().stop();
        userScore = getScore(manager.correct, manager.answered);
        outputText = String.format("You have finished the tutor.\n" +
                        "You answered %d questions, and skipped %d questions.\n" +
                        "You answered %d questions correctly, %d questions incorrectly.\n" +
                        "This gives a score of %.2f percent.",
                manager.questions, manager.skipped,
                manager.correct, manager.incorrect, userScore);
        if(projectHandler.currentProjectPath != null) {
            projectHandler.saveSessionStat("keySignature", record.setStats(manager.correct, manager.getTempIncorrectResponses().size(), userScore));
            projectHandler.saveCurrentProject();
            outputText += "\nSession auto saved.";
        }
        env.getRootController().setTabTitle("keySignatureTutor", false);
        // Sets the finished view
        resultsContent.setText(outputText);

        paneQuestions.setVisible(false);
        paneResults.setVisible(true);
        questionRows.getChildren().clear();

        Button retestBtn = new Button("Retest");
        Button clearBtn  = new Button("Clear");
        Button saveBtn = new Button("Save");

        clearBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                manager.saveTempIncorrect();
                paneResults.setVisible(false);
                paneQuestions.setVisible(true);
            }
        });

        paneResults.setPadding(new Insets(10, 10, 10, 10));
        retestBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                paneResults.setVisible(false);
                paneQuestions.setVisible(true);
                retest();

            }
        });
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                saveRecord();
            }
        });

        if (manager.getTempIncorrectResponses().size() > 0) {
            //Can re-test
            buttons.getChildren().setAll(retestBtn, clearBtn, saveBtn);
        } else {
            //Perfect score
            buttons.getChildren().setAll(clearBtn, saveBtn);
        }

        buttons.setMargin(retestBtn, new Insets(10,10,10,10));
        buttons.setMargin(clearBtn, new Insets(10,10,10,10));
        buttons.setMargin(saveBtn, new Insets(10,10,10,10));
        // Clear the current session
        manager.resetStats();
    }

    /**
     * Returns the option combo boxes to their default states.
     */
    public void resetInputs() {
        scaleBox.getSelectionModel().selectFirst();
        formBox.getSelectionModel().selectFirst();
        answerBox.getSelectionModel().selectFirst();
    }


}
