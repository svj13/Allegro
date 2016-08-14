package seng302.utility;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * A record that holds the information of one tutoring session
 */
public class TutorRecord {


    JSONObject overalTutorObject = new JSONObject();
    JSONObject overalTutorSessionObject = new JSONObject();
    Collection<JSONObject> tutorRecordList = new ArrayList<JSONObject>();
    String tutorRecordStats = "";


    /**
     * Stores all the information to save as text
     */
    //protected ArrayList<String> tutorRecordList = new ArrayList<String>();


    /**
     * Adds a question and answer to the record
     *
     * @param questionSet A list of strings containing information about a single question
     */
    public String addQuestionAnswer(String[] questionSet) {
        String line = "";

        line += "Question: " + questionSet[0];
        line += "Answer: " + questionSet[1];
        line += "Correct: " + questionSet[2];

        JSONObject jasonOFQuestion = new JSONObject();
        jasonOFQuestion.put("QuestionInfo", line);
        tutorRecordList.add(jasonOFQuestion);

        return line;

    }

    /**
     * Adds a question to the record that the user skipped
     *
     * @param question A list of strings containing the question and correct answer.
     */
    public String addSkippedQuestion(String[] question) {
        String line = "";

        line += "Skipped Question: " + question[0];
        line += "Correct Answer: " + question[1];

        JSONObject jasonOFQuestion = new JSONObject();
        jasonOFQuestion.put("QuestionInfo", line);

        tutorRecordList.add(jasonOFQuestion);

        return line;
    }


    /**
     * Sets the user's score, etc
     *
     * @param questionsAnsweredCorrectly   The number of questions the user answered correctly
     * @param questionsAnsweredIncorrectly The number of questions the user answered incorrectly
     */
    public String setStats(int questionsAnsweredCorrectly, int questionsAnsweredIncorrectly, float score) {

        tutorRecordStats = "";
        tutorRecordStats += "Questions answered correctly: " + questionsAnsweredCorrectly;
        tutorRecordStats += "Questions answered incorrectly: " + questionsAnsweredIncorrectly;
        tutorRecordStats += "Percentage answered correctly: " + String.format("%.2f", score) + "%";
        System.out.println(tutorRecordStats + "fds");

        return tutorRecordStats;
    }


    private void makeOverallTutorObject() {
        overalTutorSessionObject.put("Questions", tutorRecordList);
        overalTutorSessionObject.put("SessionStats", tutorRecordStats);
        overalTutorObject.put("Session_" + new Date().toString(), overalTutorSessionObject);

    }

    /**
     * Saves a tutoring record to a text file
     *
     * @param recordLocation Where to save the the record
     */
    public JSONObject writeToFile(String recordLocation) {
//        if (overalTutorObject.isEmpty()) {
//
//        }
        makeOverallTutorObject();
        System.out.println(overalTutorSessionObject);


        try {
            FileWriter writer = new FileWriter(recordLocation, true);
            writer.write(overalTutorObject.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            System.err.println("Problem writing to the selected file " + ex.getMessage());
        }
        return overalTutorObject;

    }
}
