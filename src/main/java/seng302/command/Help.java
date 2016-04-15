package seng302.command;


import seng302.Environment;

public class Help implements Command {

    public Help() {
    }

    public void execute(Environment env) {
        env.getTranscriptManager().setResult("" +
                "add musical term: when followed by a musical term in the format of 'name; origin; " +
                        "category; tempo', will add the musical term to the Musical Term " +
                        "dictionary \n" +
                "crotchet duration : returns the duration of a crotchet in seconds at a given tempo" +
                " speed in BPM \n" +
                "force set tempo : when followed by a tempo, it will set the given tempo, even if it" +
                " is outside of the appropriate range \n" +
                "meaning of: when followed by a musical term, will display the defintion of that" +
                " term in the transcript manager. If it does not exist, it will display that it is" +
                " not an existing musical term in the Musical Term dictionary \n" +
                "midi : when followed by a valid note, it will return its corresponding midi number" +
                " within the range of 0-127. If an octave not specified to the note, it will default " +
                "to octave 4. e.g. 'midi' C4 will return 60. It is not case sensitive \n" +
                "note : when followed by a valid midi number (within the range of 0-127), it will" +
                " return the corresponding note name and its octave e.g. 'note 60' will return C4 \n" +
                "play note : when followed by a valid midi number or valid note, the corresponding" +
                " note will be played \n" +
                "semitone up : when followed by a valid note or midi number, it will return" +
                " the note that is a semitone higher  \n" +
                "semitone down : when followed by a valid note or midi number, it will return" +
                " the note that is a semitone lower \n" +
                "set tempo : when followed by a valid tempo will change the tempo to that value \n" +
                "tempo : returns the current set tempo. When the program is launched, it will have" +
                " a default value of 120BPM \n" +
                "version : will return the current version number of the application");

    }


}