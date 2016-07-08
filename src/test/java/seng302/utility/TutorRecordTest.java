package seng302.utility;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import seng302.managers.TutorManager;

/**
 * Created by emily on 21/04/16.
 */
public class TutorRecordTest {
    TutorRecord tutorRecord;

    @Before
    public void setUp() throws Exception {
        tutorRecord = new TutorRecord();
    }

    @Test
    public void testAddQuestionAnswer() {
        JSONObject testQuestionInfo = new JSONObject();
        String[] questionAndAnswer = new String[] {"question", "answer", "correct"};
        testQuestionInfo.put("QuestionInfo", "Question: questionAnswer: answerCorrect: correct");
        tutorRecord.addQuestionAnswer(questionAndAnswer);

        assert tutorRecord.tutorRecordList.contains(testQuestionInfo);
    }

    @Test
    public void testAddSkippedQuestion() {
        JSONObject testQuestionInfo = new JSONObject();
        String[] skippedQuestion = new String[] {"skipped", "question"};
        testQuestionInfo.put("QuestionInfo", "Skipped Question: skippedCorrect Answer: question");
        tutorRecord.addSkippedQuestion(skippedQuestion);
        assert tutorRecord.tutorRecordList.contains(testQuestionInfo);

    }


    @Test
    public void testSetStats() {
        JSONObject testSessionObject = new JSONObject();
        TutorManager tm = new TutorManager();
        tm.correct = 10;
        tm.answered = 15;
        tm.incorrect = 5;

        tutorRecord.setStats(tm.correct, tm.incorrect, tm.getScore());
        assert tutorRecord.tutorRecordStats.contains("Questions answered correctly: 10");

        assert tutorRecord.tutorRecordStats.contains("Questions answered incorrectly: 5");
        assert tutorRecord.tutorRecordStats.contains("Percentage answered correctly: 66.67%");
    }
}
