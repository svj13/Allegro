package seng302.command;

import java.util.HashMap;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.musicNotation.ChordUtil;

/**
 * Created by isabelle on 24/07/16.
 */
public class Diatonic implements Command {
    String romanNumeral;
    String command;
    Note startingNote;
    String scaleType;
    String result;


    public Diatonic(String romanNumeral, String command) {
        this.romanNumeral = romanNumeral;
        this.command = command;
    }

    public Diatonic(HashMap<String, String> map) {
        String romanNumeral = map.get("function");
        if (romanNumeral != null) {
            this.command = "function";
            this.startingNote = Note.lookup(map.get("note"));
            this.scaleType = map.get("scale_type");
        }
    }

    @Override
    public void execute(Environment env) {
        if (command == "quality") {
            result = ChordUtil.getDiatonicChordQuality(this.romanNumeral);
        }
        env.getTranscriptManager().setResult(this.result);
    }
}
