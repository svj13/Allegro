package seng302.utility;

import java.util.ArrayList;

/**
 * Created by jmw280 on 7/04/16.
 */
public class PitchComparisonTutorManager {


    /**
     * A list of tuples that stores the temporary incorrect responses
     */
    private ArrayList<OutputTuple> tempIncorrectResponses = new ArrayList<OutputTuple>();


    /**
     * A list of tuples that stores all the correct responses
     */
    private ArrayList<OutputTuple> correctResponses = new ArrayList<OutputTuple>();


    /**
     * A list of tuples stores all the incorrect responses that haven't been re-attempted
     */
    private ArrayList<OutputTuple> incorrectResponses = new ArrayList<OutputTuple>();


    /**
     * Stores the number of questions that are currently being answered
     */
    public int questions = 0;

    /**
     *Stores the number of questions answered in the current set
     */
    public int answered = 0;

    /**
     *Stores the number of questions that have been answered correctly
     */
    public int correct = 0;


    /**
     *Add note pair to to the corresponding array
     * @param note1 first note name of the note pair
     * @param note2 second note name of the note pair
     * @param outcome value that represents if the note pair was answered correctly
     */
    public void add(String note1, String note2, int outcome){
        OutputTuple notePair  = new OutputTuple(note1,note2);
        if(outcome == 1){
            correctResponses.add(notePair);
            correct += 1;
        } else{
            tempIncorrectResponses.add(notePair);
        }
    }



    /**
     * Saves the temporary incorrect list to the permanent incorrect list.
     */
    public void saveTempIncorrect(){
        for( OutputTuple tuple : tempIncorrectResponses ){
            incorrectResponses.add(tuple);
        }
        tempIncorrectResponses.clear();
    }


    /**
     *Clears the temporary incorrect list
     */
    public void clearTempIncorrect() {
        tempIncorrectResponses.clear();
    }


    /**
     * Gets The temporary incorrect responses
     *@return  the temporary incorrect responses
     */
    public ArrayList<OutputTuple> getTempIncorrectResponses(){
        return tempIncorrectResponses;
    }

}
