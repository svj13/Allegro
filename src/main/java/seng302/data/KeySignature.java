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
        minorKeySignatures.put("Ab", new KeySignature("Ab", Arrays.asList("Fb","Cb","Gb","Db","Ab","Eb","Bb")));
        minorKeySignatures.put("Eb", new KeySignature("Eb", Arrays.asList("Cb","Gb","Db","Ab","Eb","Bb")));
        minorKeySignatures.put("Bb", new KeySignature("Bb", Arrays.asList("Gb","Db","Ab","Eb","Bb")));
        minorKeySignatures.put("F", new KeySignature("F", Arrays.asList("Db","Ab","Eb","Bb")));
        minorKeySignatures.put("C", new KeySignature("C", Arrays.asList("Ab","Eb","Bb")));
        minorKeySignatures.put("G", new KeySignature("G", Arrays.asList("Eb","Bb")));
        minorKeySignatures.put("D", new KeySignature("D", Arrays.asList("Bb")));

        return minorKeySignatures;
    }

    public static HashMap<String, KeySignature> getMinorKeySignatures() {
        return minorKeySignatures;
    }

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
        majorKeySignatures.put("Cb", new KeySignature("Cb", Arrays.asList("Fb","Cb","Gb","Db","Ab","Eb","Bb")));
        majorKeySignatures.put("Gb", new KeySignature("Gb", Arrays.asList("Cb","Gb","Db","Ab","Eb","Bb")));
        majorKeySignatures.put("Db", new KeySignature("Db", Arrays.asList("Gb","Db","Ab","Eb","Bb")));
        majorKeySignatures.put("Ab", new KeySignature("Ab", Arrays.asList("Db","Ab","Eb","Bb")));
        majorKeySignatures.put("Eb", new KeySignature("Eb", Arrays.asList("Ab","Eb","Bb")));
        majorKeySignatures.put("Bb", new KeySignature("Bb", Arrays.asList("Eb","Bb")));
        majorKeySignatures.put("F", new KeySignature("F", Arrays.asList("Bb")));

        return majorKeySignatures;
    }

    public static HashMap<String, KeySignature> getMajorKeySignatures() {
        return majorKeySignatures;
    }
}
