package seng302.utility.musicNotation;

import seng302.data.Note;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Jonty on 24-May-16.
 */
public class ChordUtil {


    public static String getChordName(ArrayList<Note> notes, Boolean octave) {

        if (notes.size() > 2) {
            String noteDisplay = octave ? notes.get(0).getNote() : notes.get(0).getNote().substring(0, 1); //Ignore Octave or not?
            if (notes.get(1) == notes.get(0).semitoneUp(4)) {


                return noteDisplay + " major";

            } else if (notes.get(1) == notes.get(0).semitoneUp(3)) {
                return noteDisplay + " minor";
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
