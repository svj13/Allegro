package seng302.command;


import seng302.Environment;
import java.util.Properties;

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

    public float getLength(Environment env) {
        return 0;
    };

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void execute(Environment env) {
        env.getTranscriptManager().setResult("Current Version: " + getCurrentVersion());
    }
}