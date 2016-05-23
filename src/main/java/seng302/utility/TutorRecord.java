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
    protected ArrayList<String> lines = new ArrayList<String>();

    /**
     * Record Constructor
     * @param startTime The date and time the record was begun
     * @param tutorType Which tutor created the record
     */
    public TutorRecord(Date startTime, String tutorType) {
        lines.add("Date: " + startTime.toString() + "\n");
        lines.add("Tutor type: " + tutorType + "\n");
        lines.add("\n");
    }

    /**
     * Adds a question and answer to the record
     * @param questionSet A list of strings containing information about a single question
     */
    public String addQuestionAnswer(String[] questionSet) {
        String line = "";
        lines.add("Question: " + questionSet[0] + "\n");
        lines.add("Answer: " + questionSet[1] + "\n");
        lines.add("Correct: " + questionSet[2] + "\n");
        lines.add("\n");

        line += "Question: " + questionSet[0] + "\n";
        line += "Answer: " + questionSet[1] + "\n";
        line += "Correct: " + questionSet[2] + "\n";
        line += "\n";

        return line;

    }

    /**
     * Adds a question to the record that the user skipped
     * @param question A list of strings containing the question and correct answer.
     */
    public String addSkippedQuestion(String[] question) {
        String line = "";
        lines.add("Skipped Question: " + question[0] + "\n");
        lines.add("Correct Answer: " + question[1] + "\n");
        lines.add("\n");

        line += "Skipped Question: " + question[0] + "\n";
        line += "Correct Answer: " + question[1] + "\n";
        line += "\n";

        return line;
    }

    /**
     * Adds an indicator that the user is re-testing themself
     */
    public String addRetest() {
        String line = "";
        lines.add("===== Re-testing =====\n");
        lines.add("\n");

        line+= "===== Re-testing =====\n";
        line += "\n";

        return line;
    }

    /**
     * Sets the user's score, etc
     * @param questionsAnsweredCorrectly The number of questions the user answered correctly
     * @param questionsAnsweredIncorrectly The number of questions the user answered incorrectly
     */
    public String setStats(int questionsAnsweredCorrectly, int questionsAnsweredIncorrectly, float score) {

        String line = "";
        lines.add("Questions answered correctly: " + questionsAnsweredCorrectly + "\n");
        lines.add("Questions answered incorrectly: " + questionsAnsweredIncorrectly + "\n");
        lines.add("Percentage answered correctly: " + String.format("%.2f", score) + "%\n");
        lines.add("\n");

        line += "Questions answered correctly: " + questionsAnsweredCorrectly + "\n";
        line += "Questions answered incorrectly: " + questionsAnsweredIncorrectly + "\n";
        line += "Percentage answered correctly: " + String.format("%.2f", score) + "%\n";
        line += "\n";
        return line;
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
