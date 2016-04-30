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
}
