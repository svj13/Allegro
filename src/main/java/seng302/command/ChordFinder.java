package seng302.command;

import seng302.Environment;
import seng302.data.Interval;
import seng302.data.Note;
import seng302.utility.musicNotation.ChordUtil;
import seng302.utility.musicNotation.OctaveUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * Created by Jonty on 24-May-16.
 */
public class ChordFinder implements Command {

    Boolean all = false; //Return all chords, including inversions.
    String result;
    ArrayList<Integer> midiNotes;


    /**
     * Finds a chord name for specified note values, includes the option of finding a chord from all permutations of
     * provided notes. e.g. both 'F A C' and 'A C F' would return F major.
     * @param notes ArrayList of either 3 or 4 notes.
     * @param all   indicates whether or not inversions are included.
     */
    public ChordFinder(ArrayList<Note> notes, Boolean all) {
        this.result = "No chords found for given notes.";
        this.all = all;

        if(notes.size() != 4 && notes.size() != 3){
            this.result = "Not chords found. Must provide either 3 or 4 notes.";
            return;
        }
        this.midiNotes = toMidiSet(notes, true);

        if (all) {
            //Check if input notes in all permutations create a chord.
            for (int midi : midiNotes) {
                if(findChord(midi)) return;

            }
        }
        else{
            findChord(notes.get(0).getMidi());
        }


    }

    /**
     * Helper function which checks if global notes array is a major/minor chord of the specified midi note.
     * This function is dependant of global variables midiNotes and all specifier, which, if true, will find all
     * permutatations for the given chord.
     * @param midiNote Note (midi value) to find a chord for. e.g. 60(C4) will get the major/minor chord for C
     * @return ..sds
     */
    private Boolean findChord(int midiNote){
        ArrayList<Integer> majorChord = ChordUtil.getChordMidi(midiNote, "major");
        ArrayList<Integer> minorChord = ChordUtil.getChordMidi(midiNote, "minor");


        for (int i = 0; i < majorChord.size(); i++) {
            majorChord.set(i, 60 + (majorChord.get(i) % 12));
            minorChord.set(i, 60 + (minorChord.get(i) % 12));
        }

        if(all){
            if (minorChord != null && minorChord.containsAll(midiNotes)) {
                if(this.all){
                    //Add all notes to result string.
                    this.result = "" + ChordUtil.getChordName(minorChord, false);
                    return true;
                }


            } else if (majorChord != null && majorChord.containsAll(midiNotes)) {

                this.result = "" + ChordUtil.getChordName(majorChord, false);
                return true;
            }
        }
        else{
            if(minorChord != null &&  minorChord.equals(this.midiNotes)){
                this.result = "" + ChordUtil.getChordName(minorChord, false);
                return true;

            }
            else if(majorChord != null && majorChord.equals(this.midiNotes)){

                this.result = "" + ChordUtil.getChordName(majorChord, false);
                return true;
            }
        }

        return false;

    }

    /**
     * Helper function to convert an arraylist of notes to an arraylist of corresponding midi values.
     * @param notes ArrayList of Note values.
     * @param ignoreOctave if true, treats all notes as middle Octave.
     * @return
     */
    private static ArrayList<Integer> toMidiSet(ArrayList<Note> notes, Boolean ignoreOctave) {

        ArrayList<Integer> midiNotes = new ArrayList<Integer>();
        for (Note n : notes) {
            n = ignoreOctave ? Note.lookup(OctaveUtil.addDefaultOctave(OctaveUtil.removeOctaveSpecifier(n.getNote()))) : n;
            midiNotes.add(n.getMidi());
        }
        return midiNotes;

    }

    public float getLength(Environment env) {
        return 0;
    }

    public void execute(Environment env) {

        env.getTranscriptManager().setResult(result);

    }


}
