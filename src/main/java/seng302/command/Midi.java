package seng302.command;

import java.util.ArrayList;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.musicNotation.OctaveUtil;

/**
 * Midi is used to convert from a Note to a MIDI value.
 */
public class Midi implements Command {
    private String s;

    /**
     * Adds the default octave to the note if it does not specify an octave.
     */
    public Midi(String s) {
        this.s = OctaveUtil.validateNoteString(s);
    }

    ;

    /**
     * Sets the result to the equivalent MIDI value or calls an error message if the note given is
     * not valid.
     */
    public void execute(Environment env) {
        env.getTranscriptManager().setResult(Integer.toString(Note.lookup(s).getMidi()));
    }

    public String getHelp() {
        return "When followed by a valid note, it will return its corresponding midi number" +
                " within the range of 0-127. If an octave is not specified with the note, " +
                "the default octave (4) will be used.";
    }

    public ArrayList<String> getParams() {
        ArrayList<String> params = new ArrayList<>();
        params.add("note");
        return params;
    }

    @Override
    public String getCommandText() {
        return "midi";
    }

    @Override
    public String getExample() {
        return "midi C2";
    }


}
