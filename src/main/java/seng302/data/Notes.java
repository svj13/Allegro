package seng302.data;

import java.util.*;

/**
 * Created by team5 on 2/03/16.
 */
public class Notes {
    private HashMap<String,Note> notes;

    private List<String> noteNames = new ArrayList<String>(Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"));
    public Notes()
    {
        int current_octave = -1;
        notes = new HashMap<String, Note>();
        for (int i =0; i<128; i++){
            Note temp = new Note(i,noteNames.get(i%12).concat(Integer.toString(current_octave)));
            notes.put((noteNames.get(i%12).concat(Integer.toString(current_octave))),(temp));
            notes.put(Integer.toString(i),(temp));
            if((i+1)%12 == 0){
                current_octave +=1;
            }
        }
    }

    public void semitone_up(String initial_note_name)
    {

    }


}
