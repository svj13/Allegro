package seng302.utility;

/**
 * Created by Joseph on 5/03/2016.
 */
public class Tuple {

    private String command;
    private String result;


    Tuple(){}

    Tuple(String commandString){
        command = commandString;
    }

    Tuple(String commandString, String resultString){
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
