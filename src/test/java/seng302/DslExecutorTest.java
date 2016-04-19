package seng302;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import seng302.command.Command;
import seng302.utility.TranscriptManager;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;


/**
 * Created by isabelle on 7/03/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class DslExecutorTest {
    @Mock
    private Environment env;
    private DslExecutor executor;

    @Before
    public void initializeApp() {
        executor = new DslExecutor(env);
    }

    @After
    public void clearApp() {
        executor = null;
    }

    @Test
    public void executesCommandsWithCorrectEnvironment() {
        Command mockCommand = mock(Command.class);

        executor.executeCommand(mockCommand);

        verify(mockCommand).execute(env);
    }

    @Test
    public void canParseValidCommandStrings() {
        Command command = executor.parseCommandString("midi C#1");

        assertThat(command, instanceOf(seng302.command.Midi.class));
    }

    @Test
    public void returnsANullCommandOnParseFailure() {
        Command command = executor.parseCommandString("cake");

        assertThat(command, instanceOf(seng302.command.NullCommand.class));
    }

    @Test
    public void logsErrorOnParseFailure() {
        Command command = executor.parseCommandString("cake");

        verify(env).error("'cake' is not a valid command.");
    }
}