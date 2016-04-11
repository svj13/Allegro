package seng302.data;

import java.util.HashMap;

/**
 * A static hashmap of all intervals and associated functions
 */
public class Interval {

    public static HashMap<Integer, String> intervals = new HashMap<Integer, String>() {{
        put(0, "unison");
        put(2, "major second");
        put(4, "major third");
        put(5, "perfect fourth");
        put(7, "perfect fifth");
        put(9, "major sixth");
        put(11, "major seventh");
        put(12, "perfect octave");
    }};
}
