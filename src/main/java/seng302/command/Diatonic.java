package seng302.command;

import java.util.HashMap;
import java.util.List;

import seng302.Environment;
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

    @Override
    public void execute(Environment env) {
        if (command.equals("quality")) {
            result = ChordUtil.getDiatonicChordQuality(romanNumeral);
        } else if (command.equals("chordFunction")) {
            result = ChordUtil.getChordFunction(romanNumeral, startingNote, scaleType);
        } else if (command.equals("functionOf")) {
            result = ChordUtil.getFunctionOf(startingNote, chordNote, chordType);
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

    public List<String> getParams() {
        List<String> params = new ArrayList<>();
        switch (command) {
            case "quality":
                params.add("I-VII");
                break;
            case "chordFunction":
                params.add("I-VII");
                params.add("major key");
                break;
            case "functionOf":
                params.add("chord");
                params.add("major key");
                break;
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
                return "function of D minor 7th C major";
        }
        return null;
    }

}
