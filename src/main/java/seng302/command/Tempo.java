package seng302.command;


import java.util.ArrayList;

import seng302.Environment;

/**
 * Tempo is  used to return the current set tempo. It has a default value of 120BPM
 */
public class Tempo implements Command {
    private int tempo;
    private String result;
    private boolean isSetter;
    private boolean force;

    public Tempo(){
        this.isSetter = false;
    }

    /**
     * Changes the tempo to the given value. If the value is outside of the appropriate tempo range,
     * an error message will raise and notify the user
     */
    public Tempo(String tempo, boolean force) {
        this.isSetter = true;
        try {
            this.tempo = Integer.parseInt(tempo);
            if (this.tempo < 20 || this.tempo > 300) {
                if (force == false) {
                    this.result = "Tempo outside valid range. Use 'force set tempo' command to " +
                            "override. Use 'help' for more information";
                } else {
                    this.result = String.format("Tempo changed to %d BPM", this.tempo);
                }

            } else {
                this.result = String.format("Tempo changed to %d BPM", this.tempo);
            }
        } catch (Exception e) {
            this.result = "Invalid tempo";
        }
    }

    /**
     * Executes the tempo command. It will return the current set tempo in BPM. If no tempo has
     * been set, it defaults to the value of 120BMP
     *
     */
    public void execute(Environment env) {
        if (isSetter){
            ArrayList<String> editHistoryArray = new ArrayList<String>();
            editHistoryArray.add(String.valueOf(env.getPlayer().getTempo()));
            editHistoryArray.add(String.valueOf(tempo));
            env.getEditManager().addToHistory("0", editHistoryArray);
            env.getPlayer().setTempo(tempo);
            env.getTranscriptManager().setResult(result);
            //Update project saved state
            env.getJson().checkChanges("tempo");

        } else {
            //is getting the tempo
            env.getTranscriptManager().setResult(env.getPlayer().getTempo() + " BPM");
        }

    }
}

