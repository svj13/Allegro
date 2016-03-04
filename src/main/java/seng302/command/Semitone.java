package seng302.command;

import seng302.Environment;
import seng302.data.Note;

/**
 * Created by team5 on 02-Mar-16.
 *  Command used to output a semitone either up or down from a provided note string.
 */
public class Semitone implements Command {
    private Note note;
    Boolean up;
    public Semitone(String s, Boolean up) { //true for up, false for down.
        this.note = Note.lookup(s);
        this.up = up;
    }
    public void execute(Environment env) {
        if(up) env.getTranscriptManager().setCommand(note.semitone_up().getNote());
        else env.getTranscriptManager().setCommand(note.semitone_down().getNote());
    }
}