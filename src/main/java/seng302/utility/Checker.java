package seng302.utility;

/**
 * Created by isabelle on 2/03/16.
 */
public class Checker {
    public static boolean isValidNote(String note){
        return note.matches("^[A-G|a-g][#|b]?([0-8]|-1)?$");
    }
}
