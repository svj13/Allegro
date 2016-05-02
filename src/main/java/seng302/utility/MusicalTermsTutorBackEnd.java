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

    public void removeTerm(String termName) {
        int termToRemove = 0;
        for (Term term : this.terms) {
            if (term.getMusicalTermName().equals(termName)) {
                termToRemove = terms.indexOf(term);
            }
        }
        terms.remove(termToRemove);
        System.out.println(terms);
    }
}
