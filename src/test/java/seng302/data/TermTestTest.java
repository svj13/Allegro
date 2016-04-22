package seng302.data;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TermTestTest {
    Term term1;

    @Before
    public void setUp() throws Exception {
        term1 = new Term("name", "category", "origin", "definition");
    }

    @Test
    public void testVariableStoredCorrectly() throws Exception {
        assertEquals("name", term1.getMusicalTermName());
        assertEquals("category", term1.getMusicalTermCategory());
        assertEquals("origin", term1.getMusicalTermOrigin());
    }

}