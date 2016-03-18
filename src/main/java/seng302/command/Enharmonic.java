package seng302.command;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.OctaveUtil;

/**
 * Created by Elliot on 18/03/2016.
 */
public class Enharmonic implements Command {
    private Note note;
    Boolean sharp;
    String noteval;


    public Enharmonic(String n, Boolean s) {
        this.noteval = n;
        this.sharp = s;
    }


    public void execute(Environment env) {
        try {
            if (OctaveUtil.octaveSpecifierFlag(this.noteval)) {
                this.note = Note.lookup(noteval);
                if (sharp) env.getTranscriptManager().setResult(note.sharpName());
                else {
                    env.getTranscriptManager().setResult(note.flatName());
                }
            } else {
                this.note = Note.lookup(OctaveUtil.addDefaultOctave(noteval));
                if (sharp)
                    env.getTranscriptManager().setResult(OctaveUtil.removeOctaveSpecifier(note.sharpName()));
                else
                    env.getTranscriptManager().setResult(OctaveUtil.removeOctaveSpecifier(note.flatName()));
            }
        } catch (Exception e) {
            env.error("Note is not contained in the MIDI library.");
        }
    }

}
