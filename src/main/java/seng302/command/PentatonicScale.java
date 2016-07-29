package seng302.command;

import java.util.ArrayList;
import java.util.HashMap;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.musicNotation.Checker;
import seng302.utility.musicNotation.OctaveUtil;

/**
 * Created by dominicjarvis on 29/07/16.
 */
public class PentatonicScale extends Scale {

    /**
     * Note to begin the scale on.
     */
    String startNote;

    /**
     * Type of scale. e.g major, minor, melodic minor
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

    public PentatonicScale(HashMap<String, String> scale, String outputType) {
        super(scale, outputType);
    }

    public PentatonicScale(String a, String b, String outputType) {
        super(a, b, outputType);
    }

    public PentatonicScale(String a, String b, String outputType, String direction) {
        super(a, b, outputType, direction);
    }

    public PentatonicScale(String a, String b, String outputType, String direction, String octaves) {
        super(a, b, outputType, direction, octaves);
    }

    @Override
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
                    ArrayList<Note> scale = super.getScale(direction);


                    if (scale == null) {
                        env.error("This scale goes beyond the MIDI notes available.");
                    } else {
                        if (this.outputType.equals("note")) {
                            env.getTranscriptManager().setResult(super.scaleToString(scale, true));
                        } else if (this.outputType.equals("midi")) {
                            env.getTranscriptManager().setResult(super.scaleToMidi(scale));
                        } else { // Play scale.

                            if (direction.equals("updown")) {
                                env.getPlayer().playNotes(scale);
                                env.getTranscriptManager().setResult(super.scaleToStringUpDown(scale));
                            } else if (direction.equals("down")) {
                                env.getPlayer().playNotes(scale);
                                env.getTranscriptManager().setResult(super.scaleToString(scale, false));
                            } else if (direction.equals("up")) {
                                env.getPlayer().playNotes(scale);
                                env.getTranscriptManager().setResult(super.scaleToString(scale, true));
                            } else {
                                env.error("'" + direction + "' is not a valid scale direction. Try 'up', 'updown' or 'down'.");
                            }
                        }
                    }
                } catch (IllegalArgumentException i) {
                    env.error(i.getMessage());
                }
            } catch (Exception e) {
                env.error("This scale goes beyond the MIDI notes available.");
            }
        }
    }
}
