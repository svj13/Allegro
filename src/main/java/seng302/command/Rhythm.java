package seng302.command;

import seng302.Environment;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jonty on 5/13/16.
 */
public class Rhythm implements Command{

    private String rhythm;
    private String result;
    private boolean isSetter;
    private boolean force;

    public Rhythm(){
        this.isSetter = false;
    }


    /**
     * Changes the tempo to the given value. If the value is outside of the appropriate tempo range,
     * an error message will raise and notify the user
     */
    public Rhythm(String rhythmStyle, boolean force) {
        this.isSetter = true;

        this.force = force;
        try {
            this.rhythm = rhythmStyle;

            if(rhythmStyle.equals("heavy")) this.result = "Rhythm set to heavy swing timing (3/2 1/4).";

            else if(rhythmStyle.equals("medium")) this.result = "Rhythm set to medium swing timing (2/3 1/3).";

            else if(rhythmStyle.equals("light")) this.result = "Rhythm set to light swing timing (5/8 3/8).";

            else if(rhythmStyle.equals("straight")) this.result = "Rhythm set to straight, half crotchet timing (1/2).";

            else{
                this.result = "Invalid Rhythm option. Valid swing settings are: straight, heavy, light, or medium.";
            }



        } catch (Exception e) {
            this.result = "Invalid rhythm setting.";
        }
    }

    public float getLength(Environment env) {
        return 0;
    };

    /**
     * Executes the tempo command. It will return the current set tempo in BPM. If no tempo has
     * been set, it defaults to the value of 120BMP
     *
     */
    public void execute(Environment env) {
        if (isSetter){

             //Add Rhythm to editHistory

//            ArrayList<String> editHistoryArray = new ArrayList<String>();
//            editHistoryArray.add(String.valueOf(env.getPlayer().getTempo()));
//            editHistoryArray.add(String.valueOf(rhythm));
//            env.getEditManager().addToHistory("0", editHistoryArray);


            if(rhythm.equals("straight")) env.getPlayer().getRhythmHandler().setRhythmTimings(new float[]{0.5f}); //quaver (half beat)
            else if(rhythm.equals("medium")){
                env.getPlayer().getRhythmHandler().setRhythmTimings(new float[]{2.0f/3.0f, 1.0f/3.0f} );

            }
            else if(rhythm.equals("heavy")) env.getPlayer().getRhythmHandler().setRhythmTimings(new float[]{3.0f/4.0f, 1.0f/4.0f});
            else if(rhythm.equals("light")) env.getPlayer().getRhythmHandler().setRhythmTimings(new float[]{5.0f/8.0f, 3.0f/8.0f});


            env.getTranscriptManager().setResult(result);
            //Update project saved state
            env.getProjectHandler().checkChanges("rhythm");

        } else {
            //is getting the rhythm
            env.getTranscriptManager().setResult(env.getPlayer().getRhythmHandler().toString());
        }

    }
}
