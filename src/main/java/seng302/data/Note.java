package seng302.data;

/**
 * Created by jat157 on 2/03/16.
 */
public class Note {
    int midi;
    String note;

    public Note(int midi, String note){
        this.midi = midi;
        this.note = note;

    }

    public String getNote()
    {
        return this.note;
    }

    public Integer getMidi()
    {
        return this.midi;
    }

//    public  getSemitoneHigher(){
//
//    }


}
