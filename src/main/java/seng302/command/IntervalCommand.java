package seng302.command;

/**
 * Created by Sarah on 1/04/2016.
 */


import java.util.ArrayList;
import java.util.HashMap;

import seng302.Environment;
import seng302.data.Interval;
import seng302.data.Note;
import seng302.utility.musicNotation.OctaveUtil;


public class IntervalCommand implements Command {
    String intervalName;
    String tonic;
    String outputType;
    String correspondingNote;
    String semitones;
    private boolean octaveSpecified;
    private Note note;
    private Interval playingInterval;
    private long length = 0;
    private String possibleIntervalnames = "";

    private static HashMap<String, Integer> intervalFullMap = generateHashmap();


    /**
     * used to populate the Hashmap that stores the Interval and the number of letters each interval
     * should go up.
     */
    private static HashMap generateHashmap() {

        intervalFullMap = new HashMap<String, Integer>();
        intervalFullMap.put("major second", 1);
        intervalFullMap.put("major third", 2);
        intervalFullMap.put("perfect fourth", 3);
        intervalFullMap.put("perfect fifth", 4);
        intervalFullMap.put("major sixth", 5);
        intervalFullMap.put("major seventh", 6);
        intervalFullMap.put("perfect octave", 1); //////////////////////////////////////
        intervalFullMap.put("minor second", 1);
        intervalFullMap.put("minor third", 2);
        intervalFullMap.put("augmented fourth", 3);
        intervalFullMap.put("diminished fifth", 4);
        intervalFullMap.put("minor sixth", 5);
        intervalFullMap.put("diminished seventh", 6);
        intervalFullMap.put("minor seventh", 6);
        intervalFullMap.put("minor ninth", 8);
        intervalFullMap.put("major ninth", 8);
        intervalFullMap.put("minor tenth", 9);
        intervalFullMap.put("major tenth", 9);
        intervalFullMap.put("perfect eleventh", 10);
        intervalFullMap.put("augmented eleventh", 10);
        intervalFullMap.put("perfect twelfth", 11);
        intervalFullMap.put("minor thirteenth", 12);
        intervalFullMap.put("major thirteenth", 12);
        intervalFullMap.put("minor fourteenth", 13);
        intervalFullMap.put("major fourteenth", 13);
        intervalFullMap.put("double octave", 1); ///////////////////////////////////////////////////////

        intervalFullMap.put("major 2nd", 1);
        intervalFullMap.put("major 3rd", 2);
        intervalFullMap.put("perfect 4th", 3);
        intervalFullMap.put("perfect 5th", 4);
        intervalFullMap.put("major 6th", 5);
        intervalFullMap.put("major 7th", 6);
        intervalFullMap.put("minor 2nd", 1);
        intervalFullMap.put("minor 3rd", 2);
        intervalFullMap.put("augmented 4th", 3);
        intervalFullMap.put("diminished 5th", 4);
        intervalFullMap.put("minor 6th", 5);
        intervalFullMap.put("diminished 7th", 6);
        intervalFullMap.put("minor 7th", 6);
        intervalFullMap.put("minor 9th", 8);
        intervalFullMap.put("major 9th", 8);
        intervalFullMap.put("minor 10th", 9);
        intervalFullMap.put("major 10th", 9);
        intervalFullMap.put("perfect 11th", 10);
        intervalFullMap.put("augmented 11th", 10);
        intervalFullMap.put("perfect 12th", 11);
        intervalFullMap.put("minor 13th", 12);
        intervalFullMap.put("major 13th", 12);
        intervalFullMap.put("minor 14th", 13);
        intervalFullMap.put("major 14th", 13);
        return intervalFullMap;
    }


