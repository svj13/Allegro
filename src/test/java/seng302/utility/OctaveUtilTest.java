package seng302.utility;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by emily on 7/03/16.
 */
public class OctaveUtilTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testAddDefaultOctave() {
        assertEquals(OctaveUtil.addDefaultOctave("C#"), "C#4");
    }

    @Test
    public void testRemoveOctaveSpecifier() {
        assertEquals(OctaveUtil.removeOctaveSpecifier("C#4"), "C#");
    }

    @Test
    public void testOctaveSpecifierFlag() {
        assertTrue(OctaveUtil.octaveSpecifierFlag("C#4"));
        assertFalse(OctaveUtil.octaveSpecifierFlag("C#"));
    }
}