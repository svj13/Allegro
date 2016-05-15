package seng302.data;

import java.util.ArrayList;
import java.util.Collection;
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
            new Interval(1, "minor second"),
            new Interval(2, "major second"),
            new Interval(3, "minor third"),
            new Interval(4, "major third"),
            new Interval(5, "perfect fourth"),
            new Interval(6, "augmented fourth"),
            new Interval(6, "diminished fifth"),
            new Interval(7, "perfect fifth"),
            new Interval(8, "minor sixth"),
            new Interval(9, "major sixth"),
            new Interval(9, "diminished seventh"),
            new Interval(10, "minor seventh"),
            new Interval(11, "major seventh"),

            new Interval(12, "perfect octave"),
            new Interval(13, "minor ninth"),
            new Interval(14, "major ninth"),
            new Interval(15, "minor tenth"),
            new Interval(16, "major tenth"),
            new Interval(17, "perfect eleventh"),
            new Interval(18, "augmented eleventh"),
            new Interval(18, "diminished twelfth"),
            new Interval(19, "perfect twelfth"),
            new Interval(20, "minor thirteenth"),
            new Interval(21, "major thirteenth"),
            new Interval(21, "diminished fourteenth"),
            new Interval(22, "minor fourteenth"),
            new Interval(23, "major fourteenth"),
            new Interval(24, "double octave"),
 /////////////will this break stuff?

    };

    public static Set<Integer> acceptedSemitones;

    static {
        acceptedSemitones = new HashSet<Integer>();
        acceptedSemitones.add(0);
        acceptedSemitones.add(1);
        acceptedSemitones.add(2);
        acceptedSemitones.add(3);
        acceptedSemitones.add(4);
        acceptedSemitones.add(5);
        acceptedSemitones.add(6);
        acceptedSemitones.add(7);
        acceptedSemitones.add(8);
        acceptedSemitones.add(9);
        acceptedSemitones.add(10);
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

    /**
     * Finds the enharmonic equivalents for a given interval by searching for intervals with the
     * same number of semitones but a different name
     * @param semitones the number of semitones in the interval
     * @param intervalName the name of the interval to be compared with
     * @return an arrayList of the enharmonic intervals
     */
    public static ArrayList<String> findEnharmonics(int semitones, String intervalName) {
        ArrayList enharmonics = new ArrayList<String>();
        for (Interval interval:intervals) {
            if (interval.getSemitones() == semitones && (!interval.getName().equals(intervalName))) {
                enharmonics.add(interval.getName());
            }
        }
        return enharmonics;
    }

}
