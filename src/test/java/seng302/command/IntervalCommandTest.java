package seng302.command;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;

import seng302.Environment;
import seng302.utility.TranscriptManager;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class IntervalCommandTest {

    private Environment env;
    @Mock
    private TranscriptManager transcriptManager;
    private HashMap<String, String> interval;

    @Before
    public void setUp() throws Exception {
        env = new Environment();
        env.setTranscriptManager(transcriptManager);
        interval = new HashMap<String, String>();
    }

    @Test
    public void setsCorrectSemitoneResult() {
        interval.put("interval", "perfect octave");
        new IntervalCommand(interval, "semitones").execute(env);
        verify(transcriptManager).setResult("12 semitones");
    }

    @Test
    public void setsCorrectErrorResult() {
        interval.put("interval", "blah");
        new IntervalCommand(interval, "semitones").execute(env);
        verify(transcriptManager).setResult("[ERROR] Unknown interval: blah");
    }

    @Test
    public void setsCorrectNoteResult() {
        interval.put("interval", "perfect fourth");
        interval.put("note", "G");
        new IntervalCommand(interval, "note").execute(env);
        verify(transcriptManager).setResult("C");

        interval.clear();
        interval.put("interval", "major seventh");
        interval.put("note", "G4");
        new IntervalCommand(interval, "note").execute(env);
        verify(transcriptManager).setResult("F#5");
    }

    @Test
    public void setsCorrectNoteErrors() {
        interval.put("interval", "perfect fourth");
        interval.put("note", "M");
        new IntervalCommand(interval, "note").execute(env);
        verify(transcriptManager).setResult("[ERROR] 'M' is not a valid note.");

        interval.clear();
        interval.put("interval", "blah");
        interval.put("note", "C");
        new IntervalCommand(interval, "note").execute(env);
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
