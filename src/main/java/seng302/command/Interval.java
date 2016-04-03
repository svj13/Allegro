package seng302.command;

/**
 * Created by Sarah on 1/04/2016.
 */


// *********************************
//*******************************
//CURRENTLY NOT HOOKED UP TO THE DSL CUP OR FLEX
//**********************************
//*****************************


import java.util.ArrayList;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.Checker;
import seng302.utility.OctaveUtil;


public class Interval implements Command {
    String search;
    String type;
    String outputType;
    private boolean octaveSpecified;
    private Note note;
    private char[] letters;

    public void execute(Environment env) {

    }
}

