package seng302.command;
import seng302.Environment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class KeySignature implements Command {
    private float length = 0;

    private static HashMap<String,List> keySignatures = generateKeySignatures();

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


    public KeySignature(){



    }


    private void displayKeySignature(){

        //display notes

    }


    private void displaynumberFlatsOrsharps(){

        //display the number of flats or sharps

    }








    public float getLength(Environment env){
        return length;
    }

    public void execute(Environment env){



    }










}
