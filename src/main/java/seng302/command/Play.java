package seng302.command;

import seng302.Environment;

public class Play implements Command {
	private Integer value;

	public Play(Integer n) {
		value = n;
	}

	public void execute(Environment env) {
		env.println("play " + value.toString());
	}
}