package seng302.command;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

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


    public Scale(String a, String b, String outputType) {
        this.search = a;
        this.type = b;
        this.outputType = outputType;
        this.letters = "ABCDEFG".toCharArray();
        currentLetter = Character.toUpperCase(search.charAt(0));


    }

    public Scale(String a, String b, String outputType, String scaleType) {
        this.search = a;
        this.type = b;
        this.outputType = outputType;
        this.letters = "ABCDEFG".toCharArray();
        currentLetter = Character.toUpperCase(search.charAt(0));
        this.direction = scaleType;
    }

    private void updateLetter() {
        int index = "ABCDEFG".indexOf(currentLetter);
        if (index + 1 > 6) {
            index = -1;
        }
        currentLetter = "ABCDEFG".charAt(index + 1);
    }

    public void execute(Environment env) {
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
                if (this.outputType.equals("note")) {
                    env.getTranscriptManager().setResult(scaleToString(note.getScale(type)));
                } else if (this.outputType.equals("midi")) {
                    // Is midi
                    env.getTranscriptManager().setResult(scaleToMidi(note.getScale(type)));
                } else {
                    // Is play
                    ArrayList<Note> notesToPlay = note.getScale(type);
                    // We need to alter the notes if it's down or up/down
                    if (this.direction.equals("down")) {
                        Collections.reverse(notesToPlay);
                    }
                    if (this.direction.equals("updown")) {
                        ArrayList<Note> reversedNotes = new ArrayList<Note>(notesToPlay);
                        Collections.reverse(reversedNotes);
                        notesToPlay.addAll(reversedNotes);
                    }
                    //Problem here: if notesToPlay is updown, the program can't ID what the notes should look like
                    //env.getTranscriptManager().setResult(scaleToString(notesToPlay));
                    int duration = env.getTempo() / 60;
                    for (Note note : notesToPlay) {
                        // Play each note for the duration
                        note.playNote(env.getTempo());
                    }
                }
            } catch (Exception e) {
                env.error("Note is not contained in the MIDI library.");
                System.out.println(e);
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
