package seng302.utility;

/**
 * A Tuple utility which contains information for a Command + Result pair.
 *
 * Created by team5 on 5/03/2016.
 */
public class OutputTuple {

    private String input;
    private String result;


    OutputTuple() {
    }

    OutputTuple(String commandString) {
        input = commandString;
    }

    OutputTuple(String commandString, String resultString) {
        input = commandString;
        result = resultString;
    }

    public String getInput() {
        return input;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String str) {
        result = str;
    }

    public void setInput(String str) {
        input = str;
    }

}
