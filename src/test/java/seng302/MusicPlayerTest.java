package seng302;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import seng302.data.Note;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by isabelle on 13/07/16.
 */
public class MusicPlayerTest {
    private MusicPlayer player;


    @Before
    public void setUp() throws Exception {
        player = new MusicPlayer();
    }

    @Test
    public void testStopping() {
        player.stop();
        assertFalse(player.seq.isRunning());
    }

    @Test
    public void testSimultaneousNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        notes.add(Note.lookup("C4"));
        notes.add(Note.lookup("E4"));
        player.playSimultaneousNotes(notes);
        assertTrue(player.seq.getSequence().getTracks()[0].get(1).getMessage().getMessage()[1] == 60);
        assertTrue(player.seq.getSequence().getTracks()[0].get(2).getMessage().getMessage()[1] == 64);
    }


}