    /**
     * Constructs a command of the type fetch note given tonic and interval
     *
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
        //generateHashmap();
    }


//    public EnharmonicCommand() {

//    }

    public long getLength(Environment env) {
        return length;
    }

    ;


    /**
     * This function checks that the given tonic is valid, and checks whether or not it has a
     * specified octave.
     *
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
     *
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

                    //get first letter
                    char currentLetter = Character.toUpperCase(note.getNote().charAt(0));

                    int numberOfSteps = intervalFullMap.get(intervalName);
                    int index = "ABCDEFG".indexOf(currentLetter);

                    //function that gets the expected letter
                    while (numberOfSteps > 0) {

                        if (index + 1 > 6) {
                            index = 0;
                        } else {
                            index += 1;
                        }
                        numberOfSteps -= 1;

                    }

                    char last_note = "ABCDEFG".charAt(index);

                    if (last_note != Character.toUpperCase(correspondingNote.charAt(0))) {
                        correspondingNote = note.lookup(correspondingNote).getEnharmonicWithLetter(last_note);
                    }

                    setNoteOutput(env);
                } catch (Exception e) {
                    env.error("The resulting note is higher than the highest note supported by this application.");
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
     *
     * @param env the display environment
     */
    private void getSemitones(Environment env) {
        //This section of code gets the number of semitones in a given interval
        try {
            if (semitones == null) {
                if (!intervalName.equals("")) {
                    semitones = Integer.toString(Interval.lookupByName(intervalName).getSemitones());
                } else {
                    throw new NullPointerException();
                }
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
     * Uses the findEnharmonics method to find the enharmonic intervals of a given interval and
     * prints the result of the command to the transcript
     *
     * @param env the display environment
     */
    private void getEquivalentInterval(Environment env) {
        ArrayList<String> equivalentIntervals;
        try {
            try {
                int numSemitones;
                if (intervalName != null) {
                    numSemitones = Interval.lookupByName(intervalName).getSemitones();
                } else {
                    numSemitones = Integer.valueOf(semitones);
                }


                equivalentIntervals = Interval.findEnharmonics(numSemitones, intervalName);
                if (!equivalentIntervals.isEmpty()) {
                    String outputIntervals = "";
                    for (String interval : equivalentIntervals) {
                        outputIntervals += (interval + ", ");
                    }

                    outputIntervals = outputIntervals.substring(0, outputIntervals.length() - 2);
                    env.getTranscriptManager().setResult(outputIntervals);
                } else {
                    env.getTranscriptManager().setResult("Interval has no enharmonics");
                }


            } catch (Exception e) {
                env.error("Unknown interval: " + intervalName);
            }
        } catch (Exception e) {
            env.error("\'" + tonic + "\'" + " is not a valid note.");
        }

    }

    /**
     * Plays the two notes of an interval given the interval and starting note
     *
     * @param env the display environment
     */
    private void playInterval(Environment env) {
        try {
            setNoteInformation();
            try {
                int numSemitones;
                if (intervalName != null) {
                    playingInterval = Interval.lookupByName(intervalName);
                } else {
                    //given the number of semitones
                    ArrayList<Interval> possibleIntervals = Interval.lookupBySemitones(Integer.valueOf(semitones));
                    possibleIntervalnames = "";
                    for (Interval interval : possibleIntervals) {
                        possibleIntervalnames += interval.getName() + "/";
                    }
                    intervalName = possibleIntervalnames.substring(0, possibleIntervalnames.length() - 1);

                    playingInterval = possibleIntervals.get(0);
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
                            + intervalName + " above " + note.getNote());
                } catch (Exception e) {
                    env.error("The resulting note is higher than the highest note supported by this application.");
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
     *
     * @param env the display environment
     */
    public void execute(Environment env) {
        if (outputType.equals("semitones")) {
            getSemitones(env);
        } else if (outputType.equals("note")) {
            getCorrespondingNote(env);
        } else if (outputType.equals("play")) {
            int tempo = env.getPlayer().getTempo();
            long crotchetLength = 60000 / tempo;
            length = 5 * crotchetLength;
            playInterval(env);
        } else if (outputType.equals("equivalent")) {
            getEquivalentInterval(env);
        } else {
            env.error("Unknown command");
        }

    }

    public String getHelp() {
        switch (outputType) {
            case "note":
                return "When followed by an interval name, it returns the number of semitones in that interval. When followed by an interval name and a note, it returns the note that is the specified interval above the given note. e.g. interval perfect fifth C4 will return G4.";
            case "play":
                return "When followed by an interval name and a note, it will play the given note and then the note that is the specified interval above the given note.";
            case "equivalent":
                return "When followed by a valid interval name, it returns any enharmonically equivalent intervals.";
        }
        return null;
    }
}

