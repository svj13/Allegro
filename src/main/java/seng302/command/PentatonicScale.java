//package seng302.command;
//
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//
//import seng302.Environment;
//import seng302.data.Note;
//import seng302.utility.musicNotation.Checker;
//import seng302.utility.musicNotation.OctaveUtil;
//
///**
// * Created by dominicjarvis on 29/07/16.
// */
//public class PentatonicScale extends Scale {
//
//    /**
//     * Note to begin the scale on.
//     */
//    String startNote;
//
//    /**
//     * Type of scale. e.g major, minor, melodic minor
//     */
//    String type;
//
//    /**
//     * Way to output scale. e.g note, midi or play
//     */
//    String outputType;
//
//    /**
//     * Indicates whether an octave was specified in the original command. This decides whether
//     * octaves will be shown in the output.
//     */
//    private boolean octaveSpecified;
//
//    /**
//     * The note the scale begins on.
//     */
//    private Note note;
//
//    /**
//     * The letter the current note should be. This is used to find the correct enharmonic of a
//     * note.
//     */
//    private char currentLetter;
//
//    /**
//     * Used to specify which direction to play a scale. Can be up, updown or down.
//     */
//    private String direction;
//
//    /**
//     * The number of octaves to be played.
//     */
//    private int octaves;
//
//    public PentatonicScale(HashMap<String, String> scale, String outputType) {
//        super(scale, outputType);
//        System.out.println(this.startNote);
//    }
//
//    public PentatonicScale(String a, String b, String outputType) {
//        super(a, b, outputType);
//    }
//
//    public PentatonicScale(String a, String b, String outputType, String direction) {
//        super(a, b, outputType, direction);
//    }
//
//    public PentatonicScale(String a, String b, String outputType, String direction, String octaves) {
//        super(a, b, outputType, direction, octaves);
//    }
//
//    /**
//     * Moves the current letter to the next letter. If the letter is G, the next letter will be A.
//     * This method is used to ensure one of each letter name is in each scale.
//     */
//    private static char updateLetter(char currentLetter, boolean backDown) {
//        int index = "ABCDEFG".indexOf(currentLetter);
//        if (backDown) {
//            if (index - 1 < 0) {
//                index = 7;
//            }
//            currentLetter = "ABCDEFG".charAt(index - 1);
//        } else {
//            if (index + 1 > 6) {
//                index = -1;
//            }
//            currentLetter = "ABCDEFG".charAt(index + 1);
//        }
//        return currentLetter;
//    }
//
//    private ArrayList<Note> getPentatonicScaleNotes(ArrayList<Note> standardScale) {
//        ArrayList<Note> pentScale = null;
//        if (type.equals("major")) {
//            pentScale = new ArrayList<Note>();
//            pentScale.add(standardScale.get(1));
//            pentScale.add(standardScale.get(2));
//            pentScale.add(standardScale.get(3));
//            pentScale.add(standardScale.get(5));
//            pentScale.add(standardScale.get(6));
//            pentScale.add(standardScale.get(1));
//        }
//        else if (type.equals("minor")) {
//            pentScale = new ArrayList<Note>();
//            pentScale.add(standardScale.get(1));
//            pentScale.add(standardScale.get(3));
//            pentScale.add(standardScale.get(4));
//            pentScale.add(standardScale.get(5));
//            pentScale.add(standardScale.get(7));
//            pentScale.add(standardScale.get(1));
//        }
//        return pentScale;
//    }
//
//
//    private ArrayList<Note> getScale(String direction) {
//        ArrayList<Note> scale = getPentatonicScaleNotes(note.getOctaveScale(type, octaves, true));
//
//        if (direction.equals("down")) {
//            scale = getPentatonicScaleNotes(note.getOctaveScale(type, octaves, false));
//        } else if (direction.equals("updown")) {
//            ArrayList<Note> notes = new ArrayList<Note>(scale);
//            Collections.reverse(notes);
//            scale.addAll(notes);
//        }
//        return scale;
//    }
//
//    /**
//     * The command is executed. The beginning note is found and the scale is looked up. The result
//     * is outputted or the scale is played.
//     *
//     * @param env The environment of the program.
//     */
//    public void execute(Environment env) {
//        if (Checker.isDoubleFlat(startNote) || Checker.isDoubleSharp(startNote)) {
//            env.error("Invalid scale: '" + startNote + ' ' + type + "'.");
//        } else {
//            this.note = Note.lookup(OctaveUtil.addDefaultOctave(startNote));
//            try {
//                ArrayList<Note> scale = getScale(direction);
//                if (scale == null) {
//                    env.error("This scale goes beyond the MIDI notes available.");
//                } else {
//                    if (this.outputType.equals("note")) {
//                        env.getTranscriptManager().setResult(scaleToString(startNote, scale, true));
//                    } else if (this.outputType.equals("midi")) {
//                        env.getTranscriptManager().setResult(scaleToMidi(scale));
//                    } else { // Play scale.
//
//                        if (direction.equals("updown")) {
//                            env.getPlayer().playNotes(scale);
//                            env.getTranscriptManager().setResult(scaleToStringUpDown(startNote, scale));
//                        } else if (direction.equals("down")) {
//                            env.getPlayer().playNotes(scale);
//                            env.getTranscriptManager().setResult(scaleToString(startNote, scale, false));
//                        } else if (direction.equals("up")) {
//                            env.getPlayer().playNotes(scale);
//                            env.getTranscriptManager().setResult(scaleToString(startNote, scale, true));
//                        } else {
//                            env.error("'" + direction + "' is not a valid scale direction. Try 'up', 'updown' or 'down'.");
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                env.error("This scale goes beyond the MIDI notes available.");
//            }
//        }
//
//    }
//
//    /**
//     * Converts an ArrayList of notes into an ArrayList of Strings where the strings are the correct
//     * enharmonic name for the each name.
//     *
//     * @param startNote  The note the scale begins on.
//     * @param scaleNotes An ArrayList containing the notes of the scale.
//     * @param up         Whether the scale is going up or down.
//     * @return Arraylist of correct note names.
//     */
//    public static ArrayList<String> scaleNameList(String startNote, ArrayList<Note> scaleNotes, boolean up) {
//        ArrayList<String> scale = new ArrayList<>();
//        char currentLetter = Character.toUpperCase(startNote.charAt(0));
//        for (Note note : scaleNotes) {
//            String currentNote = note.getEnharmonicWithLetter(currentLetter);
//            if (OctaveUtil.octaveSpecifierFlag(startNote)) {
//                scale.add(currentNote);
//            } else {
//                scale.add(OctaveUtil.removeOctaveSpecifier(currentNote));
//            }
//            if (up) {
//                currentLetter = updateLetter(currentLetter, false);
//            } else {
//                currentLetter = updateLetter(currentLetter, true);
//            }
//        }
//        return scale;
//    }
//
//    /**
//     * Converts an ArrayList of Notes into a String of note names.
//     *
//     * @param startNote The note the scale begins on.
//     * @param scaleNotes The notes to display.
//     * @param up Whether the scale is ascending or descending.
//     * @return The note names as a String.
//     */
//    private static String scaleToString(String startNote, ArrayList<Note> scaleNotes, boolean up) {
//        ArrayList<String> scale = scaleNameList(startNote, scaleNotes, up);
//        return String.join(" ", scale);
//    }
//
//    /**
//     * A convenience method put together the string of notes up and the string of notes downs.
//     *
//     * @param scaleNotes The notes of the scale.
//     * @return The string of the scale notes.
//     */
//    private static String scaleToStringUpDown(String startNote, ArrayList<Note> scaleNotes) {
//        int size = scaleNotes.size();
//        String up = scaleToString(startNote, new ArrayList<Note>(scaleNotes.subList(0, size / 2)), true);
//        updateLetter(Character.toUpperCase(startNote.charAt(0)), true);
//        String down = scaleToString(startNote, new ArrayList<Note>(scaleNotes.subList(size / 2, size)), false);
//        return up + " " + down;
//    }
//
//    /**
//     * Converts an ArrayList of Notes into a String of Midi numbers.
//     *
//     * @param scaleNotes The notes to display.
//     * @return The note Midi numbers as a String.
//     */
//    private String scaleToMidi(ArrayList<Note> scaleNotes) {
//        String midiValues = "";
//        for (Note note : scaleNotes) {
//            midiValues += note.getMidi() + " ";
//        }
//        return midiValues.trim();
//    }
//
//    public long getLength(Environment env) {
//        long milliseconds = 0;
//
//        if (outputType.equals("play")) {
//            ArrayList<Note> scale = getScale(direction);
//            int tempo = env.getPlayer().getTempo();
//            long crotchetLength = 60000 / tempo;
//            milliseconds = scale.size() * crotchetLength;
//        }
//
//        return milliseconds;
//    }
//
//}
