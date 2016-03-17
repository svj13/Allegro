package seng302.command;

import java.util.ArrayList;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.OctaveUtil;

/**
 * Created by emily on 18/03/16.
 */
public class Scale implements Command {
    String search;
    String type;
    private Note note;


    public Scale(String a, String b) {
        this.search = a;
        this.type = b;
    }

    public void execute(Environment env){
        try {
            if (OctaveUtil.octaveSpecifierFlag(this.search)) {
                this.note = Note.lookup(search);
            } else {
                this.note = Note.lookup(OctaveUtil.addDefaultOctave(search));
            }
            env.getTranscriptManager().setResult(scaleToString(getMajorScale(note)));
            //env.getTranscriptManager().setResult(type);
        } catch (Exception e) {
            env.error("Note is not contained in the MIDI library.");
        }
    }

    private ArrayList<Note> getMajorScale(Note note) {
        ArrayList<Note> scaleNotes = new ArrayList<Note>();
        scaleNotes.add(note);
        scaleNotes.add(note.semitoneUp(2));
        scaleNotes.add(note.semitoneUp(4));
        scaleNotes.add(note.semitoneUp(5));
        scaleNotes.add(note.semitoneUp(7));
        scaleNotes.add(note.semitoneUp(9));
        scaleNotes.add(note.semitoneUp(11));
        scaleNotes.add(note.semitoneUp(12));

        return scaleNotes;
    }

    private String scaleToString(ArrayList<Note> scaleNotes){
        String notesAsText = "";
        for (Note note:scaleNotes) {
            notesAsText += note.getNote() + " ";
        }
        return notesAsText;
    }
}
