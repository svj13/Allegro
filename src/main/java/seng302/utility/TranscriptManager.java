package seng302.utility;


import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Maintains command/result output information as well as handling save/open functionality.
 *
 * Created by team5 on 3/03/2016.
 */
public class TranscriptManager {
    private OutputTuple lastOutputTuple; // The last command + result fired generated.
    private String output;
    private ArrayList<OutputTuple> transcriptContent = new ArrayList<OutputTuple>();


    public TranscriptManager() {
        lastOutputTuple = new OutputTuple();
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
     * @param info ArrayList of (Command, Result) tuples to be saved to specified file.
     */
    public void Save(String path, ArrayList<OutputTuple> info) {

        try {
            FileWriter writer = new FileWriter(path, false); // the true variable means that if the file exists it will append to it not 100% sure thats what we want yet

            for (OutputTuple line : info) {
                writer.write(line.getCommand() + " : " + line.getResult() + ",");

            }
            writer.close();

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
    public void Open(String path) {

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
            displayText += "Command: " + content.getCommand() + "\n" + content.getResult() + "\n";
        }
        return displayText;
    }


}


