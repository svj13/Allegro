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

    public String getHelp() {
        return "Returns the duration of a crotchet in milliseconds at the current tempo.";
    }

    @Override
    public String getCommandText() {
        return "crotchet duration";
    }

    @Override
    public String getExample() {
        return "crotchet duration";
    }

}

