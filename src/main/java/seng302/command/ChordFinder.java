package seng302.command;

import java.util.ArrayList;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.musicNotation.ChordUtil;
import seng302.utility.musicNotation.OctaveUtil;

/**
 * Created by Jonty on 24-May-16.
 */
public class ChordFinder implements Command {

    Boolean all = false; //Return all chords, including inversions.
    String result;
    ArrayList<Integer> midiNotes;
    char enharmonicLetter;


    /**
     * Finds a chord name for specified note values, includes the option of finding a chord from all
     * permutations of provided notes. e.g. both 'F A C' and 'A C F' would return F major.
     *
     * @param notes ArrayList of either 3 or 4 notes.
     * @param all   indicates whether or not inversions are included.
     */
    public ChordFinder(ArrayList<Note> notes, Boolean all) {
        this.result = "";
        this.all = all;

        if (notes.size() != 4 && notes.size() != 3) {
            this.result = "Not chords found. Must provide either 3 or 4 notes.";
            return;
        }
        this.midiNotes = toMidiSet(notes, true);


        //Find all enharmonically equivalent permutations of the chord.

        if (all) {
            for (int i = 0; i < midiNotes.size(); i++) {

                enharmonicLetter = notes.get(i).getNote().charAt(0);
                findChord(midiNotes.get(i));


            }
        } else {
            for (int i = 0; i < midiNotes.size(); i++) {

                enharmonicLetter = notes.get(i).getNote().charAt(0);
                if (findChord(midiNotes.get(i))) return; //Stop after first chord found


            }
        }

        if (this.result == "") this.result = "No chords found for given notes.";

    }

