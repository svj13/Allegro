package seng302.command;

/**
 * Created by Sarah on 1/04/2016.
 */


// *********************************
//*******************************
//CURRENTLY NOT HOOKED UP TO THE DSL CUP OR FLEX
//**********************************
//*****************************

import seng302.Environment;
import seng302.data.Interval;
import seng302.data.Note;
import seng302.utility.OctaveUtil;


public class IntervalCommand implements Command {
    String intervalName;
    String tonic;
    String outputType;
    String correspondingNote;
    private boolean octaveSpecified;
    private Note note;

    public IntervalCommand(String intervalName) {
        this.intervalName = intervalName;
        this.outputType = "semitones";
    }

    public IntervalCommand(String intervalName, String tonic) {
        this.intervalName = intervalName;
        this.tonic = tonic;
        this.outputType = "note";
    }

    private void setNoteInformation() throws Exception {
        // First checks that the tonic is a valid note
        note = Note.lookup(OctaveUtil.addDefaultOctave(tonic));
        if (note == null) {
            throw new Exception();
        }

        // Provided the note was valid, determines whether or not the tonic was given with an octave
        if (OctaveUtil.octaveSpecifierFlag(tonic)) {
            octaveSpecified = true;
        } else {
            octaveSpecified = false;
        }
    }

    private void setNoteOutput(Environment env) throws Exception {
        if (!octaveSpecified) {
            correspondingNote = OctaveUtil.removeOctaveSpecifier(correspondingNote);
        }
        env.getTranscriptManager().setResult(correspondingNote);
    }

    public void execute(Environment env) {
        if (outputType.equals("semitones")) {
            //This section of code gets the number of semitones in a given interval
            try {
                env.getTranscriptManager().setResult((Integer.toString(Interval.lookupByName(intervalName).getSemitones())));
            } catch (Exception e) {
                env.error("Unknown interval: " + intervalName);
            }
        } else if (outputType.equals("note")) {
            //This section of code gets the corresponding note given a tonic and interval
            try {
                setNoteInformation();
                try {
                    int numSemitones = Interval.lookupByName(intervalName).getSemitones();
                    try {
                        correspondingNote = note.semitoneUp(numSemitones).getNote();
                        setNoteOutput(env);
                    } catch (Exception e) {
                        env.error("Invalid combination of tonic and interval.");
                    }
                } catch (Exception e) {
                    env.error("Unknown interval: " + intervalName);
                }
            } catch (Exception e) {
                env.error("\'" + tonic + "\'" + " is not a valid note.");
            }
        }

    }
}

