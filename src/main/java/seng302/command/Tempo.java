package seng302.command;


import seng302.Environment;

/**
 * Tempo is  used to return the current set tempo. It has a default value of 120BPM
 */
public class Tempo implements Command {
    private int tempo;
    private String result;
    private boolean isSetter;

    public Tempo(){
        this.isSetter = false;
    }

    public Tempo(String tempo) {
        this.isSetter = true;
        try {
            this.tempo = Integer.parseInt(tempo);
            if (this.tempo < 20 || this.tempo > 300){
                this.result = "Tempo outside valid range";
            } else {
                this.result = String.format("Tempo changed to %d BPM", this.tempo);
            }
        } catch (Exception e) {
            this.result = "Invalid tempo";
        }
    }

    public void execute(Environment env) {
        if (isSetter){
            env.setTempo(this.tempo);
            env.getTranscriptManager().setResult(result);
        } else {
            //is getting the tempo
            env.getTranscriptManager().setResult(env.getTempo() + " BPM");
        }

    }
}

