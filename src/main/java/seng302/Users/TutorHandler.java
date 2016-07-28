package seng302.Users;

import org.json.simple.JSONObject;
import seng302.Environment;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by jmw280 on 25/07/16.
 */
public class TutorHandler {
    Environment env;


    JSONObject overalPitchObject;
    JSONObject overalPitchSessionObject;
    Collection<JSONObject> pitchTutorRecordsList = new ArrayList<JSONObject>();
    String pitchTutorRecordStats = "";

    JSONObject overalIntervalObject;
    JSONObject overalIntervalSessionObject;
    Collection<JSONObject> intervalTutorRecordsList = new ArrayList<JSONObject>();
    String intervalTutorRecordStats = "";

    JSONObject overalMusicalTermObject;
    JSONObject overalMusicalTermSessionObject;
    Collection<JSONObject> musicalTermTutorRecordsList = new ArrayList<JSONObject>();
    String musicalTermTutorRecordStats = "";

    JSONObject overalScaleObject;
    JSONObject overalScaleSessionObject;
    Collection<JSONObject> scaleTutorRecordsList = new ArrayList<JSONObject>();
    String scaleTutorRecordStats = "";

    JSONObject overalChordObject;
    JSONObject overalChordSessionObject;
    Collection<JSONObject> chordTutorRecordsList = new ArrayList<JSONObject>();
    String chordTutorRecordStats = "";

    JSONObject overallSpellingObject;
    JSONObject overallSpellingSessionObject;
    Collection<JSONObject> spellingTutorRecordsList = new ArrayList<>();
    String spellingTutorRecordStats = "";


    JSONObject intervalTutorRecords;
    JSONObject musicalTermsTutorRecords;
    JSONObject scaleTutorRecords;
    JSONObject chordTutorRecords;
    JSONObject spellingTutorRecords;




    public TutorHandler(Environment env){
        this.env = env;

        intervalTutorRecords = new JSONObject();
        musicalTermsTutorRecords = new JSONObject();
        scaleTutorRecords = new JSONObject();
        chordTutorRecords = new JSONObject();
        spellingTutorRecords = new JSONObject();

        overalPitchObject = new JSONObject();
        overalPitchSessionObject = new JSONObject();

        overalIntervalObject = new JSONObject();
        overalIntervalSessionObject = new JSONObject();

        overalMusicalTermObject = new JSONObject();
        overalMusicalTermSessionObject = new JSONObject();

        overalScaleObject = new JSONObject();
        overalScaleSessionObject = new JSONObject();

        overalChordObject = new JSONObject();
        overalChordSessionObject = new JSONObject();

        overallSpellingObject = new JSONObject();
        overallSpellingSessionObject = new JSONObject();
    }


    public void saveSessionStat(String tutorType, String statString) {
        if (tutorType.equals("pitch")) {

            pitchTutorRecordStats += (statString);

        } else if (tutorType.equals("interval")) {

            intervalTutorRecordStats += (statString);

        } else if (tutorType.equals("musicalTerm")) {
            musicalTermTutorRecordStats += (statString);

        } else if (tutorType.equals("scale")) {

            scaleTutorRecordStats += (statString);

        } else if (tutorType.equals("chord")) {

            chordTutorRecordStats += (statString);
        } else if (tutorType.equals("spelling")) {

            spellingTutorRecordStats += (statString);
        }


    }


    public void saveTutorRecords(String tutorType, String record) {
        JSONObject jasonOFQuestion = new JSONObject();

        if (tutorType.equals("pitch")) {

            jasonOFQuestion.put("QuestionInfo", record);
            pitchTutorRecordsList.add(jasonOFQuestion);
            //System.out.println(pitchTutorRecordsList);

        } else if (tutorType.equals("interval")) {

            jasonOFQuestion.put("QuestionInfo", record);
            intervalTutorRecordsList.add(jasonOFQuestion);


        } else if (tutorType.equals("musicalTerm")) {
            jasonOFQuestion.put("QuestionInfo", record);
            musicalTermTutorRecordsList.add(jasonOFQuestion);
            ;

        } else if (tutorType.equals("scale")) {
            jasonOFQuestion.put("QuestionInfo", record);
            scaleTutorRecordsList.add(jasonOFQuestion);


        } else if (tutorType.equals("chord")) {

            jasonOFQuestion.put("QuestionInfo", record);
            chordTutorRecordsList.add(jasonOFQuestion);
        } else if (tutorType.equals("spelling")) {

            jasonOFQuestion.put("QuestionInfo", record);
            spellingTutorRecordsList.add(jasonOFQuestion);
        }
    }


