package seng302.data;

import java.util.HashMap;

/**
 * Created by emily on 27/05/16.
 */
public class CommandType {
    private String[] params;
    private String[] options;
    private String name;

    public static HashMap<String, CommandType> allCommands = new HashMap<String, CommandType>() {
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
}
