package seng302.command;

import java.util.ArrayList;
import java.util.HashMap;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.musicNotation.ChordUtil;
import seng302.utility.musicNotation.OctaveUtil;

/**
 *  This class handles commands related to Diatonic Chords, including finding the quality of the
 *  chord, the chord function or the function of a chord and key.
 */
public class Diatonic implements Command {
    private String romanNumeral;
    private String command;
    private String startingNote;
    private String scaleType;
    private String result;
    private String chordType;
    private String chordNote;


    /**
     * This constructor is used for the 'quality of' command.
     *
     * @param romanNumeral the function that the user wants the quality of.
     */
    public Diatonic(String romanNumeral) {
        this.romanNumeral = romanNumeral;
        this.command = "quality";
    }

    /**
     * This constructor is used for the chord function and function of commands.
     * @param map The map contained the parameters for the command.
     */
    public Diatonic(HashMap<String, String> map) {
        this.romanNumeral = map.get("function");
        // So this is a chord function command
        if (this.romanNumeral != null) {
            this.command = "chordFunction";
            this.startingNote = map.get("note");
            this.scaleType = map.get("scale_type");
        }
        this.chordType = map.get("chord_type");
        // So this is a function of command.
        if (this.chordType != null) {
            this.command = "functionOf";
            this.startingNote = OctaveUtil.capitalise(map.get("scaleNote"));
            this.chordNote = OctaveUtil.capitalise(map.get("chordNote"));
        }
    }

    /**
     * For the 'function of' command this method looks up the scale and checks if the chord note
     * is in the scale. If it is in the scale, it finds which number note it is and finds the
     * function (roman numeral) for the number note. It then checks that the quality of the chords
     * matches the quality of that chord function.
     * @return If this is all ok, it will return the function. Otherwise it return 'Non Functional'.
     */
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

    public String getHelp() {
        switch (command) {
            case "quality":
                return "When followed by a roman numeral from 1-7 (I - VII), " +
                        "displays the quality of a chord for this degree.";
            case "chordFunction":
                return "When followed by a major key (e.g. C major) and a quality, displays the diatonic chord " +
                        "function of this pair.";

            case "functionOf":
                return "When followed by a chord and a major key (e.g. C major), displays the function" +
                        "of this pair.";

        }
        return null;

    }

    public ArrayList<String> getParams() {
        ArrayList<String> params = new ArrayList<>();
        switch (command) {
            case "quality":
                params.add("I-VII");
            case "chordFunction":
                params.add("chord");
                params.add("major key");
            case "functionOf":
                params.add("chord");
                params.add("major key");
        }

        return params;
    }

    @Override
    public String getCommandText() {
        switch (command) {
            case "quality":
                return "quality of";
            case "chordFunction":
                return "chord function";
            case "functionOf":
                return "function of";
        }
        return null;
    }

    @Override
    public String getExample() {
        switch (command) {
            case "quality":
                return "quality of IV";
            case "chordFunction":
                return "chord function II C major";
            case "functionOf":
                return "function of C major II";
        }
        return null;
    }

}
