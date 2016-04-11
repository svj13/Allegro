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
    public static HashMap<Integer, Interval> intervals = new HashMap<Integer, Interval>() {{
        put(0, new Interval(0, "unison"));
        put(1, new Interval(2, "major second"));
        put(2, new Interval(4, "major third"));
        put(3, new Interval(5, "perfect fourth"));
        put(4, new Interval(7, "perfect fifth"));
        put(5, new Interval (9, "major sixth"));
        put(6, new Interval(11, "major seventh"));
        put(7, new Interval(12, "perfect octave"));
    }};

    public String getName(){
        return this.name;
    }

    public int getSemitones() {
        return this.semitones;
    }
}
