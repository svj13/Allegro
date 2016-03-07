package seng302.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by team-5 on 2/03/16.
 */
public class Note {
    int midi;
    String note;

    public static HashMap<String, Note> notes;
    private static List<String> noteNames = new ArrayList<String>(Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"));

    static {
        int current_octave = -1;
        notes = new HashMap<String, Note>();
        for (int i =0; i<128; i++){
            Note temp = new Note(i,noteNames.get(i%12).concat(Integer.toString(current_octave)));
            notes.put((noteNames.get(i%12).concat(Integer.toString(current_octave))),temp);
            notes.put(Integer.toString(i),temp);
            if((i+1)%12 == 0){
                current_octave +=1;
            }
        }
    }

    static public Note lookup(String s){
        s = s.toUpperCase();
        Note note = notes.get(s);
        if (note == null) {
            System.err.println(s + " is not a note.");
        }
        return note;
    }


    /**
     * Returns the note name of the note a semitone higher than the input
     * No error handling yet implemented
     */
    public Note semitoneUp() {
        return Note.lookup(Integer.toString(Integer.valueOf(this.getMidi()) + 1));
    }

    /**
     * Returns the note name of the note a semitone lower than the input
     * No error handling yet implemented
     */
    public Note semitoneDown()
    {
        return Note.lookup(Integer.toString(Integer.valueOf(this.getMidi()) - 1));
    }

    protected Note(int midi, String note){
        this.midi = midi;
        this.note = note;

    }

    public String getNote()
    {
        return this.note;
    }

    public Integer getMidi()
    {
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
