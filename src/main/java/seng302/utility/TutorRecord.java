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
     * Adds a question and answer to the record
     *
     * @param questionSet A list of strings containing information about a single question
     */
    public JSONObject addQuestionAnswer(String[] questionSet) {
        JSONObject question = new JSONObject();
        question.put("question", questionSet[0]);
        question.put("answer", questionSet[1]);
        question.put("correct", questionSet[2]);
        tutorRecordList.add(question);
        return question;

    }


    /**
     * Sets the user's score, etc
     *
     * @param questionsAnsweredCorrectly   The number of questions the user answered correctly
     * @param questionsAnsweredIncorrectly The number of questions the user answered incorrectly
     */
    public JSONObject setStats(int questionsAnsweredCorrectly, int questionsAnsweredIncorrectly, float score) {
        tutorRecordStats.put("questionsCorrect", questionsAnsweredCorrectly);
        tutorRecordStats.put("questionsIncorrect", questionsAnsweredIncorrectly);
        tutorRecordStats.put("percentageCorrect", String.format("%.2f", score) + "%");
        return tutorRecordStats;
    }


    private void makeOverallTutorObject() {
        overallTutorSessionObject.put("questions", tutorRecordList);
        overallTutorSessionObject.put("stats", tutorRecordStats);
        overallTutorSessionObject.put("date", new Date().toString());
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
