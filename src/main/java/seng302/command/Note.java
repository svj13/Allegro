package seng302.command;

import seng302.Environment;
import seng302.data.Notes;

/**
 * Created by isabelle on 29/02/16.
 */
public class Note implements Command {
    private Integer note;
    Notes notes;
    public Note(Integer s) {
        note = s;
        notes = new Notes();
    }
    public void execute(Environment env) {
        if (note < 0 || note > 127){
            env.println("[ERROR] The provided number is not a valid MIDI value.");
        } else {
            env.println(notes.getNote(note));
        }
    }
}
