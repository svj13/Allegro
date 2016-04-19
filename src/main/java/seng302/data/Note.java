package seng302.data;

import java.util.*;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import seng302.utility.Checker;
import seng302.utility.OctaveUtil;

/**
 * This Note class contains a static HashMap of all notes as well as the lookup method and
 * functionality for individual note objects.
 */
public class Note {
    private int midi;
    private String note;
    private HashMap<String, String> enharmonics;
    public static final int noteCount = 128;

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
        for (int i = 0; i < noteCount; i++) {
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
                    tempEnharmonics2 = generateEnharmonics(current_octave, "C", "C", "", "C"); //B#
                    notes.put("B#".concat(Integer.toString(current_octave)), new Note(i, "B#".concat(Integer.toString(current_octave)), tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "C", "", "C", "C"); //Dbb
                    notes.put("Dbb".concat(Integer.toString(current_octave)), new Note(i, "Dbb".concat(Integer.toString(current_octave)), tempEnharmonics3));
                    break;
                case 1: // C#
                    tempEnharmonics = generateEnharmonics(current_octave, "Db", "Db", "Bx", "Db");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "Db", "", "C#", "C#"); // Db
                    notes.put("Db".concat(Integer.toString(current_octave)), new Note(i, "Db".concat(Integer.toString(current_octave)), tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "Db", "C#", "", "C#"); // Bx
                    notes.put("Bx".concat(Integer.toString(current_octave)), new Note(i, "Bx".concat(Integer.toString(current_octave)), tempEnharmonics3));
                    break;
                case 2: // D
                    tempEnharmonics = generateEnharmonics(current_octave, "D", "Ebb", "Cx", "");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "D", "", "D", "D"); // Ebb
                    notes.put("Ebb".concat(Integer.toString(current_octave)), new Note(i, "Ebb".concat(Integer.toString(current_octave)), tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "D", "D", "", "D"); // Cx
                    notes.put("Cx".concat(Integer.toString(current_octave)), new Note(i, "Cx".concat(Integer.toString(current_octave)), tempEnharmonics3));
                    break;
                case 3: // D#
                    tempEnharmonics = generateEnharmonics(current_octave, "Eb", "Eb", "", "Eb");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "Eb", "Fbb", "D#", "D#"); // Eb
                    notes.put("Eb".concat(Integer.toString(current_octave)), new Note(i, "Eb".concat(Integer.toString(current_octave)), tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "Eb", "", "Eb", "D#"); //Fbb
                    notes.put("Fbb".concat(Integer.toString(current_octave)), new Note(i, "Fbb".concat(Integer.toString(current_octave)), tempEnharmonics3));
                    break;
                case 4: // E
                    tempEnharmonics = generateEnharmonics(current_octave, "E", "Fb", "Dx", "Fb");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "E", "E", "", "E"); // Fb
                    notes.put("Fb".concat(Integer.toString(current_octave)), new Note(i, "Fb".concat(Integer.toString(current_octave)), tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "E", "", "E", "E"); // Dx
                    notes.put("Dx".concat(Integer.toString(current_octave)), new Note(i, "Dx".concat(Integer.toString(current_octave)), tempEnharmonics3));
                    break;
                case 5: // F
                    tempEnharmonics = generateEnharmonics(current_octave, "F", "Gbb", "E#", "E#");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "F", "F", "", "F"); // E#
                    notes.put("E#".concat(Integer.toString(current_octave)), new Note(i, "E#".concat(Integer.toString(current_octave)), tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "F", "", "F", "F"); // Gbb
                    notes.put("Gbb".concat(Integer.toString(current_octave)), new Note(i, "Gbb".concat(Integer.toString(current_octave)), tempEnharmonics3));
                    break;
                case 6: // F#
                    tempEnharmonics = generateEnharmonics(current_octave, "Gb", "Gb", "Ex", "Gb");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "Gb", "", "F#", "F#"); // Gb
                    notes.put("Gb".concat(Integer.toString(current_octave)), new Note(i, "Gb".concat(Integer.toString(current_octave)), tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "Gb", "F#", "", "F#"); // Ex
                    notes.put("Ex".concat(Integer.toString(current_octave)), new Note(i, "Ex".concat(Integer.toString(current_octave)), tempEnharmonics3));
                    break;
                case 7: // G
                    tempEnharmonics = generateEnharmonics(current_octave, "G", "Abb", "Fx", "");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "G", "", "G", "G"); //Abb
                    notes.put("Abb".concat(Integer.toString(current_octave)), new Note(i, "Abb".concat(Integer.toString(current_octave)), tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "G", "G", "", "C"); // Fx
                    notes.put("Fx".concat(Integer.toString(current_octave)), new Note(i, "Fx".concat(Integer.toString(current_octave)), tempEnharmonics3));
                    break;
                case 8: // G#
                    tempEnharmonics = generateEnharmonics(current_octave, "Ab", "Ab", "", "Ab");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "Ab", "", "G#", "G#"); // Ab
                    notes.put("Ab".concat(Integer.toString(current_octave)), new Note(i, "Ab".concat(Integer.toString(current_octave)), tempEnharmonics2));
                    break;
                case 9: // A
                    tempEnharmonics = generateEnharmonics(current_octave, "A", "Bbb", "Gx", "");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "A", "", "A", "A"); //Bbb
                    notes.put("Bbb".concat(Integer.toString(current_octave)), new Note(i, "Bbb".concat(Integer.toString(current_octave)), tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "A", "A", "", "A"); // Gx
                    notes.put("Gx".concat(Integer.toString(current_octave)), new Note(i, "Gx".concat(Integer.toString(current_octave)), tempEnharmonics3));
                    break;
                case 10: // A#
                    tempEnharmonics = generateEnharmonics(current_octave, "Bb", "Bb", "", "Bb");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "Bb", "Cbb", "A#", "A#"); // Bb
                    notes.put("Bb".concat(Integer.toString(current_octave)), new Note(i, "Bb".concat(Integer.toString(current_octave)), tempEnharmonics2));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "Bb", "", "Bb", "A#"); // Bb
                    notes.put("Cbb".concat(Integer.toString(current_octave)), new Note(i, "Cbb".concat(Integer.toString(current_octave)), tempEnharmonics2));//cbb
                    break;
                case 11: // B
                    tempEnharmonics = generateEnharmonics(current_octave, "B", "Cb", "Ax", "Cb");
                    notes.put(noteName, new Note(i, noteName, tempEnharmonics));
                    notes.put(Integer.toString(i), new Note(i, noteName, tempEnharmonics));
                    tempEnharmonics2 = generateEnharmonics(current_octave, "B", "", "B", "B"); // Cb
                    notes.put("Cb".concat(Integer.toString(current_octave)), new Note(i, "Cb".concat(Integer.toString(current_octave)), tempEnharmonics2));
                    tempEnharmonics3 = generateEnharmonics(current_octave, "B", "B", "", "B"); // Ax
                    notes.put("Ax".concat(Integer.toString(current_octave)), new Note(i, "Ax".concat(Integer.toString(current_octave)), tempEnharmonics3));
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
        if (Checker.isValidNormalNote(simple)) {
            notesEnharmonics.put("simple", simple.concat(Integer.toString(octave)));
        } else {
            notesEnharmonics.put("simple", simple);
        }
        return notesEnharmonics;
    }


    /**
     * Returns the note object from the HashMap of notes.
     *Created the tempo class

Created the tempo class and made it so it defaults to 120BMP
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
     * Returns the descending enharmonic name. So the b value instead of the # value.
     *
     * @return descending enharmonic name.
     */
    public String getDescendingEnharmonic() {
        return this.enharmonics.get("descending");
    }

    /**
     * Finds the note x semitones higher.
     *
     * @param semitones The number of semitones to increase by
     * @return the Note object one semitone higher than the current note.
     */
    public Note semitoneUp(int semitones) {
        return Note.lookup(Integer.toString(this.getMidi() + semitones));
    }

    /**
     * Finds the note x semitones lower.
     *
     * @param semitones The number of semitones to decrease by
     * @return the Note object one semitone lower than the current note.
     */
    public Note semitoneDown(int semitones) {
        return Note.lookup(Integer.toString(this.getMidi() - semitones));
    }


    /**
     * Finds the preferred equivalent sharp note.
     *
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
    public ArrayList<Note> getOctaveScale(String type, int octaves, boolean up) {
        ArrayList<Note> scaleNotes = new ArrayList<Note>();
        if (up) {
            if (type.toLowerCase().equals("major")) {
                Note currentNote = this;
                scaleNotes.add(currentNote);
                for (int i = 0; i < octaves; i++) {
                    scaleNotes.add(currentNote.semitoneUp(2));
                    scaleNotes.add(currentNote.semitoneUp(4));
                    scaleNotes.add(currentNote.semitoneUp(5));
                    scaleNotes.add(currentNote.semitoneUp(7));
                    scaleNotes.add(currentNote.semitoneUp(9));
                    scaleNotes.add(currentNote.semitoneUp(11));
                    scaleNotes.add(currentNote.semitoneUp(12));
                    currentNote = currentNote.semitoneUp(12);
                }
                if (scaleNotes.contains(null)) {
                    return null;
                }
            } else {
                throw new IllegalArgumentException("Invalid scale type: '" + type + "'.");
            }
        } else {
            if (type.toLowerCase().equals("major")) {
                Note currentNote = this;
                scaleNotes.add(currentNote);
                for (int i = 0; i < octaves; i++) {
                    scaleNotes.add(currentNote.semitoneDown(1));
                    scaleNotes.add(currentNote.semitoneDown(3));
                    scaleNotes.add(currentNote.semitoneDown(5));
                    scaleNotes.add(currentNote.semitoneDown(7));
                    scaleNotes.add(currentNote.semitoneDown(8));
                    scaleNotes.add(currentNote.semitoneDown(10));
                    scaleNotes.add(currentNote.semitoneDown(12));
                    currentNote = currentNote.semitoneDown(12);
                }
                if (scaleNotes.contains(null)) {
                    return null;
                }
            } else {
                throw new IllegalArgumentException("Invalid scale type: '" + type + "'.");
            }
        }
        return scaleNotes;
    }

    /**
     * Convenience method for when you only want one octave.
     *
     * @param type Type of scale. major, minor etc.
     * @param up   Whether the scale goes up or down.
     * @return The Arraylist of notes that make up the scale. Or null if invalid scale.
     */
    public ArrayList<Note> getScale(String type, boolean up) {
        return this.getOctaveScale(type, 1, up);
    }

    public ArrayList<String> getAllEnharmonics() {
        Set<String> allEnharmonicsSet = new HashSet<String>();
        // Check that the enharmonics are actual notes and not blank space


        allEnharmonicsSet.add(this.enharmonics.get("below"));
        allEnharmonicsSet.add(this.enharmonics.get("above"));
        allEnharmonicsSet.add(this.enharmonics.get("simple"));
        ArrayList<String> allEnharmonics = new ArrayList<String>(allEnharmonicsSet);
        Collections.sort(allEnharmonics);
        return allEnharmonics;
    }


    public String getEnharmonicWithLetter(char letter) {
        for (String value : enharmonics.values()) {
            if (value.charAt(0) == letter) {
                return value;
            }
        }
        if (getNote().charAt(0) == letter) {
            return getNote();
        }
        return null;
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
