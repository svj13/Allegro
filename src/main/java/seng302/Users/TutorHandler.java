package seng302.Users;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.json.simple.JSONObject;
import seng302.Environment;
import seng302.utility.TutorRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
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


    /**
     * When finished a tutor session. Or when save is clicked part way through.
     */
    public void saveTutorRecordsToFile(String projectAddress) {
        if (env.getRootController().tabSaveCheck("pitchTutor")) {
            saveTutorRecordsToFile(projectAddress + "/PitchComparisonTutor.json", env.getRootController().PitchComparisonTabController.record);
        }
        if (env.getRootController().tabSaveCheck("intervalTutor")) {

            saveTutorRecordsToFile(projectAddress + "/IntervalRecognitionTutor.json", env.getRootController().IntervalRecognitionTabController.record);
        }
        if (env.getRootController().tabSaveCheck("musicalTermTutor")) {
            saveTutorRecordsToFile(projectAddress + "/MusicalTermsTutor.json", env.getRootController().MusicalTermsTabController.record);
        }
        if (env.getRootController().tabSaveCheck("scaleTutor")) {
            saveTutorRecordsToFile(projectAddress + "/ScaleRecognitionTutor.json", env.getRootController().ScaleRecognitionTabController.record);
        }
        if (env.getRootController().tabSaveCheck("chordTutor")) {
            saveTutorRecordsToFile(projectAddress + "/ChordRecognitionTutor.json", env.getRootController().ChordRecognitionTabController.record);
        }
        if (env.getRootController().tabSaveCheck("chordSpellingTutor")) {
            saveTutorRecordsToFile(projectAddress + "/ChordSpellingTutor.json", env.getRootController().ChordSpellingTabController.record);
        }
        if (env.getRootController().tabSaveCheck("keySignatureTutor")) {
            saveTutorRecordsToFile(projectAddress + "/KeySignatureTutor.json", env.getRootController().KeySignaturesTabController.record);
        }
        if (env.getRootController().tabSaveCheck("diatonicChordTutor")) {
            saveTutorRecordsToFile(projectAddress + "/DiatonicChordTutor.json", env.getRootController().DiatonicChordsController.record);
        }
    }


    public void saveTutorRecordsToFile(String filename, TutorRecord currentRecord) {
        Gson gson = new Gson();
        try {
            ArrayList<TutorRecord> records;
            try {
                JsonReader jsonReader = new JsonReader(new FileReader(filename));
                records = gson.fromJson(jsonReader, new TypeToken<ArrayList<TutorRecord>>() {
                }.getType());

                TutorRecord latest = records.get(records.size() - 1);
                if (!latest.isFinished()) {
                    records.remove(records.size() - 1);
                }
            } catch (FileNotFoundException e) {
                records = new ArrayList<>();
            } catch (JsonSyntaxException e) {
                System.err.println("File was not of the correct type. Overwriting.");
                records = new ArrayList<>();
            }
            currentRecord.setDate();
            records.add(currentRecord);

            String json = gson.toJson(records);
            try {
                FileWriter writer = new FileWriter(filename, false);
                writer.write(json);
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                System.err.println("Problem writing to the selected file " + ex.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}
