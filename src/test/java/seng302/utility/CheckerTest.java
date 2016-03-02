package seng302.utility;

import org.junit.Before;
import org.junit.Test;

import javax.print.DocFlavor;

import static org.junit.Assert.*;

/**
 * Created by isabelle on 2/03/16.
 */
public class CheckerTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testIsValidNote() throws Exception {
        assertTrue(Checker.isValidNote("C4"));
        assertFalse(Checker.isValidNote("H6"));
        assertFalse(Checker.isValidNote("play C6"));
        assertTrue(Checker.isValidNote("C#-1"));
        assertFalse(Checker.isValidNote("Cb-1"));


    }
}