    /**
     * Helper function which checks if global notes array is a major/minor chord of the specified
     * midi note. This function is dependant of global variables midiNotes and all specifier, which,
     * if true, will find all permutations for the given chord.
     *
     * @param midiNote Note (midi value) to find a chord for. e.g. 60(C4) will get the major/minor
     *                 chord for C
     * @return A boolean indicating whether a chord has been found for the given notes.
     */
    private Boolean findChord(int midiNote) {
        ArrayList<Integer> majorChord = ChordUtil.getChordMidi(midiNote, "major");
        ArrayList<Integer> minorChord = ChordUtil.getChordMidi(midiNote, "minor");
        ArrayList<Integer> minorSeventhChord = ChordUtil.getChordMidi(midiNote, "minor 7th");
        ArrayList<Integer> majorSeventhChord = ChordUtil.getChordMidi(midiNote, "major 7th");
        ArrayList<Integer> seventhChord = ChordUtil.getChordMidi(midiNote, "seventh");
        ArrayList<Integer> diminishedChord = ChordUtil.getChordMidi(midiNote, "diminished");
        ArrayList<Integer> halfDiminishedChord = ChordUtil.getChordMidi(midiNote, "half dim");
        ArrayList<Integer> diminishedSeventhChord = ChordUtil.getChordMidi(midiNote, "dim 7th");


        for (int i = 0; i < 4; i++) {
            if (i < 3) {
                majorChord.set(i, 60 + (majorChord.get(i) % 12));
                minorChord.set(i, 60 + (minorChord.get(i) % 12));
                diminishedChord.set(i, 60 + (diminishedChord.get(i) % 12));
            }

            minorSeventhChord.set(i, 60 + (minorSeventhChord.get(i) % 12));
            majorSeventhChord.set(i, 60 + (majorSeventhChord.get(i) % 12));
            seventhChord.set(i, 60 + (seventhChord.get(i) % 12));

            halfDiminishedChord.set(i, 60 + (halfDiminishedChord.get(i) % 12));
            diminishedSeventhChord.set(i, 60 + (diminishedSeventhChord.get(i) % 12));
        }


        if (all) {


            if (midiNotes.size() == 3) {
                if (minorChord != null && minorChord.containsAll(midiNotes)) {
                    if (this.all) {
                        //Add all notes to result string.
                        this.result = "" + ChordUtil.getChordName(minorChord, false, enharmonicLetter);
                        return true;
                    }
                } else if (majorChord != null && majorChord.containsAll(midiNotes)) {

                    this.result = "" + ChordUtil.getChordName(majorChord, false, enharmonicLetter);
                    return true;
                } else if (diminishedChord != null && diminishedChord.containsAll(this.midiNotes)) {

                    this.result = "" + ChordUtil.getChordName(diminishedChord, false, enharmonicLetter);

                    return true;
                }
            } else if (midiNotes.size() == 4) {
                if (majorSeventhChord != null && majorSeventhChord.containsAll(this.midiNotes)) {

                    this.result = "" + ChordUtil.getChordName(majorSeventhChord, false, enharmonicLetter);

                    return true;
                } else if (minorSeventhChord != null && minorSeventhChord.containsAll(this.midiNotes)) {

                    this.result = "" + ChordUtil.getChordName(minorSeventhChord, false, enharmonicLetter);

                    return true;
                } else if (seventhChord != null && seventhChord.containsAll(this.midiNotes)) {

                    this.result = "" + ChordUtil.getChordName(seventhChord, false, enharmonicLetter);

                    return true;
                } else if (halfDiminishedChord != null && halfDiminishedChord.containsAll(this.midiNotes)) {

                    this.result = "" + ChordUtil.getChordName(halfDiminishedChord, false, enharmonicLetter);

                    return true;
                } else if (diminishedSeventhChord != null && diminishedSeventhChord.containsAll(this.midiNotes)) {
                    if (this.result != "") this.result += " : ";
                    this.result += ChordUtil.getChordName(diminishedSeventhChord, false, enharmonicLetter);

                    return true;
                }
            }

        } else {

            if (midiNotes.size() == 3) {

                if (minorChord != null && minorChord.equals(this.midiNotes)) {
                    this.result = "" + ChordUtil.getChordName(minorChord, false, enharmonicLetter);
                    return true;

                } else if (majorChord != null && majorChord.equals(this.midiNotes)) {

                    this.result = "" + ChordUtil.getChordName(majorChord, false, enharmonicLetter);

                    return true;
                } else if (diminishedChord != null && diminishedChord.equals(this.midiNotes)) {

                    this.result = "" + ChordUtil.getChordName(diminishedChord, false, enharmonicLetter);

                    return true;
                }
            } else if (midiNotes.size() == 4) {
                if (majorSeventhChord != null && majorSeventhChord.equals(this.midiNotes)) {

                    this.result = "" + ChordUtil.getChordName(majorSeventhChord, false, enharmonicLetter);

                    return true;
                } else if (minorSeventhChord != null && minorSeventhChord.equals(this.midiNotes)) {

                    this.result = "" + ChordUtil.getChordName(minorSeventhChord, false, enharmonicLetter);

                    return true;
                } else if (seventhChord != null && seventhChord.equals(this.midiNotes)) {

                    this.result = "" + ChordUtil.getChordName(seventhChord, false, enharmonicLetter);

                    return true;
                } else if (halfDiminishedChord != null && halfDiminishedChord.equals(this.midiNotes)) {

                    this.result = "" + ChordUtil.getChordName(halfDiminishedChord, false, enharmonicLetter);

                    return true;
                } else if (diminishedSeventhChord != null && diminishedSeventhChord.equals(this.midiNotes)) {

                    this.result = "" + ChordUtil.getChordName(diminishedSeventhChord, false, enharmonicLetter);

                    return true;
                }
            }

        }

        return false;

    }

    /**
     * Helper function to convert an arraylist of notes to an arraylist of corresponding midi
     * values.
     *
     * @param notes        ArrayList of Note values.
     * @param ignoreOctave if true, treats all notes as middle Octave.
     */
    private static ArrayList<Integer> toMidiSet(ArrayList<Note> notes, Boolean ignoreOctave) {

        ArrayList<Integer> midiNotes = new ArrayList<Integer>();
        for (Note n : notes) {
            n = ignoreOctave ? Note.lookup(OctaveUtil.addDefaultOctave(OctaveUtil.removeOctaveSpecifier(n.getNote()))) : n;
            midiNotes.add(n.getMidi());
        }
        return midiNotes;

    }

    public void execute(Environment env) {

        env.getTranscriptManager().setResult(result);

    }

    public String getHelp() {
        return "Finds a chord which matches the pattern of 3 or 4 provided notes.\n " +
                "Can use 'all' extender to match inversion enharmonic chords.\n" +
                " \texample: 'find chord F A C' returns F MAJOR.\n" +
                "\t and 'find chord all C F A' also returns F MAJOR";
    }

    public ArrayList<String> getParams() {
        ArrayList<String> params = new ArrayList<>();
        params.add("note note note");
        return params;

    }

    @Override
    public String getCommandText() {
        if (all) {
            return "find chord all";
        } else {
            return "find chord";
        }
    }

    @Override
    public String getExample() {
        if (all) {
            return "find chord all F A C";
        } else {
            return "find chord F A C";
        }
    }


}
