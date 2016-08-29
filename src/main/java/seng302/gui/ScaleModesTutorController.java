package seng302.gui;

import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
        //stores all of the names of the major scales
//        type2answers = new ArrayList<>(Arrays.asList("C", "G", "D", "A", "E", "B", "F", "Bb",
//                "Eb", "Ab", "Db", "Gb"));
        System.out.println("Hello " + manager);

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

        rand = new Random();

        questionRows.getChildren().clear();
        for (int i = 0; i < manager.questions; i++) {
            HBox questionRow;
            if (rand.nextBoolean()) {
                type = 1;
                questionRow = generateQuestionPane(generateQuestionTypeOne());
            } else {
                type = 2;
                questionRow = generateQuestionPane(generateQuestionTypeOne()); //CHANGE ME
            }
            questionRows.getChildren().add(questionRow);
            questionRows.setMargin(questionRow, new Insets(10, 10, 10, 10));
        }

    }

    private ComboBox generateChoices(Pair question) {
        ComboBox options = new ComboBox<>();
        ArrayList answers = new ArrayList();



        //splitting up the answer. e.g D and dorian
        String answer = (String)question.getValue();
        answers.add(answer); //adding correct answer to answers array
        Integer degree = (Integer)((Pair)question.getKey()).getValue();

        String modeNoteString = answer.split(" ")[0];
        Note modeNote = Note.lookup(OctaveUtil.addDefaultOctave(modeNoteString));

        String modeTypeString = answer.split(" ")[1];

        //generate random boolean to determine semitones from root note
        Boolean random = rand.nextBoolean();
        String note;

        if (random) {
            note = OctaveUtil.removeOctaveSpecifier(modeNote.semitoneUp(1).getNote());
        } else {
            note = OctaveUtil.removeOctaveSpecifier(modeNote.semitoneDown(1).getNote());
        }

        answers.add(note + " " + modeTypeString);
        ArrayList<Integer> usedNum = new ArrayList<>();

        for (int i = 0; i < 3; i++) {

            Integer randomValueMode = rand.nextInt(7) + 1; //generates random number for accessing types of modes in ModeHelper
            while(randomValueMode.equals(degree) || usedNum.contains(randomValueMode)){
                randomValueMode = rand.nextInt(7) + 1;
            }

            usedNum.add(randomValueMode);
            String randomScaleType = ModeHelper.getValueModes().get(randomValueMode);
            answers.add(note + " " + randomScaleType);
            answers.add((modeNoteString + " " + randomScaleType));

        }

        Collections.shuffle(answers);
        options.getItems().addAll(answers);
        return options;
    }

    private ComboBox generateChoices2(Pair question) {
        ComboBox options = new ComboBox<>();
        ArrayList answers = new ArrayList();
        answers.add((String)(question.getValue()));


        while (answers.size() < 8) {
            Integer randomIndex = rand.nextInt(12) + 1;
            String scaleType = majorNotes.get(randomIndex);
            while (answers.contains(scaleType)) {
                randomIndex = rand.nextInt(12) + 1;
                scaleType = majorNotes.get(randomIndex);
            }
            answers.add(scaleType);

        }
        Collections.shuffle(answers);
        options.getItems().addAll(answers);
        return options;

    }



    private void handleQuestionAnswer(String userAnswer, Pair questionAndAnswer, HBox questionRow) {
        manager.answered += 1;
        Integer correct;
        disableButtons(questionRow, 1, 2);
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

        question = new String[]{
                String.format(typeOneText,
                        questionPair.getKey(),
                        questionPair.getValue()),
                userAnswer,
                String.valueOf(correct)
        };
        record.addQuestionAnswer(question);
        env.getRootController().setTabTitle(getTabID(), true);

        if (manager.answered == manager.questions) {
            finished();
        }


    }



    @Override
    HBox generateQuestionPane(Pair questionAnswer) {

        Pair data = (Pair) questionAnswer.getKey();
        String answer = (String) questionAnswer.getValue();
        final HBox questionRow = new HBox();
        Label question;
        question = new Label(String.format(typeOneText, data.getKey(), data.getValue()));
        formatQuestionRow(questionRow);

        final Label correctAnswer = correctAnswer(answer);
        final ComboBox<String> options = generateChoices(questionAnswer);
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

            questionString = String.format(typeOneText, data.getKey(), data.getValue());

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
     * @return a Pair that includes the function and the Note name.
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
     * Generate the questions that ask for the function of a chord and key?
     *
     * @return a Pair that includes the random note name and another Pair containing chord Note,
     * chord type.
     */
//    private Pair generateQuestionTypeTwo() {
//
//    }







    @Override
    void resetInputs() {
        //Diatonic tutor does not have any inputs to reset.
    }




}
