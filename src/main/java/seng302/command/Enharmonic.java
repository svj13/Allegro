package seng302.command;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.OctaveUtil;

/**
 * Created by Elliot on 18/03/2016.
 */
public class Enharmonic implements Command {
    private Note note;
    int comm;
    String noteval;


    public Enharmonic(String n, int c) {
        this.noteval = n;
        this.comm = c;
    }


    public void execute(Environment env) {
        try {
            if (OctaveUtil.octaveSpecifierFlag(this.noteval)) {
                this.note = Note.lookup(noteval);
                if (comm == 1) env.getTranscriptManager().setResult(note.flatName());
                else if (comm == 2) env.getTranscriptManager().setResult(note.simpleEnharmonic());
                else {
                    env.getTranscriptManager().setResult(note.sharpName());
                }
            } else {
                this.note = Note.lookup(OctaveUtil.addDefaultOctave(noteval));
                if (comm == 1)
                    env.getTranscriptManager().setResult(OctaveUtil.removeOctaveSpecifier(note.flatName()));
                else if (comm == 2)
                    env.getTranscriptManager().setResult(OctaveUtil.removeOctaveSpecifier(note.simpleEnharmonic()));
                else
                    env.getTranscriptManager().setResult(OctaveUtil.removeOctaveSpecifier(note.sharpName()));
            }
        } catch (Exception e) {
            env.error("Note is not contained in the MIDI library.");
        }
    }

}
