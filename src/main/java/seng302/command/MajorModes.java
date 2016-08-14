package seng302.command;

import seng302.Environment;
import seng302.data.ModeHelper;
import seng302.data.Note;
import seng302.utility.musicNotation.OctaveUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Major Modes command class deals with two command types.
 * The first is the "mode of" command. This takes a tonic and degree, and displays the corresponding
 * major mode.
 * The second is the "parent of" command. This takes a note and major mode, and displays the major
 * scale of which this is a mode.
 */
public class MajorModes implements Command {

    private String typedTonic;
    private Note tonic;
    private boolean octaveSpecified;
    private String mode;
    private Integer degree;
    private String startNote;
    private String scaleType;

    private String commandType;

    private String outputString;


    private HashMap<Integer, String> modes = new HashMap();


    /**
     * This constructor is used for the "mode of" command.
     *
     * @param tonic  The note of the major scale we are finding modes of
     * @param degree The degree of the mode we are looking for - 1 through 7 are valid
     */
    public MajorModes(String tonic, String degree ){
        this.commandType = "modeOf";
        octaveSpecified = OctaveUtil.octaveSpecifierFlag(tonic);
        this.typedTonic = tonic;
        this.tonic = Note.lookup(OctaveUtil.addDefaultOctave(tonic));
        this.degree = Integer.valueOf(degree);

    }

    /**
     * This constructor is used for the "parent of" command.
     * @param scale The major mode scale to find the parent of.
     */
    public MajorModes(HashMap<String, String> scale) {
        this.commandType = "parentOf";
        this.startNote = scale.get("note");
        this.scaleType = scale.get("scale_type");

        //continue doing things here

    }


    /**
     * The logic for the "mode of" command.
     * Gets the name of the major mode, given a tonic and mode degree.
     * Displays this information in the transcript.
     * An error is shown if the provided degree is outside the accepted range
     * @param env The environment in which the result will be shown.
     */
    public void getCorrespondingScale(Environment env) {
        if (degree >= 1 && degree <= 7) {
            ArrayList<String> majorScale = Scale.scaleNameList(typedTonic, tonic.getScale("major", true), true);
            String parentNote = majorScale.get(degree - 1);
            String mode = ModeHelper.getModes().get(degree);

            env.getTranscriptManager().setResult(parentNote + " " + mode);
        } else {
            env.error("Invalid degree: " + degree + ". Please use degree in range 1-7.");
        }

    }

    @Override
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
