package seng302.command;

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

    public void execute(Environment env) {
        System.out.println(keyword);
        try {
            Command result = CommandData.keywordToCommand.get(keyword);
            if (result != null) {
                env.getTranscriptManager().setResult(result.getHelp());
                System.out.println(result.getParams());
            } else {
                env.getTranscriptManager().setResult("Showing DSL Reference");
                env.getRootController().getTranscriptController().showDslRef();
            }
        } catch (Exception e) {
            e.printStackTrace();
            env.getTranscriptManager().setResult("Showing DSL Reference");
            env.getRootController().getTranscriptController().showDslRef();
        }


    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public String getCommandText() {
        return "help";
    }

    @Override
    public String getExample() {
        return "help play scale";
    }

}