package seng302.data;

import java.util.Map;
import java.util.TreeMap;

/**
 * Stores a static map of commands and their relevant data.
 * This class also implements getter methods, and methods which produce nice strings of the data.
 */
public class CommandType {
    private String[] params;
    private String[] options;
    private String name;
    private String example;


    public static Map<String, CommandType> playCommands = new TreeMap<String, CommandType>();

    public static Map<String, CommandType> showCommands = new TreeMap<String, CommandType>();


    public static Map<String, CommandType> translationCommands = new TreeMap<String, CommandType>();

    public static Map<String, CommandType> termCommands = new TreeMap<String, CommandType>();

    public static Map<String, CommandType> settingsCommands = new TreeMap<String, CommandType>();

    public static Map<String, CommandType> specialCommands = new TreeMap<String, CommandType>();


    /**
     * Statically stores all available commands.
     */
    public static Map<String, CommandType> allCommands = new TreeMap<String, CommandType>() {
        {
            putAll(playCommands);
            putAll(showCommands);
            putAll(specialCommands);
            putAll(translationCommands);
            putAll(termCommands);
            putAll(settingsCommands);
        }
    };

    public CommandType(String name, String params, String options, String example) {
        this.name = name;
        this.params = params.split(",");
        this.options = options.split(",");
        this.example = example;
    }

    public static Map<String, CommandType> getCommands(String commandsToGet) {
        if (commandsToGet.equals("Play")) {
            return playCommands;
        }
        if (commandsToGet.equals("Show")) {
            return showCommands;
        }
        if (commandsToGet.equals("Special")) {
            return specialCommands;
        }
        if (commandsToGet.equals("Translation")) {
            return translationCommands;
        }
        if (commandsToGet.equals("Terms")) {
            return termCommands;
        }
        if (commandsToGet.equals("Settings")) {
            return settingsCommands;
        }
        return allCommands;
    }

    public String[] getParams() {
        return this.params;
    }

    public String[] getOptions() {
        return this.options;
    }

    public String getName() {
        return this.name;
    }

    public String getExample() {
        return this.example;
    }

    /**
     * Turns all the parameters of a command into a pretty string
     *
     * @return A string containing all parameters of a command, surrounded in ()
     */
    public String getParamText() {
        String parameterString = "";
        for (String parameter : params) {
            if (!parameter.equals("")) {
                parameterString += "(" + parameter + ") ";
            }
        }
        return parameterString;
    }

    /**
     * Turns all the options of a command into a pretty string
     * @return A string containing all options of a command, surrounded in []
     */
    public String getOptionText() {
        String optionsString = "";
        for (String option : options) {
            if (!option.equals("")) {
                optionsString += "[" + option + "] ";
            }
        }
        return optionsString;
    }


    /**
     * Displays all information of a command in a pretty string
     * @return A string of the form command name (parameters) [options]
     */
    public String getDisplayText() {
        String displayText = "";
        displayText += name + " " + getParamText();
        if (!getOptionText().equals("")) {
            displayText += getOptionText();
        }
        return displayText;
    }
}
