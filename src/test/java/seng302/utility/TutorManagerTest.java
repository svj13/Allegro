package seng302.utility;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class TutorManagerTest {
    TutorManager tm;
    Pair correctPair;
    Pair incorrectPair;
    Pair skippedPair;

    @Before
    public void setUp() throws Exception {
        tm = new TutorManager();
        correctPair = new Pair("correctPair","correct");
        incorrectPair = new Pair("incorrectPair","incorrect");
        skippedPair = new Pair("SkipepdPair","skipped");
    }

    @Test
    public void testAddCorrectPair() throws Exception {
        tm.add(correctPair,1);
        assertEquals(1, tm.correct);
        // make sure its not added to an incorrect array
        assertEquals(1, tm.getCorrectResponses().size());
        assertEquals(0, tm.getIncorrectResponses().size());
        assertEquals(0, tm.getTempIncorrectResponses().size());

        assertEquals(1, tm.getCorrectResponses().size()); // make sure its added to the correct Responses array
        ArrayList<Pair> correctResponses = new ArrayList<Pair>();
        correctResponses.add(correctPair);
        assertEquals(correctResponses, tm.getCorrectResponses());
    }

    @Test
    public void testAddIncorrectPair() throws Exception {
        tm.add(incorrectPair,0);
        assertEquals(1, tm.incorrect);
        // make sure its not added to an incorrect array
        assertEquals(0, tm.getCorrectResponses().size());
        assertEquals(1, tm.getTempIncorrectResponses().size());

        ArrayList<Pair> incorrectResponses = new ArrayList<Pair>();
        incorrectResponses.add(incorrectPair);
        assertEquals(incorrectResponses, tm.getTempIncorrectResponses());
    }

    @Test
    public void testAddSkippedPair() throws Exception {
        tm.add(skippedPair,2);
        assertEquals(1, tm.skipped);
        // make sure its not added to an incorrect array
        assertEquals(0, tm.getCorrectResponses().size());
        assertEquals(1, tm.getTempIncorrectResponses().size());

        ArrayList<Pair> incorrectResponses = new ArrayList<Pair>();
        incorrectResponses.add(skippedPair);
        assertEquals(incorrectResponses, tm.getTempIncorrectResponses());
    }

    @Test
    public void testClearTempIncorrect() throws Exception {
        tm.add(skippedPair,2);
        tm.add(incorrectPair,0);

        assertEquals(2, tm.getTempIncorrectResponses().size());

        tm.clearTempIncorrect();
        assertEquals(0, tm.getTempIncorrectResponses().size());
    }

    @Test
    public void testSaveTempIncorrect() throws Exception {
        tm.add(skippedPair,2);
        tm.add(incorrectPair,0);

        assertEquals(2, tm.getTempIncorrectResponses().size());

        tm.saveTempIncorrect();
        assertEquals(0, tm.getTempIncorrectResponses().size());
        assertEquals(2, tm.getIncorrectResponses().size());
    }

    @Test
    public void testReset() throws Exception {
        tm.add(incorrectPair,0);
        tm.add(correctPair,1);
        tm.add(skippedPair,2);
        assertEquals(1, tm.incorrect);
        assertEquals(1,tm.correct);
        assertEquals(1,tm.skipped);

        tm.resetStats();
        assertEquals(0,tm.correct);
        assertEquals(0,tm.incorrect);
        assertEquals(0,tm.skipped);
    }




}