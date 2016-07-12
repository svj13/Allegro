package seng302.data;


public class Term {

    private String musicalTermName;
    private String musicalTermCategory;
    private String musicalTermOrigin;
    private String musicalTermDefinition;


    public Term(String name, String cat, String origin, String definition) {
        this.musicalTermName = name;
        this.musicalTermCategory = cat;
        this.musicalTermOrigin = origin;
        this.musicalTermDefinition = definition;
    }


    public String getMusicalTermName() {
        return this.musicalTermName;
    }

    public String getMusicalTermCategory() {
        return this.musicalTermCategory;
    }

    public String getMusicalTermOrigin() {
        return this.musicalTermOrigin;
    }

    public String getMusicalTermDefinition() {
        return this.musicalTermDefinition;
    }


}