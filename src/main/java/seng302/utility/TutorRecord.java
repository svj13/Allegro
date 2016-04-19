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

    private ArrayList<String> lines = new ArrayList<String>();


    public void setStartTime(Date startTime) {
        this.startTime = startTime;
        lines.add("Date: " + startTime.toString());
    }

    public void addQuestionAnswer(String[] questionSet) {
        lines.add("Question: " + questionSet[0]);
        lines.add("Answer: " + questionSet[1]);
        lines.add("Correct: " + questionSet[2]);
        lines.add("");
    }

    public void setQuestionsAnsweredCorrectly(int questionsAnsweredCorrectly) {
        this.questionsAnsweredCorrectly = questionsAnsweredCorrectly;
        lines.add("Questions answered correctly: " + questionsAnsweredCorrectly);
    }

    public void setQuestionsAnsweredIncorrectly(int questionsAnsweredIncorrectly) {
        this.questionsAnsweredIncorrectly = questionsAnsweredIncorrectly;
        lines.add("Questions answered incorrectly: " + questionsAnsweredIncorrectly);
    }

    public void calculatePercentageCorrect() {
        float percentage = (this.questionsAnsweredCorrectly * 100) / (this.questionsAnsweredCorrectly + this.questionsAnsweredIncorrectly);
        lines.add("Percentage answered correctly: " + percentage + "%");
    }

    public void writeToFile() {
        Path file = Paths.get("testfile.txt");
        try {
            Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
