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
    private Boolean octaveSpecified; //whether an octave was specified or not
    private Boolean arpeggio; //whether the chord was specified to play with arpeggio or not




    public Chord(HashMap<String, String> chord, String outputType) {
        /** Takes a chord and its output type (major or minor) and sets the command
         * to have all of the right variables so it can be passed to the environment.
         * Determines whether an octave was determined or not
         */
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

        this.chord = note.getChord(type);

    }

    public Chord(HashMap<String, String> chord, String outputType, Boolean arpeggio) {
        /**
         * Takes a chord, its output type (major/minor) and a boolean arpeggio or not.
         * Initializes the chord to have the right variables, determines whether an octave
         * was specified and whether the chord should be played with arpeggio or not. Sets the
         * command to have all of the right variables to play the chord
         */



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

            for (Note i : chord) {
                if (octaveSpecified == false) {
                    String j = i.getNote();
                    j = OctaveUtil.removeOctaveSpecifier(j);
                    chordString += j + ' ';
                } else {
                    String j = i.getNote();
                    chordString += j + ' ';
                }
            }
            env.getTranscriptManager().setResult(chordString);
        }

    }
}
