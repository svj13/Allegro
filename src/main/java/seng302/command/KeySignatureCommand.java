package seng302.command;
import seng302.Environment;
import seng302.data.KeySignature;
import seng302.utility.OctaveUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeySignatureCommand implements Command {

    /**
     * Note to begin the scale on.
     */
    String startNote;


    /**
     * The first character of the starting note
     */
    Character startNoteChar;

    /**
     * Type of scale. e.g major, minor
     */
    String type;
    /**
     * Way to output scale. e.g note, midi or play
     */
    String outputType;

    Integer octaves;

    /**
     * Indicates whether an octave was specified in the original command. This decides whether
     * octaves will be shown in the output.
     */
    private boolean octaveSpecified;


    private float length = 0;

    /**
     * How many flats/sharps we are looking for in a scale signature
     */
    private String numFlatsOrSharps;

    /**
     * Whether we are looking for flats or sharps
     */
    private String flatOrSharp;

    private ArrayList<String> notesInSignature;


    /**
     * map that stores all the major scales and there corresponding key signature
     */
    private static HashMap<String, KeySignature> majorKeySignatures = KeySignature.getMajorKeySignatures();

    /**
     * map that stores all the minor scales and there corresponding key signature
     */
    private static HashMap<String, KeySignature> minorKeySignatures = KeySignature.getMinorKeySignatures();



    public KeySignatureCommand(HashMap<String, String> scale, String outputType){
        this.startNote = scale.get("note");
        this.startNoteChar = Character.toUpperCase(startNote.charAt(0));

        this.type = scale.get("scale_type");
        this.outputType = outputType;

        if (scale.get("octaves") != null) {
            this.octaves = Integer.valueOf(scale.get("octaves"));
        } else {
            this.octaves = 1;
        }
    }

    /**
     * Constructor for getting scales with the given number of sharps/flats in their key sig
     */
    public KeySignatureCommand(String numSharpsOrFlats){
        if (numSharpsOrFlats.equals("0#b")) {
            this.numFlatsOrSharps = "0";
            this.flatOrSharp = "x";
        } else {
            this.numFlatsOrSharps = Character.toString(numSharpsOrFlats.charAt(0));
            this.flatOrSharp = Character.toString(numSharpsOrFlats.charAt(1));
        }
        this.outputType = "get";
    }

    /**
     * Constructor for getting scales with the given set of notes in their key signature
     */
    public KeySignatureCommand(ArrayList<String> notes) {
        this.notesInSignature = notes;
        this.outputType = "getNotes";
    }



    /**
     * Displays the key signatures in order for a given scale
     * @param env
     */
    private void displayKeySignature(Environment env){
        String outputString = "";

        List<String> sig;

        String octaveSpecifier = "";
        if(octaveSpecified){
            octaveSpecifier = startNote.substring(startNote.length() - 1);
            startNote = startNote.substring(0, startNote.length() - 1); //strip ocave specifier
        }



        if (type.toLowerCase().equals("major")) {
            if(startNoteChar.equals('C') && !(startNote.contains("#") || startNote.contains("b")) ) {
                outputString += startNote + octaveSpecifier+ " " +type + " has no key signatures";
                env.getTranscriptManager().setResult(outputString);
            }else {
                sig = majorKeySignatures.get(startNote.substring(0, 1).toUpperCase() + startNote.substring(1)).getNotes();
                env.getTranscriptManager().setResult(generateOutputString(sig, octaveSpecifier));
            }


        }else if(type.toLowerCase().equals("minor")){
            if(startNoteChar.equals('A') && !(startNote.contains("#") || startNote.contains("b")) ) {
                outputString += startNote + octaveSpecifier + " " + type + " has no key signatures";
                env.getTranscriptManager().setResult(outputString);
            }else {
                sig = minorKeySignatures.get(startNote.substring(0, 1).toUpperCase() + startNote.substring(1)).getNotes();
                env.getTranscriptManager().setResult(generateOutputString(sig, octaveSpecifier));
            }
        }else{
            env.getTranscriptManager().setResult(type + " is not a valid scale type");
        }
    }

    /**
     * Generates a string by concatenating a list and adding on an octave specifier to each item
     * @param list - list being concatenated
     * @param octaveSpecifier - the octave specifier that needs to be added to the end of each element in the list.
     * @return a string that is constructed from the list
     */
    private String generateOutputString(List<String> list, String octaveSpecifier){
        String outputString = "";
        for (String noteName : list) {
            outputString += noteName + octaveSpecifier + ", ";
        }

        outputString = outputString.substring(0, outputString.length() - 2);
        return outputString;

    }

    private String generateOutputStringScaleNames(List<String> scaleNames) {
        String outputString = "";
        for (String scaleName : scaleNames) {
            outputString += scaleName + ", ";
        }
        outputString = outputString.substring(0, outputString.length() - 2);
        return outputString;
    }


    /**
     * Displays the number of flats or sharps in a given scale
     * @param env
     */
    private void displayNumberKeySignatures(Environment env){

        List<String> sig;

        if(octaveSpecified){
            startNote = startNote.substring(0, startNote.length() - 1); //strip ocave specifier
        }

        if (type.toLowerCase().equals("major")) {
            if(startNoteChar.equals('C') && !(startNote.contains("#") || startNote.contains("b")) ) {
                env.getTranscriptManager().setResult(startNote + " major has 0# and 0b");
            }else {
                sig = majorKeySignatures.get(startNote.substring(0, 1).toUpperCase() + startNote.substring(1)).getNotes();
                env.getTranscriptManager().setResult(Integer.toString(sig.size())+sig.get(0).charAt(1));
            }


        }else if(type.toLowerCase().equals("minor")){
            if(startNoteChar.equals('A') && !(startNote.contains("#") || startNote.contains("b")) ) {
                env.getTranscriptManager().setResult(startNote + " minor has 0# and 0b");
            }else {
                sig = minorKeySignatures.get(startNote.substring(0, 1).toUpperCase() + startNote.substring(1)).getNotes();
                env.getTranscriptManager().setResult(Integer.toString(sig.size())+sig.get(0).charAt(1));
            }
        }else{
            env.getTranscriptManager().setResult(type + " is not a valid scale type");
        }

    }

    /**
     * Provided with a number and either sharps or flats, finds all scales whose key signatures
     * have this number of sharps/flats.
     * @param env
     */
    private void getScalesOfType(Environment env) {
        try {
            int numFlatsOrSharps = Integer.parseInt(this.numFlatsOrSharps);
            if (numFlatsOrSharps > 7 || numFlatsOrSharps < 0) {
                //invalid number
                env.getTranscriptManager().setResult("The provided number was invalid. Must be between 0 and 7.");
            } else {
                List<String> scalesWithThisType;
                scalesWithThisType = new ArrayList<String>();
                //valid number
                if (this.flatOrSharp.equals("b")) {
                    //Add the major scales
                    for(Map.Entry<String, KeySignature> entry : majorKeySignatures.entrySet()) {
                        KeySignature thisKeySig = entry.getValue();
                        if (thisKeySig.getNumberOfFlats() == numFlatsOrSharps) {
                            scalesWithThisType.add(thisKeySig.getStartNote() + " major");
                        }
                    }

                    //Add the minor scales
                    for(Map.Entry<String, KeySignature> entry : minorKeySignatures.entrySet()) {
                        KeySignature thisKeySig = entry.getValue();
                        if (thisKeySig.getNumberOfFlats() == numFlatsOrSharps) {
                            scalesWithThisType.add(thisKeySig.getStartNote() + " minor");
                        }
                    }
                }
                if (this.flatOrSharp.equals("#")) {
                    //Add the major scales
                    for(Map.Entry<String, KeySignature> entry : majorKeySignatures.entrySet()) {
                        KeySignature thisKeySig = entry.getValue();
                        if (thisKeySig.getNumberOfSharps() == numFlatsOrSharps) {
                            scalesWithThisType.add(thisKeySig.getStartNote() + " major");
                        }
                    }

                    //Add the minor scales
                    for(Map.Entry<String, KeySignature> entry : minorKeySignatures.entrySet()) {
                        KeySignature thisKeySig = entry.getValue();
                        if (thisKeySig.getNumberOfSharps() == numFlatsOrSharps) {
                            scalesWithThisType.add(thisKeySig.getStartNote() + " minor");
                        }
                    }
                }
                if (this.flatOrSharp.equals("x")) {
                    //Add the major scales
                    for (Map.Entry<String, KeySignature> entry : majorKeySignatures.entrySet()) {
                        KeySignature thisKeySig = entry.getValue();
                        if (thisKeySig.getNumberOfSharps() == numFlatsOrSharps &&
                                thisKeySig.getNumberOfFlats() == numFlatsOrSharps) {
                            scalesWithThisType.add(thisKeySig.getStartNote() + " major");
                        }
                    }

                    //Add the minor scales
                    for (Map.Entry<String, KeySignature> entry : minorKeySignatures.entrySet()) {
                        KeySignature thisKeySig = entry.getValue();
                        if (thisKeySig.getNumberOfSharps() == numFlatsOrSharps &&
                                thisKeySig.getNumberOfFlats() == numFlatsOrSharps) {
                            scalesWithThisType.add(thisKeySig.getStartNote() + " minor");
                        }
                    }
                }
                env.getTranscriptManager().setResult(generateOutputString(scalesWithThisType,""));
            }

        } catch (Exception e) {
            env.getTranscriptManager().setResult("The provided number was invalid.");
        }
    }

    /**
     * Given a list of notes, finds all scales whose signatures match that list.
     */
    private void getScaleWithNotes(Environment env) {
        ArrayList<String> scales = new ArrayList<String>();
        for (Map.Entry<String, KeySignature> entry : minorKeySignatures.entrySet()) {
            KeySignature thisKeySig = entry.getValue();
            if (thisKeySig.getNotes().equals(this.notesInSignature)) {
                scales.add(thisKeySig.getStartNote() + " minor");
            }
        }
        for (Map.Entry<String, KeySignature> entry : majorKeySignatures.entrySet()) {
            KeySignature thisKeySig = entry.getValue();
            if (thisKeySig.getNotes().equals(this.notesInSignature)) {
                scales.add(thisKeySig.getStartNote() + " major");
            }
        }

        if (scales.size() > 0) {
            env.getTranscriptManager().setResult(generateOutputStringScaleNames(scales));
        } else {
            env.getTranscriptManager().setResult("No scales with this key signature.");
        }

    }




    public float getLength(Environment env){
        return length;
    }


    /**
     * Called when the command is made and decides what function needs to be called
     * @param env
     */
    public void execute(Environment env){


        if (OctaveUtil.octaveSpecifierFlag(this.startNote)) {
            octaveSpecified = true;
        } else {
            octaveSpecified = false;
        }


        if (outputType.equals("notes")) {
            displayKeySignature(env);

        }else if(outputType.equals("number")){
            displayNumberKeySignatures(env);

        } else if (outputType.equals("get")) {
            getScalesOfType(env);
        } else if (outputType.equals("getNotes")) {
            getScaleWithNotes(env);
        }

    }
}
