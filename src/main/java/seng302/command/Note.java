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
        env.println(notes.getNote(note) );
    }
}
