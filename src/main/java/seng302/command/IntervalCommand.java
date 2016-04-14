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
import seng302.data.Interval;
import seng302.data.Note;
import seng302.utility.Checker;
import seng302.utility.OctaveUtil;


public class IntervalCommand implements Command {
    String search;
    String type;
    String outputType;
    private boolean octaveSpecified;
    private Note note;
    private char[] letters;

    public void execute(Environment env) {
        //This line of code gets the number of semitones in a given interval
        int semitones = Interval.lookupByName(search).getSemitones();

    }
}

