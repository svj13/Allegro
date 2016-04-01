package seng302.data;

/**
 * Created by jat157 on 1/04/16.
 */
public class MidiNotePair {

    private final String key;
    private final String value;
    public MidiNotePair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getMidi()   {    return key;    }

    public String toString() {    return key + " : " + value;  }
}
