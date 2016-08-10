package seng302.data;

import java.util.HashMap;

/**
 * Created by jmw280 on 10/08/16.
 */
public class ModeHelper {



    private HashMap<Integer, String> modes = new HashMap();



    private ModeHelper(){
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
}
