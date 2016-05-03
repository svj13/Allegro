

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
import seng302.utility.MusicalTermsTutorBackEnd;

public class MusicalTerm implements Command {
    private String result;
    private String input;
    //protected static HashMap<String, Term> MusicalTermsMap = new HashMap<String, Term>();

    private  boolean termAdded = false;
    private boolean validAdd = true;
    public Term term = null;




    private String musicalTermName;
    private String infoToGet;
    private boolean lookupTerm = false;




    public ArrayList<Term> terms;


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

        termAdded = true;
        term = new Term(musicalTermArray.get(0), musicalTermArray.get(2), musicalTermArray.get(1), musicalTermArray.get(3));

    }

    /**
     * Displays information about a given musical term.
     * @param termToLookUp The musical term in question
     * @param infoToGet Whether we are fetching the musical term's category, origin, or definition.
     */
    public MusicalTerm(String termToLookUp, String infoToGet) {
        lookupTerm = true;
        musicalTermName = termToLookUp.toLowerCase();
        this.infoToGet = infoToGet.toLowerCase();
    }


    private void addTermBool() {
        boolean resultSet = false;
        for (Term term : terms) {
            if (term.getMusicalTermName().equals(this.term.getMusicalTermName())) {
                validAdd = false;
                this.result = "Term with the name of " + this.term.getMusicalTermName() + " has already been added";
                resultSet = true;

            } else if (!resultSet) {
                this.result = "Added term: " + this.term.getMusicalTermName() +
                        "\nOrigin: " + this.term.getMusicalTermOrigin() + " \nCategory: " +
                        this.term.getMusicalTermCategory() + "\nDefinition: "
                        + this.term.getMusicalTermDefinition();
            }
        }
        if (!resultSet) {
            this.result = "Added term: " + this.term.getMusicalTermName() +
                    "\nOrigin: " + this.term.getMusicalTermOrigin() + " \nCategory: " +
                    this.term.getMusicalTermCategory() + "\nDefinition: "
                    + this.term.getMusicalTermDefinition();


        }
    }

    private void lookupTerm(){
        boolean resultSet = false;
        for(Term term : this.terms) {
            if (term.getMusicalTermName().equals(musicalTermName)) {
                // Returns the correct information
                if (infoToGet.equals("meaning")) {
                    this.result = term.getMusicalTermDefinition();
                    resultSet = true;
                } else if (infoToGet.equals("origin")) {
                    this.result = term.getMusicalTermOrigin();
                    resultSet = true;
                } else if (infoToGet.equals("category")) {
                    this.result = term.getMusicalTermCategory();
                    resultSet = true;
                } else {
                    // What the user is looking for is invalid.
                    // This may never be reachable by the DSL, but is good to have regardless.
                    this.result = String.format("%s is not recognised as part of a musical term.",
                            infoToGet);
                    resultSet = true;
                }

                //if a given term is not in the hash map it will return an error to the user
            } else if (!resultSet) {
                this.result = String.format("%s is not recognised as an existing musical term.",
                        musicalTermName);
            }

        }
        if (!resultSet)
        {
            this.result = String.format("%s is not recognised as an existing musical term.",
                    musicalTermName);
        }
    }

    public float getLength(Environment env) {
        return 0;
    };


    /**will add the musical term to the dictionary, or print the relevant definition of the musical
     * term exists in the transcript manager
     * @param env
     */
    public void execute(Environment env) {
        this.terms = env.getMttDataManager().getTerms();
        if(lookupTerm) {
            lookupTerm();
        }
        else {
            addTermBool();

        }
        if(termAdded == true && validAdd == true){
            env.getMttDataManager().addTerm(term);
            env.getProjectHandler().checkmusicTerms();
        }
        env.getTranscriptManager().setResult(result);
    }
}

