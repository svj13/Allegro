package seng302.utility;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;

import javafx.util.Pair;

/**
 * A record that holds the information of one tutoring session
 */
public class TutorRecord {

    private Date startTime;

    private int questionsAnsweredCorrectly;

    private int questionsAnsweredIncorrectly;


    /**
     * An array of questions, stored as question, answer, correct
     * For example:
     * Interval tutor would store as "Interval between Note1 and Note2", "unison/etc", true
     * Pitch tutor would store as "Is Note2 higher/lower than Note1", "higher/lower/same", false
     */
    private ArrayList<String[]> questionsAnswers = new ArrayList<String[]>();

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void addQuestionAnswer(String[] questionAndAnswer) {
        questionsAnswers.add(questionAndAnswer);
    }

    public void setQuestionsAnsweredCorrectly(int questionsAnsweredCorrectly) {
        this.questionsAnsweredCorrectly = questionsAnsweredCorrectly;
    }

    public void setQuestionsAnsweredIncorrectly(int questionsAnsweredIncorrectly) {
        this.questionsAnsweredIncorrectly = questionsAnsweredIncorrectly;
    }

    public float calculatePercentageCorrect() {
        return (this.questionsAnsweredCorrectly * 100) / (this.questionsAnsweredCorrectly + this.questionsAnsweredIncorrectly);
    }

    public void writeToFile() {
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("Date: " + startTime.toString());

        for (String[] questionSet:questionsAnswers) {
            lines.add("Question: " + questionSet[0]);
            lines.add("Answer: " + questionSet[1]);
            lines.add("Correct: " + questionSet[2]);
            lines.add("");
        }

        lines.add("Questions answered correctly: " + questionsAnsweredCorrectly);
        lines.add("Questions answered incorrectly: " + questionsAnsweredIncorrectly);
        lines.add("Percentage answered correctly: " + calculatePercentageCorrect() + "%");

        Path file = Paths.get("the-file-name.txt");
        try {
            Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        } catch (Exception e) {
            System.out.println("Couldn't save file");
        }

    }
}
