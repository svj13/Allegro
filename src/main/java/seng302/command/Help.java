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

    public float getLength(Environment env) {
        return 0;
    };

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
                    "'letter' above the current note.";
        } else if (keyword.equals("enharmonic below")) {
            result = "Returns the enharmonic that corresponds to the same note, a " +
                    "'letter' below the current note.";
        } else if (keyword.equals("force set tempo")) {
            result = "When followed by a tempo, it will set the given tempo, even if it" +
                    " is outside of the recommended range of 20-300BPM.";
        } else if (keyword.equals("meaning of")) {
            result = "When followed by a musical term, will display the definition of that" +
                    " term.";
        } else if (keyword.equals("midi")) {
            result = "When followed by a valid note, it will return its corresponding midi number" +
                    " within the range of 0-127. If an octave not specified to the note, it will default " +
                    " to octave 4. e.g. 'midi' C4 will return 60. It is not case sensitive. ";
        } else if (keyword.equals("play note")) {
            result = "When followed by a valid midi number or valid note, the corresponding" +
                    " note will be played.";
        } else if (keyword.equals("semitone up")) {
            result = "semitone up : When followed by a valid note or midi number, it will return" +
                    " the note that is a semitone higher.";
        } else if (keyword.equals("semitone down")) {
            result = "semitone down : When followed by a valid note or midi number, it will return" +
                    " the note that is a semitone lower.";
        } else if (keyword.equals("set tempo")) {
            result = "When followed by a valid tempo (20-300BPM) will change the tempo to that value.";
        } else if (keyword.equals("tempo")) {
            result = "Returns the current tempo. When the program is launched, it will have" +
                    " a default value of 120BPM.";
        } else if (keyword.equals("version")) {
            result = "Returns the current version number of the application.";
        } else if (keyword.equals("origin of")) {
            result = "When followed by a musical term, will display the origin of that" +
                    " term.";
        } else if (keyword.equals("category of")) {
            result = "When followed by a musical term, will display the category of that" +
                    " term.";
        } else if (keyword.equals("scale")) {
            result = "When followed by a valid scale (made up of a note and a scale type) " +
                    "the corresponding scale notes will be returned. e.g scale c major. ";
        } else if (keyword.equals("midi scale")) {
            result = "When followed by a valid scale (made up of a note and a scale type) " +
                    " the corresponding scale midi notes will be returned. ";
        } else if (keyword.equals("play scale")) {
            result = "When followed by a valid scale (made up of a note and a scale type)" +
                    " the corresponding scale will be played. Options for the number of octaves and direction" +
                    " can be given. E.g play scale c major [number of octaves] [up|updown|down]";
        } else if (keyword.equals("")) {
            result = "" +
                    "\nadd musical term:\nWhen followed by a musical term in the format of 'name; origin; " +
                    "category; definition', will add the musical term to the Musical Term " +
                    "dictionary. \n\n" +
                    "all enharmonics:\nReturns all of the enharmonics of a given note. \n\n" +
                    "category of:\nWhen followed by a musical term, it returns the category of that term. \n\n" +
                    "crotchet duration:\nReturns the duration of a crotchet in milliseconds at the current tempo.\n\n" +
                    "enharmonic above:\nReturns the enharmonic that corresponds to the same note, a " +
                    "'letter' above the current note.\n\n" +
                    "enharmonic below : Returns the enharmonic that corresponds to the same note, a " +
                    "'letter' below the current note. \n\n" +
                    "force set tempo:\nWhen followed by a tempo, it will set the given tempo, even if it" +
                    " is outside of the recommended range of 20-300BPM. \n\n" +
                    "meaning of:\nWhen followed by a musical term, it returns the definition of that" +
                    " term. \n\n" +
                    "midi:\nWhen followed by a valid note, it will return its corresponding midi number" +
                    " within the range of 0-127. If an octave not specified to the note, it will default " +
                    "to octave 4. e.g. 'midi' C4 will return 60. \n\n" +
                    "midi scale:\nWhen followed by a valid scale (made up of a note and a scale type) " +
                    " the corresponding scale midi notes will be returned. \n\n" +
                    "note:\nWhen followed by a valid midi number (within the range of 0-127), it will" +
                    " return the corresponding note name and its octave e.g. 'note 60' will return C4. \n\n" +
                    "origin of:\nWhen followed by a musical term, it returns the origin of that term. \n\n" +
                    "play note:\nWhen followed by a valid midi number or valid note, the corresponding" +
                    " note will be played. \n\n" +
                    "play scale:\nWhen followed by a valid scale (made up of a note and a scale type) " +
                    "the corresponding scale will be played. Options for the number of octaves and direction " +
                    "can be given. E.g play scale c major [number of octaves] [up|updown|down] \n\n" +
                    "scale:\nWhen followed by a valid scale (made up of a note and a scale type) " +
                    " the corresponding scale notes will be returned. \n\n" +
                    "semitone up:\nWhen followed by a valid note or midi number, it will return" +
                    " the note that is a semitone higher.  \n\n" +
                    "semitone down:\nWhen followed by a valid note or midi number, it will return" +
                    " the note that is a semitone lower. \n\n" +
                    "set tempo:\nWhen followed by a valid tempo (20-300BPM) will change the tempo to that value. \n\n" +
                    "tempo:\nReturns the current tempo. When the program is launched, it will have" +
                    " a default value of 120BPM. \n\n" +
                    "version:\nReturns the current version number of the application.";
        }

        env.getTranscriptManager().setResult(result);

    }


}