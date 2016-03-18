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
            env.getTranscriptManager().setResult(scaleToString(note.getMajorScale()));
            //env.getTranscriptManager().setResult(type);
        } catch (Exception e) {
            env.error("Note is not contained in the MIDI library.");
        }
    }

    private String scaleToString(ArrayList<Note> scaleNotes){
        String notesAsText = "";
        for (Note note:scaleNotes) {
            notesAsText += note.getNote() + " ";
        }
        return notesAsText;
    }
}
