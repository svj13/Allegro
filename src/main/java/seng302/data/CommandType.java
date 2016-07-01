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


    public static Map<String, CommandType> playCommands = new TreeMap<String, CommandType>() {
        {
            put("play chord", new CommandType("play chord", "note,type", "arpeggio"));
            put("play interval", new CommandType("play interval", "name,note", ""));
            put("play", new CommandType("play", "note|midi", ""));
            put("play scale", new CommandType("play scale", "note,type", "octaves,up|down|updown"));
        }
    };

    public static Map<String, CommandType> showCommands = new TreeMap<String, CommandType>() {
        {
            put("chord", new CommandType("chord", "note,type", ""));
            put("crotchet duration", new CommandType("crotchet duration", "", ""));
            put("all enharmonics", new CommandType("all enharmonics", "note", ""));
            put("enharmonic higher", new CommandType("enharmonic higher", "note", ""));
            put("enharmonic lower", new CommandType("enharmonic lower", "note", ""));
            put("interval", new CommandType("interval", "name", "note"));
            put("scale signature", new CommandType("scale signature", "note,scale type", ""));
            put("scale signature with", new CommandType("scale signature with", "number of sharps and flats|list of notes", ""));
            put("scale", new CommandType("scale", "note,type", ""));
            put("semitone up", new CommandType("semitone up", "note|midi", ""));
            put("semitone down", new CommandType("semitone down", "note|midi", ""));
            put("tempo", new CommandType("tempo", "", ""));
            put("rhythm", new CommandType("rhythm", "", ""));
        }
    };


    public static Map<String, CommandType> translationCommands = new TreeMap<String, CommandType>() {
        {
            put("note", new CommandType("note", "midi number", ""));
            put("midi", new CommandType("midi", "note", ""));

        }
    };

    public static Map<String, CommandType> termCommands = new TreeMap<String, CommandType>() {
        {
            put("add musical term", new CommandType("add musical term", "name; origin; category; definition", ""));
            put("origin of", new CommandType("origin of", "musical term name", ""));
            put("meaning of", new CommandType("meaning of", "musical term name", ""));
            put("category of", new CommandType("category of", "musical term name", ""));

        }
    };

    public static Map<String, CommandType> settingsCommands = new TreeMap<String, CommandType>() {
        {
            put("set tempo", new CommandType("set tempo", "20-300", ""));
            put("set rhythm", new CommandType("set rhythm", "straight|light|medium|heavy", ""));
            put("set rhythm", new CommandType("set rhythm", "fraction,fraction,fraction", ""));
            put("force set tempo", new CommandType("force set tempo", "1 or higher", ""));
        }
    };

    public static Map<String, CommandType> specialCommands = new TreeMap<String, CommandType>() {
        {
            put("help", new CommandType("help", "", "command name"));
            put("undo", new CommandType("undo", "", ""));
            put("redo", new CommandType("redo", "", ""));
            put("version", new CommandType("version", "", ""));
        }
    };


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

    public CommandType(String name, String params, String options) {
        this.name = name;
        this.params = params.split(",");
        this.options = options.split(",");
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
