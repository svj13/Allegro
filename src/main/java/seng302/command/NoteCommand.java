package seng302.command;

import seng302.Environment;
import seng302.data.Note;

/**
 * Created by isabelle on 29/02/16.
 */
public class NoteCommand implements Command {
    private int note;
    public NoteCommand(int s) {
        note = s;
    }
    public void execute(Environment env) {
        if (note < 0 || note > 127){
            env.getTranscriptManager().setResult("[ERROR] The provided number is not a valid MIDI value.");
        } else {
            env.getTranscriptManager().setResult(Note.lookup(Integer.toString(note)).getNote());
        }
    }
}
