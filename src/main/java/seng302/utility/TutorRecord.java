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

    private ArrayList<String> lines = new ArrayList<String>();

    public TutorRecord(Date startTime) {
        lines.add("Date: " + startTime.toString());
    }

    public void addQuestionAnswer(String[] questionSet) {
        lines.add("Question: " + questionSet[0]);
        lines.add("Answer: " + questionSet[1]);
        lines.add("Correct: " + questionSet[2]);
        lines.add("");
    }

    public void addSkippedQuestion(String[] question) {
        lines.add("Skipped Question: " + question[0]);
        lines.add("Correct Answer: " + question[1]);
        lines.add("");
    }

    public void addRetest() {
        lines.add("===== Re-testing =====");
        lines.add("");
    }

    public void setStats(int questionsAnsweredCorrectly, int questionsAnsweredIncorrectly) {
        lines.add("Questions answered correctly: " + questionsAnsweredCorrectly);
        lines.add("Questions answered incorrectly: " + questionsAnsweredIncorrectly);

        float percentage = (questionsAnsweredCorrectly * 100) / (questionsAnsweredCorrectly + questionsAnsweredIncorrectly);
        lines.add("Percentage answered correctly: " + percentage + "%");
        lines.add("");
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
