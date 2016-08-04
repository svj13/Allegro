package seng302.command;


import java.util.Properties;

import seng302.Environment;

public class Version implements Command {

    private String currentVersion;
    Properties props;

    public Version() {

        props = new Properties();

        try {
            props.load(getClass().getResourceAsStream("/vals.properties"));
            currentVersion = props.getProperty("version");
        } catch (Exception e) {
            currentVersion = "null";
        }
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void execute(Environment env) {
        env.getTranscriptManager().setResult("Current Version: " + getCurrentVersion());
    }

    public String getHelp() {
        return "Returns the current version number of the application.";
    }
}