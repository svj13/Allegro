package seng302.command;

/**
 * Created by Sarah on 3/04/2016.
 */

import java.util.HashMap;

import seng302.Environment;

public class MusicalTerm implements Command {
    private String musicalTerm;
    private String result;
    private boolean isSetter;

    /**A function that takes a musical term as a parameter and returns the definition of that
     * term along with the origin of the work (e.g. French, Italian). It will return an error if
     * it is not a recognised musical term
     */
    public MusicalTerm () { this.isSetter = false; }

    public MusicalTerm(String musicalTerm) {
        this.musicalTerm = musicalTerm.toLowerCase(); //changes input to lower case
        this.isSetter = true; //The function has been given an input
        try {
            if (this.musicalTerm.equals("lento")) {
                this.result = "Origin: Italian \nCategory: Tempo \nDefinition: Slowly";


            }

        } catch (Exception e) {
            this.result = String.format("%s is not recognised as a musical term.",
                    this.musicalTerm);

        }

    }

    public static void main(String args[]) {
        HashMap MusicalTermsMap = new HashMap<String, String>();
        /**Inserting the definitions of all of the musical terms
         */
        MusicalTermsMap.put("lento", "\"Origin: Italian \nCategory: Tempo \nDefinition: Slowly\";");

    }

    public void execute(Environment env) {
        if (isSetter) {
            env.getTranscriptManager().setResult(result);
        }
    }

}
