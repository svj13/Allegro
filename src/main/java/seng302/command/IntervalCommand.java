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


public class IntervalCommand implements Command {
    String intervalName;
    String tonic;
    String outputType;
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


    public void execute(Environment env) {
        if (outputType.equals("semitones")) {
            //This section of code gets the number of semitones in a given interval
            try {
                env.getTranscriptManager().setResult((Integer.toString(Interval.lookupByName(intervalName).getSemitones())));
            } catch (Exception e) {
                env.error("Unknown interval: " + intervalName);
            }
        } else if (outputType.equals("note")) {
            //First, search for the note
            note = Note.lookup(tonic);

            //Second, get the number of semitones
            int numSemitones = Interval.lookupByName(intervalName).getSemitones();

            note = note.semitoneUp(numSemitones);
            env.getTranscriptManager().setResult(note.getNote());
        }

    }
}

