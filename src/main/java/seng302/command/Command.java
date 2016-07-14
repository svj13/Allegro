package seng302.command;

import seng302.Environment;

/**
 * All Command classes implement this interface.
 */
public interface Command {
    default long getLength(Environment env) {
        return 0;
    }

    ;

    void execute(Environment env);

}