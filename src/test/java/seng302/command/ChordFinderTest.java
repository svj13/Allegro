package seng302.command;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import seng302.Environment;
import seng302.data.Note;
import seng302.managers.TranscriptManager;
import seng302.utility.musicNotation.OctaveUtil;

import java.util.HashSet;

import static org.mockito.Mockito.verify;

/**
 * Created by Jonty on 24-May-16.
 */

@RunWith(MockitoJUnitRunner.class)
public class ChordFinderTest {

    Environment env;
    @Mock
    private TranscriptManager transcriptManager;

    @Before
    public void setUp() throws Exception {
        env = new Environment();
        env.setTranscriptManager(transcriptManager);

    }

    @Test
    public void testGetLength() throws Exception {

    }

    @Test
    public void testValidTrioNotAll() throws Exception {
        //C Major: C E G
        HashSet<Note> notes = new HashSet<Note>();
        notes.add(Note.lookup(OctaveUtil.validateNoteString("C")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("E")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("G")));


        new ChordFinder(notes, false).execute(env);
        verify(transcriptManager).setResult("C Major");

    }

    @Test
    public void testValidTrioUnOrdered() {


    }


}