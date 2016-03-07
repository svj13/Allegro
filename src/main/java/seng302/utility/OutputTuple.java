package seng302.utility;

/**
 * A Tuple utility which contains information for a Command + Result pair.
 *
 * Created by team5 on 5/03/2016.
 */
public class OutputTuple {

    private String command;
    private String result;


    OutputTuple(){}

    OutputTuple(String commandString){
        command = commandString;
    }

    OutputTuple(String commandString, String resultString){
        command = commandString;
        result = resultString;
    }

    public String getCommand(){
        return command;
    }

    public String getResult(){
        return result;
    }

    public void setResult(String str){
        result = str;
    }

    public void setCommand(String str) {command = str; }

}
