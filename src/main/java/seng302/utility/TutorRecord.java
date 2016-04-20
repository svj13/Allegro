package seng302.utility;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javafx.util.Pair;

/**
 * A record that holds the information of one tutoring session
 */
public class TutorRecord {

    private ArrayList<String> lines = new ArrayList<String>();

    public TutorRecord(Date startTime, String tutorType) {
        lines.add("Date: " + startTime.toString() + "\n");
        lines.add("Tutor type: " + tutorType + "\n");
    }

    public void addQuestionAnswer(String[] questionSet) {
        lines.add("Question: " + questionSet[0] + "\n");
        lines.add("Answer: " + questionSet[1] + "\n");
        lines.add("Correct: " + questionSet[2] + "\n");
        lines.add("\n");
    }

    public void addSkippedQuestion(String[] question) {
        lines.add("Skipped Question: " + question[0] + "\n");
        lines.add("Correct Answer: " + question[1] + "\n");
        lines.add("\n");
    }

    public void addRetest() {
        lines.add("===== Re-testing =====\n");
        lines.add("\n");
    }

    public void setStats(int questionsAnsweredCorrectly, int questionsAnsweredIncorrectly) {
        lines.add("Questions answered correctly: " + questionsAnsweredCorrectly + "\n");
        lines.add("Questions answered incorrectly: " + questionsAnsweredIncorrectly + "\n");

        float percentage = (questionsAnsweredCorrectly * 100) / (questionsAnsweredCorrectly + questionsAnsweredIncorrectly);
        lines.add("Percentage answered correctly: " + percentage + "%\n");
        lines.add("\n");
    }

    public void writeToFile(String recordLocation) {
//        Path file;
//        // Create a new file
//        file = Paths.get(recordLocation);
//        try {
//            Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

        try {
            FileWriter writer = new FileWriter(recordLocation, true);
            for (String line : lines) {
                writer.write(line);

            }
            writer.close();
        } catch (IOException ex) {
            System.err.println("Problem writing to the selected file " + ex.getMessage());
        }


    }
}
