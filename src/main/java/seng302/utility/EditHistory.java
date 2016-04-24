package seng302.utility;

import java.util.ArrayList;

/**
 * Undo/redo history manager for the application Created by team 5 on 24/04/16.
 */
public class EditHistory {
    private ArrayList<String> idStack = new ArrayList<String>();
    // Stack of commands to redo operations
    private ArrayList<ArrayList<String>> redoStack = new ArrayList<ArrayList<String>>();
    // Stack of commands to undo operations
    private ArrayList<ArrayList<String>> undoStack = new ArrayList<ArrayList<String>>();
    private int location = 0;
    // Set true when there are actions that can be undone
    private boolean canUndo = false;
    // Set true when there are actions that can be redone
    private boolean canRedo = false;

    public EditHistory() {

    }

    public void addToHistory(String type, String effect) {

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
        int type = Integer.parseInt(undoStack.get(location).get(0));
        switch (type) {
            case 0:
                changeTempo(undoStack.get(location).get(1));
                break;
            case 1:
                deleteMusicalTerm(undoStack.get(location).get(1));
        }
    }

    public void redoCommand() {
        int type = Integer.parseInt(undoStack.get(location).get(0));
        switch (type) {
            case 0:
                changeTempo(undoStack.get(location).get(1));
                break;
            case 1:
                addMusicalTerm(undoStack.get(location).get(1));
        }
    }

    private void changeTempo(String newTempo) {

    }

    private void deleteMusicalTerm(String termToDelete) {

    }

    private void addMusicalTerm(String termToAdd) {

    }

}
