package seng302;

import java.io.StringReader;

import seng302.command.Command;
import seng302.command.NullCommand;

public class DslExecutor {
    private Environment environment;

    public DslExecutor(Environment env) {
        environment = env;
    }

    /**
     * Once the command string has been parsed into a Command, it can be executed. The execute
     * method for each command is specified in the particular command class.
     *
     * @param command The Command to be executed.
     */
    public void executeCommand(Command command) {
        command.execute(environment);
    }

    /**
     * If the command is still a string, it must be parsed first.
     * @param command_string The command string to be parsed into a Command object.
     */
    public void executeCommand(String command_string) {
        executeCommand(parseCommandString(command_string));
    }


    /**
     * Parses the commands as they are entered.
     * @return The parsed command.
     */
    public Command parseCommandString(String command_string) {
        DslParser parser = new DslParser(new DslLexer(new StringReader(command_string)));

        Object parseResult = null;

        try {
            parseResult = parser.parse().value;
        } catch (Exception e) {
            environment.error(String.format("Invalid command: '" + command_string + "'."));
        }

        if (parseResult instanceof Command) {
            return (Command) parseResult;
        } else {
            return new NullCommand();
        }
    }
}
