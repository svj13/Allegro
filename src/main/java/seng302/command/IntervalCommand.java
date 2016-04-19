package seng302.command;

/**
 * Created by Sarah on 1/04/2016.
 */


import java.util.ArrayList;
import java.util.HashMap;

import seng302.Environment;
import seng302.data.Interval;
import seng302.data.Note;
import seng302.utility.OctaveUtil;


public class IntervalCommand implements Command {
    String intervalName;
    String tonic;
    String outputType;
    String correspondingNote;
    String semitones;
    private boolean octaveSpecified;
    private Note note;
    private Interval playingInterval;


    /**
     * Constructs a command of the type fetch note given tonic and interval
     * @param outputType whether this interval will be played or displayed
     */
    public IntervalCommand(HashMap<String, String> interval, String outputType) {
        if (interval.get("interval") != null) {
            this.intervalName = interval.get("interval");
        } else if (interval.get("semitones") != null) {
            this.semitones = interval.get("semitones");
        }
        if (interval.get("note") != null) {
            this.tonic = interval.get("note");
        }
        this.outputType = outputType;
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
                int numSemitones;
                if (intervalName != null) {
                    numSemitones = Interval.lookupByName(intervalName).getSemitones();
                } else {
                    numSemitones = Integer.valueOf(semitones);
                }
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
            if (semitones == null) {
                semitones = Integer.toString(Interval.lookupByName(intervalName).getSemitones());
            }
            env.getTranscriptManager().setResult(semitones + " semitones");
        } catch (NullPointerException e) {
            if (semitones != null) {
                env.error("Unknown interval: " + semitones);
            } else {
                env.error("Unknown interval: " + intervalName);
            }
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
                int numSemitones;
                if (intervalName != null) {
                    playingInterval = Interval.lookupByName(intervalName);
                } else {
                    playingInterval = Interval.lookupBySemitones(Integer.valueOf(semitones));
                }
                numSemitones = playingInterval.getSemitones();
                try {
                    if (note.semitoneUp(numSemitones) == null) {
                        throw new Exception();
                    }
                    ArrayList<Note> notes = new ArrayList<Note>();
                    notes.add(note);
                    notes.add(note.semitoneUp(numSemitones));
                    // Waits for three crotchets
                    env.getPlayer().playNotes(notes, (48));
                    env.getTranscriptManager().setResult("Playing interval "
                             + playingInterval.getName() + " above " + note.getNote());
                } catch (Exception e) {
                    env.error("Invalid combination of tonic and interval.");
                }
            } catch (Exception e) {
                if (intervalName != null) {
                    env.error("Unknown interval: " + intervalName);
                } else if (semitones != null) {
                    env.error("Unknown interval: " + semitones);
                } else {
                    env.error("Unknown interval.");
                }
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

