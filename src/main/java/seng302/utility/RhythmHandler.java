package seng302.utility;

import java.util.Arrays;

/**
 * Created by jonty on 5/13/16.
 *
 * Handles any rhythm functionality, such as containing information specific to rhythm.
 *
 */
public class RhythmHandler {

    private int beatResolution; //Ticks per beat. (generally crotchet)
    int rIndex;
    int[] rhythmTimings;


    public RhythmHandler(){
        beatResolution = 24;
        rhythmTimings = new int[]{beatResolution/2}; //Default of half the resolution (8th)
        rIndex = 0;

    }

    /**
     * Returns the next note play duration in respect to the beat resolution.
     * @return next note play duration.
     */
    public int getNextTickTiming(){
        if(rIndex < rhythmTimings.length){
            return rhythmTimings[rIndex++];
        }
        else{

            return rhythmTimings[rIndex = 0];

        }
    }

    /**
     * Sets rhythm timings using float time division values. such as 0.5f for half timing.
     * @param divisions timing division array. i.e [1/2] for straight, or [2/3, 1/3] for medium swing.
     */
    public void setRhythmTimings(float[] divisions){
        int[] timings = new int[divisions.length];

        for(int i = 0; i < timings.length;  i++){
            timings[i] = (int)(beatResolution * divisions[i]);
            System.out.println(timings[i]);
        }
        this.rhythmTimings = timings;

    }

    /**
     *  Used to directly set the timings if the beat resolution is known.
     *  For example, for straight, half timing with a beat resolution of 24, the input will be [12]
     * @param timings int array of beat timings.
     */
    public void setRhythmTimings(int[] timings){
        this.rhythmTimings = timings;

    }
    public void setBeatResolution(int ticks){
        beatResolution = ticks;
    }
    public int getBeatResolution(){ return beatResolution;}
    public int[] getRhythmTimings(){
        return rhythmTimings;
    }
    public String toString(){
        String retString = "Rhythm beat divisions:";

        for(int timing : rhythmTimings){
            retString += " "  + asFraction(timing, beatResolution);

        }
        return retString;
    }

    public void resetIndex(){
        rIndex = 0;
    }


    /**
     * Finds the greatest common multiple of two numbers
     * @param a divident
     * @param b divisor
     * @return gcm of a and b
     */
    public static long gcm(long a, long b) {
        return b == 0 ? a : gcm(b, a % b); // Not bad for one line of code :)
    }


    /**
     * Returns a simplifed fraction string of a divident and divisor.
     * @param a divident
     * @param b divisor
     * @return A string represetitive of fraction in simplist form e.g. a=50, b = 100 returns '1/2'
     */
    public static String asFraction(long a, long b) {
        long gcm = gcm(a, b);
        return (a / gcm) + "/" + (b / gcm);
    }

}
