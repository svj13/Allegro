package seng302.utility;

/**
 * Created by team-5 on 4/03/16.
 */
public class OctaveUtil {

    /**
     * If the last character in the Note is not a number, the number 4 is
     * added as the default octave specifier.
     *
     * @param s Note
     * @return Note with octave specified.
     */
    public static String addDefaultOctave(String s) {
        try {
            String last = s.substring(s.length() - 1);
            Integer.valueOf(last);
        } catch (Exception e) {
            s = s + "4";
        }
        return s;
    }

    /**
     * Removes last character of the Note string (the Octave specifier).
     *
     * @param s Note
     * @return Note without an octave specified.
     */
    public static String removeOctaveSpecifier(String s) {
        String initial = s.substring(0,s.length() - 1);
        return initial;
    }

    /**
     * Returns boolean of whether a note has an octave specifier or not.
     *
     * @param s Note
     * @return boolean of octave specifier presence
     */
    public static boolean octaveSpecifierFlag(String s) {
        try {
            String last = s.substring(s.length() - 1);
            Integer.valueOf(last);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
