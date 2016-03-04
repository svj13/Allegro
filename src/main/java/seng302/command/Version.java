package seng302.command;

import seng302.Environment;

public class Version implements Command {

	public void execute(Environment env) {
		env.getTranscriptManager().setResult("Current Version: 0.1b");
	}
}