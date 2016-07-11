package seng302.command;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;


import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import seng302.Environment;
import seng302.MusicPlayer;
import seng302.managers.TranscriptManager;

@RunWith(MockitoJUnitRunner.class)
public class ChordTest {
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


    /**
     * Checks to see if invalid chords are being handled correctly, namely chords that contain
     * notes that exceed the octave range
     */
    @Test
    public void catchInvalidChords() {

        HashMap<String, String> chordMap1 = new HashMap<String, String>();
        chordMap1.put("note", "G9");
        chordMap1.put("scale_type", "major");

        HashMap<String, String> chordMap2 = new HashMap<String, String>();
        chordMap2.put("note", "c#9");
        chordMap2.put("scale_type", "minor");

        /** this error is handled by the DSL */
        //HashMap<String, String> chordMap3 = new HashMap<String, String>();
        //chordMap3.put("note", "100");
        //chordMap3.put("scale_type", "major");

        new Chord(chordMap1, "chord").execute(env);
        verify(transcriptManager).setResult("Invalid chord: G9 major. Exceeds octave range.");
        new Chord(chordMap2, "chord").execute(env);
        verify(transcriptManager).setResult("Invalid chord: c#9 minor. Exceeds octave range.");
        //new ChordUtil(chordMap3, "chord").execute(env);
        //verify(transcriptManager).setResult("[ERROR] Invalid command. new ChordUtil(chordMap1, "play").execute(env)//
    }

    /**
     * Tests to see if when a valid chord is passed through, that it prints the correct
     * output.
     * -should print notes of chords ascending from root note
     * -should print correct enharmonic
     * -should print whether it is playing a chord or not
     * -prints octave specifiers
     *
     */
    @Test
    public void correctChordPrintOut() {

        HashMap<String, String> chordMap1 = new HashMap<String, String>();
        chordMap1.put("note", "C4");
        chordMap1.put("scale_type", "major");

        HashMap<String, String> chordMap2 = new HashMap<String, String>();
        chordMap2.put("note", "G");
        chordMap2.put("scale_type", "minor");

        HashMap<String, String> chordMap3 = new HashMap<String, String>();
        chordMap3.put("note", "C");
        chordMap3.put("scale_type", "major");



        new Chord(chordMap1, "chord").execute(env);
        verify(transcriptManager).setResult("C4 E4 G4 ");
        new Chord(chordMap2, "chord").execute(env);
        verify(transcriptManager).setResult("G Bb D ");
        new Chord(chordMap3, "chord").execute(env);
        verify(transcriptManager).setResult("C E G ");



    }

    @Test
    public void validInversionNoOctaveOutputTest(){
        HashMap<String, String> chordMap = new HashMap<String, String>();
        chordMap.put("note", "G");
        chordMap.put("scale_type", "major");
        chordMap.put("inversion", "1");
        new Chord(chordMap, "chord").execute(env);
        verify(transcriptManager).setResult("B D G ");

    }

    @Test
    public void validInversionOctaveOutputTest(){
        HashMap<String, String> chordMap = new HashMap<String, String>();
        chordMap.put("note", "G4");
        chordMap.put("scale_type", "major");
        chordMap.put("inversion", "1");

        new Chord(chordMap, "chord").execute(env);
        verify(transcriptManager).setResult("B4 D5 G5 ");

    }


    /**Check to see if when the chord is playing, it prints the correct message
     *
     */
    @Test
    public void playChord() {

        HashMap<String, String> chordMap1 = new HashMap<String, String>();
        chordMap1.put("note", "C");
        chordMap1.put("scale_type", "major");
        chordMap1.put("playStyle", "arpeggio");

        HashMap<String, String> chordMap2 = new HashMap<String, String>();
        chordMap2.put("note", "G");
        chordMap2.put("scale_type", "major");


        new Chord(chordMap1, "play").execute(env);
        verify(transcriptManager).setResult("Playing chord C4 major");

        new Chord(chordMap2, "play").execute(env);
        verify(transcriptManager).setResult("Playing chord G4 major");


    }


    /**
     * Check to see if when the chord is playing, it prints the correct message
     */
    @Test
    public void playChordInversion() {

        HashMap<String, String> chordMap1 = new HashMap<String, String>();
        chordMap1.put("note", "F");
        chordMap1.put("scale_type", "major");
        chordMap1.put("playStyle", "arpeggio");
        chordMap1.put("inversion", "1");

        HashMap<String, String> chordMap2 = new HashMap<String, String>();
        chordMap2.put("note", "G");
        chordMap2.put("scale_type", "major");


        new Chord(chordMap1, "play").execute(env);
        verify(transcriptManager).setResult("Playing chord F4 major");

        new Chord(chordMap2, "play").execute(env);
        verify(transcriptManager).setResult("Playing chord G4 major");


    }


}
