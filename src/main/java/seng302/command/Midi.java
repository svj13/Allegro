package seng302.command;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.Checker;

/**
 * Created by emily on 2/03/16.
 */
public class Midi implements Command {
    private String s;
    public Midi(String s) {
        this.s = addDefaultOctave(s);

    }
    public void execute(Environment env) {
        if (Checker.isValidNormalNote(s)){
            env.println(Integer.toString(Note.lookup(s).getMidi()));
        } else {
            env.println("Invalid note");
        }
    }

    /**
     * If the last character in the Note is not a number, the number 4 is
     * added as the deafult octave specifier.
     * @param s Note
     * @return Note with octave specified.
     */
    private String addDefaultOctave(String s){
        try{
            String last = s.substring(s.length() - 1);
            Integer.valueOf(last);
        }
        catch(Exception e) {
            s = s + "4";
        }
        return s;
    };
}
