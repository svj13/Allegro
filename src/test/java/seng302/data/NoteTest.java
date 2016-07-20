package seng302.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by isabelle on 6/03/16.
 */
public class NoteTest {

    HashMap<String, String> enharmonics;

    @Before
    public void setUp() throws Exception {
        enharmonics = new HashMap<String, String>();
    }

    // Lookup Method Tests ---------------------------

    @Test
    public void testLookupGoodNote() throws Exception {
        enharmonics.clear();
        enharmonics.put("descending", "C4");
        assertEquals(new Note(60, "C4", enharmonics), Note.lookup("C4"));
    }

    @Test
    public void testLookupGoodMidi() throws Exception {
        enharmonics.clear();
        enharmonics.put("descending", "C4");
        assertEquals(new Note(60, "C4", enharmonics), Note.lookup("60"));
    }

    @Test
    public void testLookupBadNote() throws Exception {
        assertEquals(null, Note.lookup("C10"));
    }

    @Test
    public void testLookupBadMidiNegative() throws Exception {
        assertEquals(null, Note.lookup("-123"));
    }

    @Test
    public void testLookupBadMidiTooBig() throws Exception {
        assertEquals(null, Note.lookup("210"));
    }

    @Test
    public void testLookupEnharmonics() throws Exception {
        assertEquals(Note.lookup("F4").getMidi(), Note.lookup("E#4").getMidi());
    }

    @Test
    public void testLookupEnharmonicsNoOctave() throws Exception {
        assertEquals(Note.lookup("E"), Note.lookup("Fb"));
    }

    @Test
    public void testLookupEnharmonicsDoubleSharp() throws Exception {
        assertEquals(Note.lookup("Cx4").getMidi(), Note.lookup("Ebb4").getMidi());
    }

    @Test
    public void testLookupExceptionEnharmonics() throws Exception {
        assertEquals(Note.lookup("G#"), Note.lookup("Ab"));
    }

    @Test
    public void testLookupEnharmonicsOverOctave() throws Exception {
        assertEquals(Note.lookup("C5").getMidi(), Note.lookup("B#5").getMidi());
    }

    @Test
    public void testLookupTopEnharmonic() throws Exception {
        assertEquals(Note.lookup("G9").getMidi(), Note.lookup("Abb9").getMidi());
    }

    @Test
    public void testLookupBottomEnharmonic() throws Exception {
        assertEquals(Note.lookup("C-1").getMidi(), Note.lookup("B#-1").getMidi());
    }

    // Semitone Method Tests -----------------------

    @Test
    public void testSemitoneUpNormalNote() throws Exception {
        assertEquals(Note.lookup("C#4"), Note.lookup("C4").semitoneUp(1));
    }

    @Test
    public void testManySemitonesUpNormalNote() throws Exception {
        assertEquals(Note.lookup("C#5"), Note.lookup("C4").semitoneUp(13));
    }

    @Test
    public void testSemitoneUpTopNote() throws Exception {
        assertEquals(null, Note.lookup("G9").semitoneUp(1));
    }

    @Test
    public void testTwoSemitonesUpTooHigh() throws Exception {
        assertEquals(null, Note.lookup("F#9").semitoneUp(2));
    }

    @Test
    public void testSemitoneDownNormalNote() throws Exception {
        assertEquals(Note.lookup("C4"), Note.lookup("C#4").semitoneDown(1));
    }

    @Test
    public void testManySemitonesDownNormalNote() throws Exception {
        assertEquals(Note.lookup("C4"), Note.lookup("C#5").semitoneDown(13));
    }

    @Test
    public void testSemitoneDownBottomNote() throws Exception {
        assertEquals(null, Note.lookup("C-1").semitoneDown(1));
    }

    @Test
    public void testTwoSemitonesDownTooLow() throws Exception {
        assertEquals(null, Note.lookup("C#-1").semitoneDown(2));
    }
    // Enharmonic Notes Exist test

    // Major Scale Method Tests -----------------------------------------

    @Test
    public void testCMajorScale() throws Exception {
        ArrayList<Note> scale = new ArrayList<Note>(
                Arrays.asList(Note.lookup("C4"), Note.lookup("D4"), Note.lookup("E4"),
                        Note.lookup("F4"), Note.lookup("G4"), Note.lookup("A4"),
                        Note.lookup("B4"), Note.lookup("C5")));
        assertEquals(scale, Note.lookup("C4").getScale("major", true, false));
    }

    @Test
    public void testDMajorScale() throws Exception {
        ArrayList<Note> scale = new ArrayList<Note>(
                Arrays.asList(Note.lookup("D4"), Note.lookup("E4"),
                        Note.lookup("F#4"), Note.lookup("G4"), Note.lookup("A4"),
                        Note.lookup("B4"), Note.lookup("C#5"), Note.lookup("D5")));
        assertEquals(scale, Note.lookup("D4").getScale("major", true, false));
    }

    @Test
    public void testMajorScaleTooHigh() throws Exception {
        assertEquals(null, Note.lookup("A8").getScale("major", true, false));
    }

    // Get Method Tests

    @Test
    public void testGetNote() throws Exception {
        assertEquals("C4", Note.lookup("C4").getNote());
    }

    @Test
    public void testGetMidi() throws Exception {
        assertEquals((Integer) 60, Note.lookup("C4").getMidi());
    }

}