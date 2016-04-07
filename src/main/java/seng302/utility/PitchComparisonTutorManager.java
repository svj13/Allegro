package seng302.utility;

import java.util.ArrayList;

/**
 * Created by jmw280 on 7/04/16.
 */
public class PitchComparisonTutorManager {



    private ArrayList<OutputTuple> tempIncorrectResponses = new ArrayList<OutputTuple>();
    private ArrayList<OutputTuple> correctResponses = new ArrayList<OutputTuple>();
    private ArrayList<OutputTuple> incorrectResponses = new ArrayList<OutputTuple>();

    public int questions = 0;
    public int answered = 0;
    public int correct = 0;


    public void add(String note1, String note2, Boolean outcome){
        OutputTuple notePair  = new OutputTuple(note1,note2);
        if(outcome){
            correctResponses.add(notePair);
            correct += 1;
        } else{
            tempIncorrectResponses.add(notePair);
        }
    }



    public void saveTempIncorrect(){
//        questions = tempIncorrectResponses.size();
        for( OutputTuple tuple : tempIncorrectResponses ){
            incorrectResponses.add(tuple);
        }
        tempIncorrectResponses.clear();
    }

    public void clearTempIncorrect() {
        tempIncorrectResponses.clear();
    }

    public ArrayList<OutputTuple> getTempIncorrectResponses(){
        return tempIncorrectResponses;
    }



}
