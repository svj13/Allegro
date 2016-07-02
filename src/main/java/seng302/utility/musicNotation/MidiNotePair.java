package seng302.utility.musicNotation;

/**
 * Created by jat157 on 1/04/16.
 */
public class MidiNotePair {

    private final int key;
    private final String value;

    public MidiNotePair(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getMidi() {
        return key;
    }

    public String toString() {
        return key + " : " + value;
    }
}
