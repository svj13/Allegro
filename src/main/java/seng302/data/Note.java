package seng302.data;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                    tempEnharmonics2 = generateEnharmonics(current_octave, "C", "C", "", "C");
                    notes.put("B#".concat(Integer.toString(current_octave)), new Note(i, "B#", tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "C", "", "C", "C");
                    notes.put("Dbb".concat(Integer.toString(current_octave)), new Note(i, "Dbb", tempEnharmonics3));
                    break;
                case 1: // C#
                    tempEnharmonics = generateEnharmonics(current_octave, "Db", "Db", "Bx", "Db");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "Db", "", "C#", "C#"); // Db
                    notes.put("Db".concat(Integer.toString(current_octave)), new Note(i, "Db", tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "Db", "C#", "", "C#"); // Bx
                    notes.put("Bx".concat(Integer.toString(current_octave)), new Note(i, "Bx", tempEnharmonics3));
                    break;
                case 2: // D
                    tempEnharmonics = generateEnharmonics(current_octave, "D", "Ebb", "Cx", "");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "D", "", "D", "D"); // Ebb
                    notes.put("B#".concat(Integer.toString(current_octave)), new Note(i, "Ebb", tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "D", "D", "", "D"); // Cx
                    notes.put("Dbb".concat(Integer.toString(current_octave)), new Note(i, "Cx", tempEnharmonics3));
                    break;
                case 3: // D#
                    tempEnharmonics = generateEnharmonics(current_octave, "Eb", "Eb", "", "Eb");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "Eb", "", "D#", "D#"); // Eb
                    notes.put("B#".concat(Integer.toString(current_octave)), new Note(i, "Eb", tempEnharmonics2));
                    break;
                case 4: // E
                    tempEnharmonics = generateEnharmonics(current_octave, "E", "Fb", "Dx", "Fb");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "E", "E", "", "E"); // Fb
                    notes.put("B#".concat(Integer.toString(current_octave)), new Note(i, "Fb", tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "E", "", "E", "E"); // Dx
                    notes.put("Dbb".concat(Integer.toString(current_octave)), new Note(i, "Dx", tempEnharmonics3));
                    break;
                case 5: // F
                    tempEnharmonics = generateEnharmonics(current_octave, "F", "Gbb", "E#", "E#");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "F", "F", "", "F"); // E#
                    notes.put("B#".concat(Integer.toString(current_octave)), new Note(i, "E#", tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "F", "", "F", "F"); // Gbb
                    notes.put("Dbb".concat(Integer.toString(current_octave)), new Note(i, "Gbb", tempEnharmonics3));
                    break;
                case 6: // F#
                    tempEnharmonics = generateEnharmonics(current_octave, "Gb", "Gb", "Ex", "Gb");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "Gb", "", "F#", "F#"); // Gb
                    notes.put("B#".concat(Integer.toString(current_octave)), new Note(i, "Gb", tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "Gb", "F#", "", "F#"); // Ex
                    notes.put("Dbb".concat(Integer.toString(current_octave)), new Note(i, "Ex", tempEnharmonics3));
                    break;
                case 7: // G
                    tempEnharmonics = generateEnharmonics(current_octave, "G", "Abb", "Fx", "");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "G", "", "G", "G"); //Abb
                    notes.put("B#".concat(Integer.toString(current_octave)), new Note(i, "Abb", tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "G", "G", "", "C"); // Fx
                    notes.put("Dbb".concat(Integer.toString(current_octave)), new Note(i, "Fx", tempEnharmonics3));
                    break;
                case 8: // G#
                    tempEnharmonics = generateEnharmonics(current_octave, "Ab", "Ab", "", "Ab");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "Ab", "", "G#", "G#"); // Ab
                    notes.put("B#".concat(Integer.toString(current_octave)), new Note(i, "Ab", tempEnharmonics2));
                    break;
                case 9: // A
                    tempEnharmonics = generateEnharmonics(current_octave, "A", "Bbb", "Gx", "");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "A", "", "A", "A"); //Bbb
                    notes.put("B#".concat(Integer.toString(current_octave)), new Note(i, "Bbb", tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "A", "A", "", "A"); // Gx
                    notes.put("Dbb".concat(Integer.toString(current_octave)), new Note(i, "Gx", tempEnharmonics3));
                    break;
                case 10: // A#
                    tempEnharmonics = generateEnharmonics(current_octave, "Bb", "Bb", "", "Bb");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "Bb", "", "A#", "A#"); // Bb
                    notes.put("B#".concat(Integer.toString(current_octave)), new Note(i, "Bb", tempEnharmonics2));
                    break;
                case 11: // B
                    tempEnharmonics = generateEnharmonics(current_octave, "B", "Cb", "Ax", "Cb");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "B", "", "B", "B"); // Cb
                    notes.put("B#".concat(Integer.toString(current_octave)), new Note(i, "Cb", tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "B", "B", "", "B"); // Ax
                    notes.put("Dbb".concat(Integer.toString(current_octave)), new Note(i, "Ax", tempEnharmonics3));
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
        notesEnharmonics.put("simple", simple.concat(Integer.toString(octave)));
        return notesEnharmonics;
    }


    /**
     * Returns the note object from the HashMap of notes.
     *
     * @param s The note or MIDI value to lookup.
     * @return Note object that matches the String supplied.
     */
    static public Note lookup(String s) {
        s = s.toUpperCase();
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
        return this.enharmonics.get("below");
    }


    public String flatName() {
        return this.enharmonics.get("above");
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
