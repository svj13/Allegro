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
        new IntervalCommand(intervalWords, "G", "note").execute(env);
        verify(transcriptManager).setResult("C");

        intervalWords.clear();
        intervalWords.add("major");
        intervalWords.add("seventh");
        new IntervalCommand(intervalWords, "G4", "note").execute(env);
        verify(transcriptManager).setResult("F#5");
    }

    @Test
    public void setsCorrectNoteErrors() {
        intervalWords.add("perfect");
        intervalWords.add("fourth");
        new IntervalCommand(intervalWords, "M", "note").execute(env);
        verify(transcriptManager).setResult("[ERROR] 'M' is not a valid note.");

        intervalWords.clear();
        intervalWords.add("blah");
        new IntervalCommand(intervalWords, "C", "note").execute(env);
        verify(transcriptManager).setResult("[ERROR] Unknown interval: blah");
    }

    @Test
    public void setsCorrectPlayErrors() {
        intervalWords.add("perfect");
        intervalWords.add("fourth");
        new IntervalCommand(intervalWords, "M", "play").execute(env);
        verify(transcriptManager).setResult("[ERROR] 'M' is not a valid note.");

        intervalWords.clear();
        intervalWords.add("blah");
        new IntervalCommand(intervalWords, "C", "play").execute(env);
        verify(transcriptManager).setResult("[ERROR] Unknown interval: blah");

        intervalWords.clear();
        intervalWords.add("15");
        new IntervalCommand(intervalWords, "C", "play").execute(env);
        verify(transcriptManager).setResult("[ERROR] Unknown interval: 15");

        intervalWords.clear();
        intervalWords.add("-1");
        new IntervalCommand(intervalWords, "C", "play").execute(env);
        verify(transcriptManager).setResult("[ERROR] Unknown interval: -1");
    }
}
