package seng302.command;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import seng302.Environment;
import seng302.utility.TranscriptManager;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class IntervalCommandTest {

    private Environment env;
    @Mock
    private TranscriptManager transcriptManager;
    private ArrayList<String> intervalWords;

    @Before
    public void setUp() throws Exception {
        env = new Environment();
        env.setTranscriptManager(transcriptManager);
        intervalWords = new ArrayList<String>();
    }

    @Test
    public void setsCorrectSemitoneResult() {
        intervalWords.add("perfect");
        intervalWords.add("octave");
        new IntervalCommand(intervalWords).execute(env);
        verify(transcriptManager).setResult("12");
    }

    @Test
    public void setsCorrectErrorResult() {
        intervalWords.add("blah");
        new IntervalCommand(intervalWords).execute(env);
        verify(transcriptManager).setResult("[ERROR] Unknown interval: blah");
    }

    @Test
    public void setsCorrectNoteResult() {
        intervalWords.add("perfect");
        intervalWords.add("fourth");
        new IntervalCommand(intervalWords, "G").execute(env);
        verify(transcriptManager).setResult("C");

        intervalWords.clear();
        intervalWords.add("major");
        intervalWords.add("seventh");
        new IntervalCommand(intervalWords, "G4").execute(env);
        verify(transcriptManager).setResult("F#5");
    }

    @Test
    public void setsCorrectNoteErrors() {
        intervalWords.add("perfect");
        intervalWords.add("fourth");
        new IntervalCommand(intervalWords, "M").execute(env);
        verify(transcriptManager).setResult("[ERROR] 'M' is not a valid note.");

        intervalWords.clear();
        intervalWords.add("blah");
        new IntervalCommand(intervalWords, "C").execute(env);
        verify(transcriptManager).setResult("[ERROR] Unknown interval: blah");
    }
}
