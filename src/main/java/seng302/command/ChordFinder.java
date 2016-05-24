package seng302.command;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.musicNotation.ChordUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * Created by Jonty on 24-May-16.
 */
public class ChordFinder implements Command {

    Boolean all = false; //Return all chords, including inversions.
    String result;
    HashSet<Note> notes;


    public ChordFinder(HashSet<Note> notes, Boolean all) {

        this.all = all;
        this.notes = notes;
        this.result = "No chords found for given notes.";
        if (!all) {

            for (Note n : this.notes) {

                ArrayList<Note> majorChord = ChordUtil.getChord(n, "major");

                ArrayList<Note> minorChord = ChordUtil.getChord(n, "minor");
                if (minorChord != null && minorChord.containsAll(notes)) {
                    //Add all notes to result string.
                    this.result = "" + ChordUtil.getChordName(minorChord);
                    return;
                } else if (majorChord != null && majorChord.containsAll(notes)) {
                    this.result = "" + ChordUtil.getChordName(majorChord);
                    return;
                }


            }
        }

    }

    public float getLength(Environment env) {
        return 0;
    }

    public void execute(Environment env) {
        System.out.println(result);
        env.getTranscriptManager().setResult(result);

    }


}
