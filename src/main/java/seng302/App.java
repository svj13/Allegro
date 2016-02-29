package seng302;

import java.io.StringReader;
import seng302.command.Command;
import seng302.command.NullCommand;

public class App
{
    private Environment environment;

    public App(Environment env) {
        environment = env;
    }

    public void executeCommand(Command command) {
        command.execute(environment);
    }

    public void executeCommand(String command_string) {
        executeCommand(parseCommandString(command_string));
    }

    public Command parseCommandString(String command_string) {
        DslParser parser = new DslParser(new DslLexer(new StringReader(command_string)));

        Object parseResult = null;

        try {
            parseResult = parser.parse().value;
        } catch (Exception e) {
            environment.error(String.format("Failed to parse command: \"%s\"", command_string));
        }

        if (parseResult instanceof Command) {
            return (Command)parseResult;
        } else {
            environment.error(String.format("Expected command object but got \"%s\"", parseResult));
            return new NullCommand();
        }
    }
}
