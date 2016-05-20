package seng302.command;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import seng302.Environment;
import seng302.managers.TranscriptManager;

import static org.mockito.Mockito.verify;

/**
 * Created by emily on 20/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class KeySignatureCommandTest {

    private Environment env;
    @Mock
    private TranscriptManager transcriptManager;

    @Before
    public void setUp() throws Exception {
        env = new Environment();
        env.setTranscriptManager(transcriptManager);
    }


    @Test
    public void testKeySigFetchNotes() {
        HashMap<String, String> scale = new HashMap<String, String>();
        scale.put("scale_type", "minor");
        scale.put("note", "C");
        new KeySignatureCommand(scale, "notes").execute(env);
        verify(transcriptManager).setResult("Bb, Eb, Ab");

        scale.clear();
        scale.put("scale_type", "major");
        scale.put("note", "C");
        new KeySignatureCommand(scale, "notes").execute(env);
        verify(transcriptManager).setResult("C major has no key signatures");

        scale.clear();
        scale.put("scale_type", "major");
        scale.put("note", "C#");
        new KeySignatureCommand(scale, "notes").execute(env);
        verify(transcriptManager).setResult("F#, C#, G#, D#, A#, E#, B#");


    }

    @Test
    public void testGetNumberSharpsFlats() {
        HashMap<String, String> scale = new HashMap<String, String>();
        scale.put("scale_type", "minor");
        scale.put("note", "C");
        new KeySignatureCommand(scale, "number").execute(env);
        verify(transcriptManager).setResult("3b");

        scale.clear();
        scale.put("scale_type", "major");
        scale.put("note", "C");
        new KeySignatureCommand(scale, "number").execute(env);
        verify(transcriptManager).setResult("C major has 0# and 0b");

        scale.clear();
        scale.put("scale_type", "major");
        scale.put("note", "C#");
        new KeySignatureCommand(scale, "number").execute(env);
        verify(transcriptManager).setResult("7#");
    }

    @Test
    public void testGetScaleWithNumberSharpsFlats() {
        new KeySignatureCommand("2#").execute(env);
        verify(transcriptManager).setResult("D major, B minor");

        new KeySignatureCommand("2b").execute(env);
        verify(transcriptManager).setResult("Bb major, G minor");

        new KeySignatureCommand("0#b").execute(env);
        verify(transcriptManager).setResult("C major, A minor");
    }


}
