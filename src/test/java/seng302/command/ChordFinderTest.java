package seng302.command;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import seng302.Environment;
import seng302.data.Note;
import seng302.managers.TranscriptManager;
import seng302.utility.musicNotation.OctaveUtil;

import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Jonty on 24-May-16.
 */

@RunWith(MockitoJUnitRunner.class)
public class ChordFinderTest {

    Environment env;
    @Mock
    private TranscriptManager transcriptManager;

    @Before
    public void setUp() throws Exception {
        env = new Environment();
        env.setTranscriptManager(transcriptManager);

    }

    @Test
    public void testGetLength() throws Exception {

    }

    @Test
    public void testValidTrioNotAll() throws Exception {
        //C Major: C E G
        ArrayList<Note> notes = new ArrayList<Note>();
        notes.add(Note.lookup(OctaveUtil.validateNoteString("C4")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("E4")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("G4")));

        new ChordFinder(notes, false).execute(env);
        verify(transcriptManager).setResult("C major");
        //C minor (C Eb G)
        notes.clear();
        notes.add(Note.lookup(OctaveUtil.validateNoteString("C4")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("D#4")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("G4")));
        new ChordFinder(notes, false).execute(env);
        verify(transcriptManager).setResult("C minor");

        //F major (F A C)
        notes.clear();
        notes.add(Note.lookup(OctaveUtil.validateNoteString("F")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("A")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("C")));
        new ChordFinder(notes, false).execute(env);
        verify(transcriptManager).setResult("F major");


        //F minor (F Ab C)
        notes.clear();
        notes.add(Note.lookup(OctaveUtil.validateNoteString("F")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("Ab")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("C")));
        new ChordFinder(notes, false).execute(env);
        verify(transcriptManager).setResult("F minor");
    }

    @Test
    public void testValidTrioEnharmonics() throws Exception {
        //C Major: C E G
        ArrayList<Note> notes = new ArrayList<Note>();
        notes.add(Note.lookup(OctaveUtil.validateNoteString("C")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("E")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("G")));


        new ChordFinder(notes, false).execute(env);
        verify(transcriptManager).setResult("C major");
        //C minor (C Eb G)
        notes.clear();
        notes.add(Note.lookup(OctaveUtil.validateNoteString("B#")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("Eb")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("Fx")));
        new ChordFinder(notes, false).execute(env);
        verify(transcriptManager).setResult("C minor");

        //F major (F A C)
        notes.clear();
        notes.add(Note.lookup(OctaveUtil.validateNoteString("F")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("A")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("C")));
        new ChordFinder(notes, false).execute(env);
        verify(transcriptManager).setResult("F major");


        //F mibor (F Ab C)
        notes.clear();
        notes.add(Note.lookup(OctaveUtil.validateNoteString("E#")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("G#")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("B#")));
        new ChordFinder(notes, false).execute(env);
        verify(transcriptManager).setResult("F minor");
    }


    /**
     * Tests Finding chords in inputted in different orders.
     */
    @Test
    public void testValidTrioUnOrdered() {
        //C Major: C E G in order E G C
        ArrayList<Note> notes = new ArrayList<Note>();

        notes.add(Note.lookup(OctaveUtil.validateNoteString("E")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("G")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("C")));

        new ChordFinder(notes, false).execute(env);
        verify(transcriptManager).setResult("C major");




    }

    /**
     * tests Trios with no valid matching chords
     */
    @Test
    public void testInvalidTrio() {
        // A B C (No valid chord)
        ArrayList<Note> notes = new ArrayList<Note>();

        notes.add(Note.lookup(OctaveUtil.validateNoteString("A")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("B")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("C")));

        new ChordFinder(notes, false).execute(env);


        notes.clear();
        notes.add(Note.lookup(OctaveUtil.validateNoteString("F")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("A")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("G")));

        new ChordFinder(notes, false).execute(env);
        verify(transcriptManager, times(2)).setResult("No chords found for given notes.");
    }

    /**
     * tests Note sets of different sizes which should be invalid.
     */
    @Test
    public void testInvalidSizedSets() {
        // A B C (No valid chord)
        ArrayList<Note> notes = new ArrayList<Note>();

        notes.add(Note.lookup(OctaveUtil.validateNoteString("A")));

        new ChordFinder(notes, false).execute(env);

        notes.clear();
        notes.add(Note.lookup(OctaveUtil.validateNoteString("F")));
        notes.add(Note.lookup(OctaveUtil.validateNoteString("A")));

        new ChordFinder(notes, false).execute(env);
        verify(transcriptManager, times(2)).setResult("Not chords found. Must provide either 3 or 4 notes.");
    }






}