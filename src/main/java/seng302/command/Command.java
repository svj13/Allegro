package seng302.command;

import seng302.Environment;

/**
 * All Command classes implement this interface.
 */
public interface Command {
    float getLength(Environment env);

    void execute(Environment env);

}