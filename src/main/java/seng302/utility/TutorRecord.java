package seng302.utility;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A record that holds the information of one tutoring session
 */
public class TutorRecord {

    private List<Map<String, String>> questions = new ArrayList();
    private Map<String, Number> stats = new HashMap<>();
    private Date date;
    private boolean finished = false;

    /**
     * Adds a question and answer to the record
     *
     * @param questionSet A list of strings containing information about a single question
     */
    public Map<String, String> addQuestionAnswer(String[] questionSet) {
        Map<String, String> question = new HashMap<>();
        question.put("question", questionSet[0]);
        question.put("answer", questionSet[1]);
        question.put("correct", questionSet[2]);
        questions.add(question);
        return question;
    }


    /**
     * Sets the user's score, etc
     *
     * @param questionsAnsweredCorrectly   The number of questions the user answered correctly
     * @param questionsAnsweredIncorrectly The number of questions the user answered incorrectly
     */
    public Map<String, Number> setStats(int questionsAnsweredCorrectly, int questionsAnsweredIncorrectly, float score) {
        stats.put("questionsCorrect", questionsAnsweredCorrectly);
        stats.put("questionsIncorrect", questionsAnsweredIncorrectly);
        stats.put("percentageCorrect", score);
        return stats;
    }


    public void setDate() {
        date = new Date();
    }

    public void setFinished() {
        finished = true;
    }

    public boolean isFinished() {
        return finished;
    }

    /**
     * Saves a tutoring record to a text file
     *
     * @param recordLocation Where to save the the record
     */
    public void writeToFile(String recordLocation) {
        setDate();
        Gson gson = new Gson();


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
