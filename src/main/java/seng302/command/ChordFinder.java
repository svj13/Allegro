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

        this.all = all;
        this.midiNotes = toMidiSet(notes, true);

        System.out.println(" input -> " + Arrays.toString(this.midiNotes.toArray()));

        this.result = "No chords found for given notes.";
        if (!all) {


            for (int midi : midiNotes) {

                ArrayList<Integer> majorChord = ChordUtil.getChordMidi(midi, "major");
                ArrayList<Integer> minorChord = ChordUtil.getChordMidi(midi, "minor");
                //Convert all Notes to Octave 4 equivalents.

                System.out.println(" major ->" + Arrays.toString(majorChord.toArray()));

                for (int i = 0; i < majorChord.size(); i++) {
                    majorChord.set(i, 60 + (majorChord.get(i) % 12));
                    minorChord.set(i, 60 + (minorChord.get(i) % 12));
                }


                if (minorChord != null && minorChord.containsAll(midiNotes)) {
                    System.out.println("minor all");
                    //Add all notes to result string.
                    this.result = "" + ChordUtil.getChordNameMidi(minorChord, false);
                    return;
                } else if (majorChord != null && majorChord.containsAll(midiNotes)) {
                    System.out.println("major all");

                    this.result = "" + ChordUtil.getChordNameMidi(majorChord, false);
                    return;
                }


            }
        }


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
        System.out.println(result);
        env.getTranscriptManager().setResult(result);

    }


}
