package seng302.command;
import seng302.Environment;
import seng302.data.Note;
import seng302.utility.OctaveUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class KeySignature implements Command {

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
     * map that stores all the major scales and there corresponding key signature
     */
    private static HashMap<String,List> majorKeySignatures = generateMajorKeySignatures();

    /**
     * map that stores all the minor scales and there corresponding key signature
     */
    private static HashMap<String,List> minorKeySignatures = generateMinorKeySignatures();




    public KeySignature(HashMap<String, String> scale, String outputType){
        this.startNote = scale.get("note");
        this.startNoteChar = Character.toUpperCase(startNote.charAt(0));
        System.out.println(scale);

        this.type = scale.get("scale_type");
        this.outputType = outputType;

        if (scale.get("octaves") != null) {
            this.octaves = Integer.valueOf(scale.get("octaves"));
        } else {
            this.octaves = 1;
        }
    }


    /**
     * Displays the key signatures in order for a given scale
     * @param env
     */
    private void displayKeySignature(Environment env){
        String outputString = "";
        System.out.println(startNoteChar);

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
                sig = majorKeySignatures.get(startNote.substring(0, 1).toUpperCase() + startNote.substring(1));
                env.getTranscriptManager().setResult(generateOutputString(sig, octaveSpecifier));
            }


        }else if(type.toLowerCase().equals("minor")){
            if(startNoteChar.equals('A') && !(startNote.contains("#") || startNote.contains("b")) ) {
                outputString += startNote + octaveSpecifier + " " + type + " has no key signatures";
                env.getTranscriptManager().setResult(outputString);
            }else {
                sig = minorKeySignatures.get(startNote.substring(0, 1).toUpperCase() + startNote.substring(1));
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


    /**
     * Displays the number of flats or sharps in a given scale
     * @param env
     */
    private void displayNumberKeySignatures(Environment env){

        System.out.println(startNote);
        System.out.println(startNoteChar);
        List<String> sig;

        if(octaveSpecified){
            startNote = startNote.substring(0, startNote.length() - 1); //strip ocave specifier
        }

        if (type.toLowerCase().equals("major")) {
            if(startNoteChar.equals('C') && !(startNote.contains("#") || startNote.contains("b")) ) {
                env.getTranscriptManager().setResult(startNote + " has 0# and 0b");
            }else {
                sig = majorKeySignatures.get(startNote.substring(0, 1).toUpperCase() + startNote.substring(1));
                env.getTranscriptManager().setResult(Integer.toString(sig.size())+sig.get(0).charAt(1));
            }


        }else if(type.toLowerCase().equals("minor")){
            if(startNoteChar.equals('A') && !(startNote.contains("#") || startNote.contains("b")) ) {
                env.getTranscriptManager().setResult(startNote + " has 0# and 0b");
            }else {
                sig = minorKeySignatures.get(startNote.substring(0, 1).toUpperCase() + startNote.substring(1));
                env.getTranscriptManager().setResult(Integer.toString(sig.size())+sig.get(0).charAt(1));
            }
        }else{
            env.getTranscriptManager().setResult(type + " is not a valid scale type");
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

        }

    }


    /**
     * Generates a map with all the key signatures for each  major scale
     * @return
     */
    private static HashMap<String,List> generateMajorKeySignatures(){
        HashMap<String,List>majorKeySignatures = new HashMap<String, List>();

        majorKeySignatures.put("C", Arrays.asList());
        majorKeySignatures.put("G", Arrays.asList("F#"));
        majorKeySignatures.put("D", Arrays.asList("F#","C#"));
        majorKeySignatures.put("A", Arrays.asList("F#","C#","G#"));
        majorKeySignatures.put("E", Arrays.asList("F#","C#","G#","D#"));
        majorKeySignatures.put("B", Arrays.asList("F#","C#","G#","D#","A#"));
        majorKeySignatures.put("F#", Arrays.asList("F#","C#","G#","D#","A#","E#"));
        majorKeySignatures.put("C#", Arrays.asList("F#","C#","G#","D#","A#","E#","B#"));
        majorKeySignatures.put("Cb", Arrays.asList("Fb","Cb","Gb","Db","Ab","Eb","Bb"));
        majorKeySignatures.put("Gb", Arrays.asList("Cb","Gb","Db","Ab","Eb","Bb"));
        majorKeySignatures.put("Db", Arrays.asList("Gb","Db","Ab","Eb","Bb"));
        majorKeySignatures.put("Ab", Arrays.asList("Db","Ab","Eb","Bb"));
        majorKeySignatures.put("Eb", Arrays.asList("Ab","Eb","Bb"));
        majorKeySignatures.put("Bb", Arrays.asList("Eb","Bb"));
        majorKeySignatures.put("F", Arrays.asList("Bb"));

        return majorKeySignatures;

    }


    /**
     * Generates a map with all the key signatures for each  minor scale
     * @return
     */
    private static HashMap<String,List> generateMinorKeySignatures(){
        HashMap<String,List>minorKeySignatures = new HashMap<String, List>();

        minorKeySignatures.put("A", Arrays.asList());
        minorKeySignatures.put("E", Arrays.asList("F#"));
        minorKeySignatures.put("B", Arrays.asList("F#","C#"));
        minorKeySignatures.put("F#", Arrays.asList("F#","C#","G#"));
        minorKeySignatures.put("C#", Arrays.asList("F#","C#","G#","D#"));
        minorKeySignatures.put("G#", Arrays.asList("F#","C#","G#","D#","A#"));
        minorKeySignatures.put("D#", Arrays.asList("F#","C#","G#","D#","A#","E#"));
        minorKeySignatures.put("A#", Arrays.asList("F#","C#","G#","D#","A#","E#","B#"));
        minorKeySignatures.put("Ab", Arrays.asList("Fb","Cb","Gb","Db","Ab","Eb","Bb"));
        minorKeySignatures.put("Eb", Arrays.asList("Cb","Gb","Db","Ab","Eb","Bb"));
        minorKeySignatures.put("Bb", Arrays.asList("Gb","Db","Ab","Eb","Bb"));
        minorKeySignatures.put("F", Arrays.asList("Db","Ab","Eb","Bb"));
        minorKeySignatures.put("C", Arrays.asList("Ab","Eb","Bb"));
        minorKeySignatures.put("G", Arrays.asList("Eb","Bb"));
        minorKeySignatures.put("D", Arrays.asList("Bb"));

        return minorKeySignatures;

    }

}
