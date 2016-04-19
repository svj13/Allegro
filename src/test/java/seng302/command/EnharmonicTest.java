package seng302.command;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import seng302.Environment;
import seng302.command.Enharmonic;
import seng302.utility.TranscriptManager;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by team 5 on 18/03/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class EnharmonicTest extends TestCase {

    private Environment env;
    @Mock
    private TranscriptManager transcriptManager;

    @Before
    public void setUp() throws Exception {
        env = new Environment();
        env.setTranscriptManager(transcriptManager);
    }

    @Test
    public void testEnharmonicSharpName() throws Exception {
        new Enharmonic("C4", 1).execute(env);
        verify(transcriptManager).setResult("B#4");

        new Enharmonic("C", 1).execute(env);
        verify(transcriptManager).setResult("B#");

        new Enharmonic("F4", 1).execute(env);
        verify(transcriptManager).setResult("E#4");

    }

    @Test
    public void testEnharmonicFlatName() throws Exception {
        new Enharmonic("E4", 0).execute(env);
        verify(transcriptManager).setResult("Fb4");

        new Enharmonic("F4", 0).execute(env);
        verify(transcriptManager).setResult("Gbb4");

    }

    @Test
    public void testSimpleEnharmonic() throws Exception {
        new Enharmonic("C4", 2).execute(env);
        verify(transcriptManager).setResult("B#4");

        new Enharmonic("D4", 2).execute(env);
        verify(transcriptManager).setResult("[ERROR] Note does not have a simple enharmonic.");

        new Enharmonic("E4", 2).execute(env);
        verify(transcriptManager).setResult("Fb4");

        new Enharmonic("F4", 2).execute(env);
        verify(transcriptManager).setResult("E#4");

        new Enharmonic("G4", 2).execute(env);
        verify(transcriptManager, times(2)).setResult("[ERROR] Note does not have a simple enharmonic.");

        new Enharmonic("A4", 2).execute(env);
        verify(transcriptManager, times(3)).setResult("[ERROR] Note does not have a simple enharmonic.");

        new Enharmonic("B4", 2).execute(env);
        verify(transcriptManager).setResult("Cb4");

    }
}