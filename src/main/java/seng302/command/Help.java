package seng302.command;


import seng302.App;
import seng302.Environment;

import seng302.utility.Checker;


public class Help implements Command {

    public Help() {
    }

    public void execute(Environment env) {
        env.getTranscriptManager().setResult("" +
                "midi : when followed by a valid note, it will return its corresponding midi number" +
                " within the range of 0-127. If an octave not specified to the note, it will default " +
                "to octave 4. e.g. 'midi' C4 will return 60. It is not case sensitive \n" +
                "note : when followed by a valid midi number (within the range of 0-127), it will" +
                " return the corresponding note name and its octave e.g. 'note 60' will return C4 \n" +
                "semitone up : when followed by a valid note or midi number, it will return" +
                " the note that is a semitone higher  \n" +
                "semitone down : when followed by a valid note or midi number, it will return" +
                " the note that is a semitone lower \n" +
                "version : will return the current version number of the application \n \n " +
                "Dom is a poo poo :p");

    }


}