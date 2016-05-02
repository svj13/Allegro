package seng302.command;

/**
 *
 * MusicalTerm is used to look up and add musical
 * terms to the musical terms hashmap
 * Created by Sarah on 3/04/2016.
 *
 *
 */

import java.util.ArrayList;
import java.util.HashMap;

import seng302.Environment;
import seng302.data.Term;

public class MusicalTerm implements Command {
    private String result;
    private String input;
    protected static HashMap<String, String> MusicalTermsMap = new HashMap<String, String>();
    private ArrayList<String> inArray;
    private  boolean termAdded = false;
    private boolean validAdd = true;
    public Term term;



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
     */
    public MusicalTerm(ArrayList<String> musicalTermArray) {
        this.inArray = musicalTermArray;
        termAdded = true;
        term = new Term(musicalTermArray.get(0), musicalTermArray.get(2), musicalTermArray.get(1), musicalTermArray.get(3));

        String definition = "Origin: " + musicalTermArray.get(1) + "\nCategory: " + musicalTermArray.get(2) +
                "\nDefinition: " + musicalTermArray.get(3);
        if(MusicalTermsMap.get(musicalTermArray.get(0)) != null){
            validAdd = false;
            this.result = "Term with the name of " + musicalTermArray.get(0) +  " has already been added";

        }else {
            this.result = "Added term: " + term.getMusicalTermName() +
                    "\nOrigin: " + term.getMusicalTermOrigin() + " \nCategory: " +
                    term.getMusicalTermCategory() + "\nDefinition: " + term.getMusicalTermDefinition();
            MusicalTermsMap.put(musicalTermArray.get(0), definition);
        }
    }

    public MusicalTerm(String termToLookUp) {
        String musicalTermName = termToLookUp.toLowerCase();

        //checks to see if the definition exists in the hashmap
        if (MusicalTermsMap.get(musicalTermName) != null) {
            this.result = this.MusicalTermsMap.get(musicalTermName);

            //if a given term is not in the hash map it will return an error to the user
        } else {
            this.result = String.format("%s is not recognised as an existing musical term.",
                    musicalTermName);
        }
    }



    /**will add the musical term to the dictionary, or print the relevant defintion if the musical
     * term exists in the transcript manager
     * @param env
     */
    public void execute(Environment env) {

        if(termAdded == true && validAdd == true){
            env.getMttDataManager().addTerm(term);
            env.getEditManager().addToHistory("1", this.inArray);
        }

        env.getTranscriptManager().setResult(result);

    }






}
