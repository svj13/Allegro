package seng302.utility;

import org.junit.Before;
import org.junit.Test;
import seng302.utility.musicNotation.Checker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by isabelle on 2/03/16.
 */
public class CheckerTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testIsValidNote() throws Exception {
        assertTrue(Checker.isValidNormalNote("C4"));
        assertTrue(Checker.isValidNormalNote("C#"));
        assertTrue(Checker.isValidNormalNote("C#-1"));
        assertTrue(Checker.isValidNormalNote("G9"));
        assertTrue(Checker.isValidNormalNote("Fb3"));
        assertTrue(Checker.isValidNormalNote("Cx"));
        assertTrue(Checker.isValidNormalNote("Gbb"));
        assertTrue(Checker.isValidNormalNote("Ax7"));

        assertFalse(Checker.isValidNormalNote("H6"));
        assertFalse(Checker.isValidNormalNote("play C6"));
        assertFalse(Checker.isValidNormalNote("Cb-1"));
        assertFalse(Checker.isValidNormalNote("A#-1"));
        assertFalse(Checker.isValidNormalNote("G#9"));
        assertFalse(Checker.isValidNormalNote("Cbb-1"));
        assertFalse(Checker.isValidNormalNote("Gx9"));

    }

    @Test
    public void testIsValidNoteNoOctave() throws Exception {
        assertTrue(Checker.isValidNoteNoOctave("C"));
        assertTrue(Checker.isValidNoteNoOctave("Dbb"));
        assertTrue(Checker.isValidNoteNoOctave("G#"));
        assertTrue(Checker.isValidNoteNoOctave("Ex"));
        assertTrue(Checker.isValidNoteNoOctave("Fb"));

        assertFalse(Checker.isValidNoteNoOctave("C4"));
    }

    @Test
    public void testIsValidMidiNote() throws Exception {
        assertTrue(Checker.isValidMidiNote("12"));

        assertFalse(Checker.isValidMidiNote("1000"));
        assertFalse(Checker.isValidMidiNote("cake"));
        assertFalse(Checker.isValidMidiNote("the"));
        assertFalse(Checker.isValidMidiNote("-5"));


    }

    @Test
    public void testIsDoubleSharp() throws Exception {
        assertTrue(Checker.isDoubleSharp("Cx"));

        assertFalse(Checker.isDoubleSharp("D#"));

    }

    @Test
    public void testIsDoubleFlat() throws Exception {
        assertTrue(Checker.isDoubleFlat("Cbb"));

        assertFalse(Checker.isDoubleFlat("D#"));
        assertFalse(Checker.isDoubleFlat("Eb"));

    }
}