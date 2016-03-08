package seng302.utility;

import org.junit.Before;
import org.junit.Test;

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

        assertFalse(Checker.isValidNormalNote("H6"));
        assertFalse(Checker.isValidNormalNote("play C6"));
        assertFalse(Checker.isValidNormalNote("Cb-1"));
        assertFalse(Checker.isValidNormalNote("G#9"));

    }

    @Test
    public void testIsValidMidiNote() throws Exception {
        assertTrue(Checker.isValidMidiNote("12"));

        assertFalse(Checker.isValidMidiNote("1000"));
        assertFalse(Checker.isValidMidiNote("cake"));
        assertFalse(Checker.isValidMidiNote("the"));
        assertFalse(Checker.isValidMidiNote("-5"));


    }
}