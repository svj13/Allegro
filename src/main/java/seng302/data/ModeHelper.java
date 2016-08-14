package seng302.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jmw280 on 10/08/16.
 */
public class ModeHelper {


    private static Map modes = new HashMap<Integer, String>();

    static {

        modes.put(1, "ionian");
        modes.put(2, "dorian");
        modes.put(3, "phrygian");
        modes.put(4, "lydian");
        modes.put(5, "mixolydian");
        modes.put(6, "aeolian");
        modes.put(7, "locrian");

    }

    public static Map<Integer, String> getModes() {
        return modes;
    }


}
