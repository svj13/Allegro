package seng302.command;
import seng302.Environment;
import seng302.data.Note;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class KeySignature implements Command {

    /**
     * Note to begin the scale on.
     */
    String startNote;


    Character startNoteChar;

    /**
     * Type of scale. e.g major, minor
     */
    String type;
    /**
     * Way to output scale. e.g note, midi or play
     */
    String outputType;

    /**
     * Indicates whether an octave was specified in the original command. This decides whether
     * octaves will be shown in the output.
     */
    private boolean octaveSpecified;


    /**
     * The letter the current note should be. This is used to find the correct enharmonic of a
     * note.
     */
    private char currentLetter;


    /**
     * The number of octaves to be played.
     */
    private int octaves;

    private float length = 0;

    private static HashMap<String,List> keySignatures = generateKeySignatures();





    public KeySignature(HashMap<String, String> scale, String outputType){
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



    private void displayKeySignature(Environment env){
        String outputString = "";
        System.out.println(startNoteChar);
        if(startNoteChar.equals('C') && !(startNote.contains("#") || startNote.contains("b")) ){
            outputString += startNote + " has no key signatures";

        }else {

            List<String> sig = keySignatures.get(startNote.substring(0, 1).toUpperCase() + startNote.substring(1));
            for (String noteName : sig) {
                outputString += noteName + ", ";
            }
            outputString = outputString.substring(0, outputString.length() - 2);
        }

        env.getTranscriptManager().setResult(outputString);

    }


    private void displaynumberFlatsOrsharps(Environment env){

        System.out.println(startNote);
        System.out.println(startNoteChar);

        if(startNoteChar.equals('C') && !(startNote.contains("#") || startNote.contains("b")) ){
            env.getTranscriptManager().setResult(startNote + " has 0# and 0b");
        }else {

            List<String> sig = keySignatures.get(startNote.substring(0, 1).toUpperCase() + startNote.substring(1));
            env.getTranscriptManager().setResult(Integer.toString(sig.size())+sig.get(0).charAt(1));
        }
    }




    public float getLength(Environment env){
        return length;
    }

    public void execute(Environment env){
        if (outputType.equals("notes")) {
            displayKeySignature(env);

        }else if(outputType.equals("number")){
            displaynumberFlatsOrsharps(env);

        }

    }










    private static HashMap<String,List> generateKeySignatures(){
        HashMap<String,List> signatures = new HashMap<String, List>();

        signatures.put("C", Arrays.asList());
        signatures.put("G", Arrays.asList("F#"));
        signatures.put("D", Arrays.asList("F#","C#"));
        signatures.put("A", Arrays.asList("F#","C#","G#"));
        signatures.put("E", Arrays.asList("F#","C#","G#","D#"));
        signatures.put("B", Arrays.asList("F#","C#","G#","D#","A#"));
        signatures.put("F#", Arrays.asList("F#","C#","G#","D#","A#","E#"));
        signatures.put("C#", Arrays.asList("F#","C#","G#","D#","A#","E#","B#"));
        signatures.put("Cb", Arrays.asList("Fb","Cb","Gb","Db","Ab","Eb","Bb"));
        signatures.put("Gb", Arrays.asList("Cb","Gb","Db","Ab","Eb","Bb"));
        signatures.put("Db", Arrays.asList("Gb","Db","Ab","Eb","Bb"));
        signatures.put("Ab", Arrays.asList("Db","Ab","Eb","Bb"));
        signatures.put("Eb", Arrays.asList("Ab","Eb","Bb"));
        signatures.put("Bb", Arrays.asList("Eb","Bb"));
        signatures.put("F", Arrays.asList("Bb"));

        return signatures;

    }




}
