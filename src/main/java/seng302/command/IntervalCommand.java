package seng302.command;

/**
 * Created by Sarah on 1/04/2016.
 */


// *********************************
//*******************************
//CURRENTLY NOT HOOKED UP TO THE DSL CUP OR FLEX
//**********************************
//*****************************

import java.util.ArrayList;

import seng302.Environment;
import seng302.MusicPlayer;
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


    /**
     * Constructs a command of the type lookup number of semitones
     * @param intervalName A list of the words in the interval name
     */
    public IntervalCommand(ArrayList<String> intervalName) {
        this.intervalName = createIntervalName(intervalName);
        this.outputType = "semitones";
    }

    /**
     * Constructs a command of the type fetch note given tonic and interval
     * @param intervalName A list of the words in the interval name
     * @param tonic the starting note
     * @param outputType whether this interval will be played or displayed
     */
    public IntervalCommand(ArrayList<String> intervalName, String tonic, String outputType) {
        this.intervalName = createIntervalName(intervalName);
        this.tonic = tonic;
        this.outputType = outputType;
    }


    /**
     * Given a list of words, turns them into a string interval name.
     * @param intervalWords List of words
     * @return string representation of the user's inputted interval name.
     */
    private String createIntervalName(ArrayList<String> intervalWords) {
        String interval = "";
        for (String word:intervalWords) {
            interval += (word + " ");
        }
        return interval.trim();
    }

    /**
     * This function checks that the given tonic is valid, and checks whether
     * or not it has a specified octave.
     * @throws Exception if the note is invalid
     */
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

    /**
     * This function shows output. Called when the user is requesting the corresponding note.
     * @param env the display environment
     * @throws Exception if the tonic + interval is outside the accepted range
     */
    private void setNoteOutput(Environment env) throws Exception {
        if (!octaveSpecified) {
            correspondingNote = OctaveUtil.removeOctaveSpecifier(correspondingNote);
        }
        env.getTranscriptManager().setResult(correspondingNote);
    }

    /**
     * Gets the corresponding note when given a starting note and an interval.
     * @param env
     */
    private void getCorrespondingNote(Environment env) {
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

    /**
     * Gets the number of semitones represented by an interval.
     * @param env
     */
    private void getSemitones(Environment env) {
        //This section of code gets the number of semitones in a given interval
        try {
            String semitones = Integer.toString(Interval.lookupByName(intervalName).getSemitones());
            env.getTranscriptManager().setResult(semitones);
        } catch (Exception e) {
            env.error("Unknown interval: " + intervalName);
        }
    }

    /**
     * Plays the two notes of an interval given the interval and starting note
     * @param env
     */
    private void playInterval(Environment env) {
        try {
            setNoteInformation();
            try {
                int numSemitones = Interval.lookupByName(intervalName).getSemitones();
                try {
                    if (note.semitoneUp(numSemitones) == null) {
                        throw new Exception();
                    }
                    ArrayList<Note> notes = new ArrayList<Note>();
                    notes.add(note);
                    notes.add(note.semitoneUp(numSemitones));
                    // Waits for three crotchets
                    env.getPlayer().playNotes(notes, (48));
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

    /**
     * The execute function decides which function to run.
     * @param env
     */
    public void execute(Environment env) {
        if (outputType.equals("semitones")) {
            getSemitones(env);
        } else if (outputType.equals("note")) {
            getCorrespondingNote(env);
        } else if (outputType.equals("play")) {
            playInterval(env);
        } else {
            env.error("Unknown command");
        }

    }
}

