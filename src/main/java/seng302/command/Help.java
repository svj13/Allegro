package seng302.command;


import seng302.App;
import seng302.Environment;

import seng302.utility.Checker;


public class Help implements Command {

    public Help() {
    }

    public void execute(Environment env) {
        env.getTranscriptManager().setResult("" +
                "crotchet duration : returns the duration of a crotchet in seconds at the given tempo" +
                        " speed in BPM \n" +
                "force set tempo : when followed by a tempo, it will set the given tempo, even if it" +
                        " is outside of the appropriate range \n" +
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
                "version : will return the current version number of the application \n \n " +
                "Dom is a poo poo ");

    }


}