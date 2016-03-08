package seng302.command;

import seng302.Environment;

public class Version implements Command {

    private String currentVersion;

    public Version() {
        currentVersion = "1.0";
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void execute(Environment env) {
        env.getTranscriptManager().setResult("Current Version: " + currentVersion);
    }
}