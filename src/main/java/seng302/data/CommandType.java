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
            put("Play Chord", new CommandType("play chord", "note,type", "arpeggio"));
            put("Play Interval", new CommandType("play interval", "name,note", ""));
            put("Play Note", new CommandType("play", "note|midi", ""));
            put("Play Scale", new CommandType("play scale", "note,type", "octaves,up|down|updown"));
        }
    };

    public static Map<String, CommandType> showCommands = new TreeMap<String, CommandType>() {
        {
            put("Show Chord", new CommandType("chord", "note,type", ""));
            put("Show Crotchet Duration", new CommandType("crotchet duration", "", ""));
            put("Show All Enharmonics", new CommandType("all enharmonics", "note", ""));
            put("Show Higher Enharmonic", new CommandType("enharmonic higher", "note", ""));
            put("Show Lower Enharmonic", new CommandType("enharmonic lower", "note", ""));
            put("Show Interval", new CommandType("interval", "name", "note"));
            put("Show Key Signature", new CommandType("scale signature", "note,scale type", ""));
            put("Show Scales with Key Signature", new CommandType("scale signature with", "number of sharps and flats|list of notes", ""));
            put("Show Scale", new CommandType("scale", "note,type", ""));
            put("Show Semitone Up", new CommandType("semitone up", "note|midi", ""));
            put("Show Semitone Down", new CommandType("semitone down", "note|midi", ""));
            put("Show Tempo", new CommandType("tempo", "", ""));
            put("Show Rhythm", new CommandType("rhythm", "", ""));
        }
    };


    public static Map<String, CommandType> translationCommands = new TreeMap<String, CommandType>() {
        {
            put("Midi to Note", new CommandType("note", "midi number", ""));
            put("Note to Midi Value", new CommandType("midi", "note", ""));

        }
    };

    public static Map<String, CommandType> termCommands = new TreeMap<String, CommandType>() {
        {
            put("Add Musical Term", new CommandType("add musical term", "name; origin; category; definition", ""));
            put("Get Origin", new CommandType("origin of", "musical term name", ""));
            put("Get Definition", new CommandType("meaning of", "musical term name", ""));
            put("Get Category", new CommandType("category of", "musical term name", ""));

        }
    };

    public static Map<String, CommandType> settingsCommands = new TreeMap<String, CommandType>() {
        {
            put("Set Tempo", new CommandType("set tempo", "20-300", ""));
            put("Set Rhythm Defaults", new CommandType("set rhythm", "straight|light|medium|heavy", ""));
            put("Set Rhythm Custom", new CommandType("set rhythm", "fraction,fraction,fraction", ""));
            put("Force Set Tempo", new CommandType("force set tempo", "1 or higher", ""));
        }
    };

    public static Map<String, CommandType> specialCommands = new TreeMap<String, CommandType>() {
        {
            put("Help", new CommandType("help", "", "command name"));
            put("Undo Action", new CommandType("undo", "", ""));
            put("Redo Action", new CommandType("redo", "", ""));
            put("Version", new CommandType("version", "", ""));
            put("???", new CommandType("twinkle", "", ""));
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
