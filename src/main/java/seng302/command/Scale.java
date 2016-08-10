package seng302.command;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.musicNotation.Checker;
import seng302.utility.musicNotation.OctaveUtil;

/**
 * This command is invoked when the user wants to play or list the notes of a scale.
 */
public class Scale implements Command {

    /**
     * Note to begin the scale on.
     */
    String startNote;

    /**
     * Type of scale. e.g major, minor, melodic minor
     */
    private static String type;

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
     *
     */
    private Boolean pentatonic = false;

    public Scale(HashMap<String, String> scale, String outputType) {
        this.startNote = scale.get("note");
        this.type = scale.get("scale_type");
        this.outputType = outputType;
        currentLetter = Character.toUpperCase(startNote.charAt(0));
        if (scale.get("direction") != null) {
            this.direction = scale.get("direction");
        } else {
            direction = "up";
        }
        if (scale.get("octaves") != null) {
            this.octaves = Integer.valueOf(scale.get("octaves"));
        } else {
            this.octaves = 1;
        }
        pentatonicCheck();
    }

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
        pentatonicCheck();
    }

    /**
     * This constructor specifies the direction to play the scale.
     *
     * @param a          The start Note.
     * @param b          The scale type.
     * @param outputType The way the scale should be outputted.
     * @param direction  The direction to play the scale.
     */
    public Scale(String a, String b, String outputType, String direction) {
        this(a, b, outputType);
        this.direction = direction;
        octaves = 1;
        pentatonicCheck();
    }

    /**
     * A constructor that takes in the number of octaves to play.
     *
     * @param a          The start note.
     * @param b          The scale type.
     * @param outputType The way the scale is outputted.
     * @param direction  The direction to play the scale.
     * @param octaves    The number of octaves of the scale to play.
     */
    public Scale(String a, String b, String outputType, String direction, String octaves) {
        this(a, b, outputType, direction);
        this.octaves = Integer.parseInt(octaves);
        pentatonicCheck();
    }

    /**
     * Moves the current letter to the next letter. If the letter is G, the next letter will be A.
     * This method is used to ensure one of each letter name is in each scale.
     * @param currentLetter The current letter.
     * @param backDown Whether to iterate through the letters forwards or backwards.
     * @return The new letter.
     */
    private static char updateLetter(char currentLetter, boolean backDown) {
        int index = "ABCDEFG".indexOf(currentLetter);
        if (backDown) {
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
        return currentLetter;
    }

    /**
     * This methods gets a scale in a particular direction. For updown it will get the scale in each
     * direction and then combine them.
     *
     * @param direction the direction of the scale (up, down, updown)
     * @return An arraylist of notes of the scale in the correct direction.
     */
    private ArrayList<Note> getScale(String direction) {
        ArrayList<Note> scale = note.getOctaveScale(type, octaves, true);

        if (direction.equals("down")) {
            scale = note.getOctaveScale(type, octaves, false);
        } else if (direction.equals("updown")) {
            ArrayList<Note> notes = new ArrayList<Note>(scale);
            Collections.reverse(notes);
            scale.addAll(notes);
        }
        return scale;
    }

    /**
     * The command is executed. The beginning note is found and the scale is looked up. The result
     * is outputted or the scale is played.
     *
     * @param env The environment of the program.
     */
    public void execute(Environment env) {
        if (Checker.isDoubleFlat(startNote) || Checker.isDoubleSharp(startNote)) {
            env.error("Invalid scale: '" + startNote + ' ' + type + "'.");
        } else {
            this.note = Note.lookup(OctaveUtil.addDefaultOctave(startNote));
            try {
                ArrayList<Note> scale = getScale(direction);
                if (scale == null) {
                    env.error("This scale goes beyond the MIDI notes available.");
                } else {
                    if (this.outputType.equals("note")) {
                        String stringOfScale = scaleToString(startNote, scale, true);
                        if (pentatonic) {
                            stringOfScale = scaleToPentatonicString(stringOfScale);
                            if (type.equals("major") || type.equals("minor")) {
                                env.getTranscriptManager().setResult(stringOfScale);
                            } else {
                                env.error((type.substring(0, 1).toUpperCase().concat(type.substring(1))).concat(" pentatonic scales are not currently supported."));
                            }
                        } else {
                            env.getTranscriptManager().setResult(stringOfScale);
                        }
                    } else if (this.outputType.equals("midi")) {
                        String midiScaleString = scaleToMidi(scale);
                        if (pentatonic) {
                            midiScaleString = scaleToPentatonicString(midiScaleString);
                            if (type.equals("major") || type.equals("minor")) {
                                env.getTranscriptManager().setResult(midiScaleString);
                            } else {
                                env.error((type.substring(0, 1).toUpperCase().concat(type.substring(1))).concat(" pentatonic midi scales are not currently supported."));
                            }
                        } else {
                            env.getTranscriptManager().setResult(midiScaleString);
                        }
                    } else { // Play scale.
                        if (pentatonic) {
                            if (type.equals("major") || type.equals("minor")) {
                                if (direction.equals("updown")) {
                                    env.getTranscriptManager().setResult(scaleToPentatonicString(scaleToStringUpDown(startNote, scale)));
                                    scale = scaleToPentatonic(scale);
                                    env.getPlayer().playNotes(scale);
                                } else if (direction.equals("down")) {
                                    env.getTranscriptManager().setResult(scaleToPentatonicString(scaleToString(startNote, scale, false)));
                                    scale = scaleToPentatonic(scale);
                                    env.getPlayer().playNotes(scale);
                                } else if (direction.equals("up")) {
                                    env.getTranscriptManager().setResult(scaleToPentatonicString(scaleToString(startNote, scale, true)));
                                    scale = scaleToPentatonic(scale);
                                    env.getPlayer().playNotes(scale);
                                } else {
                                    env.error("'" + direction + "' is not a valid scale direction. Try 'up', 'updown' or 'down'.");
                                }
                            } else {
                                env.error((type.substring(0, 1).toUpperCase().concat(type.substring(1))).concat(" pentatonic scales are not currently supported."));
                            }
                        } else {
                            if (direction.equals("updown")) {
                                env.getPlayer().playNotes(scale);
                                env.getTranscriptManager().setResult(scaleToStringUpDown(startNote, scale));
                            } else if (direction.equals("down")) {
                                env.getPlayer().playNotes(scale);
                                env.getTranscriptManager().setResult(scaleToString(startNote, scale, false));
                            } else if (direction.equals("up")) {
                                env.getPlayer().playNotes(scale);
                                env.getTranscriptManager().setResult(scaleToString(startNote, scale, true));
                            } else {
                                env.error("'" + direction + "' is not a valid scale direction. Try 'up', 'updown' or 'down'.");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                env.error("This scale goes beyond the MIDI notes available.");
            }
        }

    }


    /**
     * Converts an ArrayList of notes into an ArrayList of Strings where the strings are the correct
     * enharmonic name for the each name.
     *
     * @param startNote  The note the scale begins on.
     * @param scaleNotes An ArrayList containing the notes of the scale.
     * @param up         Whether the scale is going up or down.
     * @return Arraylist of correct note names.
     */
    public static ArrayList<String> scaleNameList(String startNote, ArrayList<Note> scaleNotes, boolean up) {
        ArrayList<String> scale = new ArrayList<>();
        char currentLetter = Character.toUpperCase(startNote.charAt(0));
        for (Note note : scaleNotes) {
            String currentNote;
            if (!type.equals("major pentatonic") && !type.equals("minor pentatonic")) {
                currentNote = note.getEnharmonicWithLetter(currentLetter);
            } else {
                if (type.equals("major pentatonic")) {
                    currentNote = note.getEnharmonicWithLetter(currentLetter);
                } else {
                    currentNote = note.getEnharmonicWithLetter(currentLetter);
                }
            }
            if (OctaveUtil.octaveSpecifierFlag(startNote)) {
                scale.add(currentNote);
            } else {
                scale.add(OctaveUtil.removeOctaveSpecifier(currentNote));
            }
            if (up) {
                currentLetter = updateLetter(currentLetter, false);
            } else {
                currentLetter = updateLetter(currentLetter, true);
            }
        }
        return scale;
    }

    /**
     * Converts an ArrayList of Notes into a String of note names.
     *
     * @param startNote The note the scale begins on.
     * @param scaleNotes The notes to display.
     * @param up Whether the scale is ascending or descending.
     * @return The note names as a String.
     */
    private static String scaleToString(String startNote, ArrayList<Note> scaleNotes, boolean up) {
        ArrayList<String> scale = scaleNameList(startNote, scaleNotes, up);
        return String.join(" ", scale);
    }

    /**
     * A convenience method put together the string of notes up and the string of notes downs.
     *
     * @param scaleNotes The notes of the scale.
     * @return The string of the scale notes.
     */
    private static String scaleToStringUpDown(String startNote, ArrayList<Note> scaleNotes) {
        int size = scaleNotes.size();
        String up = scaleToString(startNote, new ArrayList<Note>(scaleNotes.subList(0, size / 2)), true);
        updateLetter(Character.toUpperCase(startNote.charAt(0)), true);
        String down = scaleToString(startNote, new ArrayList<Note>(scaleNotes.subList(size / 2, size)), false);
        return up + " " + down;
    }

    /**
     * Converts an ArrayList of Notes into a String of Midi numbers.
     *
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

    /**
     * Converts the given (7 note) scale to a pentatonic scale
     */
    private ArrayList<Note> scaleToPentatonic(ArrayList<Note> scaleNotes) {
        ArrayList<Note> pentatonicScale = new ArrayList<Note>();
        if (type.equals("major")) {
            pentatonicScale.add(scaleNotes.get(0));
            pentatonicScale.add(scaleNotes.get(1));
            pentatonicScale.add(scaleNotes.get(2));
            pentatonicScale.add(scaleNotes.get(4));
            pentatonicScale.add(scaleNotes.get(5));
            pentatonicScale.add(scaleNotes.get(7));
            if (direction.equals("updown")) {
                ArrayList<Note> notes = new ArrayList<Note>(pentatonicScale);
                Collections.reverse(notes);
                pentatonicScale.addAll(notes);
            }
        } else if (type.equals("minor")) {
            pentatonicScale.add(scaleNotes.get(0));
            pentatonicScale.add(scaleNotes.get(2));
            pentatonicScale.add(scaleNotes.get(3));
            pentatonicScale.add(scaleNotes.get(4));
            pentatonicScale.add(scaleNotes.get(6));
            pentatonicScale.add(scaleNotes.get(7));
            if (direction.equals("updown")) {
                ArrayList<Note> notes = new ArrayList<Note>(pentatonicScale);
                Collections.reverse(notes);
                pentatonicScale.addAll(notes);
            }
        }
        return pentatonicScale;
    }

    private String scaleToPentatonicString(String scale) {
        String pentScaleStr = "";
        String[] splitString = scale.split(" ");
        if (type.equals("major")) {
            pentScaleStr += splitString[0] + " ";
            pentScaleStr += splitString[1] + " ";
            pentScaleStr += splitString[2] + " ";
            pentScaleStr += splitString[4] + " ";
            pentScaleStr += splitString[5] + " ";
            pentScaleStr += splitString[7];
        } else if (type.equals("minor")) {
            pentScaleStr += splitString[0] + " ";
            pentScaleStr += splitString[2] + " ";
            pentScaleStr += splitString[3] + " ";
            pentScaleStr += splitString[4] + " ";
            pentScaleStr += splitString[6] + " ";
            pentScaleStr += splitString[7];
        }
        if (direction.equals("updown")) {
            String reversedScale = (new StringBuilder().append(pentScaleStr).reverse()).toString();
            pentScaleStr += " " + reversedScale;
        }

        return pentScaleStr;
    }


    /**
     * Checks to see if the output type of a pentatonic scale
     */
    private void pentatonicCheck() {
        if (this.outputType.equals("pentatonic_note")) {
            this.outputType = "note";
            this.pentatonic = true;
        } else if (this.outputType.equals("pentatonic_midi")) {
            this.outputType = "midi";
            this.pentatonic = true;
        } else if (this.outputType.equals("pentatonic_play")) {
            this.outputType = "play";
            this.pentatonic = true;
        }
    }


    public long getLength(Environment env) {
        long milliseconds = 0;

        if (outputType.equals("play")) {
            ArrayList<Note> scale = getScale(direction);
            int tempo = env.getPlayer().getTempo();
            long crotchetLength = 60000 / tempo;
            milliseconds = scale.size() * crotchetLength;
        }

        return milliseconds;
    }

    public String getHelp() {
        switch (outputType) {
            case "note":
                return "When followed by a valid scale (made up of a note and a scale type)" +
                        " the corresponding scale notes will be displayed.";
            case "play":
                return "When followed by a valid scale (made up of a note and a scale type)" +
                        " the corresponding scale will be played. The number of" +
                        " octaves and direction may optionally be given.";

            case "midi":
                return "When followed by a valid scale (made up of a note and a scale type) " +
                        "the corresponding scale midi notes will be displayed. ";

        }
        return null;
    }

    public List<String> getParams() {
        List<String> params = new ArrayList<>();
        params.add("note");
        params.add("type");
        return params;
    }

    public List<String> getOptions() {
        List<String> options = new ArrayList<>();
        if (outputType.equals("play")) {
            options.add("octaves");
            options.add("up|down|updown");
        }

        return options;
    }

    @Override
    public String getCommandText() {
        switch (outputType) {
            case "note":
                return "scale";
            case "play":
                return "play scale";

            case "midi":
                return "midi scale";

        }
        return null;
    }

    @Override
    public String getExample() {
        switch (outputType) {
            case "note":
                return "scale A minor";
            case "play":
                return "play scale C major updown";

            case "midi":
                return "midi scale A minor";

        }
        return null;
    }
}
