package seng302.data;

import java.util.HashMap;

/**
 * A static hashmap of all intervals and associated functions
 */
public class Interval {

    private int semitones;
    private String name;

    protected Interval(int semitones, String name) {
        this.semitones = semitones;
        this.name = name;
    }

    // Populates a hashmap of intervals.
    public static HashMap<String, Interval> intervals = new HashMap<String, Interval>() {{
        put("unison", new Interval(0, "unison"));
        put("major second", new Interval(2, "major second"));
        put("major third", new Interval(4, "major third"));
        put("perfect fourth", new Interval(5, "perfect fourth"));
        put("perfect fifth", new Interval(7, "perfect fifth"));
        put("major sixth", new Interval (9, "major sixth"));
        put("major seventh", new Interval(11, "major seventh"));
        put("perfect octave", new Interval(12, "perfect octave"));
    }};

}
