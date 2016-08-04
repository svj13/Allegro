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
    String startNote, firstNote; //the root note & firstNote ( relevant only to inversions)
    Note note; //the note the current note should be
    private Boolean octaveSpecified;
    private Boolean arpeggioFlag = false;
    private String result;
    int invLevel;  //Inversion level. (0 if no inversion)

    ArrayList<Integer> letters;

    /**
     * Creates a chord command.
     *
     * @param chord      A map that contains the chord's starting note and scale type - major or
     *                   minor.
     *
     * Chord command is used for either outputting chord notes, or playing a specified chord.
     * Can specify chord scale types (major/minor), inversions and play types (arpeggio)
     *
     * @param chord A map that contains the chord's starting note and scale type - major or minor.
     * @param outputType Whether the chord is to be displayed or played.
     */
    public Chord(HashMap<String, String> chord, String outputType) {
        this.startNote = chord.get("note");


        //gets whether the chord to be played is a scale type (major/minor) or a
        // chord type (diminished, major 7th etc)
        if (chord.get("scale_type") != null) {
            this.type = chord.get("scale_type");

        } else {
            this.type = chord.get("chord_type");
        }

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

        //getting the chord array (F4, A5, C5)
        this.chord = ChordUtil.getChord(note, type);

        //checking to see if the array is set to null (i.e notes are invalid)
        if (this.chord == null) {
            this.result = "Invalid chord: " + startNote + ' ' + type + ". Exceeds octave range." ;
            return; //Return without resuming processing the chords.
        } else {
            this.result = null;
        }
        this.startNote = this.chord.get(0).getNote();
        this.firstNote = startNote;
        this.letters = chord.containsKey("scale_type") ? getNoteLetterIndices(this.startNote, 3)
                : getNoteLetterIndices(this.startNote, 4);




        if(chord.containsKey("inversion")){

            invLevel = Integer.parseInt(chord.get("inversion"));

            for (int l = 0; l < invLevel; l++) {
                if(this.chord.get(0).getMidi() >= 120) { //Inversion exceeds range.
                    this.result = "Invalid chord: " + startNote + ' ' + type + ". Exceeds octave range." ;
                    return;
                }
                this.chord = ChordUtil.invertChord(this.chord);
                int x = letters.remove(0);
                letters.add(x);

            }

        }

        this.startNote = this.chord.get(0).getNote(); //StartNote will only change if the chord is inverted.
        currentLetter = Character.toUpperCase(startNote.charAt(0));


        //checks to see if arpeggio was specified or not. Will play simultaneously if not
        try {
            if (chord.get("playStyle").equals("arpeggio")) {
                this.arpeggioFlag = true;
            }
        } catch (Exception e) {
            this.arpeggioFlag = false;
        }

    }

    /**
     * Returns an arrayList of integers which correspond to the letter positions from 'ABCDEFG'
     * Used for references for which enharmonic a note should use in a chord.
     *
     * @param n
     * @return ArrayList of integers corresponding to indices of 'ABCDEFG' string
     */
    private ArrayList<Integer> getNoteLetterIndices(String n, int noteCount) {
        String noteLetters = "ABCDEFG";
        char startLetter = Character.toUpperCase(n.charAt(0));
        int startIndex = noteLetters.indexOf(startLetter);
        ArrayList<Integer> l = new ArrayList<Integer>();

        for(int i = 0; i < noteCount; i++){
            l.add(startIndex + (i * 2));
        }

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


    public long getLength(Environment env) {
        long milliseconds = 0;

        if (outputType.equals("play")) {
            ArrayList<Note> chord = this.chord;
            int tempo = env.getPlayer().getTempo();
            long crotchetLength = 60000 / tempo;
            if (arpeggioFlag) {
                milliseconds = chord.size() * crotchetLength;
            } else {
                milliseconds = crotchetLength;
            }
        }
        return milliseconds;
    }

    /**
     * Prints the chord to the given environment
     *
     * @param env The environment to print to
     */
    private void showChord(Environment env) {
        String chordString = "";
        int c = 0;

        for (Note i : chord) {

            String j = i.getEnharmonicWithLetter("ABCDEFG".charAt(letters.get(c) % 7));

            //String j = i.getEnharmonicWithLetter(currentLetter);
            if (!octaveSpecified) {

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
     *
     * @param env The environment to play in
     */
    private void playChord(Environment env) {
        if (!arpeggioFlag) {
            env.getPlayer().playSimultaneousNotes(chord);
        } else {
            env.getPlayer().playNotes(chord);
        }
        String output = String.format("Playing chord %s %s", firstNote, type);
        if (invLevel > 0) output += " inversion " + invLevel;
        this.result = output;

        env.getTranscriptManager().setResult(this.result);
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

    /**
     * Used by the DSL processor to handle accepted inversions (shortened as well)
     * @param input command inversion extension
     * @return Inversion number (As a string)
     */
    public static String inversionString(String input){

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

    public String getHelp() {
        if (outputType.equals("play")) {
            return "When followed by a valid chord and a valid chord type (i.e. major, minor), will play the given chord";
        } else {
            return "Having errors with the text for this";
        }
    }

    public ArrayList<String> getParams() {
        ArrayList<String> params = new ArrayList<>();
        params.add("note");
        params.add("type");
        return params;
    }

    public ArrayList<String> getOptions() {
        ArrayList<String> options = new ArrayList<>();
        options.add("inversion number");
        if (outputType.equals("play")) {
            options.add("arpeggio");
        }
        return options;
    }
}
