package seng302.utility;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

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
        assert tutorRecord.lines.contains("Question: question\n");
        assert tutorRecord.lines.contains("Answer: answer\n");
        assert tutorRecord.lines.contains("Correct: correct\n");
    }

    @Test
    public void testAddSkippedQuestion() {
        String[] skippedQuestion = new String[] {"skipped", "question"};
        tutorRecord.addSkippedQuestion(skippedQuestion);
        assert tutorRecord.lines.contains("Skipped Question: skipped\n");
        assert tutorRecord.lines.contains("Correct Answer: question\n");

    }

    @Test
    public void testAddRetest() {
        tutorRecord.addRetest();
        assert tutorRecord.lines.contains("===== Re-testing =====\n");
    }

    @Test
    public void testSetStats() {
        tutorRecord.setStats(10, 5);
        assert tutorRecord.lines.contains("Questions answered correctly: 10\n");
        assert tutorRecord.lines.contains("Questions answered incorrectly: 5\n");
        assert tutorRecord.lines.contains("Percentage answered correctly: 66.67%\n");
    }
}
