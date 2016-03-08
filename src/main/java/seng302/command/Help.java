package seng302.command;


import seng302.App;
import seng302.Environment;

import seng302.utility.Checker;


public class Help implements Command {
    String helpValue;
    String helpLine = "\n" + new String(new char[10]).replace("\0","_") + "\n";


    public Help(String s) {
        helpValue = s;
    }
    public void execute(Environment env) {


        // WANT TO USE SWITCH WHICH WAS INTRODUCED IN JDK 7, SO WHY DOES IT COMPLAIN DAMNIT
        //I JUST WANT TO DO ONE THING WHY INTELLIJ WHY. I BET IT'S YOU MAVEN
        //IT'S ALWAYS FUCKING MAVEN.



        if (Checker.isCommand(helpValue) ) {
            String hv = helpValue.toLowerCase();

            if(hv.equals("midi")){
                env.getTranscriptManager().setResult("Converts a Note Value into a midi numerical value\n" +
                        "e.g. midi C# will return 68 as that is the midi equivalent."
                        + helpLine
                        + "Accepted values: Integer between 0 and 127"
                );
            }

        } else {


            System.out.println("ERROR!!");
            env.error("\'" + helpValue + "\'" + " is not a valid command.");
        }
    }



}