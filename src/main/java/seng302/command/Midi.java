package seng302.command;

import seng302.Environment;
import seng302.data.Notes;
import seng302.utility.Checker;

/**
 * Created by emily on 2/03/16.
 */
public class Midi implements Command {
    private String s;
    Notes notes;
    public Midi(String s) {
        this.s = addDefaultOctave(s);
        notes = Notes.getInstance();
    }
    public void execute(Environment env) {
        if (Checker.isValidNormalNote(s)){
            env.println(Integer.toString(notes.getMidi(s)));
        } else {
            env.println("Invalid note");
        }
    }

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
