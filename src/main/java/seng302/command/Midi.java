package seng302.command;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.OctaveUtil;
import seng302.utility.Checker;

/**
 * Created by emily on 2/03/16.
 */
public class Midi implements Command {
    private String s;
    public Midi(String s) {
        this.s = OctaveUtil.addDefaultOctave(s);

    }
    public void execute(Environment env) {
        if (Checker.isValidNormalNote(s.toUpperCase())){
            env.getTranscriptManager().setResult(Integer.toString(Note.lookup(s).getMidi()));
        } else {
            env.error("\'" + s + "\'" + " is not a valid note.");
        }
    }


}
