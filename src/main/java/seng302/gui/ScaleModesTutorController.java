package seng302.gui;

import java.util.*;

import com.jfoenix.controls.JFXSlider;
import com.sun.javafx.sg.prism.NGShape;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import seng302.Environment;
import seng302.command.Scale;
import seng302.data.Note;
import seng302.command.MajorModes;
import seng302.data.ModeHelper;
import seng302.utility.TutorRecord;
import seng302.utility.musicNotation.ChordUtil;
import seng302.utility.musicNotation.OctaveUtil;

/**
 * Created by Sarah on 28/08/16.
 */
public class ScaleModesTutorController extends TutorController {
    @FXML
    JFXSlider numQuestions;

    private Random rand;
    private final String typeOneText = "What is the mode of %s if it is of degree %s?";
    private final String typeTwoText = "What is the parent major scale of %s %s?";
    private Integer type = 1;
    private ArrayList<String> majorNotes = new ArrayList<>(Arrays.asList("C", "G", "D", "A", "E",
            "B", "F", "Bb", "Eb", "Ab", "Db", "Gb"));



    public void create(Environment env) {
        super.create(env);
        initialiseQuestionSelector();
        rand = new Random();
    }


    @FXML
    /**
     * When the go button is pressed, a new tutoring session is launched
     */
    private void goAction(ActionEvent event) {
        record = new TutorRecord();
        paneInit.setVisible(false);
        paneQuestions.setVisible(true);
        paneResults.setVisible(false);
        manager.resetEverything();
        manager.questions = selectedQuestions;

        rand = new Random();
        List qPanes = new ArrayList<>();

        questionRows.getChildren().clear();
        for (int i = 0; i < manager.questions; i++) {
            HBox questionRow;
            if (rand.nextBoolean()) {
                type = 1;
                questionRow = generateQuestionPane(generateQuestionTypeOne());
            } else {
                type = 2;
                questionRow = generateQuestionPane(generateQuestionTypeTwo());
            }
            TitledPane qPane = new TitledPane("Question " + (i + 1), questionRow);
            qPane.setPadding(new Insets(2, 2, 2, 2));
            qPanes.add(qPane);
            questionRows.setMargin(questionRow, new Insets(10, 10, 10, 10));
        }
        qAccordion.getPanes().addAll(qPanes);
        questionRows.getChildren().add(qAccordion);

    }

    /**
     * Figures out what type the questions is based on the answer. If the answer is a type 2 answer
     * then the question is type 2. Otherwise it is type 1.
     *
     * @param questionAndAnswer The pair data for the question.
     * @return an Integer indicating question type 1 or 2
     */
    private Integer getTypeOfQuestion(Pair questionAndAnswer) {
        Pair question = (Pair) questionAndAnswer.getKey();
        try {
            Integer determineTypeOfQuestion = (Integer) question.getValue();
            return 1;
        } catch(Exception e) {
            return 2;
        }
    }

    /**
     * generates the questions for the tutor for question type 1
     * Type 1 question: What is the mode of NOTE major of degree #?
     * Type 1 answer: the answer options are the mode note and the mode type (e.g. D Dorian)
     * @param question
     * @return options
     *
     */

    private ComboBox generateChoices(Pair question) {
        ComboBox options = new ComboBox<>();
        ArrayList answers = new ArrayList(); //stores the options that appear in the tutor. One correct answer contained

        //splitting up the answer. e.g D and dorian
        String answer = (String)question.getValue();
        answers.add(answer); //adding correct answer to answers array
        Integer degree = (Integer)((Pair)question.getKey()).getValue();

        //splits the mode note into a separate string
        String modeNoteString = answer.split(" ")[0];
        Note modeNote = Note.lookup(OctaveUtil.addDefaultOctave(modeNoteString));
        //splits the the mode type into a separate string
        String modeTypeString = answer.split(" ")[1];

        //generate random boolean to determine semitones from root note. It will either be one semitone up from
        //the correct mode note or a semitone down. This is to prevent the user from identifying patterns to
        // narrow down the correct answer
        Boolean random = rand.nextBoolean();
        String note;
        if (random) {
            note = OctaveUtil.removeOctaveSpecifier(modeNote.semitoneUp(1).getNote());
        } else {
            note = OctaveUtil.removeOctaveSpecifier(modeNote.semitoneDown(1).getNote());
        }

        //adds the correct answer to the answer options
        answers.add(note + " " + modeTypeString);
        ArrayList<Integer> usedNum = new ArrayList<>();

        //for loop for generate random wrong answers for the options
        for (int i = 0; i < 3; i++) {
            Integer randomValueMode = rand.nextInt(7) + 1; //generates random number for accessing types of modes in ModeHelper

            //if it has already been generated, reshuffle
            while(randomValueMode.equals(degree) || usedNum.contains(randomValueMode)){
                randomValueMode = rand.nextInt(7) + 1;
            }
            usedNum.add(randomValueMode); //adds new value to options array
            String randomScaleType = ModeHelper.getValueModes().get(randomValueMode);
            answers.add(note + " " + randomScaleType);
            answers.add((modeNoteString + " " + randomScaleType));
        }

        //shuffles the answers to be put in a random order
        Collections.shuffle(answers);
        options.getItems().addAll(answers);
        return options;
    }


