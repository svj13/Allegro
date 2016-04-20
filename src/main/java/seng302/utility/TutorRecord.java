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

    /**
     * Stores all the information to save as text
     */
    private ArrayList<String> lines = new ArrayList<String>();

    /**
     * Record Constructor
     * @param startTime The date and time the record was begun
     * @param tutorType Which tutor created the record
     */
    public TutorRecord(Date startTime, String tutorType) {
        lines.add("Date: " + startTime.toString() + "\n");
        lines.add("Tutor type: " + tutorType + "\n");
    }

    /**
     * Adds a question and answer to the record
     * @param questionSet A list of strings containing information about a single question
     */
    public void addQuestionAnswer(String[] questionSet) {
        lines.add("Question: " + questionSet[0] + "\n");
        lines.add("Answer: " + questionSet[1] + "\n");
        lines.add("Correct: " + questionSet[2] + "\n");
        lines.add("\n");
    }

    /**
     * Adds a question to the record that the user skipped
     * @param question A list of strings containing the question and correct answer.
     */
    public void addSkippedQuestion(String[] question) {
        lines.add("Skipped Question: " + question[0] + "\n");
        lines.add("Correct Answer: " + question[1] + "\n");
        lines.add("\n");
    }

    /**
     * Adds an indicator that the user is re-testing themself
     */
    public void addRetest() {
        lines.add("===== Re-testing =====\n");
        lines.add("\n");
    }

    /**
     * Sets the user's score, etc
     * @param questionsAnsweredCorrectly The number of questions the user answered correctly
     * @param questionsAnsweredIncorrectly The number of questions the user answered incorrectly
     */
    public void setStats(int questionsAnsweredCorrectly, int questionsAnsweredIncorrectly) {
        lines.add("Questions answered correctly: " + questionsAnsweredCorrectly + "\n");
        lines.add("Questions answered incorrectly: " + questionsAnsweredIncorrectly + "\n");

        float percentage = (questionsAnsweredCorrectly * 100) / (questionsAnsweredCorrectly + questionsAnsweredIncorrectly);
        lines.add("Percentage answered correctly: " + percentage + "%\n");
        lines.add("\n");
    }

    /**
     * Saves a tutoring record to a text file
     * @param recordLocation Where to save the the record
     */
    public void writeToFile(String recordLocation) {
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
