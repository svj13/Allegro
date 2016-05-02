package seng302.command;

import seng302.Environment;

/**
 * All Command classes implement this interface.
 */
public interface Command {
    public float getLength(Environment env);
    public void execute(Environment env);

}