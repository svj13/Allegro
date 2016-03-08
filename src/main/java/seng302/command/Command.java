package seng302.command;

import seng302.Environment;

/**
 * All Command classes implement this interface.
 */
public interface Command {
    public void execute(Environment env);
}