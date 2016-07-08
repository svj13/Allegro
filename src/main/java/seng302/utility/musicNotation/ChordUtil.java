package seng302.utility.musicNotation;

import java.util.ArrayList;

import seng302.data.Note;

/**
 * Created by Jonty on 24-May-16.
 */
public class ChordUtil {


    /**
     * Returns the chord name for the given midi vaue.
     *
     * @param notes  ArrayList of Note values
     * @param octave if true, treats all Notes as their middle octave value.
     * @return Name of the given chord. e.g. C major or C minor.
     */
    public static String getChordName(ArrayList<Integer> notes, Boolean octave) {

        if (notes.size() > 2) {
            String noteDisplay = octave ? Note.lookup(String.valueOf(notes.get(0))).getNote() : OctaveUtil.removeOctaveSpecifier(Note.lookup(String.valueOf(notes.get(0))).getNote()); //Ignore Octave or not?


            if (notes.get(1) == notes.get(0) + 4) {

                return noteDisplay + " major";

            } else if (notes.get(1) == notes.get(0) + 3) {
                return noteDisplay + " minor";
            }
        }
        throw new IllegalArgumentException("Not a chord");

    }


    /**
     * Retrieves the Chord notes for a specified Note. e.g. major chord for Note C4 will return
     * Notes C4, E4, G4.
     *
     * @param note Note note corresponding to the chord.
     * @param type String type of chord (either major or minor)
     * @return ArrayList of Notes corresponding to the chord.
     */
    public static ArrayList<Note> getChord(Note note, String type) {
        ArrayList<Note> chordNotes = new ArrayList<Note>();
        //for major chords (triads)
        if (type.toLowerCase().equals("major")) {
            Note currentNote = note;
            chordNotes.add(currentNote);
            chordNotes.add(currentNote.semitoneUp(4));
            chordNotes.add(currentNote.semitoneUp(7));
            if (chordNotes.contains(null)) {
                return null;
            }

        //for minor chords (triads)
        } else if (type.toLowerCase().equals("minor")) {
            Note currentNote = note;
            chordNotes.add(currentNote);
            chordNotes.add(currentNote.semitoneUp(3));
            chordNotes.add(currentNote.semitoneUp(7));
            if (chordNotes.contains(null)) {
                return null;
            }


        } else {
            throw new IllegalArgumentException("Invalid chord type: '" + type + "'.");
        }
        return chordNotes;
    }

    /**
     * Just like the getChord method except it takes an arrayList of midi values rather than Note
     * values.
     *
     * @param type String type of chord (either major or minor)
     * @return ArrayList of Notes corresponding to the chord.
     */

    public static ArrayList<Integer> getChordMidi(int midi, String type) {
        ArrayList<Integer> chordNotes = new ArrayList<Integer>();
        Note currentNote = Note.lookup(String.valueOf(midi));
        if (type.toLowerCase().equals("major")) {

            chordNotes.add(currentNote.getMidi());
            chordNotes.add(currentNote.semitoneUp(4).getMidi());
            chordNotes.add(currentNote.semitoneUp(7).getMidi());
            if (chordNotes.contains(null)) {
                return null;
            }


        } else if (type.toLowerCase().equals("minor")) {

            chordNotes.add(currentNote.getMidi());
            chordNotes.add(currentNote.semitoneUp(3).getMidi());
            chordNotes.add(currentNote.semitoneUp(7).getMidi());
            if (chordNotes.contains(null)) {
                return null;
            }
        } else {
            throw new IllegalArgumentException("Invalid chord type: '" + type + "'.");
        }
        return chordNotes;
    }


}
