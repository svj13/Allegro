package seng302.data;

import java.util.HashSet;
import java.util.Set;

/**
 * This class works similarly to the Note class.
 * Contains a static hash map of all the intervals, indexed from 0-7 for random selection.
 * Also contains getters for the individual intervals.
 */
public class Interval {

    private int semitones;
    private String name;

    /**
     * Constructs a new Interval object.
     * @param semitones The number of semitones added to an original note
     * @param name The lexical interval name
     */
    protected Interval(int semitones, String name) {
        this.semitones = semitones;
        this.name = name;
    }

    // Creates a hash map of all currently accepted intervals.
//    public static HashMap<Integer, Interval> intervals = new HashMap<Integer, Interval>() {{
//        put(0, new Interval(0, "unison"));
//        put(1, new Interval(2, "major second"));
//        put(2, new Interval(4, "major third"));
//        put(3, new Interval(5, "perfect fourth"));
//        put(4, new Interval(7, "perfect fifth"));
//        put(5, new Interval (9, "major sixth"));
//        put(6, new Interval(11, "major seventh"));
//        put(7, new Interval(12, "perfect octave"));
//    }};

    public static Interval[] intervals = {
            new Interval(0, "unison"),
            new Interval(2, "major second"),
            new Interval(4, "major third"),
            new Interval(5, "perfect fourth"),
            new Interval(7, "perfect fifth"),
            new Interval(9, "major sixth"),
            new Interval(11, "major seventh"),
            new Interval(12, "perfect octave")
    };

    public static Set<Integer> acceptedSemitones;

    static {
        acceptedSemitones = new HashSet<Integer>();
        acceptedSemitones.add(0);
        acceptedSemitones.add(2);
        acceptedSemitones.add(4);
        acceptedSemitones.add(5);
        acceptedSemitones.add(7);
        acceptedSemitones.add(9);
        acceptedSemitones.add(11);
        acceptedSemitones.add(12);
    }



    /**
     * Gets the lexical name of an interval.
     * @return Name of an interval
     */
    public String getName(){
        return this.name;
    }

    /**
     * Gets the number of semitones an interval represents.
     * @return Number of semitones represented by an interval
     */
    public int getSemitones() {
        return this.semitones;
    }

    public static Interval lookupByName(String name) {
        for (Interval interval:intervals) {
            if (interval.getName().equals(name)) {
                return interval;
            }
        }
        return null;
    }

    public static Interval lookupBySemitones(int semitones) {
        for (Interval interval:intervals) {
            if (interval.getSemitones() == semitones) {
                return interval;
            }
        }
        return null;
    }

}
