package seng302.command;

import seng302.Environment;

import java.util.List;

public class Find implements Command {
    List<Integer> commandArguments;

    public Find(List<Integer> args) {
        commandArguments = args;
    }

    public void execute(Environment env) {
        env.println(String.format("find %s", commandArguments));
    }
}
