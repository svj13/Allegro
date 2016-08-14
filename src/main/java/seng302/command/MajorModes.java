package seng302.command;

import seng302.Environment;
import seng302.data.ModeHelper;
import seng302.data.Note;
import seng302.utility.musicNotation.OctaveUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jmw280 on 10/08/16.
 */
public class MajorModes implements Command {

    //private String tonic;
    private Note tonic;
    private boolean octaveSpecified;
    private String mode;
    private Integer degree;
    private String startNote;
    private String scaleType;

    private String commandType;

    private String outputString;


    private HashMap<Integer, String> modes = new HashMap();


    // This one is used for mode of command
    public MajorModes(String tonic, String degree ){
        this.commandType = "modeOf";
        octaveSpecified = OctaveUtil.octaveSpecifierFlag(tonic);
        this.tonic = Note.lookup(OctaveUtil.addDefaultOctave(tonic));
        this.degree = Integer.valueOf(degree);

    }

    // Used for finding parent of
    public MajorModes(HashMap<String, String> scale) {
        this.commandType = "parentOf";
        this.startNote = scale.get("note");
        this.scaleType = scale.get("scale_type");

        //continue doing things here

    }


    public void getCorrespondingScale(Environment env) {
        if (degree >= 1 && degree <= 7) {
            ArrayList<Note> majorScale = tonic.getScale("major", true);
            Note parentNote = majorScale.get(degree - 1);
            String mode = ModeHelper.getModes().get(degree);

            String displayNote;

            if (octaveSpecified) {
                displayNote = parentNote.getNote();
            } else {
                displayNote = OctaveUtil.removeOctaveSpecifier(parentNote.getNote());
            }

            env.getTranscriptManager().setResult(displayNote + " " + mode);
        } else {
            env.error("Invalid degree: " + degree + ". Please use degree in range 1-7.");
        }





    }

    public void execute(Environment env){
        if (commandType.equals("modeOf")) {
            getCorrespondingScale(env);

        }

    }

    @Override
    public String getHelp() {
        if (commandType.equals("modeOf")) {
            return "Given a starting note and a degree, provides the corresponding scale" +
                    " of the given note and the major mode of the given degree.";
        } else {
            return "Given a note and a major mode name, displays the name of the corresponding " +
                    "major scale.";
        }
    }

    @Override
    public String getCommandText() {
        if (commandType.equals("modeOf")) {
            return "mode of";
        } else {
            return "parent of";
        }
    }

    @Override
    public String getExample() {
        if (commandType.equals("modeOf")) {
            return "mode of C 2";
        } else {
            return "parent of A phrygian";
        }
    }

    @Override
    public List<String> getParams() {
        List<String> params = new ArrayList<>();
        if (commandType.equals("modeOf")) {
            params.add("note");
            params.add("1-7");
        } else {
            params.add("note");
            params.add("major mode type");
        }
        return params;
    }
}
