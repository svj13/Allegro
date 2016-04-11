package seng302.command;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import javax.sound.midi.*;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.Checker;
import seng302.utility.OctaveUtil;

/**
 * Created by emily on 18/03/16.
 */
public class Scale implements Command {
    String search;
    String type;
    String outputType;
    private boolean octaveSpecified;
    private Note note;
    private char[] letters;
    private char currentLetter;
    private String direction = "up";
    private Environment env;


    public Scale(String a, String b, String outputType) {
        this.search = a;
        this.type = b;
        this.outputType = outputType;
        this.letters = "ABCDEFG".toCharArray();
        currentLetter = Character.toUpperCase(search.charAt(0));


    }

    public Scale(String a, String b, String outputType, String direction) {
        this.search = a;
        this.type = b;
        this.outputType = outputType;
        this.letters = "ABCDEFG".toCharArray();
        currentLetter = Character.toUpperCase(search.charAt(0));
        this.direction = direction;
    }

    private void updateLetter() {
        int index = "ABCDEFG".indexOf(currentLetter);
        if (index + 1 > 6) {
            index = -1;
        }
        currentLetter = "ABCDEFG".charAt(index + 1);
    }

    public void execute(Environment env) {
        this.env = env;
        if (Checker.isDoubleFlat(search) || Checker.isDoubleSharp(search)) {
            env.error("Invalid scale: '" + search + ' ' + type + "'.");
        } else {
            try {
                if (OctaveUtil.octaveSpecifierFlag(this.search)) {
                    octaveSpecified = true;
                    this.note = Note.lookup(search);
                } else {
                    octaveSpecified = false;
                    this.note = Note.lookup(OctaveUtil.addDefaultOctave(search));
                }
                try {
                    if (this.outputType.equals("note")) {
                        env.getTranscriptManager().setResult(scaleToString(note.getScale(type)));
                    } else if (this.outputType.equals("midi")) {
                        env.getTranscriptManager().setResult(scaleToMidi(note.getScale(type)));
                    } else { // Play scale.
                        ArrayList<Note> notesToPlay = note.getScale(type);
                        env.getPlayer().playNotes(notesToPlay);
                    }
                } catch (IllegalArgumentException i) {
                    env.error(i.getMessage());
                }
            } catch (Exception e) {
                env.error("Note is not contained in the MIDI library.");
            }
        }
    }

    private String scaleToString(ArrayList<Note> scaleNotes) {
        String notesAsText = "";
        for (Note note : scaleNotes) {
            String currentNote = note.getEnharmonicWithLetter(currentLetter);
            if (octaveSpecified) {
                notesAsText += currentNote + " ";
            } else {
                notesAsText += OctaveUtil.removeOctaveSpecifier(currentNote) + " ";
            }
            updateLetter();
        }
        return notesAsText.trim();
    }

    private String scaleToMidi(ArrayList<Note> scaleNotes) {
        String midiValues = "";
        for (Note note : scaleNotes) {
            midiValues += note.getMidi() + " ";
        }
        return midiValues.trim();
    }
}
