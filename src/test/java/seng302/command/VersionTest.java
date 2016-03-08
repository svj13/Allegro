package seng302.command;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import seng302.Environment;
import seng302.utility.TranscriptManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by isabelle on 8/03/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class VersionTest {

    private Environment env;
    @Mock
    private TranscriptManager transcriptManager;

    @Before
    public void setUp() throws Exception {
        env = new Environment();
        env.setTranscriptManager(transcriptManager);
    }

    @Test
    public void testExecute() throws Exception {
        Version version = new Version();
        version.execute(env);
        verify(transcriptManager).setResult("Current Version: " + version.getCurrentVersion());

    }
}