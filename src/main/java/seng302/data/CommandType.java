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

    /**
     * Statically stores all available commands.
     */
    public static Map<String, CommandType> allCommands = new TreeMap<String, CommandType>() {
        {
            put("Play Chord", new CommandType("play chord", "note,type", "arpeggio"));
            put("Play Interval", new CommandType("play interval", "name,note", ""));
            put("Play Note", new CommandType("play", "note|midi", ""));
            put("Play Scale", new CommandType("play scale", "note,type", "octaves,up|down|updown"));
        }
    };

    protected CommandType(String name, String params, String options) {
        this.name = name;
        this.params = params.split(",");
        this.options = options.split(",");
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

    /**
     * Turns all the parameters of a command into a pretty string
     *
     * @return A string containing all parameters of a command, surrounded in ()
     */
    public String getParamText() {
        String parameterString = "";
        for (String parameter : params) {
            parameterString += "(" + parameter + ") ";
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
