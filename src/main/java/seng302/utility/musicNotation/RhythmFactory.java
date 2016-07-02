package seng302.utility.musicNotation;

/**
 * Created by Jonty on 18-May-16. <p> Used to perform rhythm related transformations such as
 * converting a float to a fraction, or converting a fraction string back to floating point values.
 */

public class RhythmFactory {

    /**
     * Finds the greatest common multiple of two numbers
     *
     * @param a divident
     * @param b divisor
     * @return gcm of a and b
     */
    public static long gcm(long a, long b) {
        return b == 0 ? a : gcm(b, a % b); // Not bad for one line of code :)
    }


    /**
     * Returns a simplifed fraction string of a divident and divisor.
     *
     * @param a divident
     * @param b divisor
     * @return A string represetitive of fraction in simplist form e.g. a=50, b = 100 returns '1/2'
     */
    public static String asFraction(long a, long b) {
        long gcm = gcm(a, b);
        return (a / gcm) + "/" + (b / gcm);
    }

    /**
     * Takes a string which includes a sequence of fractions, and returns an array of decimal floats
     * extracted from the input string.
     *
     * @param fracString string contained a sequence of fractions
     * @return float array of converted fractions
     */
    public static float[] fractionStringToFloatArray(String fracString) {

        int x = 0;
        while (!Character.isDigit(fracString.charAt(x))) x++;
        int j = fracString.length() - 1;

        while (!Character.isDigit(fracString.charAt(j))) j--;
        String[] items = fracString.substring(x, j + 1).split(" ");

        float[] results = new float[items.length];

        for (int i = 0; i < items.length; i++) {
            try {
                String[] fract = items[i].split("/");

                results[i] = (float) (Float.parseFloat(fract[0]) / Float.parseFloat(fract[1]));

            } catch (NumberFormatException nfe) {
                System.err.println("Invalid float array conversion");
            }
            ;
        }

        return results;

    }


}
