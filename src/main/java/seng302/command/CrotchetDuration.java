package seng302.command;

import seng302.Environment;

/**
 * Created by Sarah on 21/03/2016.
 */
public class CrotchetDuration implements Command {

    public void execute(Environment env) {
        int tempo = env.getPlayer().getTempo();
        float speed = (float) 60000 / tempo;
        env.getTranscriptManager().setResult(String.format("The duration of a crotchet at %d BPM is " +
                "%.2f milliseconds.", tempo, speed));
    }

}

