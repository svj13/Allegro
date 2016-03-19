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
 * Created by isabelle on 18/03/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ScaleTest {

    private Environment env;
    @Mock
    private TranscriptManager transcriptManager;

    @Before
    public void setUp() throws Exception {
        env = new Environment();
        env.setTranscriptManager(transcriptManager);
    }

    @Test
    public void setsCorrectScaleResult() {
        new Scale("C4", "major", "note").execute(env);
        verify(transcriptManager).setResult("C4 D4 E4 F4 G4 A4 B4 C5");
    }

    @Test
    public void setsCorrectScaleResultWithoutOctaveSpecifier() {
        new Scale("C", "major", "note").execute(env);
        verify(transcriptManager).setResult("C D E F G A B C");
    }

    @Test
    public void setsCorrectScaleResultWithFlat() {
        new Scale("F", "major", "note").execute(env);
        verify(transcriptManager).setResult("F G A Bb C D E F");
    }

    @Test
    public void setsCorrectScaleResultWithEnharmonicSharp() {
        new Scale("F#", "major", "note").execute(env);
        verify(transcriptManager).setResult("F# G# A# B C# D# E# F#");
    }

    @Test
    public void setsCorrectScaleResultWithTwoEnharmonicSharps() {
        new Scale("C#", "major", "note").execute(env);
        verify(transcriptManager).setResult("C# D# E# F# G# A# B# C#");
    }

    @Test
    public void setsCorrectScaleResultWithFlatScale() {
        new Scale("Fb", "major", "note").execute(env);
        verify(transcriptManager).setResult("Fb Gb Ab Bbb Cb Db Eb Fb");
    }

    @Test
    public void setsCorrectErrorMessage() {
        new Scale("C4", "cake", "note").execute(env);
        verify(transcriptManager).setResult("[ERROR] Invalid scale type: 'cake'.");
    }

    @Test
    public void setsCorrectErrorMessageBadNote() {
        new Scale("cake", "major", "note").execute(env);
        verify(transcriptManager).setResult("[ERROR] Note is not contained in the MIDI library.");
    }

    @Test
    public void correctResultWithCaseInsensitivity() {
        new Scale("c#", "major", "note").execute(env);
        verify(transcriptManager).setResult("C# D# E# F# G# A# B# C#");
    }

    @Test
    public void correctResultWithCaseInsensitivityOfType() {
        new Scale("C#", "maJOr", "note").execute(env);
        verify(transcriptManager).setResult("C# D# E# F# G# A# B# C#");
    }

    @Test
    public void correctErrorMessageForDoubleSharpScale() {
        new Scale("Fx", "major", "note").execute(env);
        verify(transcriptManager).setResult("[ERROR] Invalid scale: 'Fx major'.");
    }

    // MIDI scale tests

    @Test
    public void setsCorrectScaleResultMidi() {
        new Scale("C4", "major", "midi").execute(env);
        verify(transcriptManager).setResult("60 62 64 65 67 69 71 72");
    }

    @Test
    public void setsCorrectErrorMessageMidi() {
        new Scale("C4", "cake", "midi").execute(env);
        verify(transcriptManager).setResult("[ERROR] Invalid scale type: 'cake'.");
    }

    @Test
    public void setsCorrectErrorMessageBadNoteMidi() {
        new Scale("cake", "major", "midi").execute(env);
        verify(transcriptManager).setResult("[ERROR] Note is not contained in the MIDI library.");
    }

    @Test
    public void setsCorrectEnharmonicScaleMidi() {
        new Scale("B#", "major", "midi").execute(env);
        verify(transcriptManager).setResult("60 62 64 65 67 69 71 72");
    }

    @Test
    public void correctErrorMessageForDoubleSharpScaleMidi() {
        new Scale("Fx", "major", "midi").execute(env);
        verify(transcriptManager).setResult("[ERROR] Invalid scale: 'Fx major'.");
    }





}