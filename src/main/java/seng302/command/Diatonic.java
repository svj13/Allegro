package seng302.command;

import java.util.ArrayList;
import java.util.HashMap;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.musicNotation.ChordUtil;
import seng302.utility.musicNotation.OctaveUtil;

/**
 * Created by isabelle on 24/07/16.
 */
public class Diatonic implements Command {
    String romanNumeral;
    String command;
    String startingNote;
    String scaleType;
    String result;
    String chordType;
    String chordNote;


    public Diatonic(String romanNumeral) {
        this.romanNumeral = romanNumeral;
        this.command = "quality";
    }

    public Diatonic(HashMap<String, String> map) {
        this.romanNumeral = map.get("function");
        if (this.romanNumeral != null) {
            this.command = "chordFunction";
            this.startingNote = map.get("note");
            this.scaleType = map.get("scale_type");
        }
        this.chordType = map.get("chord_type");
        if (this.chordType != null) {
            this.command = "functionOf";
            this.startingNote = OctaveUtil.capitalise(map.get("scaleNote"));
            this.chordNote = OctaveUtil.capitalise(map.get("chordNote"));
        }
    }

    private String getFunctionOf() {
        Note noteScaleStart = Note.lookup(OctaveUtil.addDefaultOctave(startingNote));
        ArrayList<Note> scale = noteScaleStart.getScale("major", true);
        ArrayList<String> scaleNoteNames = Scale.scaleNameList(startingNote, scale, true);
        if (scaleNoteNames.contains(chordNote)) {
            // The note is in the scale.
            Integer numberOfNote = scaleNoteNames.indexOf(chordNote);
            String romanNumeral = ChordUtil.integerToRomanNumeral(numberOfNote + 1);
            String quality = ChordUtil.getDiatonicChordQuality(romanNumeral);
            if (quality.equals(chordType)) {
                result = romanNumeral;
            } else {
                result = "Non Functional";
            }
        } else {
            result = "Non Functional";
        }
        return result;
    }

    @Override
    public void execute(Environment env) {
        if (command == "quality") {
            result = ChordUtil.getDiatonicChordQuality(romanNumeral);
        } else if (command == "chordFunction") {
            result = ChordUtil.getChordFunction(romanNumeral, startingNote, scaleType);
        } else if (command == "functionOf") {
            result = getFunctionOf();
        }
        env.getTranscriptManager().setResult(this.result);
    }
}
