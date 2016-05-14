package seng302.utility;

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
    public void setBeatResolution(int ticks){
        beatResolution = ticks;
    }
    public int getBeatResolution(){ return beatResolution;}

    public void resetIndex(){
        rIndex = 0;
    }

}
