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
    private Environment env;

    public EditHistory(Environment env) {
        this.env = env;
    }

    public void addToHistory(String type, ArrayList<String> effect) {
        ArrayList<String> toAdd = new ArrayList<String>();
        toAdd.add(type);
        for (String val : effect) {
            toAdd.add(val);
        }
        commandStack.add(toAdd);
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
        int type = Integer.parseInt(commandStack.get(location).get(0));
        switch (type) {
            case 0:
                changeTempo(commandStack.get(location).get(1));
                break;
            case 1:
                deleteMusicalTerm(commandStack.get(location).get(1));
        }
    }

    /**
     * called to redo a command.
     */
    public void redoCommand() {
        int type = Integer.parseInt(commandStack.get(location).get(0));
        switch (type) {
            case 0:
                changeTempo(commandStack.get(location).get(1));
                break;
            case 1:
                addMusicalTerm(commandStack.get(location));
        }
    }

    private void changeTempo(String newTempo) {
        new Tempo(newTempo, true).execute(env);
    }

    private void deleteMusicalTerm(String termToDelete) {

    }

    private void addMusicalTerm(ArrayList<String> termToAdd) {
        new MusicalTerm(termToAdd).execute(env);
    }

}
