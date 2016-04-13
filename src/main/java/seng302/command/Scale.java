package seng302.command;


import java.util.ArrayList;
import java.util.Collections;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.Checker;
import seng302.utility.OctaveUtil;

/**
 * This command is invoked when the user wants to play or list the notes of a scale.
 */
public class Scale implements Command {

    /**
     * Note to begin the scale on.
     */
    String startNote;

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
     * The note the scale begins on.
     */
    private Note note;

    /**
     * The letter the current note should be. This is used to find the correct enharmonic of a
     * note.
     */
    private char currentLetter;

    /**
     * Used to specify which direction to play a scale. Can be up, updown or down.
     */
    private String direction;

    /**
     * The number of octaves to be played.
     */
    private int octaves;


    /**
     * This constructor does not specify a direction so it defaults to 'up'.
     *
     * @param a          The startNote.
     * @param b          The type of scale.
     * @param outputType The way to output the scale.
     */
    public Scale(String a, String b, String outputType) {
        this.startNote = a;
        this.type = b;
        this.outputType = outputType;
        currentLetter = Character.toUpperCase(startNote.charAt(0));
        direction = "up";
        octaves = 1;

    }

    /**
     * This constructor specifies the direction to play the scale.
     * @param a The start Note.
     * @param b The scale type.
     * @param outputType The way the scale should be outputted.
     * @param direction The direction to play the scale.
     */
    public Scale(String a, String b, String outputType, String direction) {
        this.startNote = a;
        this.type = b;
        this.outputType = outputType;
        currentLetter = Character.toUpperCase(startNote.charAt(0));
        this.direction = direction;
        octaves = 1;
    }

    /**
     * A constructor that takes in the number of octaves to play.
     * @param a The start note.
     * @param b The scale type.
     * @param outputType The way the scale is outputted.
     * @param direction The direction to play the scale.
     * @param octaves The number of octaves of the scale to play.
     */
    public Scale(String a, String b, String outputType, String direction, int octaves) {
        this.startNote = a;
        this.type = b;
        this.outputType = outputType;
        currentLetter = Character.toUpperCase(startNote.charAt(0));
        this.direction = direction;
        this.octaves = octaves;
    }

    /**
     * Moves the current letter to the next letter. If the letter is G,
     * the next letter will be A. This method is used to ensure one of each letter name
     * is in each scale.
     */
    private void updateLetter(boolean switchBack) {
        int index = "ABCDEFG".indexOf(currentLetter);
        if (switchBack) {
            if (index - 1 < 0) {
                index = 7;
            }
            currentLetter = "ABCDEFG".charAt(index - 1);
        } else {
            if (index + 1 > 6) {
                index = -1;
            }
            currentLetter = "ABCDEFG".charAt(index + 1);
        }
    }

    /**
     * The command is executed. The beginning note is found and the scale is looked up.
     * The result is outputted or the scale is played.
     * @param env The environment of the program.
     */
    public void execute(Environment env) {
        if (Checker.isDoubleFlat(startNote) || Checker.isDoubleSharp(startNote)) {
            env.error("Invalid scale: '" + startNote + ' ' + type + "'.");
        } else {
            try {
                if (OctaveUtil.octaveSpecifierFlag(this.startNote)) {
                    octaveSpecified = true;
                    this.note = Note.lookup(startNote);
                } else {
                    octaveSpecified = false;
                    this.note = Note.lookup(OctaveUtil.addDefaultOctave(startNote));
                }
                try {
                    ArrayList<Note> scale = note.getScale(type, true);
                    if (direction.equals("down")) {
                        scale = note.getScale(type, false);
                    }
                    if (scale == null) {
                        env.error("This scale goes beyond the MIDI notes available.");
                    } else {
                        if (this.outputType.equals("note")) {
                            env.getTranscriptManager().setResult(scaleToString(scale, true));
                        } else if (this.outputType.equals("midi")) {
                            env.getTranscriptManager().setResult(scaleToMidi(scale));
                        } else { // Play scale.
                            if (direction.equals("updown")) {
                                ArrayList<Note> notes = new ArrayList<Note>(scale);
                                Collections.reverse(notes);
                                scale.addAll(notes);
                                env.getPlayer().playNotes(scale);
                                env.getTranscriptManager().setResult(scaleToStringUpDown(scale));
                            } else if (direction.equals("down")) {
                                env.getPlayer().playNotes(scale);
                                env.getTranscriptManager().setResult(scaleToString(scale, false));
                            } else if (direction.equals("up")) {
                                env.getPlayer().playNotes(scale);
                                env.getTranscriptManager().setResult(scaleToString(scale, true));
                            } else {
                                env.error("'" + direction + "' is not a valid scale direction. Try 'up', 'updown' or 'down'.");
                            }
                        }
                    }
                } catch (IllegalArgumentException i) {
                    env.error(i.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                env.error("Note is not contained in the MIDI library.");
            }
        }
    }

    /**
     * Converts an ArrayList of Notes into a String of note names.
     * @param scaleNotes The notes to display.
     * @return The note names as a String.
     */
    private String scaleToString(ArrayList<Note> scaleNotes, boolean up) {
        String notesAsText = "";
        for (Note note : scaleNotes) {
            String currentNote = note.getEnharmonicWithLetter(currentLetter);
            if (octaveSpecified) {
                notesAsText += currentNote + " ";
            } else {
                notesAsText += OctaveUtil.removeOctaveSpecifier(currentNote) + " ";
            }
            if (up) {
                updateLetter(false);
            } else {
                updateLetter(true);
            }
        }

        return notesAsText.trim();
    }

    /**
     * A convenience method put together the string of notes up and the string of notes downs.
     *
     * @param scaleNotes The notes of the scale.
     * @return The string of the scale notes.
     */
    private String scaleToStringUpDown(ArrayList<Note> scaleNotes) {
        String up = scaleToString(new ArrayList<Note>(scaleNotes.subList(0, 8)), true);
        updateLetter(true);
        String down = scaleToString(new ArrayList<Note>(scaleNotes.subList(8, 16)), false);
        return up + " " + down;
    }

    /**
     * Converts an ArrayList of Notes into a String of Midi numbers.
     * @param scaleNotes The notes to display.
     * @return The note Midi numbers as a String.
     */
    private String scaleToMidi(ArrayList<Note> scaleNotes) {
        String midiValues = "";
        for (Note note : scaleNotes) {
            midiValues += note.getMidi() + " ";
        }
        return midiValues.trim();
    }
}
