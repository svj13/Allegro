package seng302.utility;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jonty on 2/03/16.
 */
public class CommandHistoryTest {
    TranscriptManager tm;
    @Before
    public void setUp() throws Exception {



        tm = new TranscriptManager();

        tm.setCommand("note 60");
        tm.setResult("C4");
        tm.setCommand("midi G#0");
        tm.setResult("0");







    }

    @Test
     public void testScrollingUp(){

        String textInput = "comma"; //Emulated beginning to write a command.


        /**
         * note 60
         * midi G#0
         * input: comma
         */

        assertEquals("midi G#0", tm.historyController.handleScrollUp(textInput) ); //Pressing up before any history is added


        assertEquals("note 60", tm.historyController.handleScrollUp(textInput) );

        //assertEquals("note 60", tm.inputHistory.handleScrollUp("midi G#0"));


        assertEquals("note 60", tm.historyController.handleScrollUp("note 60"));








    }

    @Test
    public void testUpAndDown(){
        tm = new TranscriptManager();
        String textInput = "comma"; //Emulated beginning to write a command.

        assertEquals(textInput, tm.historyController.handleScrollUp(textInput) ); //Pressing up before any history is added



        tm.setCommand("note 60");
        tm.setResult("C4");

        assertEquals("note 60", tm.historyController.handleScrollUp(textInput) ); //up to history command.
        //assertEquals(textInput, tm.historyController.handleScrollDown("note 60"));

        assertEquals("", tm.historyController.handleScrollDown(textInput) ); //Pressing down again should clear cmd.

    }





}