    /**
     * generates the questions for the tutor for question type 2
     * Type 2 question: What is the parent scale of MODE MODE_TYPE?
     * Type 2 answer: NOTE major
     *
     * @param question
     * @return options
     */
    private ComboBox generateChoices2(Pair question) {

        ComboBox options = new ComboBox<>();
        ArrayList answers = new ArrayList(); //stores the options that appear in the tutor. One correct answer contain
        answers.add((String)(question.getValue()));

        //adds 8 answers to answers array
        while (answers.size() < 8) {
            Integer randomIndex = rand.nextInt(12);
            String scaleType = majorNotes.get(randomIndex); //selects random scaleType based on random index

            //if it has already been generated, reshuffle
            while (answers.contains(scaleType + " major")) {
                randomIndex = rand.nextInt(12);
                scaleType = majorNotes.get(randomIndex);
            }
            answers.add(scaleType + " major");

        }
        Collections.shuffle(answers);
        options.getItems().addAll(answers);
        return options;

    }


    /**
     * Reacts accordingly to a user's input. Formats the question row as to whether the question was answered
     * correctly or not. Also saves the tutor record
     * @param userAnswer User's selectoin
     * @param questionAndAnswer
     * @param questionRow HBox containing GUI data
     */
    private void handleQuestionAnswer(String userAnswer, Pair questionAndAnswer, HBox questionRow) {
        manager.answered += 1;
        Integer correct;
        disableButtons(questionRow, 1, 3);
        String correctAnswer = (String) questionAndAnswer.getValue();
        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            correct = 1;
            manager.add(questionAndAnswer, 1);
            formatCorrectQuestion(questionRow);
        } else {
            correct = 0;
            manager.add(questionAndAnswer, 0);
            formatIncorrectQuestion(questionRow);
            //Shows the correct answer
            questionRow.getChildren().get(3).setVisible(true);
        }


        Pair questionPair = (Pair) questionAndAnswer.getKey();
        String[] question;


        if (getTypeOfQuestion(questionAndAnswer) == 1) {
            question = new String[]{
                    String.format(typeOneText,
                            questionPair.getKey(),
                            questionPair.getValue()),
                    userAnswer,
                    String.valueOf(correct)
            };
        } else {
            question = new String[]{
                    String.format(typeTwoText,
                            questionPair.getKey(),
                            questionPair.getValue()),
                    userAnswer,
                    String.valueOf(correct)
            };
        }

        record.addQuestionAnswer(question);
        env.getRootController().setTabTitle(getTabID(), true);

        if (manager.answered == manager.questions) {
            finished();
        }


    }


    /**
     * GUI formatting for the tutor
     * @param questionAnswer
     * @return
     */
    @Override
    HBox generateQuestionPane(Pair questionAnswer) {

        Pair data = (Pair) questionAnswer.getKey();
        String answer = (String) questionAnswer.getValue();
        final HBox questionRow = new HBox();
        final ComboBox<String> options;
        Label question;
        if (getTypeOfQuestion(questionAnswer) == 1) {
            question = new Label(String.format(typeOneText, data.getKey(), data.getValue()));
            options = generateChoices(questionAnswer);

        } else {
            question = new Label(String.format(typeTwoText, data.getKey(), data.getValue()));
            options = generateChoices2(questionAnswer);
        }
        formatQuestionRow(questionRow);

        final Label correctAnswer = correctAnswer(answer);


        options.setOnAction(event ->
                //System.out.println("check clicked answer")
                handleQuestionAnswer(options.getValue().toLowerCase(), questionAnswer, questionRow)
        );

        Button skip = new Button("Skip");
        styleSkipButton(skip);

        skip.setOnAction(event -> {
            // Disables only input buttons
            disableButtons(questionRow, 1, 3);
            formatSkippedQuestion(questionRow);
            manager.questions -= 1;
            manager.add(data, 2);
            String questionString;

            if (getTypeOfQuestion(questionAnswer) == 1) {
                questionString = String.format(typeOneText, data.getKey(), data.getValue());
            } else {
                questionString = String.format(typeTwoText, data.getKey(), data.getValue());
            }

            String[] questionList = new String[]{
                    questionString,
                    answer,
                    "2"
            };
            record.addQuestionAnswer(questionList);
            env.getRootController().setTabTitle(getTabID(), true);
            if (manager.answered == manager.questions) {
                finished();
            }

        });

        questionRow.getChildren().add(0, question);
        questionRow.getChildren().add(1, options);
        questionRow.getChildren().add(2, skip);
        questionRow.getChildren().add(3, correctAnswer);

        questionRow.prefWidthProperty().bind(paneQuestions.prefWidthProperty());
        return questionRow;
    }


    /**
     * Generate the questions that asks for the mode of a given scale and a given degree
     *
     * @return a pair with the question asked and the correct answer.
     */
    private Pair generateQuestionTypeOne() {

        //generate random note and random degree
        Integer degree = rand.nextInt(7) + 1; //generates random degree of mode
        Integer idx = rand.nextInt(majorNotes.size()); //random index to select random tonic note
        String randomScaleName = (majorNotes.get(idx)); //gets random scale name

        Pair question = new Pair(randomScaleName, degree);

        String answer = MajorModes.getCorrespondingScaleString(randomScaleName, degree);
        return new Pair(question, answer);


    }





    /**
     * Generate the questions that asks for the parent scale of a given mode and its type
     *
     * @return a Pair with the questoin asked and the correct answer
     */
    private Pair generateQuestionTypeTwo() {

        //generate random degree and random note
        Integer degree = rand.nextInt(7) + 1; //generates random degree of mode
        String randomNote = (String)ModeHelper.getModeNoteMap().get(degree).get(rand.nextInt(12));
        String randomType= ModeHelper.getValueModes().get(degree);

        Pair question = new Pair(randomNote, randomType);
        String answer = MajorModes.getParentScaleString(randomNote, randomType);
        return new Pair(question, answer);


    }



    @Override
    void resetInputs() {
        //Diatonic tutor does not have any inputs to reset.
    }




}
