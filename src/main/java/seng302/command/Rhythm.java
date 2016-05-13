package seng302.command;

import seng302.Environment;

import java.util.ArrayList;

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
    public Rhythm(String swingStyle, boolean force) {
        this.isSetter = true;
        System.out.println(swingStyle);
        this.force = force;
        try {
            this.rhythm = swingStyle;

            if(swingStyle.equals("heavy")){


            }else if(swingStyle.equals("medium")){

                this.result = "Rhythm set to 2/3, 1/3  swing. ";
            }else if(swingStyle == "light"){

            }
            else if(swingStyle.equals("straight")){
                this.result = "Rhythm set to straight, half crotchet duration.";
            }
            else{
                this.result = "Invalid Rhythm setting. Valid styles are: straight, heavy, light, or medium.";
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
            System.out.println("executiooooooon");
            // Add Tempo to editHistory

//            ArrayList<String> editHistoryArray = new ArrayList<String>();
//            editHistoryArray.add(String.valueOf(env.getPlayer().getTempo()));
//            editHistoryArray.add(String.valueOf(rhythm));
//            env.getEditManager().addToHistory("0", editHistoryArray);
            if(rhythm == "straight") env.getPlayer().getRhythmHandler().setRhythmTimings(new int[12]);
            else if(rhythm == "medium") env.getPlayer().getRhythmHandler().setRhythmTimings(new int[12]);

            env.getTranscriptManager().setResult(result);
            //Update project saved state
            //env.getProjectHandler().checkChanges("tempo");

        } else {
            //is getting the tempo
            env.getTranscriptManager().setResult( "nope");
        }

    }
}
