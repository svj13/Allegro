package seng302.command;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;


import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import seng302.Environment;
import seng302.utility.TranscriptManager;

@RunWith(MockitoJUnitRunner.class)
public class NoteCommandTest {

    private Environment env;
    @Mock private TranscriptManager transcriptManager;

    @Before
    public void setUp() throws Exception {
        env = new Environment();
        env.setTranscriptManager(transcriptManager);
    }

    @Test
    public void setsCorrectNoteResult() {
        new NoteCommand("1").execute(env);
        verify(transcriptManager).setResult("C#-1");
    }

    @Test
    public void setsCorrectErrorResult() {
        new NoteCommand("128").execute(env);
        verify(transcriptManager).setResult("[ERROR] '128' is not a valid MIDI value.");
    }
}