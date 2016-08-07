package seng302.utility;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * A record that holds the information of one tutoring session
 */
public class TutorRecord {


    JSONArray overallTutorObject = new JSONArray();
    JSONObject overallTutorSessionObject = new JSONObject();
    JSONArray tutorRecordList = new JSONArray();
    JSONObject tutorRecordStats = new JSONObject();


    /**
     * Stores all the information to save as text
     */
    //protected ArrayList<String> tutorRecordList = new ArrayList<String>();


    /**
     * Adds a question and answer to the record
     *
     * @param questionSet A list of strings containing information about a single question
     */
    public JSONObject addQuestionAnswer(String[] questionSet) {
        JSONObject question = new JSONObject();
        question.put("Question", questionSet[0]);
        question.put("Answer", questionSet[1]);
        question.put("Correct", questionSet[2]);

        tutorRecordList.add(question);

        return question;

    }

    /**
     * Adds a question to the record that the user skipped
     *
     * @param question A list of strings containing the question and correct answer.
     */
    public JSONObject addSkippedQuestion(String[] question) {
        JSONObject skippedQuestion = new JSONObject();
        skippedQuestion.put("Skipped Question", question[0]);
        skippedQuestion.put("Correct Answer", question[1]);

        tutorRecordList.add(skippedQuestion);

        return skippedQuestion;
    }


    /**
     * Sets the user's score, etc
     *
     * @param questionsAnsweredCorrectly   The number of questions the user answered correctly
     * @param questionsAnsweredIncorrectly The number of questions the user answered incorrectly
     */
    public JSONObject setStats(int questionsAnsweredCorrectly, int questionsAnsweredIncorrectly, float score) {
        tutorRecordStats.put("Questions Correct", questionsAnsweredCorrectly);
        tutorRecordStats.put("Questions Incorrect", questionsAnsweredIncorrectly);
        tutorRecordStats.put("Percentage Correct", String.format("%.2f", score) + "%");

        return tutorRecordStats;
    }


    private void makeOverallTutorObject() {
        overallTutorSessionObject.put("Questions", tutorRecordList);
        overallTutorSessionObject.put("Stats", tutorRecordStats);
        overallTutorSessionObject.put("Date", new Date().toString());
        overallTutorObject.add(overallTutorSessionObject);

    }

    /**
     * Saves a tutoring record to a text file
     *
     * @param recordLocation Where to save the the record
     */
    public void writeToFile(String recordLocation) {
        if (overallTutorObject.isEmpty()) {

            makeOverallTutorObject();
        }

        try {
            FileWriter writer = new FileWriter(recordLocation, true);
            writer.write(overallTutorObject.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            System.err.println("Problem writing to the selected file " + ex.getMessage());
        }

    }

//    public static void readFromFile(String recordLocation) {
//        try {
//            JSONParser parser = new JSONParser();
//            FileReader reader = new FileReader(recordLocation);
//            JSONObject readStuff =  (JSONObject) parser.parse(reader);
//            String gross = readStuff.toJSONString();
//            Integer start = gross.indexOf("{\"SessionStats");
//            String smallerGross = gross.substring(start,gross.length()-1);
//            Object obj = parser.parse(smallerGross);
//            JSONObject jObj = (JSONObject) obj;
//            System.out.println(jObj.get("SessionStats"));
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}
