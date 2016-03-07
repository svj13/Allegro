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
public class SemitoneTest {

    private Environment env;
    @Mock private TranscriptManager transcriptManager;

    @Before
    public void setUp() throws Exception {
        env = new Environment();
        env.setTranscriptManager(transcriptManager);
    }

    @Test
    public void printsCorrectSemitoneUp() {
        new Semitone("C#-1", true).execute(env);
        verify(transcriptManager).setResult("D-1");
    }

    @Test
    public void printsCorrectSemitoneDown() {
        new Semitone("C#-1", false).execute(env);
        verify(transcriptManager).setResult("C-1");
    }

    @Test
    public void printsCorrectError() {
        new Semitone("Cake", true).execute(env);
        verify(transcriptManager).setResult("Cake is not contained in the midi library");
    }
}