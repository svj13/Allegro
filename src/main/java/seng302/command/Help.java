package seng302.command;

import java.util.ArrayList;

import seng302.Environment;
import seng302.data.CommandData;

public class Help implements Command {

    String keyword;

    public Help() {
        keyword = "";
    }

    public Help(String keyword) {
        this.keyword = keyword;
    }

    private String generateHelpResult(Command command) {
        String output = "";
        output += command.getCommandText() + ":\n";
        output += command.getHelp() + "\n";
        if (command.getParams().size() > 0) {
            output += "Parameters: ";
            output += String.join(", ", command.getParams());
            output += "\n";
        }
        if (command.getOptions().size() > 0) {
            output += "Options: ";
            output += String.join(", ", command.getOptions());
            output += "\n";
        }
        output += "Example: ";
        output += command.getExample();
        return output;
    }

    public void execute(Environment env) {
        try {
            Command result = CommandData.keywordToCommand.get(keyword);
            if (result != null) {
                env.getTranscriptManager().setResult(generateHelpResult(result));
            } else {
                env.getTranscriptManager().setResult("Showing DSL Reference");
                env.getRootController().getTranscriptController().showDslRef();
            }
        } catch (Exception e) {
            env.getTranscriptManager().setResult("Showing DSL Reference");
            env.getRootController().getTranscriptController().showDslRef();
        }


    }

    @Override
    public String getHelp() {
        return "When typed alone, opens the DSL reference menu. When followed by a command name, " +
                "gives more information about that command.";
    }

    @Override
    public String getCommandText() {
        return "help";
    }

    @Override
    public ArrayList<String> getOptions() {
        ArrayList<String> options = new ArrayList<>();
        options.add("command");
        return options;
    }

    @Override
    public String getExample() {
        return "help play scale";
    }

}