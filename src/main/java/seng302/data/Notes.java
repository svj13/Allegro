package seng302.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by team5 on 2/03/16.
 */
public class Notes {
    private ArrayList<Note> notes;

    private List<String> noteNames = new ArrayList<String>(Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"));
    public Notes()
    {
        int current_octave = -1;
        notes = new ArrayList<Note>();
        for (int i =0; i<128; i++){
            notes.add(new Note(i,noteNames.get(i%12).concat(Integer.toString(current_octave))));
            if((i+1)%12 == 0){
                current_octave +=1;
            }
        }
    }

}
