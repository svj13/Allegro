package seng302.utility;

import junit.framework.TestCase;
import junit.framework.TestResult;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Elliot on 7/03/2016.
 */
public class OutputTupleTest {

    @Before
    public void setUp() throws Exception {
        OutputTuple tuple = new OutputTuple();
        tuple.setCommand("");
        tuple.setResult("");
    }

    @Test
    public void testGetCommand() throws Exception {
        OutputTuple tuple = new OutputTuple("", "");
        assertEquals(tuple.getCommand(), "");
    }

    @Test
    public void testGetResult() throws Exception {
        OutputTuple tuple = new OutputTuple("", "");
        assertEquals(tuple.getResult(), "");
    }

    @Test
    public void testSetResult() throws Exception {

    }

    @Test
    public void testSetCommand() throws Exception {

    }
}