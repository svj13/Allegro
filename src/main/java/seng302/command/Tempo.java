package seng302.command;


import seng302.Environment;

/**
 * Tempo is  used to return the current set tempo. It has a default value of 120BPM
 */
public class Tempo implements Command {
    private String temp = "120";

    public Tempo() {
    }

    public Tempo(String temp) {
        this.temp = temp;
    }

    public void execute(Environment env) {
        env.getTranscriptManager().setResult(temp + " BPM");

    }
}

