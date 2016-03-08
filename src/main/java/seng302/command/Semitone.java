package seng302.command;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.OctaveUtil;

/**
 * Command used to output a semitone either up or down from a
 * provided note string.
 */
public class Semitone implements Command {
    private Note note;
    Boolean up;
    String searchval;

    public Semitone(String s, Boolean up) { //true for up, false for down.
        this.searchval = s;
        this.up = up;
    }

    /**
     * If the note has an octave specifier, then return the Note a semitone higher or lower.
     * Otherwise, add a default octave specifier, find the correct note and then remove the octave
     * specifier before displaying the result.
     */
    public void execute(Environment env) {
        try {
            if (OctaveUtil.octaveSpecifierFlag(this.searchval)) {
                this.note = Note.lookup(searchval);
                if (up) env.getTranscriptManager().setResult(note.semitoneUp().getNote());
                else {
                    env.getTranscriptManager().setResult(note.semitoneDown().getDescendingEharmonic());
                }
            } else {
                this.note = Note.lookup(OctaveUtil.addDefaultOctave(searchval));
                if (up)
                    env.getTranscriptManager().setResult(OctaveUtil.removeOctaveSpecifier(note.semitoneUp().getNote()));
                else
                    env.getTranscriptManager().setResult(OctaveUtil.removeOctaveSpecifier(note.semitoneDown().getDescendingEharmonic()));
            }
        } catch (Exception e) {
            env.error("Note is not contained in the MIDI library.");
        }
    }
}