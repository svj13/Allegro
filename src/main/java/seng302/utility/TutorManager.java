package seng302.utility;

import java.util.ArrayList;

import javafx.util.Pair;

/**
 * Created by jmw280 on 7/04/16.
 */
public class TutorManager {


    /**
     * A list of tuples that stores the temporary incorrect responses
     */
    private ArrayList<Pair> tempIncorrectResponses = new ArrayList<Pair>();


    /**
     * A list of tuples that stores all the correct responses
     */
    private ArrayList<Pair> correctResponses = new ArrayList<Pair>();


    /**
     * A list of tuples stores all the incorrect responses that haven't been re-attempted
     */
    private ArrayList<Pair> incorrectResponses = new ArrayList<Pair>();


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
     * @param pair pair of anything to store
     * @param outcome value that represents if the note pair was answered correctly
     */
    public void add(Pair pair, int outcome){
        if(outcome == 1){
            correctResponses.add(pair);
            correct += 1;
        } else{
            tempIncorrectResponses.add(pair);
        }
    }



    /**
     * Saves the temporary incorrect list to the permanent incorrect list.
     */
    public void saveTempIncorrect(){
        for( Pair pair : tempIncorrectResponses ){
            incorrectResponses.add(pair);
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
    public ArrayList<Pair> getTempIncorrectResponses(){
        return tempIncorrectResponses;
    }

}
