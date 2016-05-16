package seng302.utility;

import java.util.Arrays;

/**
 * Created by jonty on 5/13/16.
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
    public int getNextTickTiming(){
        if(rIndex < rhythmTimings.length){
            return rhythmTimings[rIndex++];
        }
        else{

            return rhythmTimings[rIndex = 0];

        }
    }

    public void setRhythmTimings(float[] divisions){
        int[] timings = new int[divisions.length];

        for(int i = 0; i < timings.length;  i++){
            timings[i] = (int)(beatResolution * divisions[i]);
            System.out.println(timings[i]);
        }
        this.rhythmTimings = timings;

    }
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
