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
    private String alternateName;

    /**
     * Constructs a new Interval object.
     * @param semitones The number of semitones added to an original note
     * @param name The lexical interval name
     */
    protected Interval(int semitones, String name, String alternateName) {
        this.semitones = semitones;
        this.name = name;
        this.alternateName = alternateName;
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
            new Interval(0, "unison", null),
            new Interval(2, "major second", "major 2nd"),
            new Interval(4, "major third", "major 3rd"),
            new Interval(5, "perfect fourth", "perfect 4th"),
            new Interval(7, "perfect fifth", "perfect 5th"),
            new Interval(9, "major sixth", "major 6th"),
            new Interval(11, "major seventh", "major 7th"),
            new Interval(12, "perfect octave", null)
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
     * Gets the 'alternate' name of an interval
     * @return Secondary accepted name of an interval
     */
    public String getAlternateName(){
        return this.alternateName;
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
            if (name != null &&(interval.getName().equals(name) || interval.getAlternateName().equals(name))) {
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
