package seng302.command;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.musicNotation.Checker;

/**
 * NoteCommand is used to convert from a MIDI value to a Note.
 */
public class NoteCommand implements Command {
    private String note;


    public NoteCommand(String s) {
        note = s;
    }

    public float getLength(Environment env) {
        return 0;
    }

    ;

    /**
     * Sets the result to the equivalent Note or calls an error message if the MIDI given is not a
     * valid note.
     */
    public void execute(Environment env) {
        if (Checker.isValidMidiNote(note)) {
            env.getTranscriptManager().setResult(Note.lookup(note).getNote());
        } else {
            env.error("\'" + note + "\'" + " is not a valid MIDI value.");
        }
    }
}
