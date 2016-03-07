package seng302.command;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.Checker;

/**
 * Created by isabelle on 29/02/16.
 */
public class NoteCommand implements Command {
    private String note;
    public NoteCommand(String s) {
        note = s;
    }
    public void execute(Environment env) {
        if (Checker.isValidMidiNote(note)){
            env.getTranscriptManager().setResult(Note.lookup(note).getNote());
        } else {
            env.error("\'" + note + "\'" + " is not a valid MIDI value.");
        }
    }
}
