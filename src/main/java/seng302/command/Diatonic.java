package seng302.command;

import java.util.HashMap;

import seng302.Environment;

/**
 * Created by isabelle on 24/07/16.
 */
public class Diatonic implements Command {
    String romanNumeral;
    String command;

    public Diatonic(String romanNumeral, String command) {
        this.romanNumeral = romanNumeral;
        this.command = command;
    }

    public Diatonic(HashMap<String, String> map) {

    }

    @Override
    public long getLength(Environment env) {
        return 0;
    }

    @Override
    public void execute(Environment env) {

    }
}
