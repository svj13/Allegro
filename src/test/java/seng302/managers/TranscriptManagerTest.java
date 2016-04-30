package seng302.managers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by jat157 on 7/03/16.
 */
public class TranscriptManagerTest {
    TranscriptManager tm;
    File testFile;

    @Before
    public void setUp() throws Exception {
        tm = new TranscriptManager();

        tm.setCommand("note 60");
        tm.setResult("C4");
        tm.setCommand("midi G#0");
        tm.setResult("0");

        testFile = new File("src/test/java/seng302/utility/test.txt");
        //testFile.createNewFile();


    }

    @Test
    public void testSave() throws Exception {
        assertEquals(2, tm.getTranscriptTuples().size());

        tm.save(testFile.getAbsolutePath());

        assertTrue(testFile.exists());


    }

    @Test
    public void testOpen() throws Exception {
        setUp();
        testSave();

        tm = new TranscriptManager();
        tm.open(testFile.getAbsolutePath());

        assertEquals(2, tm.getTranscriptTuples().size());

        //test adding new transcript to imported list.
        tm.setCommand("midi C#");
        tm.setResult("61");


        assertEquals(3, tm.getTranscriptTuples().size());


    }

    @After
    public void tearDown() throws Exception {

        try {
            testFile.delete();
        } catch (Exception e) {
            System.err.println("test file could not be deleted!");
        }

    }

}