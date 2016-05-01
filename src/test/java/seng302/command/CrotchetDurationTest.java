package seng302.command;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import seng302.Environment;
import seng302.managers.TranscriptManager;

import static org.mockito.Mockito.verify;

/**
 * Created by emily on 1/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class CrotchetDurationTest {

    private Environment env;
    @Mock
    private TranscriptManager transcriptManager;

    @Before
    public void setUp() throws Exception {
        env = new Environment();
        env.setTranscriptManager(transcriptManager);
    }


    @Test
    public void testGetsDefaultCrotchet() {
        new CrotchetDuration().execute(env);
        verify(transcriptManager).setResult("The speed of a crotchet at 120 BPM" +
                " is 500.00 milliseconds.");
    }

    @Test
    public void testGetsDifferentCrotchet() {
        //Alter the tempo
        new Tempo("45", true).execute(env);
        verify(transcriptManager).setResult("The speed of a crotchet at 45 BPM" +
                " is 133.33 milliseconds.");

    }

    @Test
    public void testRoundsCorrectly() {
        new Tempo("36", true).execute(env);
        verify(transcriptManager).setResult("The speed of a crotchet at 36 BPM" +
                " is 166.67 milliseconds.");
    }


}
