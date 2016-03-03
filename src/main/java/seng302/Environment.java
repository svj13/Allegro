package seng302;

import javax.swing.*;

public class Environment {

    JTextArea output;

    public Environment(JTextArea givenOutput){
        output = givenOutput;
    }

    //This determines how we display output. Could be written to a text transcript box instead.
    public void println(String s) {
        output.append(s + "\n");
    }

    public void error(String error_message) {
        output.append(String.format("[ERROR] %s\n", error_message));
    }
}