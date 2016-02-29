package seng302.command;

import seng302.Environment;

/**
 * Created by isabelle on 29/02/16.
 */
public class Note implements Command {
    private Integer note;
    public Note(Integer s) {
        note = s;
    }
    public void execute(Environment env) {
        env.println(note.toString());
    }
}
