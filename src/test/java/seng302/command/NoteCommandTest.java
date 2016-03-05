package seng302.command;

import org.junit.Test;
import static org.mockito.Mockito.*;


import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import seng302.Environment;

@RunWith(MockitoJUnitRunner.class)
public class NoteCommandTest {

    @Mock private Environment env;

    @Test
    public void printsCorrectNote() {
        new NoteCommand(1).execute(env);

        //verify(env).println("C#-1");
    }

    @Test
    public void printsCorrectError() {
        new NoteCommand(128).execute(env);
        //verify(env).println("[ERROR] The provided number is not a valid MIDI value.");
    }
}