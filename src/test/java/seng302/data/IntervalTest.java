package seng302.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Emily on 11/04/16.
 */
public class IntervalTest {


    @Before
    public void setUp() throws Exception {
    }


    // Tests that the lookup by name method gets the correct object.
    @Test
    public void testLookupByName() throws Exception {
        assertEquals(0, Interval.lookupByName("unison").getSemitones());
        assertEquals("unison", Interval.lookupByName("unison").getName());
        assertEquals(2, Interval.lookupByName("major second").getSemitones());
        assertEquals("major second", Interval.lookupByName("major second").getName());
        assertEquals(12, Interval.lookupByName("perfect octave").getSemitones());
        assertEquals("perfect octave", Interval.lookupByName("perfect octave").getName());
    }

    // Tests that the lookup by semitone method gets the correct object.
    @Test
    public void testLookupBySemitones() throws Exception {
        assertEquals(0, Interval.lookupBySemitones(0).get(0).getSemitones());
        assertEquals("unison", Interval.lookupBySemitones(0).get(0).getName());
        assertEquals(2, Interval.lookupBySemitones(2).get(0).getSemitones());
        assertEquals("major second", Interval.lookupBySemitones(2).get(0).getName());
        assertEquals(12, Interval.lookupBySemitones(12).get(0).getSemitones());
        assertEquals("perfect octave", Interval.lookupBySemitones(12).get(0).getName());
    }

    @Test
    public void testLookupInvalidSemitones() throws Exception {
        assertEquals(null, Interval.lookupBySemitones(-1));
        assertEquals(null, Interval.lookupBySemitones(100));
    }

    @Test
    public void testLookupInvalidNames() throws Exception {
        assertEquals(null, Interval.lookupByName("octave"));
        assertEquals(null, Interval.lookupByName("perfect second"));
        assertEquals(null, Interval.lookupByName("test"));
    }




}