package seng302.command;

import seng302.Environment;
import seng302.data.Notes;

/**
 * Created by team5 on 02-Mar-16.
 *  Command used to output a semitone either up or down from a provided note string.
 */
public class Semitone implements Command {
    private String note;
    Notes notes;
    Boolean up;
    public Semitone(String s, Boolean up) { //true for up, false for down.
        this.note = s;
        this.notes = new Notes();
        this.up = up;
    }
    public void execute(Environment env) {
        if(up) env.println(notes.semitone_up(note));
        else env.println(notes.semitone_down(note));
    }
}