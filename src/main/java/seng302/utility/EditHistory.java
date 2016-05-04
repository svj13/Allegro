package seng302.utility;

import java.util.ArrayList;

import seng302.Environment;
import seng302.command.MusicalTerm;
import seng302.command.Tempo;

/**
 * Undo/redo history manager for the application Created by team 5 on 24/04/16.
 */
public class EditHistory {
    private ArrayList<String> idStack = new ArrayList<String>();
    // Stack of commands to redo operations
//    private ArrayList<ArrayList<String>> redoStack = new ArrayList<ArrayList<String>>();
    // Stack of commands to undo operations
    private ArrayList<ArrayList<String>> commandStack = new ArrayList<ArrayList<String>>();
    private int location = 0;
    // Set true when there are actions that can be undone
    private boolean canUndo = false;
    // Set true when there are actions that can be redone
    private boolean canRedo = false;
    // Set true when something should be added
    private boolean ath = true;


    private Environment env;

    public EditHistory(Environment env) {
        this.env = env;
    }

    public void addToHistory(String type, ArrayList<String> effect) {
        if (location > 0 && ath) {
            for (int i = 0; i < location; i++) {
                commandStack.remove(0);
            }
            ArrayList<String> toAdd = new ArrayList<String>();
            toAdd.add(type);
            for (String val : effect) {
                toAdd.add(val);
            }
            commandStack.add(0, toAdd);
            canUndo = true;
            canRedo = false;
            location = 0;
        } else if (ath) {
            ArrayList<String> toAdd = new ArrayList<String>();
            toAdd.add(type);
            for (String val : effect) {
                toAdd.add(val);
            }
            commandStack.add(0, toAdd);
            canUndo = true;
            canRedo = false;
            location = 0;
        }
    }

    public void addToHistory(String type, String effect) {
        ArrayList<String> toAdd = new ArrayList<String>();
        toAdd.add(type);
        toAdd.add(effect);
        commandStack.add(toAdd);
    }

    public String getUndoId() {
        if (canUndo) {
            return idStack.get(location);
        } else {
            return null;
        }
    }

    public String getRedoId() {
        if (canRedo) {
            return idStack.get(location - 1);
        } else {
            return null;
        }
    }

    /**
     * Called to undo a command.
     */
    public void undoCommand() {
        if (canUndo) {
            ath = false;
            int type = Integer.parseInt(commandStack.get(location).get(0));
            switch (type) {
                case 0:
                    changeTempo(commandStack.get(location).get(1));
                    break;
                case 1:
                    deleteMusicalTerm(commandStack.get(location).get(1));
                    break;
            }
            ath = true;
            location += 1;
            if (location > commandStack.size() - 1) {
                canUndo = false;
            }
            canRedo = true;
        } else {
            env.error("No command to undo.");
        }
    }

    /**
     * called to redo a command.
     */
    public void redoCommand() {
        if (canRedo) {
            int type = Integer.parseInt(commandStack.get(location - 1).get(0));
            ath = false;
            switch (type) {
                case 0:
                    changeTempo(commandStack.get(location - 1).get(2));
                    break;
                case 1:
                    addMusicalTerm(commandStack.get(location - 1));
                    break;
            }
            ath = true;
            location -= 1;
            if (location == 0) {
                canRedo = false;
            }
        } else {
            env.error("No command to redo.");
        }
    }

    private void changeTempo(String newTempo) {
        new Tempo(newTempo, true).execute(env);
    }

    private void deleteMusicalTerm(String termToDelete) {
        env.getMttDataManager().removeTerm(termToDelete);
        env.getTranscriptManager().setResult(String.format("Musical Term %s has been deleted.",
                termToDelete));
    }

    private void addMusicalTerm(ArrayList<String> termToAdd) {
        ArrayList<String> termArgs = new ArrayList<String>();
        termArgs.add(termToAdd.get(1));
        termArgs.add(termToAdd.get(2));
        termArgs.add(termToAdd.get(3));
        termArgs.add(termToAdd.get(4));

        new MusicalTerm(termArgs).execute(env);
    }

}
