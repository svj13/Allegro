package seng302.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by isabelle on 6/03/16.
 */
public class NoteTest {

    public Note middleC;
    public Note middleCSharp;
    public Note gNine;
    public Note cNegativeOne;

    @Before
    public void setUp() throws Exception {
        middleC = new Note(60, "C4");
        middleCSharp = new Note(61, "C#4");
        gNine = new Note(127, "G9");
        cNegativeOne = new Note(0, "C-1");
    }

    @Test
    public void testLookupGoodNote() throws Exception {
        assertEquals(Note.lookup("C4"), new Note(60, "C4"));
    }

    @Test
    public void testLookupGoodMidi() throws Exception {
        assertEquals(Note.lookup("60"), new Note(60, "C4"));
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