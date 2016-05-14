package seng302.command;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import seng302.Environment;
import seng302.managers.TranscriptManager;

import static org.mockito.Mockito.times;
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

        interval.put("interval", "diminished fifth");
        new IntervalCommand(interval, "semitones").execute(env);
        verify(transcriptManager).setResult("6 semitones");

        interval.put("interval", "major ninth");
        new IntervalCommand(interval, "semitones").execute(env);
        verify(transcriptManager).setResult("14 semitones");

        interval.put("interval", "double octave");
        new IntervalCommand(interval, "semitones").execute(env);
        verify(transcriptManager).setResult("24 semitones");
    }



    @Test
    public void setsCorrectSemitonesWihSameNameResult() {
        interval.put("interval", "minor ninth");
        new IntervalCommand(interval, "semitones").execute(env);

        interval.put("interval", "minor 9th");
        new IntervalCommand(interval, "semitones").execute(env);
        verify(transcriptManager, times(2)).setResult("13 semitones");

        interval.put("interval", "diminished fifth");
        new IntervalCommand(interval, "semitones").execute(env);

        interval.put("interval", "diminished 5th");
        new IntervalCommand(interval, "semitones").execute(env);
        verify(transcriptManager, times(2)).setResult("6 semitones");

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
    public void setsCorrectNoteResultSameKey() {
        interval.put("interval", "diminished 7th");
        interval.put("note", "E");
        new IntervalCommand(interval, "note").execute(env);
        verify(transcriptManager).setResult("Db");

        interval.clear();
        interval.put("interval", "major 6th");
        interval.put("note", "E");
        new IntervalCommand(interval, "note").execute(env);
        verify(transcriptManager).setResult("C#");

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

        interval.clear();
        interval.put("interval", "minor seventh");
        interval.put("note", "G9");
        new IntervalCommand(interval, "note").execute(env);
        verify(transcriptManager).setResult("[ERROR] The resulting note is higher than the highest note supported by this application.");

    }




    @Test
    public void setsCorrectPlayErrors() {
        interval.put("interval", "perfect fourth");
        interval.put("note", "M");
        new IntervalCommand(interval, "play").execute(env);
        verify(transcriptManager).setResult("[ERROR] 'M' is not a valid note.");

        interval.clear();
        interval.put("interval", "blah");
        interval.put("note", "C");
        new IntervalCommand(interval, "play").execute(env);
        verify(transcriptManager).setResult("[ERROR] Unknown interval: blah");

//        interval.clear();
//        interval.put("semitones", "15");
//        interval.put("note", "C");
//        new IntervalCommand(interval, "play").execute(env);
//        verify(transcriptManager).setResult("[ERROR] Unknown interval: 15");

        interval.clear();
        interval.put("semitones", "-1");
        interval.put("note", "C");
        new IntervalCommand(interval, "play").execute(env);
        verify(transcriptManager).setResult("[ERROR] Unknown interval: -1");
    }
}
