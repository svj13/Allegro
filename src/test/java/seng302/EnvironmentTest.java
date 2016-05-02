package seng302;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import seng302.managers.TranscriptManager;

import static org.mockito.Mockito.verify;

/**
 * Created by isabelle on 7/03/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class EnvironmentTest {

    private Environment env;
    @Mock
    private TranscriptManager transcriptManager;

    @Before
    public void setUp() throws Exception {
        env = new Environment();
        env.setTranscriptManager(transcriptManager);
    }

    @Test
    public void testError() throws Exception {
        env.error("This went wrong.");
        verify(transcriptManager).setResult("[ERROR] This went wrong.");
    }
}