package seng302;

import seng302.utility.TranscriptManager;

import javax.swing.*;

public class Environment {


    private DslExecutor executor;



    private TranscriptManager transcriptManager;




    public Environment(){
        //output = givenOutput;

        executor = new DslExecutor(this);
        transcriptManager = new TranscriptManager();
    }





    //This determines how we display output. Could be written to a text transcript box instead.
//    public void println(String s) {
//        output.append(s + "\n");
//    }




    public void error(String error_message) {
        transcriptManager.setCommand(String.format("[ERROR] %s\n", error_message));


    }

    public DslExecutor getExecutor() {
        return executor;
    }

    public TranscriptManager getTranscriptManager() {
        return transcriptManager;
    }
}