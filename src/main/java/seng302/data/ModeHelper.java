package seng302.data;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to store a list of major and minor melodic valueModes, along with their corresponding degree.
 */
public class ModeHelper {


    private static Map valueModes = new HashMap<Integer, String>();
    private static Map keyModes = new HashMap<String, Integer>();
    private static Map modeNoteMap = new HashMap<String, ArrayList>();

    private static Map mmValueModes = new HashMap<Integer, String>();
    private static Map mmKeyModes = new HashMap<String, Integer>();
    private static Map mmModeNoteMap = new HashMap<String, ArrayList>();

    static {

        valueModes.put(1, "ionian");
        valueModes.put(2, "dorian");
        valueModes.put(3, "phrygian");
        valueModes.put(4, "lydian");
        valueModes.put(5, "mixolydian");
        valueModes.put(6, "aeolian");
        valueModes.put(7, "locrian");

        mmValueModes.put(1, "minormajor");
        mmValueModes.put(2, "dorian b2");
        mmValueModes.put(3, "lydian #5");
        mmValueModes.put(4, "lydian dominant");
        mmValueModes.put(5, "mixolydian b6");
        mmValueModes.put(6, "locrian #2");
        mmValueModes.put(7, "altered");

    }

    static {

        keyModes.put("ionian", 1);
        keyModes.put("dorian", 2);
        keyModes.put("phrygian", 3);
        keyModes.put("lydian", 4);
        keyModes.put("mixolydian", 5);
        keyModes.put("aeolian", 6);
        keyModes.put("locrian", 7);

        mmKeyModes.put("minormajor", 1);
        mmKeyModes.put("dorian b2", 2);
        mmKeyModes.put("lydian #5", 3);
        mmKeyModes.put("lydian dominant", 4);
        mmKeyModes.put("mixolydian b6", 5);
        mmKeyModes.put("locrian #2", 6);
        mmKeyModes.put("altered", 7);

    }

    static {

        modeNoteMap.put(1, new ArrayList<>(Arrays.asList("C", "G", "D", "A", "E", "B", "F", "Bb", "Eb", "Ab", "Db", "Gb")));
        modeNoteMap.put(2, new ArrayList<>(Arrays.asList("D", "A", "E", "B", "F#", "C#", "G", "C", "F", "Bb", "Eb", "Ab")));
        modeNoteMap.put(3, new ArrayList<>(Arrays.asList("E", "B", "F#", "C#", "G#", "D#", "A", "D", "G", "C", "F", "Bb")));
        modeNoteMap.put(4, new ArrayList<>(Arrays.asList("F", "C", "G", "D", "A", "E", "Bb", "Eb", "Ab", "Dd", "Gb", "Cb")));
        modeNoteMap.put(5, new ArrayList<>(Arrays.asList("G", "D", "A", "E", "B", "F#", "C", "F", "Bb", "Eb", "Ab", "Db")));
        modeNoteMap.put(6, new ArrayList<>(Arrays.asList("A", "E", "B", "F#", "C#", "G#", "D", "G", "C", "F", "Bb", "EB")));
        modeNoteMap.put(7, new ArrayList<>(Arrays.asList("B", "F#", "C#", "G#", "D#", "A#", "E", "A", "D", "G", "C", "F")));

//        .put(1, new ArrayList<>(Arrays.asList("C", "G", "D", "A", "E", "B", "F", "Bb", "Eb", "Ab", "Db", "Gb")));
//        modeNoteMap.put(2, new ArrayList<>(Arrays.asList("D", "A", "E", "B", "F#", "C#", "G", "C", "F", "Bb", "Eb", "Ab")));
//        modeNoteMap.put(3, new ArrayList<>(Arrays.asList("E", "B", "F#", "C#", "G#", "D#", "A", "D", "G", "C", "F", "Bb")));
//        modeNoteMap.put(4, new ArrayList<>(Arrays.asList("F", "C", "G", "D", "A", "E", "Bb", "Eb", "Ab", "Dd", "Gb", "Cb")));
//        modeNoteMap.put(5, new ArrayList<>(Arrays.asList("G", "D", "A", "E", "B", "F#", "C", "F", "Bb", "Eb", "Ab", "Db")));
//        modeNoteMap.put(6, new ArrayList<>(Arrays.asList("A", "E", "B", "F#", "C#", "G#", "D", "G", "C", "F", "Bb", "EB")));
//        modeNoteMap.put(7, new ArrayList<>(Arrays.asList("B", "F#", "C#", "G#", "D#", "A#", "E", "A", "D", "G", "C", "F")));


    }


    public static Map<Integer, String> getMajorValueModes() {
        return valueModes;
    }

    public static Map<Integer, String> getMelodicMinorValueModes() {
        return mmValueModes;
    }

    public static Map<String, Integer> getKeyModes() {
        return keyModes;
    }

    public static Map<String, ArrayList> getModeNoteMap() {
        return modeNoteMap;
    }


}
