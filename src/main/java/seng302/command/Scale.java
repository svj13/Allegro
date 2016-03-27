package seng302.command;

import sun.plugin2.jvm.CircularByteBuffer;

import java.util.ArrayList;

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


    public Scale(String a, String b, String outputType) {
        this.search = a;
        this.type = b;
        this.outputType = outputType;
        this.letters = "ABCDEFG".toCharArray();


    }

    public void execute(Environment env){
        if (Checker.isDoubleFlat(search) || Checker.isDoubleFlat(search)) {
            env.error("Invalid scale: '" + search + ' ' + type + "'.");
        } else {
            if (type.equals("major")) {
                try {
                    if (OctaveUtil.octaveSpecifierFlag(this.search)) {
                        octaveSpecified = true;
                        this.note = Note.lookup(search);
                    } else {
                        octaveSpecified = false;
                        this.note = Note.lookup(OctaveUtil.addDefaultOctave(search));
                    }
                    if (this.outputType.equals("note")) {
                        env.getTranscriptManager().setResult(scaleToString(note.getMajorScale()));
                    } else {
                        // Is midi
                        env.getTranscriptManager().setResult(scaleToMidi(note.getMajorScale()));
                    }
                } catch (Exception e) {
                    env.error("Note is not contained in the MIDI library.");
                }
            } else {
                env.error("Invalid scale type: '" + type + "'.");
            }
        }
    }

    private String scaleToString(ArrayList<Note> scaleNotes){
        String notesAsText = "";
        Note previousNote = null;
        for (Note note:scaleNotes) {
            String noteName;
            if (previousNote != null && previousNote.getNote().charAt(0) == note.getNote().charAt(0)) {
                System.out.println("here");
                noteName = note.flatName();
            } else {
                noteName = note.getNote();
            }

            if (this.octaveSpecified) {
                notesAsText += noteName + " ";
            } else {
                notesAsText += OctaveUtil.removeOctaveSpecifier(note.getNote()) + " ";
            }
            previousNote = note;
        }
        return notesAsText.trim();

    }

    private String scaleToMidi(ArrayList<Note> scaleNotes) {
        String midiValues = "";
        for (Note note:scaleNotes) {
            midiValues += note.getMidi() + " ";
        }
        return midiValues.trim();
    }
}
