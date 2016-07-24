package seng302.utility.musicNotation;

import java.util.ArrayList;

import seng302.data.Note;

/**
 * Created by Jonty on 24-May-16.
 */
public class ChordUtil {



    public static String getChordName(ArrayList<Integer> notes, boolean octave, char enharmonicLetter){

        String type = getChordType(notes);


        String n = octave ? Note.lookup(notes.get(0).toString()).getEnharmonicWithLetter(enharmonicLetter) :
                        OctaveUtil.removeOctaveSpecifier(Note.lookup(notes.get(0).toString())
                                .getEnharmonicWithLetter(enharmonicLetter));

        return n +" " + type;

    }

    public static String getChordType(ArrayList<Integer> notes){
        if (notes.size() > 3) {
            //String noteDisplay = octave ? Note.lookup(String.valueOf(notes.get(0))).getNote() : OctaveUtil.removeOctaveSpecifier(Note.lookup(String.valueOf(notes.get(0))).getNote()); //Ignore Octave or not?

            if (notes.get(1) % 12 == (notes.get(0) + 3) % 12 && notes.get(2) % 12 == (notes.get(0) + 7) % 12
                    && notes.get(3) % 12 == (notes.get(0) + 10) % 12) {
                return "minor 7th";
                //for major 7th chords
            } else if (notes.get(1) % 12 == (notes.get(0) + 4) % 12 && notes.get(2) % 12 == (notes.get(0) + 7) % 12
                    && notes.get(3) % 12 == (notes.get(0) + 11) % 12) {
                return "major 7th";
                //for 7th chords
            } else if (notes.get(1) % 12 == (notes.get(0) + 4) % 12 && notes.get(2) % 12 == (notes.get(0) + 7) % 12
                    && notes.get(3) % 12 == (notes.get(0) + 10) % 12) {
                return "seventh";
                //for half diminished chords
            } else if (notes.get(1) % 12 == (notes.get(0) + 3) % 12 && notes.get(2) % 12 == (notes.get(0) + 6) % 12
                    && notes.get(3) % 12 == (notes.get(0) + 10) % 12) {
                return "half diminished";
                //for diminished chords
            } else if (notes.get(1) % 12 == (notes.get(0) + 3) % 12 && notes.get(2) % 12 == (notes.get(0) + 6) % 12
                    && notes.get(3) % 12 == (notes.get(0) + 9) % 12) {
                return "diminished 7th";
                //for diminished chords
            }
        }


        if (notes.size() > 2) { //Scales

            //for major chords
            if (notes.get(1) % 12 == (notes.get(0) + 4) % 12 && notes.get(2) % 12 == (notes.get(0) + 7) % 12) {
                return "major";
                //for minor chords
            } else if (notes.get(1) % 12 == (notes.get(0) + 3) % 12 && notes.get(2) % 12 == (notes.get(0) + 7) % 12) {
                return "minor";
                //for minor 7th chords
            } else if (notes.get(1) % 12 == (notes.get(0) + 3) % 12 && notes.get(2) % 12 == (notes.get(0) + 6) % 12) {
                return "diminished";
            }


        }
        throw new IllegalArgumentException("Not a chord");
    }




    /**
     * Returns the chord name for the given midi vaue.
     *
     * @param notes  ArrayList of Note values
     * @param octave if true, treats all Notes as their middle octave value.
     * @return Name of the given chord. e.g. C major or C minor.
     */
    public static String getChordName(ArrayList<Integer> notes, Boolean octave) {

        if (notes.size() > 3) {
            String noteDisplay = octave ? Note.lookup(String.valueOf(notes.get(0))).getNote() : OctaveUtil.removeOctaveSpecifier(Note.lookup(String.valueOf(notes.get(0))).getNote()); //Ignore Octave or not?

            if (notes.get(1) % 12 == (notes.get(0) + 3) % 12 && notes.get(2) % 12 == (notes.get(0) + 7) % 12
                    && notes.get(3) % 12 == (notes.get(0) + 10) % 12) {
                return noteDisplay + " minor 7th";
                //for major 7th chords
            } else if (notes.get(1) % 12 == (notes.get(0) + 4) % 12 && notes.get(2) % 12 == (notes.get(0) + 7) % 12
                    && notes.get(3) % 12 == (notes.get(0) + 11) % 12) {
                return noteDisplay + " major 7th";
                //for 7th chords
            } else if (notes.get(1) % 12 == (notes.get(0) + 4) % 12 && notes.get(2) % 12 == (notes.get(0) + 7) % 12
                    && notes.get(3) % 12 == (notes.get(0) + 10) % 12) {
                return noteDisplay + " seventh";
                //for half diminished chords
            } else if (notes.get(1) % 12 == (notes.get(0) + 3) % 12 && notes.get(2) % 12 == (notes.get(0) + 6) % 12
                    && notes.get(3) % 12 == (notes.get(0) + 10) % 12) {
                return noteDisplay + " half diminished";
                //for diminished chords
            } else if (notes.get(1) % 12 == (notes.get(0) + 3) % 12 && notes.get(2) % 12 == (notes.get(0) + 6) % 12
                    && notes.get(3) % 12 == (notes.get(0) + 9) % 12) {
                return noteDisplay + " diminished 7th";
                //for diminished chords
            }
        }


        if (notes.size() > 2) { //Scales
            String noteDisplay = octave ? Note.lookup(String.valueOf(notes.get(0))).getNote() : OctaveUtil.removeOctaveSpecifier(Note.lookup(String.valueOf(notes.get(0))).getNote()); //Ignore Octave or not?

            //for major chords
            if (notes.get(1) % 12 == (notes.get(0) + 4) % 12 && notes.get(2) % 12 == (notes.get(0) + 7) % 12) {
                return noteDisplay + " major";
                //for minor chords
            } else if (notes.get(1) % 12 == (notes.get(0) + 3) % 12 && notes.get(2) % 12 == (notes.get(0) + 7) % 12) {
                return noteDisplay + " minor";
                //for minor 7th chords
            } else if (notes.get(1) % 12 == (notes.get(0) + 3) % 12 && notes.get(2) % 12 == (notes.get(0) + 6) % 12) {
                return noteDisplay + " diminished";
            }


        }
        throw new IllegalArgumentException("Not a chord");

    }




