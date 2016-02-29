package seng302;

public class Environment {

    public void println(String s) {
        System.out.println(s);
    }

    public void error(String error_message) {
        System.err.format("[ERROR] %s%n", error_message);
    }
}