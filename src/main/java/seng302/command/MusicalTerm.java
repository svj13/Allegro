package seng302.command;

/**
 * Created by Sarah on 3/04/2016.
 */

import java.util.ArrayList;
import java.util.HashMap;

import seng302.Environment;
import seng302.data.Term;

public class MusicalTerm implements Command {
    private String result;
    private String input;
    private boolean isSetter;
    private static HashMap<String, String> MusicalTermsMap = new HashMap<String, String>();

    private  boolean termAdded = false;
    private Term term;


    /**MusicalTerm(String) is a function that takes a musical term as a parameter and returns
     * the definition of that term along with the origin of the work (e.g. French, Italian).
     * It will return an error if it is not a recognised musical term
     */
    public MusicalTerm () { this.isSetter = false; }


    private String createMusicalTermString(ArrayList<String> musicalWords) {
        String interval = "";
        for (String word:musicalWords) {
            interval += (word + " ");
        }
        return interval.trim();
    }


    /**
     *
     * Checks to see if a musical term stream has been inputted and splits this input into the
     * 4 corresponding categories: name; origin; category; definition.
     *
     * If the musical term name is not null, it will look up to see if it exists in the dictionary
     * and reutrn the definition, else it will notify the user that the musical term is not in
     * the dictionary.
     *
     * If the musical term name is null, it will add it to the dictionary and format it accordingly
     *
     *
     * @param musicalTermArray
     * @param input
     */
    public MusicalTerm( ArrayList<String> musicalTermArray,Boolean input) {
        String musicalTerm = createMusicalTermString(musicalTermArray);

        if(input == true) {
            musicalTerm = musicalTerm.toLowerCase();
            String[] parts = musicalTerm.split(" ");
            String musicalTermName = parts[0];

            this.isSetter = true; //it has an input

            //checks to see if the definition exists in the hashmap
            if (MusicalTermsMap.get(musicalTermName) != null) {

                this.result = this.MusicalTermsMap.get(musicalTermName);

                //if a given term is not in the hash map it will return an error to the user
            } else {
                this.result = String.format("%s is not recognised as an existing musical term.",
                        musicalTermName);


            }

        }

        else{
            musicalTerm = musicalTerm.toLowerCase();
            String[] parts = musicalTerm.split(" ");
            termAdded = true;
            term = new Term(parts[0],parts[2],parts[1],parts[3]);

            String definition = "Origin: " + parts[1] + "\nCategory: " + parts[2] +
                    "\nDefinition: " + parts[3];

            MusicalTermsMap.put(parts[0], definition);

        }
    }


    /**
     *
     * @param muscialTerm
     */
    public void addMusicalTerm(ArrayList<String> muscialTerm) {
        this.input = input;
    }



    public void execute(Environment env) {

        if(termAdded == true){
            env.getMttDataManager().addTerm(term);

        } else {

            env.getTranscriptManager().setResult(result);

        }
    }






}
