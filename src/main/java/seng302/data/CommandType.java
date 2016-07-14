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


    public static Map<String, CommandType> playCommands = new TreeMap<String, CommandType>() {
        {
            put("play chord", new CommandType("play chord", "note,type", "arpeggio", "play chord C# major"));
            put("play interval", new CommandType("play interval", "name,note", "", "play interval unison D"));
            put("play", new CommandType("play", "note|midi", "", "play C5"));
            put("play scale", new CommandType("play scale", "note,type", "octaves,up|down|updown", "play scale C major"));
        }
    };

    public static Map<String, CommandType> showCommands = new TreeMap<String, CommandType>() {
        {
            put("chord", new CommandType("chord", "note,type", "", "chord D minor"));
            put("crotchet duration", new CommandType("crotchet duration", "", "", "crotchet duration"));
            put("all enharmonics", new CommandType("all enharmonics", "note", "", "all enharmonics A"));
            put("enharmonic higher", new CommandType("enharmonic higher", "note", "", "enharmonic higher B"));
            put("enharmonic lower", new CommandType("enharmonic lower", "note", "", "enharmonic lower C"));
            put("find chord", new CommandType("find chord", "note note note", "", "find chord F A C"));
            put("find chord all", new CommandType("find chord all", "note note note", "", "find chord all F A C"));
            put("interval", new CommandType("interval", "name", "note", "interval minor 2nd C"));
            put("scale signature", new CommandType("scale signature", "note,scale type", "", "scale signature A major"));
            put("scale signature with", new CommandType("scale signature with", "number of sharps and flats|list of notes", "", "scale signature with 2#"));
            put("scale", new CommandType("scale", "note,type", "", "scale A minor"));
            put("semitone up", new CommandType("semitone up", "note|midi", "", "semitone up 100"));
            put("semitone down", new CommandType("semitone down", "note|midi", "", "semitone down A"));
            put("tempo", new CommandType("tempo", "", "", "tempo"));
            put("rhythm", new CommandType("rhythm", "", "", "rhythm"));
        }
    };


    public static Map<String, CommandType> translationCommands = new TreeMap<String, CommandType>() {
        {
            put("note", new CommandType("note", "midi number", "", "note 10"));
            put("midi", new CommandType("midi", "note", "", "midi C2"));

        }
    };

    public static Map<String, CommandType> termCommands = new TreeMap<String, CommandType>() {
        {
            put("add musical term", new CommandType("add musical term", "name; origin; category; definition", "", "add musical term Lento; Italian; Tempo; Slowly"));
            put("origin of", new CommandType("origin of", "musical term name", "", "origin of Lento"));
            put("meaning of", new CommandType("meaning of", "musical term name", "", "meaning of Lento"));
            put("category of", new CommandType("category of", "musical term name", "", "category of Lento"));

        }
    };

    public static Map<String, CommandType> settingsCommands = new TreeMap<String, CommandType>() {
        {
            put("set tempo", new CommandType("set tempo", "20-300", "", "set tempo 150"));
            put("set rhythm", new CommandType("set rhythm", "straight|light|medium|heavy|fraction fraction fraction", "", "set rhythm 1/3 1/3 1/3"));
            put("force set tempo", new CommandType("force set tempo", "1 or higher", "", "force set tempo 500"));
        }
    };

    public static Map<String, CommandType> specialCommands = new TreeMap<String, CommandType>() {
        {
            put("help", new CommandType("help", "", "command name", "help play chord"));
            put("undo", new CommandType("undo", "", "", "undo"));
            put("redo", new CommandType("redo", "", "", "redo"));
            put("version", new CommandType("version", "", "", "version"));
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
