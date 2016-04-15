package seng302.command;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import seng302.Environment;
import seng302.utility.TranscriptManager;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class IntervalCommandTest {

    private Environment env;
    @Mock
    private TranscriptManager transcriptManager;

    @Before
    public void setUp() throws Exception {
        env = new Environment();
        env.setTranscriptManager(transcriptManager);
    }

    @Test
    public void setsCorrectSemitoneResult() {
        new IntervalCommand("perfect octave").execute(env);
        verify(transcriptManager).setResult("12");
    }

    @Test
    public void setsCorrectErrorResult() {
        new IntervalCommand("blah").execute(env);
        verify(transcriptManager).setResult("[ERROR] Unknown interval: blah");
    }

    @Test
    public void setsCorrectNoteResult() {
        new IntervalCommand("perfect fourth", "G").execute(env);
        verify(transcriptManager).setResult("C");
    }

    @Test
    public void setsCorrectNoteErrors() {
        new IntervalCommand("perfect fourth", "M").execute(env);
        verify(transcriptManager).setResult("[ERROR] 'M' is not a valid note");

        new IntervalCommand("blah", "C").execute(env);
        verify(transcriptManager).setResult("[ERROR] Unknown interval: blah");
    }
}
