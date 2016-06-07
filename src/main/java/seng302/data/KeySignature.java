package seng302.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by emily on 18/05/16.
 */
public class KeySignature {
    private String startNote;

    private List<String> notesInSig;

    private int numSharps;

    private int numFlats;

    public static HashMap<String, KeySignature> minorKeySignatures = generateMinorKeySignatures();

    public static HashMap<String, KeySignature> majorKeySignatures = generateMajorKeySignatures();


    /**
     * An object of type key signature
     * @param note The note that the key signature belongs to
     * @param notesInSig The notes that are in the given key signature
     */
    protected KeySignature(String note, List<String> notesInSig) {
        this.startNote = note;
        this.notesInSig = notesInSig;
        this.numFlats = 0;
        this.numSharps = 0;
        for (Object item:notesInSig) {
            if (item.toString().contains("#")) {
                this.numSharps += 1;
            }
            if (item.toString().contains("b")) {
                this.numFlats += 1;
            }

        }
    }

    public String getStartNote() {
        return this.startNote;
    }

    public List<String> getNotes() {
        return this.notesInSig;
    }

    public int getNumberOfSharps() {
        return this.numSharps;
    }

    public int getNumberOfFlats() {
        return this.numFlats;
    }

    /**
     * Generates the key signatures of all accepted minor scales
     * @return a hash map of key signatures
     */
    private static HashMap<String, KeySignature> generateMinorKeySignatures() {
        HashMap<String,KeySignature>minorKeySignatures = new HashMap<String, KeySignature>();

        minorKeySignatures.put("A", new KeySignature("A", Arrays.asList("")));
        minorKeySignatures.put("E", new KeySignature("E", Arrays.asList("F#")));
        minorKeySignatures.put("B", new KeySignature("B", Arrays.asList("F#","C#")));
        minorKeySignatures.put("F#", new KeySignature("F#", Arrays.asList("F#","C#","G#")));
        minorKeySignatures.put("C#", new KeySignature("C#", Arrays.asList("F#","C#","G#","D#")));
        minorKeySignatures.put("G#", new KeySignature("G#", Arrays.asList("F#","C#","G#","D#","A#")));
        minorKeySignatures.put("D#", new KeySignature("D#", Arrays.asList("F#","C#","G#","D#","A#","E#")));
        minorKeySignatures.put("A#", new KeySignature("A#", Arrays.asList("F#","C#","G#","D#","A#","E#","B#")));

        minorKeySignatures.put("Ab", new KeySignature("Ab", Arrays.asList("Bb","Eb","Ab","Db","Gb","Cb","Fb")));
        minorKeySignatures.put("Eb", new KeySignature("Eb", Arrays.asList("Bb","Eb","Ab","Db","Gb","Cb")));
        minorKeySignatures.put("Bb", new KeySignature("Bb", Arrays.asList("Bb","Eb","Ab","Db","Gb")));
        minorKeySignatures.put("F", new KeySignature("F", Arrays.asList("Bb","Eb","Ab","Db")));
        minorKeySignatures.put("C", new KeySignature("C", Arrays.asList("Bb","Eb","Ab")));
        minorKeySignatures.put("G", new KeySignature("G", Arrays.asList("Bb","Eb")));
        minorKeySignatures.put("D", new KeySignature("D", Arrays.asList("Bb")));

        return minorKeySignatures;
    }

    public static HashMap<String, KeySignature> getMinorKeySignatures() {
        return minorKeySignatures;
    }

    /**
     * Generates the key signatures of all accepted major scales
     * @return a hash map of key signatures
     */
    private static HashMap<String, KeySignature> generateMajorKeySignatures() {
        HashMap<String,KeySignature>majorKeySignatures = new HashMap<String, KeySignature>();

        majorKeySignatures.put("C", new KeySignature("C", Arrays.asList("")));
        majorKeySignatures.put("G", new KeySignature("G", Arrays.asList("F#")));
        majorKeySignatures.put("D", new KeySignature("D", Arrays.asList("F#","C#")));
        majorKeySignatures.put("A", new KeySignature("A", Arrays.asList("F#","C#","G#")));
        majorKeySignatures.put("E", new KeySignature("E", Arrays.asList("F#","C#","G#","D#")));
        majorKeySignatures.put("B", new KeySignature("B", Arrays.asList("F#","C#","G#","D#","A#")));
        majorKeySignatures.put("F#", new KeySignature("F#", Arrays.asList("F#","C#","G#","D#","A#","E#")));
        majorKeySignatures.put("C#", new KeySignature("C#", Arrays.asList("F#","C#","G#","D#","A#","E#","B#")));



        majorKeySignatures.put("Cb", new KeySignature("Cb", Arrays.asList("Bb","Eb","Ab","Db","Gb","Cb","Fb")));
        majorKeySignatures.put("Gb", new KeySignature("Gb", Arrays.asList("Bb","Eb","Ab","Db","Gb","Cb")));
        majorKeySignatures.put("Db", new KeySignature("Db", Arrays.asList("Bb","Eb","Ab","Db","Gb")));
        majorKeySignatures.put("Ab", new KeySignature("Ab", Arrays.asList("Bb","Eb","Ab","Db")));
        majorKeySignatures.put("Eb", new KeySignature("Eb", Arrays.asList("Bb","Eb","Ab")));
        majorKeySignatures.put("Bb", new KeySignature("Bb", Arrays.asList("Bb","Eb")));
        majorKeySignatures.put("F", new KeySignature("F", Arrays.asList("Bb")));

        return majorKeySignatures;
    }

    public static HashMap<String, KeySignature> getMajorKeySignatures() {
        return majorKeySignatures;
    }
}
