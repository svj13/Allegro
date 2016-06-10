package seng302.utility;

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
        tutorRecord = new TutorRecord(new Date(), "Test");
    }

    @Test
    public void testAddQuestionAnswer() {
        String[] questionAndAnswer = new String[] {"question", "answer", "correct"};
        tutorRecord.addQuestionAnswer(questionAndAnswer);
        assert tutorRecord.tutorRecordList.contains("Question: question\n");
        assert tutorRecord.tutorRecordList.contains("Answer: answer\n");
        assert tutorRecord.tutorRecordList.contains("Correct: correct\n");
    }

    @Test
    public void testAddSkippedQuestion() {
        String[] skippedQuestion = new String[] {"skipped", "question"};
        tutorRecord.addSkippedQuestion(skippedQuestion);
        assert tutorRecord.tutorRecordList.contains("Skipped Question: skipped\n");
        assert tutorRecord.tutorRecordList.contains("Correct Answer: question\n");

    }

    @Test
    public void testAddRetest() {
        tutorRecord.addRetest();
        assert tutorRecord.tutorRecordList.contains("===== Re-testing =====\n");
    }

    @Test
    public void testSetStats() {
        TutorManager tm = new TutorManager();
        tm.correct = 10;
        tm.answered = 15;
        tm.incorrect = 5;

        tutorRecord.setStats(tm.correct, tm.incorrect, tm.getScore());
        assert tutorRecord.tutorRecordList.contains("Questions answered correctly: 10\n");
        assert tutorRecord.tutorRecordList.contains("Questions answered incorrectly: 5\n");
        assert tutorRecord.tutorRecordList.contains("Percentage answered correctly: 66.67%\n");
    }
}
