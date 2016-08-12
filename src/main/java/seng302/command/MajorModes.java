package seng302.command;

import seng302.Environment;
import seng302.data.Interval;
import seng302.data.Note;

import java.util.HashMap;

/**
 * Created by jmw280 on 10/08/16.
 */
public class MajorModes implements Command {

    private String tonic;
    private String mode;
    private Integer degree;

    private String outputString;


    private HashMap<Integer, String> modes = new HashMap();



    public MajorModes(String tonic, String degree ){
        this.tonic = tonic;
        this.degree = Integer.valueOf(degree);

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
        //String outputString = note + modes.get(degree);



    }







    public void execute(Environment env){

    }
}
