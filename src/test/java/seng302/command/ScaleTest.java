package seng302.command;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;

import seng302.Environment;
import seng302.MusicPlayer;
import seng302.data.Note;
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
    @Mock
    private MusicPlayer player;

    @Before
    public void setUp() throws Exception {
        env = new Environment();
        env.setTranscriptManager(transcriptManager);
        env.setPlayer(player);
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

    @Test
    public void testPlayScale() {
        new Scale("F", "major", "play").execute(env);
        verify(transcriptManager).setResult("F G A Bb C D E F");
        verify(player).playNotes(Note.lookup("F4").getScale("major"));
    }

    @Test
    public void testPlayCorrectScaleDown() {
        new Scale("C", "major", "play", "down").execute(env);
        verify(transcriptManager).setResult("C B A G F E D C");
        ArrayList<Note> scale = Note.lookup("C4").getScale("major");
        Collections.reverse(scale);
        verify(player).playNotes(scale);
    }

    @Test
    public void testPlayCorrectScaleUpDown() {
        new Scale("C", "major", "play", "updown").execute(env);
        verify(transcriptManager).setResult("C D E F G A B C C B A G F E D C");
        ArrayList<Note> notesToPlay = Note.lookup("C4").getScale("major");
        ArrayList<Note> notes = new ArrayList<Note>(notesToPlay);
        Collections.reverse(notes);
        notesToPlay.addAll(notes);
        verify(player).playNotes(notesToPlay);
    }

    @Test
    public void testCorrectErrorInvalidDirection() {
        new Scale("C", "major", "play", "cake").execute(env);
        verify(transcriptManager).setResult("[ERROR] 'cake' is not a valid scale direction. Try 'up', 'updown' or 'down'.");
    }

    @Test
    public void testNoteTooHighForScale() {
        new Scale("C9", "major", "note").execute(env);
        verify(transcriptManager).setResult("[ERROR] This scale goes beyond the MIDI notes available.");
    }

    @Test
    public void testNoteTooLowForScale() {
        new Scale("D-1", "major", "note", "down").execute(env);
        verify(transcriptManager).setResult("[ERROR] This scale goes beyond the MIDI notes available.");
    }

    @Test
    public void testNoteTooHighForScaleTwoOctaves() {
        new Scale("C8", "major", "note", "up", 2).execute(env);
        verify(transcriptManager).setResult("[ERROR] This scale goes beyond the MIDI notes available.");
    }

    @Test
    public void testNoteTooHighForScaleUpDown() {
        new Scale("E9", "major", "note", "updown").execute(env);
        verify(transcriptManager).setResult("[ERROR] This scale goes beyond the MIDI notes available.");
    }






}