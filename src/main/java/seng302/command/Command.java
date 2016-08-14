package seng302.command;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Gets a textual description of what each command does.
     */
    String getHelp();

    /**
     * Gets a list of parameters required by a command
     * @return A list containing textual representations of parameters, or an empty list if the
     * command has no parameters.
     */
    default List<String> getParams() {
        return new ArrayList<>();
    }

    /**
     * Gets a list of options available to a command
     * @return A list containing textual representations of options, or an empty list if the
     * command has no options.
     */
    default List<String> getOptions() {
        return new ArrayList<>();
    }

    /**
     * This is the string the user types in to use this command.
     * @return The string representation of the command
     */
    String getCommandText();

    /**
     * Gets an example of a command being used - shows the command along with example parameters/options.
     * @return A string representation of an example command.
     */
    String getExample();

}