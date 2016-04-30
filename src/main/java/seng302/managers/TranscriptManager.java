package seng302.managers;


import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import seng302.utility.CommandHistory;
import seng302.utility.OutputTuple;

/**
 * Maintains command/result output information as well as handling save/open functionality.
 *
 * Created by team5 on 3/03/2016.
 */
public class TranscriptManager {
    CommandHistory historyController;
    private OutputTuple lastOutputTuple; // The last command + result fired generated.
    private String output;
    private ArrayList<OutputTuple> transcriptContent = new ArrayList<OutputTuple>();
    public boolean unsavedChanges = false;






    public TranscriptManager() {
        lastOutputTuple = new OutputTuple();
        historyController = new CommandHistory(this);

    }

    public String cycleInputUp(String field){
        return historyController.handleScrollUp(field);
    }


    public String cycleInputDown(String field){
        return historyController.handleScrollDown(field);
    }
    public void resetHistoryLevel(){
        historyController.resetLevel();
    }

    /**
     * @return ArrayList of (Command, Result) Tuples that have been fired.
     */
    public ArrayList<OutputTuple> getTranscriptTuples() {
        return transcriptContent;
    }


    /**
     * Saves Called commands/results to a local text file.
     *
     * @param path Text document path to save the data to.
     */
    public void save(String path) {

        try {
            FileWriter writer = new FileWriter(path, false); // the true variable means that if the file exists it will append to it not 100% sure thats what we want yet

            for (OutputTuple line : transcriptContent) {
                writer.write(line.getInput() + " : " + line.getResult() + ",");

            }
            writer.close();
            unsavedChanges = false;

        } catch (IOException ex) {

            System.err.println("Problem writing to the selected file " + ex.getMessage());
        }
    }


    /**
     * Clears the current transcript and generates a command/result history from a previously
     * generated text file.
     *
     * @param path The path of the command/result text document.
     */
    public void open(String path) {

        transcriptContent.clear(); // they want it so that when a file is opened the transcript gets replaced with the new content

        try {
            FileReader reader = new FileReader(path);
            BufferedReader input = new BufferedReader(reader);
            String str;
            while ((str = input.readLine()) != null) {
                String[] commandList = str.split(",");


                for (String element : commandList) {
                    String[] commandAndResult = element.split("\\s:\\s");
                    OutputTuple newOutputTuple = new OutputTuple(commandAndResult[0], commandAndResult[1]);
                    transcriptContent.add(newOutputTuple);
                }
            }



        } catch (IOException ex) {
            System.out.println("problem Reading from file");
        }
    }

    /**
     * Sets the current command output with a provided command string.
     *
     * @param s Command string.
     */
    public void setCommand(String s) {
        lastOutputTuple = new OutputTuple(s);
        output = "Command: " + s + "\n";
    }

    /**
     * Adds the command result to the current command output string.
     *
     * @param s Result string to be added to the last command.
     */
    public void setResult(String s) {
        lastOutputTuple.setResult(s); //pretty ugly way to do it. Adds result to last tuple.
        output += s + "\n";

        transcriptContent.add(lastOutputTuple);
        unsavedChanges = true;






    }


    /**
     * @return a raw string containing the previous command/result entry.
     */
    public String getLastCommand() {
        return output;
    }

    /**
     * Generates a string containing the history of command/result outputs.
     *
     * @return String containing output history; commands and their results separated by lines.
     */
    public String convertToText() {
        String displayText = "";
        for (OutputTuple content : transcriptContent) {
            displayText += "Command: " + content.getInput() + "\n" + content.getResult() + "\n";
        }
        return displayText;
    }

    /**
     * Method to write only the commands to a file, for re-use
     * @param path the path of the document to save to
     */
    public void saveCommandsOnly(String path) {
        try {
            FileWriter writer = new FileWriter(path, false);
            for (OutputTuple line : transcriptContent) {
                writer.write(line.getInput() + "\n");
            }
            writer.close();
            unsavedChanges = false;

        } catch (IOException ex) {

            System.err.println("Problem writing to the selected file " + ex.getMessage());
        }
    }

    /**
     * Loads a commands-only text file for execution
     * @param path the path to the file containing the commands
     * @return an array list of textual commands
     */
    public ArrayList<String> loadCommands(String path) {

        try {
            FileReader reader = new FileReader(path);
            BufferedReader input = new BufferedReader(reader);
            String str;
            ArrayList<String> commands = new ArrayList<String>();
            while ((str = input.readLine()) != null) {
                for (String command:str.split("\n")) {
                    commands.add(command);
                }
            }
            return commands;
        } catch (IOException ex) {
            System.out.println("problem Reading from file");
            return null;
        }
    }

}


