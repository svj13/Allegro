package seng302.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import seng302.Environment;
import seng302.data.ModeHelper;
import seng302.data.Note;
import seng302.utility.musicNotation.OctaveUtil;

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


    private static ArrayList<String> majorNotes = new ArrayList<>(Arrays.asList("C", "G", "D", "A", "E", "B", "F", "Bb", "Eb", "Ab", "Db", "Gb"));


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
        this.tonic = Note.lookup(OctaveUtil.addDefaultOctave(startNote));

    }

    /**
     * Takes a parent scale note and a degree, and returns the mode of the corresponding parent scale and degree as a
     * string
     * @return result
     */
    public static String getCorrespondingScaleString(String typedTonic, Integer degree) {
        Note tonic = Note.lookup(OctaveUtil.addDefaultOctave(typedTonic));

        if (degree >= 1 && degree <= 7) {
            ArrayList<String> majorScale = Scale.scaleNameList(typedTonic, tonic.getScale("major", true), true, "major");
            String parentNote = majorScale.get(degree - 1);
            String mode = ModeHelper.getValueModes().get(degree);
            String result = parentNote + " " + mode;
            return result;
        } else {
            return "Invalid degree: " + degree + ". Please use degree in range 1-7.";
        }

    }

    /**
     * Takes a mode note of a scale and its type (e.g Dorian) and returns the parent scale
     * it belongs to as a string
     * @param startNote
     * @param scaleType
     * @return parentScale
     */
    public static String getParentScaleString(String startNote, String scaleType) {
        Integer degree = ModeHelper.getkeyModes().get(scaleType);
        for (String note : majorNotes) {
            Note tonic = Note.lookup(OctaveUtil.addDefaultOctave(note));
            ArrayList<String> majorScale = Scale.scaleNameList(note, tonic.getScale("major", true), true, scaleType);
            if (majorScale.get(degree - 1).equals(startNote)) {
                String parentScale = note + " major";
                return parentScale;
            }
        }

        return "Start note did not match";
    }


    /**
     *  The logic for the "parent of" command.
     *  Gets the name of the parent scale given a major mode scale for the transcript manager
     * @param env
     */
    public void getParentscale(Environment env){
        degree = ModeHelper.getkeyModes().get(scaleType);
        for(String note : majorNotes){
            Note tonic = Note.lookup(OctaveUtil.addDefaultOctave(note));
            ArrayList<String> majorScale = Scale.scaleNameList(note, tonic.getScale("major", true), true, scaleType);
            if(majorScale.get(degree - 1).equals(startNote)){
                env.getTranscriptManager().setResult(note + " major");
                System.out.println(getParentScaleString("D", "dorian"));
//            } else {
//                env.error("Invalid note/scale type. Please check they are spelt correctly");
         }

        }

    }


    /**
     * The logic for the "mode of" command.
     * Gets the name of the major mode, given a tonic and mode degree for the transcript manager.
     * Displays this information in the transcript.
     * An error is shown if the provided degree is outside the accepted range
     * @param env The environment in which the result will be shown.
     */
    public void getCorrespondingScale(Environment env) {
        if (degree >= 1 && degree <= 7) {
            ArrayList<String> majorScale = Scale.scaleNameList(typedTonic, tonic.getScale("major", true), true, "major");
            String parentNote = majorScale.get(degree - 1);
            String mode = ModeHelper.getValueModes().get(degree);
            env.getTranscriptManager().setResult(parentNote + " " + mode);
        } else {
            env.error("Invalid degree: " + degree + ". Please use degree in range 1-7.");
        }

    }

    @Override
    public void execute(Environment env){
        if (commandType.equals("modeOf")) {
            getCorrespondingScale(env);
        }else if( commandType.equals("parentOf")){
            getParentscale(env);
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