    public static char nextChordLetterChar(char c){
        int index = "ABCDEFG".indexOf(c);

        if (index >= 5) index -= 7; //Wraps around

        return "ABCDEFG".charAt(index + 2);
    }

    /**
     * Inverts a chord one time by shifting the first element to the end position.
     *
     * @param chord ArrayList of Notes resembling a chord
     * @return ArrayList of Notes resembling an inverted chord
     */
    public static ArrayList<Note> invertChord(ArrayList<Note> chord) {

        Note first = chord.remove(0); //Pop first element
        if (first.getMidi() + 12 <= 127) {
            chord.add(Note.lookup(String.valueOf(first.getMidi() + 12)));
        } else chord.add(Note.lookup(String.valueOf(first.getMidi() + 12 - 120)));


        return chord;
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
            //for minor 7th chords (4 note chords)
        } else if (type.toLowerCase().equals("minor 7th") ||
                type.toLowerCase().equals("minor seventh")) {
            Note currentNote = note;
            chordNotes.add(currentNote);
            chordNotes.add(currentNote.semitoneUp(3));
            chordNotes.add(currentNote.semitoneUp(7));
            chordNotes.add(currentNote.semitoneUp(10));
            if (chordNotes.contains(null)) {
                return null;
            }
            //for major 7th chords (4 note chords)
        } else if (type.toLowerCase().equals("major 7th") ||
                type.toLowerCase().equals("major seventh")) {
            Note currentNote = note;
            chordNotes.add(currentNote);
            chordNotes.add(currentNote.semitoneUp(4));
            chordNotes.add(currentNote.semitoneUp(7));
            chordNotes.add(currentNote.semitoneUp(11));
            if (chordNotes.contains(null)) {
                return null;
            }
            //for 7th chords (4-note chords)
        } else if (type.toLowerCase().equals("seventh") ||
                type.toLowerCase().equals("7th") ||
                type.toLowerCase().equals("seven")) {
            Note currentNote = note;
            chordNotes.add(currentNote);
            chordNotes.add(currentNote.semitoneUp(4));
            chordNotes.add(currentNote.semitoneUp(7));
            chordNotes.add(currentNote.semitoneUp(10));
            if (chordNotes.contains(null)) {
                return null;
            }
            //for diminished chords (triad)
        } else if (type.toLowerCase().equals("diminished") ||
                type.toLowerCase().equals("dim")) {
            Note currentNote = note;
            chordNotes.add(currentNote);
            chordNotes.add(currentNote.semitoneUp(3));
            chordNotes.add(currentNote.semitoneUp(6));
            if (chordNotes.contains(null)) {
                return null;
            }
            //for half diminished chords (4 note chords)
        } else if (type.toLowerCase().equals("half diminished seventh") ||
                type.toLowerCase().equals("half dim seventh") ||
                type.toLowerCase().equals("half dim 7th") ||
                type.toLowerCase().equals("half diminished 7th") ||
                type.toLowerCase().equals("half dim") ||
                type.toLowerCase().equals("half diminished")) {
            Note currentNote = note;
            chordNotes.add(currentNote);
            chordNotes.add(currentNote.semitoneUp(3));
            chordNotes.add(currentNote.semitoneUp(6));
            chordNotes.add(currentNote.semitoneUp(10));
            if (chordNotes.contains(null)) {
                return null;
            }
            //for diminished chords (4 chords)
        } else if (type.toLowerCase().equals("diminished seventh") ||
                type.toLowerCase().equals("dim seventh") ||
                type.toLowerCase().equals("dim 7th") ||
                type.toLowerCase().equals("diminished 7th")) {
            Note currentNote = note;
            chordNotes.add(currentNote);
            chordNotes.add(currentNote.semitoneUp(3));
            chordNotes.add(currentNote.semitoneUp(6));
            chordNotes.add(currentNote.semitoneUp(9));
            if (chordNotes.contains(null)) {
                return null;
            }
            //if the string does not match a chord type
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
        ArrayList<Integer> chordMidiNotes = new ArrayList<Integer>();
        ArrayList<Note> chordNotes = ChordUtil.getChord(Note.lookup(String.valueOf(midi)), type);
        try {
            for (Note note : chordNotes) {
                chordMidiNotes.add(note.getMidi());
            }
        } catch (NullPointerException e) {
            return null;
        }
        return chordMidiNotes;
    }


}
