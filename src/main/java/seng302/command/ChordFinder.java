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
            //Check if input notes in order create a chord.
            findChord(notes.get(0).getMidi());
        }


    }
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
                    this.result = "" + ChordUtil.getChordNameMidi(minorChord, false);
                    return true;
                }


            } else if (majorChord != null && majorChord.containsAll(midiNotes)) {


                this.result = "" + ChordUtil.getChordNameMidi(majorChord, false);
                return true;
            }
        }
        else{
            if(minorChord != null &&  minorChord == this.midiNotes){
                this.result = "" + ChordUtil.getChordNameMidi(minorChord, false);
                return true;

            }
            else if(majorChord != null && majorChord == this.midiNotes){
                this.result = "" + ChordUtil.getChordNameMidi(majorChord, false);
                return true;
            }
        }




        return false;

    }

    private ArrayList<Integer> toMidiSet(ArrayList<Note> notes, Boolean ignoreOctave) {

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
