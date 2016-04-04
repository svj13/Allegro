package seng302.command;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.OctaveUtil;
import seng302.utility.Checker;

/**
 * Midi is used to convert from a Note to a MIDI value.
 */
public class Midi implements Command {
    private Note a;

    /**
     * Adds the default octave to the note if it does not specify an octave.
     */
    public Midi(Note s) {
        a = s;
    }

    /**
     * Sets the result to the equivalent MIDI value or calls an error message if the note given is
     * not valid.
     */
    public void execute(Environment env) {
        env.getTranscriptManager().setResult(Integer.toString(a.getMidi()));
    }


}
