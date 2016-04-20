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
    private static  HashMap<String, String> MusicalTermsMap = new HashMap<String, String>();

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

    public MusicalTerm( ArrayList<String> musicalTermArray,Boolean input) {
        String musicalTerm = createMusicalTermString(musicalTermArray);

        if(input == true) {
            musicalTerm = musicalTerm.toLowerCase();
            String[] parts = musicalTerm.split(";");
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










//    public MusicalTerm(ArrayList<String> musicalTermList, boolean whatDo) {
//        IntiailizeHashMap();
//        Integer musicalTermWords = musicalTermList.size();
//        if (whatDo == false) {//            this.isSetter = true; //The function has been given an input
// private HashMap<String, String> MusicalTermsMap;
//            //checks to see if the definition exists in the hashmap
//            if (MusicalTermsMap.get(this.musicalTerm) != null) {
//                this.result = MusicalTermsMap.get(musicalTerm);
//            }
//
//            //if a given term is not in the hash map, it will return an error message to the user
//            if (MusicalTermsMap.get(this.musicalTerm) == null) {
//                this.result = String.format("%s is not recognised as a musical term.",
//                        this.musicalTerm);
//            }
//             //an array to handle the "meaning of"iailizeHashMap();
//        Integer musicalTermWords = musicalTermList.size();
//        if (whatDo == false) {
//             //an array to handle the "meaning of"
//            //input if there is more than one word (more than one argument)
//

//            //iterates through the musicalTermList to ensure all of the arguments are case insensitive
//            for (int i = 0; i < musicalTermWords; i++) {
//                if (i == 0) {
//                    this.musicalTerm = musicalTermList.get(i).toLowerCase();
//                } else {
//                    this.musicalTerm = this.musicalTerm.concat(" " + musicalTermList.get(i).toLowerCase());
//                }
//            }
//
//            this.isSetter = true; //The function has been given an input
            //input if there is more than one word (more than one argument)
//
//            //iterates through the musicalTermList to ensure all of the arguments are case insensitive
//            for (int i = 0; i < musicalTermWords; i++) {
//                if (i == 0) {
//                    this.musicalTerm = musicalTermList.get(i).toLowerCase();
//

//                    this.musicalTerm = this.musicalTerm.concat(" " + musicalTermList.get(i).toLowerCase());
//                }
//            }
//
//musicalTermOrigine
//        } else {
//            for (int i = 0; i < musicalTermWords; i++) {
//                switch (count) {
//                    case 0:
//                        if (musicalTermList.get(i).equals(";")) {
//                            count += 1;
//                        } else {
//                            if (this.musicalTermName == null) {
//                                this.musicalTermName = musicalTermList.get(i).toLowerCase();
//                            } else {
//                                this.musicalTermName = this.musicalTermName.concat("Term: " + musicalTermList.get(i).toLowerCase());
//                            }e
//                      {  }
//                        break;
//                    case 1:
//                        if (musicalTermList.get(i).equals(";")) {
//                            count += 1;
//                        } else {
//                            if (this.musicalTermOrigin == null) {
//                                this.musicalTermOrigin = musicalTermList.get(i).toLowerCase();
//                            } else {
//                                this.musicalTermOrigin = this.musicalTermOrigin.concat("Origin: " + musicalTermList.get(i).toLowerCase());
//                            }
//                        }
//                        break;
//                    case 2:
//                        if (musicalTermList.get(i).equals(";")) {
//                            count += 1;e
//                        } else {
//                            if (this.musicalTermCategory == null) {
//                                this.musicalTermCategory = musicalTermList.get(i).toLowerCase();
//                            } else {;
//                                this.musicalTermCategory = this.musicalTermCategory.concat("Category: " + musicalTermList.get(i).toLowerCase());
//                            }
//                        }
//                        break;
//                    case 3:
//                        if (musicalTermList.get(i).equals(";")) {
//                            count += 1;
//                        } else {e
//                            if (this.musicalTermDefinition == null) {
//                                this.musicalTermDefinition = musicalTermList.get(i).toLowerCase();
//                            } else {
//                                this.musicalTermDefinition = this.musicalTermDefinition.concat("Definition: " + musicalTermList.get(i).toLowerCase());
//                            }
//                        }
//                        break;
//
//                }
//            }
//            System.out.println(this.musicalTermName);
//            System.out.println(this.musicalTermOrigin);
//            System.out.println(this.musicalTermCategory);
//            System.out.println(this.musicalTermDefinition);
//            System.out.println(musicalTermList);e
//            System.out.println(this.MusicalTermsMap);
//
//        }
//
//    }


    /**
     * Intialises the hashmap that contains all of the musical terms
     *
     */
//    public void InitializeHashMap() {
//        this.MusicalTermsMap = new HashMap<String, String>();
//
//
//        //preset definitions for testing
//        MusicalTermsMap.put("lento", "Origin: Italian \nCategory: Tempo \nDefinition: The speed at" +
//                " which a passage of music is or should be played");
//        MusicalTermsMap.put("a b", "Origin: Italian \nCategory: Tempo \nDefinition: The speed at" +
//                " which a passage of music is or should be played");
//    }



}
