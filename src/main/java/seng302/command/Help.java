package seng302.command;

import seng302.Environment;

public class Help implements Command {

    public void execute(Environment env) {
        env.error("This command is not implemented yet.");
    }
}