package seng302.command;


import seng302.Environment;

public class Help implements Command {

    String keyword;

    public Help() {
        keyword = "";
    }

    public Help(String keyword) {
        this.keyword = keyword;
    }

    public void execute(Environment env) {
        String result = "";

        if (keyword.equals("note")) {
            result = "When followed by a valid midi number (within the range of 0-127), it will" +
                    " return the corresponding note name and its octave e.g. 'note 60' will return C4.";
        } else if (keyword.equals("add musical term")) {
            result = "When followed by a musical term in the format of 'name; origin; " +
                    "category; definition', it will add the musical term to the Musical Term " +
                    "dictionary.";
        } else if (keyword.equals("all enharmonics")) {
            result = "Return all the enharmonics of a given note.";
        } else if (keyword.equals("crotchet duration")) {
            result = "Returns the duration of a crotchet in milliseconds at the current tempo.";
        } else if (keyword.equals("enharmonic above")) {
            result = "Returns the enharmonic that corresponds to the same note, a " +
                    "'letter' above the current note. Returns an error should there be no higher " +
                    "enharmonic. ";
        } else if (keyword.equals("")) {
            result = "" +
                    "add musical term : When followed by a musical term in the format of 'name; origin; " +
                    "category; definition', will add the musical term to the Musical Term " +
                    "dictionary. \n" +
                    "all enharmonics : Returns all of the enharmonics of a given note. \n" +
                    "crotchet duration : Returns the duration of a crotchet in milliseconds at the current tempo.\n" +
                    "enharmonic above : Returns the enharmonic that corresponds to the same note, a " +
                    "'letter' above the current note. Returns an error should there be no higher " +
                    "enharmonic. \n" +
                    "enharmonic below : Returns the enharmonic that corresponds to the sam note, a " +
                    "'letter' below the current note. Returns an error should there be no lower" +
                    "enharmonic. \n" +
                    "force set tempo : When followed by a tempo, it will set the given tempo, even if it" +
                    " is outside of the appropriate range. \n" +
                    "meaning of : When followed by a musical term, will display the defintion of that" +
                    " term in the transcript manager. If it does not exist, it will display that it is" +
                    " not an existing musical term in the Musical Term dictionary. \n" +
                    "midi : When followed by a valid note, it will return its corresponding midi number" +
                    " within the range of 0-127. If an octave not specified to the note, it will default " +
                    "to octave 4. e.g. 'midi' C4 will return 60. It is not case sensitive. \n" +
                    "note : When followed by a valid midi number (within the range of 0-127), it will" +
                    " return the corresponding note name and its octave e.g. 'note 60' will return C4. \n" +
                    "play note : When followed by a valid midi number or valid note, the corresponding" +
                    " note will be played. \n" +
                    "semitone up : When followed by a valid note or midi number, it will return" +
                    " the note that is a semitone higher.  \n" +
                    "semitone down : When followed by a valid note or midi number, it will return" +
                    " the note that is a semitone lower. \n" +
                    "set tempo : When followed by a valid tempo will change the tempo to that value. \n" +
                    "tempo : Returns the current set tempo. When the program is launched, it will have" +
                    " a default value of 120BPM. \n" +
                    "version : Will return the current version number of the application.";
        }

        env.getTranscriptManager().setResult(result);

    }


}