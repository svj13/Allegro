package seng302.command;

import seng302.Environment;
import seng302.data.Note;

import java.util.HashMap;

/**
 * Created by jmw280 on 10/08/16.
 */
public class MajorMode implements Command {

    private Note tonic;
    private String mode;
    private Integer degree;

    private String outputString;


    private HashMap<Integer, String> modes = new HashMap();



    private MajorMode(Note tonic, Integer degree ){
        this.tonic = tonic;
        this.degree = degree;

        generateModes();
    }

    private void generateModes(){
        modes.put(1, "Ionian");
        modes.put(2, "Dorian");
        modes.put(3, "Phrygian");
        modes.put(4, "Lydian");
        modes.put(5, "Mixolydian");
        modes.put(6, "Aeolian");
        modes.put(7, "Locrian");
    }




    public void getCorresponingScale(Note key, Integer degree){
        //next note = get next note in scale * degree
        String outputString = note + modes.get(degree);



    }







    public void execute(Environment env){

    }
}
