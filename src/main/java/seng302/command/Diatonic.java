package seng302.command;

import java.util.HashMap;

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
        if (command == "quality") {
            result = ChordUtil.getDiatonicChordQuality(romanNumeral);
        } else if (command == "chordFunction") {
            result = ChordUtil.getChordFunction(romanNumeral, startingNote, scaleType);
        } else if (command == "functionOf") {
            result = ChordUtil.getFunctionOf(startingNote, chordNote, chordType);
        }
        env.getTranscriptManager().setResult(this.result);
    }
}
