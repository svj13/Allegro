package seng302.command;

import seng302.Environment;

/**
 * Created by dominicjarvis on 30/04/16.
 */
public class UndoRedo implements Command {

    private int action;

    public UndoRedo(int action) {
        this.action = action;
    }

    public void execute(Environment env) {
        if (action == 0) {
            env.getEditManager().undoCommand();
        } else if (action == 1) {
            env.getEditManager().redoCommand();
        }
    }

    @Override
    public String getHelp() {
        if (action == 0) {
            return "Undoes the most recent reversible command.";
        } else {
            return "Re-executes the most recently undone command.";
        }
    }

    @Override
    public String getCommandText() {
        if (action == 0) {
            return "undo";
        } else {
            return "redo";
        }
    }

    @Override
    public String getExample() {
        return getCommandText();
    }
}