    public void saveTutorRecordsToFile(String projectAddress) {
        try {

            if (env.getRootController().tabSaveCheck("pitchTutor")) {
                FileWriter pitchFile = new FileWriter(projectAddress + "/PitchComparisonRecords.json", true);
                overalPitchSessionObject.put("Questions", pitchTutorRecordsList);
                overalPitchSessionObject.put("SessionStats", pitchTutorRecordStats);


                overalPitchObject.put("Session_" + new Date().toString(), overalPitchSessionObject);
                pitchFile.write(overalPitchObject.toJSONString());
                //file1.write(overalpitchSessionObject.toJSONString());
                pitchTutorRecordsList.clear();
                pitchTutorRecordStats = "";
                overalPitchSessionObject.clear();
                overalPitchObject.clear();

                pitchFile.flush();
                pitchFile.close();
            }

            if (env.getRootController().tabSaveCheck("intervalTutor")) {

                FileWriter intervalFile = new FileWriter(projectAddress + "/IntervalRecognitionRecords.json", true);
                overalIntervalSessionObject.put("Questions", intervalTutorRecordsList);
                overalIntervalSessionObject.put("SessionStats", intervalTutorRecordStats);
                overalIntervalObject.put("Session_" + new Date().toString(), overalIntervalSessionObject);
                intervalFile.write(overalIntervalObject.toJSONString());
                intervalTutorRecordsList.clear();
                intervalTutorRecordStats = "";
                overalIntervalSessionObject.clear();
                overalIntervalObject.clear();
                intervalFile.flush();
                intervalFile.close();
            }

            if (env.getRootController().tabSaveCheck("musicalTermTutor")) {

                FileWriter MusicalTermFile = new FileWriter(projectAddress + "/MusicalTermsRecords.json", true);
                overalMusicalTermSessionObject.put("Questions", musicalTermTutorRecordsList);
                overalMusicalTermSessionObject.put("SessionStats", musicalTermTutorRecordStats);
                overalMusicalTermObject.put("Session_" + new Date().toString(), overalMusicalTermSessionObject);
                MusicalTermFile.write(overalMusicalTermObject.toJSONString());
                musicalTermTutorRecordsList.clear();
                musicalTermTutorRecordStats = "";
                overalMusicalTermSessionObject.clear();
                overalMusicalTermObject.clear();
                MusicalTermFile.flush();
                MusicalTermFile.close();
            }
            if (env.getRootController().tabSaveCheck("scaleTutor")) {

                FileWriter scaleFile = new FileWriter(projectAddress + "/ScaleRecognitionRecords.json", true);
                overalScaleSessionObject.put("Questions", scaleTutorRecordsList);
                overalScaleSessionObject.put("SessionStats", scaleTutorRecordStats);
                overalScaleObject.put("Session_" + new Date().toString(), overalScaleSessionObject);
                scaleFile.write(overalScaleObject.toJSONString());
                scaleTutorRecordsList.clear();
                scaleTutorRecordStats = "";
                overalScaleSessionObject.clear();
                overalScaleObject.clear();
                scaleFile.flush();
                scaleFile.close();

            }

            if (env.getRootController().tabSaveCheck("chordTutor")) {
                FileWriter chordFile = new FileWriter(projectAddress + "/ChordRecognitionRecords.json", true);
                overalChordSessionObject.put("Questions", chordTutorRecordsList);
                overalChordSessionObject.put("SessionStats", chordTutorRecordStats);
                overalChordObject.put("Session_" + new Date().toString(), overalChordSessionObject);
                chordFile.write(overalChordObject.toJSONString());
                chordTutorRecordsList.clear();
                chordTutorRecordStats = "";
                overalChordSessionObject.clear();
                overalChordObject.clear();
                chordFile.flush();
                chordFile.close();
            }

            if (env.getRootController().tabSaveCheck("chordSpellingTutor")) {
                FileWriter spellingFile = new FileWriter(projectAddress + "/ChordSpellingRecords.json", true);
                overallSpellingSessionObject.put("Questions", spellingTutorRecordsList);
                overallSpellingSessionObject.put("SessionStats", spellingTutorRecordStats);
                overallSpellingObject.put("Session_" + new Date().toString(), overallSpellingSessionObject);
                spellingFile.write(overallSpellingObject.toJSONString());
                spellingTutorRecordsList.clear();
                spellingTutorRecordStats = "";
                overallSpellingSessionObject.clear();
                overallSpellingObject.clear();
                spellingFile.flush();
                spellingFile.close();
            }

        } catch (IOException e) {
            e.printStackTrace();

        }


    }




}
