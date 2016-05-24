package seng302.utility.musicNotation;

import seng302.data.Note;

import java.util.ArrayList;

/**
 * Created by Jonty on 24-May-16.
 */
public class ChordUtil {


    public static String getChordName(ArrayList<Note> notes) {

        if (notes.size() > 2) {
            if (notes.get(1) == notes.get(0).semitoneUp(4)) {
                return notes.get(0).getNote() + " major";

            } else if (notes.get(1) == notes.get(0).semitoneUp(3)) {
                return notes.get(0).getNote() + "minor";
            }
        }
        throw new IllegalArgumentException("Not a chord");

    }


    public static ArrayList<Note> getChord(Note note, String type) {
        ArrayList<Note> chordNotes = new ArrayList<Note>();
        if (type.toLowerCase().equals("major")) {
            Note currentNote = note;
            chordNotes.add(currentNote);
            chordNotes.add(currentNote.semitoneUp(4));
            chordNotes.add(currentNote.semitoneUp(7));
            if (chordNotes.contains(null)) {
                return null;
            }


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
}
