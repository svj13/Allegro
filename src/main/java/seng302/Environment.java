package seng302;

public class Environment {

    //This determines how we display output. Could be written to a text transcript box instead.
    public void println(String s) {
        System.out.println(s);
    }

    public void error(String error_message) {
        System.err.format("[ERROR] %s%n", error_message);
    }
}