package seng302.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by isabelle on 6/03/16.
 */
public class NoteTest {

    public Note middleC;
    public Note middleCSharp;
    public Note gNine;
    public Note cNegativeOne;
    HashMap<String, String> enharmonics;

    @Before
    public void setUp() throws Exception {
        enharmonics  = new HashMap<String, String>();
        enharmonics.put("descending", "C4");
        middleC = new Note(60, "C4", enharmonics);
        enharmonics.clear();
        enharmonics.put("descending", "Db4");
        middleCSharp = new Note(61, "C#4", enharmonics);
        enharmonics.clear();
        enharmonics.put("descending", "G9");
        gNine = new Note(127, "G9", enharmonics);
        enharmonics.clear();
        enharmonics.put("descending", "C-1");
        cNegativeOne = new Note(0, "C-1", enharmonics);
    }

    @Test
    public void testLookupGoodNote() throws Exception {
        enharmonics.clear();
        enharmonics.put("descending", "C4");
        assertEquals(Note.lookup("C4"), new Note(60, "C4", enharmonics));
    }

    @Test
    public void testLookupGoodMidi() throws Exception {
        enharmonics.clear();
        enharmonics.put("descending", "C4");
        assertEquals(Note.lookup("60"), new Note(60, "C4", enharmonics));
    }

    @Test
    public void testLookupBadNote() throws Exception {
        assertEquals(Note.lookup("C10"), null);
    }
    @Test
    public void testLookupBadMidiNegative() throws Exception {
        assertEquals(Note.lookup("-123"), null);
    }
    @Test
    public void testLookupBadMidiTooBig() throws Exception {
        assertEquals(Note.lookup("210"), null);
    }

    @Test
    public void testSemitoneUpNormalNote() throws Exception {
        assertEquals(middleC.semitoneUp(), middleCSharp);
    }

    @Test
    public void testSemitoneUpTopNote() throws Exception {
        assertEquals(gNine.semitoneUp(), null);
    }

    @Test
    public void testSemitoneDownNormalNote() throws Exception {
        assertEquals(middleCSharp.semitoneDown(), middleC);
    }

    @Test
    public void testSemitoneDownBottomNote() throws Exception {
        assertEquals(cNegativeOne.semitoneDown(), null);
    }

    @Test
    public void testGetNote() throws Exception {
        assertEquals(middleC.getNote(), "C4");
    }

    @Test
    public void testGetMidi() throws Exception {
        assertEquals(middleC.getMidi(), 60, 0.001);
    }
}