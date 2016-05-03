package seng302.utility;

import java.util.ArrayList;

import seng302.data.Term;

/**
 * Created by jmw280 on 17/04/16.
 */
public class MusicalTermsTutorBackEnd {


    private ArrayList<Term> terms = new ArrayList<Term>();


    public void addTerm(Term term){
        terms.add(term);

    }
    public ArrayList<Term> getTerms(){
        return terms;
    }

    public void setTerms(ArrayList<Term> t){
        this.terms = t;
    }

}
