package seng302.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This Note class contains a static HashMap of all notes as well as the lookup method
 * and functionality for individual note objects.
 */
public class Note {
    int midi;
    String note;
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
            HashMap<String, String> tempEnharmonics = new HashMap<String, String>();
            int index = i % 12;
            switch (index) {
                case 1:
                    tempEnharmonics.put("descending", "Db".concat(Integer.toString(current_octave)));
                    break;
                case 3:
                    tempEnharmonics.put("descending", "Eb".concat(Integer.toString(current_octave)));
                    break;
                case 6:
                    tempEnharmonics.put("descending", "Gb".concat(Integer.toString(current_octave)));
                    break;
                case 8:
                    tempEnharmonics.put("descending", "Ab".concat(Integer.toString(current_octave)));
                    break;
                case 10:
                    tempEnharmonics.put("descending", "Bb".concat(Integer.toString(current_octave)));
                    break;
                default:
                    tempEnharmonics.put("descending", noteNames.get(i % 12).concat(Integer.toString(current_octave)));
                    break;
            }
            Note temp = new Note(i, noteNames.get(i % 12).concat(Integer.toString(current_octave)), tempEnharmonics);
            notes.put((noteNames.get(i % 12).concat(Integer.toString(current_octave))), temp);
            notes.put(Integer.toString(i), temp);
            if ((i + 1) % 12 == 0) {
                current_octave += 1;
            }
        }
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
    public String getDescendingEharmonic() {
        System.out.println(this.enharmonics.get("descending"));
        return this.enharmonics.get("descending");
    }

    /**
     * Finds the note one semitone higher.
     * @return the Note object one semitone higher than the current note.
     */
    public Note semitoneUp() {
        return Note.lookup(Integer.toString(this.getMidi() + 1));
    }

    /**
     * Finds the note one semitone lower.
     * @return the Note object one semitone lower than the current note.
     */
    public Note semitoneDown() {
        return Note.lookup(Integer.toString(this.getMidi() - 1));
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
