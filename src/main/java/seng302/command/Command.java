package seng302.command;

import seng302.Environment;

public interface Command {
    public void execute(Environment env);
}