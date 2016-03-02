package seng302.command;

import seng302.Environment;

public class Version implements Command {

	public void execute(Environment env) {
		env.println("Current Version: 0.1b");
	}
}