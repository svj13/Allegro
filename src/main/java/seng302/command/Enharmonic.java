package seng302.command;

import java.util.ArrayList;
import java.util.HashSet;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.OctaveUtil;

/**
 * Created by team 5 on 18/03/2016.
 */
public class Enharmonic implements Command {
    private Note note;
    int comm;
    String noteval;


    public Enharmonic(String n, int c) {
        this.noteval = n;
        this.comm = c;
    }

    public float getLength(Environment env) {
        return 0;
    };


    public void execute(Environment env) {
        try {
            if (OctaveUtil.octaveSpecifierFlag(this.noteval)) {
                this.note = Note.lookup(noteval);
                if (comm == 1) {
                    if (note.flatName().length() == 1) {
                        env.error("Note does not have a lower enharmonic.");
                    } else {
                        env.getTranscriptManager().setResult(note.flatName());
                    }
                } else if (comm == 2) {
                    if (note.simpleEnharmonic().length() == 0) {
                        env.error("Note does not have a simple enharmonic.");
                    } else {
                        env.getTranscriptManager().setResult(note.simpleEnharmonic());
                    }
                } else if (comm == 0) {
                    if (note.sharpName().length() == 1) {
                        env.error("Note does not have a higher enharmonic.");
                    } else {
                        env.getTranscriptManager().setResult(note.sharpName());
                    }
                } else {
                    String allEnharmonics = "";
                    ArrayList<String> arrayAllEnharmonics = note.getAllEnharmonics();
                    for (String enharmonic : arrayAllEnharmonics) {
                        if (allEnharmonics.length() == 0) {
                            allEnharmonics = enharmonic;
                        } else {
                            allEnharmonics = allEnharmonics + " " + enharmonic;
                        }
                    }
                    env.getTranscriptManager().setResult(allEnharmonics);
                }
            } else {
                this.note = Note.lookup(OctaveUtil.addDefaultOctave(noteval));
                if (comm == 1) {
                    if (note.flatName().length() == 1) {
                        env.error("Note does not have a lower enharmonic.");
                    } else {
                        env.getTranscriptManager().setResult(OctaveUtil.removeOctaveSpecifier(note.flatName()));
                    }
                } else if (comm == 2) {
                    if (note.simpleEnharmonic().length() == 0) {
                        env.error("Note does not have a simple enharmonic.");
                    } else {
                        env.getTranscriptManager().setResult(OctaveUtil.removeOctaveSpecifier(note.simpleEnharmonic()));
                    }
                } else if (comm == 0) {
                    if (note.sharpName().length() == 1) {
                        env.error("Note does not have a higher enharmonic.");
                    } else {
                        env.getTranscriptManager().setResult(OctaveUtil.removeOctaveSpecifier(note.sharpName()));
                    }
                } else {
                    String allEnharmonics = "";
                    ArrayList<String> arrayAllEnharmonics = note.getAllEnharmonics();
                    for (String enharmonic : arrayAllEnharmonics) {
                        if (allEnharmonics.length() == 0) {
                            allEnharmonics = OctaveUtil.removeOctaveSpecifier(enharmonic);
                        } else {
                            allEnharmonics = allEnharmonics + " " + OctaveUtil.removeOctaveSpecifier(enharmonic);
                        }
                    }
                    env.getTranscriptManager().setResult(allEnharmonics);
                }
            }
        } catch (Exception e) {
            env.error("Note is not contained in the MIDI library.");
        }
    }

}
