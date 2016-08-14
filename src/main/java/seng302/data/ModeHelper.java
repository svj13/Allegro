package seng302.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to store a list of major valueModes, along with their corresponding degree.
 */
public class ModeHelper {


    private static Map valueModes = new HashMap<Integer, String>();
    private static Map keyModes = new HashMap<String, Integer>();

    static {

        valueModes.put(1, "ionian");
        valueModes.put(2, "dorian");
        valueModes.put(3, "phrygian");
        valueModes.put(4, "lydian");
        valueModes.put(5, "mixolydian");
        valueModes.put(6, "aeolian");
        valueModes.put(7, "locrian");

    }

    static {

        keyModes.put("ionian", 1);
        keyModes.put("dorian", 2);
        keyModes.put("phrygian", 3);
        keyModes.put("lydian", 4);
        keyModes.put("mixolydian", 5);
        keyModes.put("aeolian", 6);
        keyModes.put("locrian", 7);

    }




    public static Map<Integer, String> getValueModes() {
        return valueModes;
    }
    public static Map<String, Integer> getkeyModes() {
        return keyModes;
    }


}
