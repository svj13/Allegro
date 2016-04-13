package seng302.command;

/**
 * Created by Sarah on 3/04/2016.
 */

import java.util.ArrayList;
import java.util.HashMap;

import seng302.Environment;

public class MusicalTerm implements Command {
    private String musicalTerm;
    private String result;
    private boolean isSetter;
    private HashMap<String, String> MusicalTermsMap;

    /**MusicalTerm(String) is a function that takes a musical term as a parameter and returns
     * the definition of that term along with the origin of the work (e.g. French, Italian).
     * It will return an error if it is not a recognised musical term
     */
    public MusicalTerm () { this.isSetter = false; }

    public MusicalTerm(ArrayList<String> musicalTermList) {
        IntiailizeHashMap();
        Integer musicalTermWords = musicalTermList.size(); //an array to handle the "meaning of"
        //input if there is more than one word (more than one argument)

        //iterates through the musicalTermList to ensure all of the arguments are case insensitive
        for (int i = 0; i < musicalTermWords; i++)
        {
            if (i == 0)
            {
                this.musicalTerm = musicalTermList.get(i).toLowerCase();
            }
            else
            {
                this.musicalTerm = this.musicalTerm.concat(" " + musicalTermList.get(i).toLowerCase());
            }
        }

        this.isSetter = true; //The function has been given an input

        //checks to see if the definition exists in the hashmap
        if (MusicalTermsMap.get(this.musicalTerm) != null) {
            this.result = MusicalTermsMap.get(musicalTerm);
        }

        //if a given term is not in the hash map, it will return an error message to the user
        if (MusicalTermsMap.get(this.musicalTerm) == null) {
            this.result = String.format("%s is not recognised as a musical term.",
                    this.musicalTerm);
        }

    }

    public void IntiailizeHashMap() {
        this.MusicalTermsMap = new HashMap<String, String>();


        //preset definitions for testing
        MusicalTermsMap.put("lento", "Origin: Italian \nCategory: Tempo \nDefinition: The speed at" +
                " which a passage of music is or should be played");
        MusicalTermsMap.put("a b", "Origin: Italian \nCategory: Tempo \nDefinition: The speed at" +
                " which a passage of music is or should be played");
    }

    public void execute(Environment env) {
        if (isSetter) {
            env.getTranscriptManager().setResult(result);
        }
    }

}
