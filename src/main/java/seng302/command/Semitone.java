package seng302.command;

import java.util.ArrayList;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.musicNotation.OctaveUtil;

/**
 * Command used to output a semitone either up or down from a provided note string.
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
                if (up) env.getTranscriptManager().setResult(note.semitoneUp(1).getNote());
                else {
                    env.getTranscriptManager().setResult(note.semitoneDown(1).getDescendingEnharmonic());
                }
            } else {
                this.note = Note.lookup(OctaveUtil.addDefaultOctave(searchval));
                if (up)
                    env.getTranscriptManager().setResult(OctaveUtil.removeOctaveSpecifier(note.semitoneUp(1).getNote()));
                else
                    env.getTranscriptManager().setResult(OctaveUtil.removeOctaveSpecifier(note.semitoneDown(1).getDescendingEnharmonic()));
            }
        } catch (Exception e) {
            env.error("Note is not contained in the MIDI library.");
        }
    }

    public String getHelp() {
        if (up) {
            return "When followed by a valid note or midi number, it will return the note" +
                    " that is a semitone higher.";
        } else {
            return "When followed by a valid note or midi number, it will return the note" +
                    " that is a semitone lower.";

        }
    }

    public ArrayList<String> getParams() {
        ArrayList<String> params = new ArrayList<>();
        params.add("note|midi");
        return params;
    }

    @Override
    public String getCommandText() {
        if (up) {
            return "semitone up";
        } else {
            return "semitone down";
        }
    }

    @Override
    public String getExample() {
        if (up) {
            return "semitone up 100";
        } else {
            return "semitone down A";
        }
    }
}