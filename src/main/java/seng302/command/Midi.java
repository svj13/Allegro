package seng302.command;

import seng302.Environment;
import seng302.data.Notes;

/**
 * Created by emily on 2/03/16.
 */
public class Midi implements Command {
    private String midi;
    Notes notes;
    public Midi(String s) {
        midi = s;
        notes = new Notes();
    }
    public void execute(Environment env) {
        env.println(Integer.toString(notes.getMidi(midi)));
    }
}
