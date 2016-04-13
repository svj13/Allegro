package seng302.command;

/**
 * Created by Sarah on 3/04/2016.
 */

import java.util.ArrayList;
import java.util.HashMap;

import seng302.Environment;

public class MusicalTerm implements Command {
    private String musicalTerm;
    private String musicalTermName;
    private String musicalTermCategory;
    private String musicalTermOrigin;
    private String musicalTermDefinition;
    private int count = 0;
    private String result;
    private String input;
    private boolean isSetter;
    private HashMap<String, String> MusicalTermsMap;

    /**MusicalTerm(String) is a function that takes a musical term as a parameter and returns
     * the definition of that term along with the origin of the work (e.g. French, Italian).
     * It will return an error if it is not a recognised musical term
     */
    public MusicalTerm () { this.isSetter = false; }

    public MusicalTerm(ArrayList<String> musicalTermList, boolean whatDo) {
        IntiailizeHashMap();
        Integer musicalTermWords = musicalTermList.size();
        if (whatDo == false) {
             //an array to handle the "meaning of"
            //input if there is more than one word (more than one argument)

            //iterates through the musicalTermList to ensure all of the arguments are case insensitive
            for (int i = 0; i < musicalTermWords; i++) {
                if (i == 0) {
                    this.musicalTerm = musicalTermList.get(i).toLowerCase();
                } else {
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
        } else {
            for (int i = 0; i < musicalTermWords; i++) {
                switch (count) {
                    case 0:
                        if (musicalTermList.get(i).equals(";")) {
                            count += 1;
                        } else {
                            if (this.musicalTermName == null) {
                                this.musicalTermName = musicalTermList.get(i).toLowerCase();
                            } else {
                                this.musicalTermName = this.musicalTermName.concat("Term: " + musicalTermList.get(i).toLowerCase());
                            }
                        }
                        break;
                    case 1:
                        if (musicalTermList.get(i).equals(";")) {
                            count += 1;
                        } else {
                            if (this.musicalTermOrigin == null) {
                                this.musicalTermOrigin = musicalTermList.get(i).toLowerCase();
                            } else {
                                this.musicalTermOrigin = this.musicalTermOrigin.concat("Origin: " + musicalTermList.get(i).toLowerCase());
                            }
                        }
                        break;
                    case 2:
                        if (musicalTermList.get(i).equals(";")) {
                            count += 1;
                        } else {
                            if (this.musicalTermCategory == null) {
                                this.musicalTermCategory = musicalTermList.get(i).toLowerCase();
                            } else {
                                this.musicalTermCategory = this.musicalTermCategory.concat("Category: " + musicalTermList.get(i).toLowerCase());
                            }
                        }
                        break;
                    case 3:
                        if (musicalTermList.get(i).equals(";")) {
                            count += 1;
                        } else {
                            if (this.musicalTermDefinition == null) {
                                this.musicalTermDefinition = musicalTermList.get(i).toLowerCase();
                            } else {
                                this.musicalTermDefinition = this.musicalTermDefinition.concat("Definition: " + musicalTermList.get(i).toLowerCase());
                            }
                        }
                        break;

                }
            }
            System.out.println(this.musicalTermName);
            System.out.println(this.musicalTermOrigin);
            System.out.println(this.musicalTermCategory);
            System.out.println(this.musicalTermDefinition);
            System.out.println(musicalTermList);

        }

    }


    /**
     * Intialises the hashmap that contains all of the musical terms
     *
     */
    public void IntiailizeHashMap() {
        this.MusicalTermsMap = new HashMap<String, String>();


        //preset definitions for testing
        MusicalTermsMap.put("lento", "Origin: Italian \nCategory: Tempo \nDefinition: The speed at" +
                " which a passage of music is or should be played");
        MusicalTermsMap.put("a b", "Origin: Italian \nCategory: Tempo \nDefinition: The speed at" +
                " which a passage of music is or should be played");
    }

    /**
     *
     * @param muscialTerm
     */
    public void addMusicalTerm(ArrayList<String> muscialTerm) {
        this.input = input;
    }

    public void execute(Environment env) {
        if (isSetter) {
            env.getTranscriptManager().setResult(result);
        }
    }

}
