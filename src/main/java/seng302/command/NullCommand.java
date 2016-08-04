package seng302.command;

import seng302.Environment;

/**
 * A NullCommand is returned when the command can not be parsed.
 */
public class NullCommand implements Command {

    /**
     * A NullCommand should do nothing when executed. The error message is handled in the
     * DslExecutor.
     */
    public void execute(Environment env) {
    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public String getCommandText() {
        return null;
    }

    @Override
    public String getExample() {
        return null;
    }
}
