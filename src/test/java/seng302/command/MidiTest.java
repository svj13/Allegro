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
public class MidiTest {

    private Environment env;
    @Mock private TranscriptManager transcriptManager;

    @Before
    public void setUp() throws Exception {
        env = new Environment();
        env.setTranscriptManager(transcriptManager);
    }

    @Test
    public void printsCorrectMidi() {
        new Midi("C#-1").execute(env);
        verify(transcriptManager).setResult("1");
    }

    @Test
    public void printsCorrectMidiUnspecifiedOctave() {
        new Midi("C#").execute(env);
        verify(transcriptManager).setResult("61");
    }

    @Test
    public void setsCorrectErrorResult() {
        new Midi("Z#").execute(env);
        verify(transcriptManager).setResult("[ERROR] \'Z#\' is not a valid note.");
    }
}