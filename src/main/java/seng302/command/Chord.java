package seng302.command;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.musicNotation.Checker;
import seng302.utility.musicNotation.ChordUtil;
import seng302.utility.musicNotation.OctaveUtil;

/**
 * Created by Sarah on 11/05/2016.
 * ChordUtil is used to obtain the notes required to make up a chord
 */
public class Chord implements Command {
    private ArrayList<Note> chord = new ArrayList<Note>();
    private char currentLetter; //the letter the current note should be
    String type; //where it is major or minor
    String outputType; //whether it wants to be played or printed
    String startNote; //the root note
    Note note; //the note the current note should be
    private Boolean octaveSpecified;
    private Boolean arpeggioFlag = false;
    private String result;

    ArrayList<Integer> letters;
    //int[] letters;
    /**
     * Creates a chord command.
     * @param chord A map that contains the chord's starting note and scale type - major or minor.
     * @param outputType Whether the chord is to be displayed or played.
     */
    public Chord(HashMap<String, String> chord, String outputType) {
        this.startNote = chord.get("note");
        this.type = chord.get("scale_type");
        this.outputType = outputType;
        currentLetter = Character.toUpperCase(startNote.charAt(0));

        //Determines whether an octave was specified or not. Will set the default octave if not
        if (OctaveUtil.octaveSpecifierFlag(this.startNote)) {
            octaveSpecified = true;
            this.note = Note.lookup(startNote);
        } else {
            octaveSpecified = false;
            this.note = Note.lookup(OctaveUtil.addDefaultOctave(startNote));
        }

        //getting the chord array
        this.chord = ChordUtil.getChord(note, type);

        //checking to see if the array is set to null (i.e notes are invalid)
        if (this.chord == null) {
            this.result = "Invalid chord: " + startNote + ' ' + type + ". Exceeds octave range." ;
        } else {
            this.result = null;
        }
        this.startNote = this.chord.get(0).getNote();
        this.letters = getLetters(this.startNote);


        if(chord.containsKey("inversion")){
            System.out.println("here");
            int invLevel = Integer.parseInt(chord.get("inversion"));
            System.out.println(invLevel);
            for (int l = 0; l < invLevel; l++) {
                this.chord = ChordUtil.invertChord(this.chord);
                int x = letters.remove(0);
                letters.add(x);

            }
/*
            if(chord.get("inversion").equals("1")){
                //first inversion
                this.chord = ChordUtil.invertChord(this.chord);

            }
            else if(chord.get("inversion").equals("2")){
                //Second inversion
                this.chord = ChordUtil.invertChord(this.chord);
                this.chord = ChordUtil.invertChord(this.chord);

            }
            else if(chord.get("inversion").equals("3") && this.chord.size() > 3 ){
                //If 3rd inversion and chord has atleast 4 notes.
                this.chord = ChordUtil.invertChord(this.chord);
                this.chord = ChordUtil.invertChord(this.chord);
                this.chord = ChordUtil.invertChord(this.chord);

            }
            */
            for(Note n : this.chord){
                System.out.println("chord: "  +n.getNote());
            }
        }
        this.startNote = this.chord.get(0).getNote();
        currentLetter = Character.toUpperCase(startNote.charAt(0));




        //checks to see if arpeggio was specified or not. Will play simultaneously if not
        try {
            if (chord.get("playStyle").equals("arpeggio")) {
                this.arpeggioFlag = true;
            }
        } catch (Exception e){
            this.arpeggioFlag = false;
        }


    }

    private ArrayList<Integer> getLetters(String n) {
        String noteLetters = "ABCDEFG";
        char startLetter = Character.toUpperCase(n.charAt(0));
        int startIndex = noteLetters.indexOf(startLetter);
        ArrayList<Integer> l = new ArrayList<Integer>();
        l.add(startIndex);
        l.add(startIndex + 2);
        l.add(startIndex + 4);
        return l;


    }

    /**
     * Updates by two letters at once, as we are skipping two places ahead in the scale.
     */
    private  void updateLetter() {

        int index = "ABCDEFG".indexOf(currentLetter);

        if (index >= 5) index -= 7; //Wraps around

        currentLetter = "ABCDEFG".charAt(index + 2);
    }



    public float getLength(Environment env) {
        return 0;
    }

    /**
     * Prints the chord to the given environment
     * @param env The environment to print to
     */
    private void showChord(Environment env) {
        String chordString = "";
        int c = 0;
        for (Note i : chord) {
            System.out.println(c % 7);
            String j = i.getEnharmonicWithLetter("ABCDEFG".charAt(letters.get(c) % 7));
            //String j = i.getEnharmonicWithLetter(currentLetter);
            if (!octaveSpecified) {
                System.out.println("j: " + j);
                j = OctaveUtil.removeOctaveSpecifier(j);

            }
            chordString += j + ' ';
            updateLetter();
            c++;
        }

        env.getTranscriptManager().setResult(chordString);
    }

    /**
     * Plays the chord and prints a message
     * @param env The environment to play in
     */
    private void playChord(Environment env) {
        if (!arpeggioFlag) {
            env.getPlayer().playSimultaneousNotes(chord);
        } else {
            env.getPlayer().playNotes(chord);
        }
        String output = String.format("Playing chord %s %s type", startNote, type);
        if ()
        env.getTranscriptManager().setResult("Playing chord " + startNote + ' ' + type);
    }


    public void execute(Environment env) {

        if (Checker.isDoubleFlat(startNote) || Checker.isDoubleSharp(startNote)) {
            //Disregards double sharps/double flats
            env.error("Invalid chord: '" + startNote + ' ' + type + "'.");
        } else if (result != null) {
            // result has been set as an error
            env.getTranscriptManager().setResult(this.result);
        } else {
            if (outputType.equals("chord")) {
                showChord(env);
            } else {
                playChord(env);
            }

        }

    }

    public static String inversionString(String input){
        System.out.println("called; " + input);
        input = input.toLowerCase();
        if (input.equals("inversion 1") || input.equals("inv 1")) {
            return "1";
        }
        else if(input.equals("inv 2") || input.equals("inversion 2")){
            return "2";
        } else if (input.equals("inv 3") || input.equals("inversion 3")) {
            return "3";
        }
        else return "";

    }

}
