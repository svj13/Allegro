package seng302.command;


import java.util.ArrayList;
import java.util.HashMap;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.Checker;
import seng302.MusicPlayer;
import seng302.command.Scale;
import seng302.utility.OctaveUtil;

/**
 * Created by Sarah on 11/05/2016.
 * Chord is used to obtain the notes required to make up a chord
 */
public class Chord implements Command {
    private ArrayList<Note> chord = new ArrayList<Note>();
    private int octaves; //number of octaves to be played
    private char currentLetter; //the letter the current note should be
    String type; //where it is major or minor
    String outputType; //whether it wants to be played or printed
    String startNote; //the root note
    Note note; //the note the current note should be
    private Boolean octaveSpecified;
    private Boolean arpeggioFlag = false;



    public Chord(HashMap<String, String> chord, String outputType) {
        /**Takes in a chord comprised of notes and an output type (whether the note is to be
         * played or printed). Sets up the chord to be passed to the environment to be executed
         */
        this.startNote = chord.get("note");
        this.type = chord.get("scale_type");
        this.outputType = outputType;
        currentLetter = Character.toUpperCase(startNote.charAt(0));
        System.out.println(chord);
        System.out.println(outputType);

        //checks to see if an octave was specified or not. Will use default octave if not
        if (OctaveUtil.octaveSpecifierFlag(this.startNote)) {
            octaveSpecified = true;
            this.note = Note.lookup(startNote);
        } else {
            octaveSpecified = false;
            this.note = Note.lookup(OctaveUtil.addDefaultOctave(startNote));
        }
        this.chord = note.getChord(type);

        //checks to see if arpeggio was specified or not. Will play simultaneously if not
        try {
            if (chord.get("playStyle").equals("arpeggio")) {
                this.arpeggioFlag = true;
            }
        } catch (Exception e){
            this.arpeggioFlag = false;
        }

    }

    /**
     * Updates by two letters at once, as we are skipping two places ahead in the scale.
     */
    private  void updateLetter() {
        int index = "ABCDEFG".indexOf(currentLetter);
        if (index == 5) {
            index = -2;
        }
        if (index == 6) {
            index = -1;
        }
        currentLetter = "ABCDEFG".charAt(index + 2);
    }



    public float getLength(Environment env) {
        return 0;
    };


    public void execute(Environment env) {

        if (Checker.isDoubleFlat(startNote) || Checker.isDoubleSharp(startNote)) {
            //Disregards double sharps/double flats
            env.error("Invalid chord: '" + startNote + ' ' + type + "'.");
        } else {
            String chordString = "";
            if (outputType == "chord") {
                // showing the chord
                for (Note i : chord) {
                    if (octaveSpecified == false) {
                        String j = i.getEnharmonicWithLetter(currentLetter);
                        j = OctaveUtil.removeOctaveSpecifier(j);
                        chordString += j + ' ';
                    } else {
                        String j = i.getEnharmonicWithLetter(currentLetter);
                        chordString += j + ' ';
                    }
                    updateLetter();
                }
                env.getTranscriptManager().setResult(chordString);
            } else {
                // playing the chord
                if (arpeggioFlag == false) {
                    env.getPlayer().playSimultaneousNotes(chord);
                } else {
                    env.getPlayer().playNotes(chord);
                }
                env.getTranscriptManager().setResult("Playing chord " + startNote + ' ' + type);

            }

        }

    }
}
