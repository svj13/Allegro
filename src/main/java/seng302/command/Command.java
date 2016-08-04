package seng302.command;

import java.util.ArrayList;
import java.util.Set;

import seng302.Environment;

/**
 * All Command classes implement this interface.
 */
public interface Command {
    /**
     * This method is used for Dsl playback to determine how long the program should wait while the
     * command runs.
     *
     * @param env the current environment.
     * @return the number of milliseconds the program should wait.
     */
    default long getLength(Environment env) {
        return 0;
    }


    /**
     * Run the command.
     * @param env the current environment.
     */
    void execute(Environment env);

    String getHelp();

//    default ArrayList<String> getParams() {
//        return params;
//    }

    default ArrayList<String> getParams() {
        return new ArrayList<>();
    }

    default ArrayList<String> getOptions() {
        return new ArrayList<>();
    }

    String getCommandText();

    String getExample();

}