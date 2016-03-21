package seng302.data;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seng302.utility.Checker;
import seng302.utility.OctaveUtil;

/**
 * This Note class contains a static HashMap of all notes as well as the lookup method
 * and functionality for individual note objects.
 */
public class Note {
    private int midi;
    private String note;
    private HashMap<String, String> enharmonics;

    public static HashMap<String, Note> notes;
    private static List<String> noteNames = new ArrayList<String>(Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"));

    protected Note(int midi, String note, HashMap<String, String> enharmonics) {
        this.midi = midi;
        this.note = note;
        this.enharmonics = enharmonics;
    }

    /**
     * This is how the HashMap of notes is populated.
     */
    static {
        int current_octave = -1;
        notes = new HashMap<String, Note>();
        for (int i = 0; i < 128; i++) {
            HashMap<String, String> tempEnharmonics;
            HashMap<String, String> tempEnharmonics2;
            HashMap<String, String> tempEnharmonics3;

            String noteName = noteNames.get(i % 12).concat(Integer.toString(current_octave));
            int index = i % 12;
            switch (index) {
                case 0: // C
                    tempEnharmonics = generateEnharmonics(current_octave, "C", "Dbb", "B#", "B#");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "C", "C", "Note does not have enharmonic down", "C"); //Dbb
                    notes.put("B#".concat(Integer.toString(current_octave)), new Note(i, "B#", tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "C", "", "C", "C"); //B#
                    notes.put("Dbb".concat(Integer.toString(current_octave)), new Note(i, "Dbb", tempEnharmonics3));
                    break;
                case 1: // C#
                    tempEnharmonics = generateEnharmonics(current_octave, "Db", "Db", "Bx", "Db");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "Db", "Note does not have enharmonic up", "C#", "C#"); // Db
                    notes.put("Db".concat(Integer.toString(current_octave)), new Note(i, "Db", tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "Db", "C#", "Note does not have enharmonic down", "C#"); // Bx
                    notes.put("Bx".concat(Integer.toString(current_octave)), new Note(i, "Bx", tempEnharmonics3));
                    break;
                case 2: // D
                    tempEnharmonics = generateEnharmonics(current_octave, "D", "Ebb", "Cx", "Note does not have simple enharmonic");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "D", "Note does not have enharmonic up", "D", "D"); // Ebb
                    notes.put("Ebb".concat(Integer.toString(current_octave)), new Note(i, "Ebb", tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "D", "D", "Note does not have enharmonic down", "D"); // Cx
                    notes.put("Cx".concat(Integer.toString(current_octave)), new Note(i, "Cx", tempEnharmonics3));
                    break;
                case 3: // D#
                    tempEnharmonics = generateEnharmonics(current_octave, "Eb", "Eb", "Note does not have enharmonic down", "Eb");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "Eb", "Note does not have enharmonic up", "D#", "D#"); // Eb
                    notes.put("Eb".concat(Integer.toString(current_octave)), new Note(i, "Eb", tempEnharmonics2));
                    break;
                case 4: // E
                    tempEnharmonics = generateEnharmonics(current_octave, "E", "Fb", "Dx", "Fb");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "E", "E", "Note does not have enharmonic down", "E"); // Fb
                    notes.put("Fb".concat(Integer.toString(current_octave)), new Note(i, "Fb", tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "E", "Note does not have enharmonic up", "E", "E"); // Dx
                    notes.put("Dx".concat(Integer.toString(current_octave)), new Note(i, "Dx", tempEnharmonics3));
                    break;
                case 5: // F
                    tempEnharmonics = generateEnharmonics(current_octave, "F", "Gbb", "E#", "E#");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "F", "F", "", "F"); // E#
                    notes.put("E#".concat(Integer.toString(current_octave)), new Note(i, "E#", tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "F", "Note does not have enharmonic up", "F", "F"); // Gbb
                    notes.put("Gbb".concat(Integer.toString(current_octave)), new Note(i, "Gbb", tempEnharmonics3));
                    break;
                case 6: // F#
                    tempEnharmonics = generateEnharmonics(current_octave, "Gb", "Gb", "Ex", "Gb");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "Gb", "Note does not have enharmonic up", "F#", "F#"); // Gb
                    notes.put("Gb".concat(Integer.toString(current_octave)), new Note(i, "Gb", tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "Gb", "F#", "Note does not have enharmonic down", "F#"); // Ex
                    notes.put("Ex".concat(Integer.toString(current_octave)), new Note(i, "Ex", tempEnharmonics3));
                    break;
                case 7: // G
                    tempEnharmonics = generateEnharmonics(current_octave, "G", "Abb", "Fx", "Note does not have simple enharmonic");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "G", "Note does not have enharmonic up", "G", "G"); //Abb
                    notes.put("Abb".concat(Integer.toString(current_octave)), new Note(i, "Abb", tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "G", "G", "Note does not have enharmonic down", "C"); // Fx
                    notes.put("Fx".concat(Integer.toString(current_octave)), new Note(i, "Fx", tempEnharmonics3));
                    break;
                case 8: // G#
                    tempEnharmonics = generateEnharmonics(current_octave, "Ab", "Ab", "Note does not have enharmonic down", "Ab");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "Ab", "Note does not have enharmonic up", "G#", "G#"); // Ab
                    notes.put("Ab".concat(Integer.toString(current_octave)), new Note(i, "Ab", tempEnharmonics2));
                    break;
                case 9: // A
                    tempEnharmonics = generateEnharmonics(current_octave, "A", "Bbb", "Gx", "Note does not have simple enharmonic");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "A", "Note does not have enharmonic up", "A", "A"); //Bbb
                    notes.put("Bbb".concat(Integer.toString(current_octave)), new Note(i, "Bbb", tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "A", "A", "Note does not have enharmonic down", "A"); // Gx
                    notes.put("Gx".concat(Integer.toString(current_octave)), new Note(i, "Gx", tempEnharmonics3));
                    break;
                case 10: // A#
                    tempEnharmonics = generateEnharmonics(current_octave, "Bb", "Bb", "Note does not have enharmonic down", "Bb");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "Bb", "Note does not have enharmonic up", "A#", "A#"); // Bb
                    notes.put("Bb".concat(Integer.toString(current_octave)), new Note(i, "Bb", tempEnharmonics2));
                    break;
                case 11: // B
                    tempEnharmonics = generateEnharmonics(current_octave, "B", "Cb", "Ax", "Cb");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "B", "Note does not have enharmonic up", "B", "B"); // Cb
                    notes.put("Cb".concat(Integer.toString(current_octave)), new Note(i, "Cb", tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "B", "B", "Note does not have enharmonic down", "B"); // Ax
                    notes.put("Ax".concat(Integer.toString(current_octave)), new Note(i, "Ax", tempEnharmonics3));
                    break;
                default:
                    break;
            }
            if ((i + 1) % 12 == 0) {
                current_octave += 1;
            }
        }
    }

    private static HashMap<String, String> generateEnharmonics(Integer octave, String desc, String above, String below, String simple) {
        HashMap<String, String> notesEnharmonics = new HashMap<String, String>();
        notesEnharmonics.put("descending", desc.concat(Integer.toString(octave)));
        notesEnharmonics.put("above", above.concat(Integer.toString(octave)));
        notesEnharmonics.put("below", below.concat(Integer.toString(octave)));
        if (Checker.isValidNormalNote(simple))
        {
            notesEnharmonics.put("simple", simple.concat(Integer.toString(octave)));
        }
        else
        {
            notesEnharmonics.put("simple", simple);
        }
        return notesEnharmonics;
    }


    /**
     * Returns the note object from the HashMap of notes.
     *
     * @param s The note or MIDI value to lookup.
     * @return Note object that matches the String supplied.
     */
    static public Note lookup(String s) {
        s = OctaveUtil.capitalise(s);
        Note note = notes.get(s);
        if (note == null) {
            System.err.println(s + " is not a note.");
        }
        return note;
    }

    /**
     * Returns the descending enharmonic name.
     * So the b value instead of the # value.
     * @return descending enharmonic name.
     */
    public String getDescendingEnharmonic() {
        return this.enharmonics.get("descending");
    }

    /**
     * Finds the note x semitones higher.
     * @param semitones The number of semitones to increase by
     * @return the Note object one semitone higher than the current note.
     */
    public Note semitoneUp(int semitones) {
        return Note.lookup(Integer.toString(this.getMidi() + semitones));
    }

    /**
     * Finds the note x semitones lower.
     * @param semitones The number of semitones to decrease by
     * @return the Note object one semitone lower than the current note.
     */
    public Note semitoneDown(int semitones) {
        return Note.lookup(Integer.toString(this.getMidi() - semitones));
    }


    /**
     * Finds the preferred equivalent sharp note.
     * @return the sharp name for the original note.
     */
    public String sharpName() {
        return this.enharmonics.get("above");
    }


    public String flatName() {
        return this.enharmonics.get("below");
    }

    public String simpleEnharmonic() {
        return this.enharmonics.get("simple");
    }

    /**
     * Makes an array list of the major scale starting on the note.
     *
     * @return array list of notes in the scale. If any notes are null, the scale returned will be
     * null.
     */
    public ArrayList<Note> getMajorScale() {
        ArrayList<Note> scaleNotes = new ArrayList<Note>();
        scaleNotes.add(this);
        scaleNotes.add(this.semitoneUp(2));
        scaleNotes.add(this.semitoneUp(4));
        scaleNotes.add(this.semitoneUp(5));
        scaleNotes.add(this.semitoneUp(7));
        scaleNotes.add(this.semitoneUp(9));
        scaleNotes.add(this.semitoneUp(11));
        scaleNotes.add(this.semitoneUp(12));
        for (Note note : scaleNotes) {
            if (note == null) {
                return null;
            }
        }
        return scaleNotes;
    }

    /**
     * @return the note name.
     */
    public String getNote() {
        return this.note;
    }

    /**
     * @return the MIDI value.
     */
    public Integer getMidi() {
        return this.midi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note1 = (Note) o;
        return midi == note1.midi &&
                Objects.equals(note, note1.note);
    }

}
