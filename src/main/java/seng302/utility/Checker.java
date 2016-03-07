package seng302.utility;

/**
 * Created by isabelle on 2/03/16.
 */
public class Checker {
    /**
     * This method checks that a note is valid.
     * It just checks for the 128 true note names at this point and will not allow enharmonics.
     * @param note
     * @return
     */
    public static boolean isValidNormalNote(String note){
        return note.matches("^[A-G|a-g]([0-8]|-1)?$|^[C|D|G|F|A]#?([0-8]|-1)?$|^[C|D|F]#?9$|^[E|G]9");
    }

    public static boolean isValidNoteNoOctave(String note){
        return note.matches("^[A-G|a-g]#?$");
    }

    public static boolean isValidMidiNote(String note){
        return note.matches("^(0?[0-9]?[0-9]|1[01][0-9]|12[0-7])$");
    }
}
