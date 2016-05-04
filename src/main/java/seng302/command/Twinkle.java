package seng302.command;

import java.util.ArrayList;

import seng302.Environment;
import seng302.data.Note;

/**
 * Created by isabelle on 4/05/16.
 */
public class Twinkle implements Command {


    public float getLength(Environment env) {
        int tempo = env.getPlayer().getTempo();
        float crotchetLength = 60000 / tempo;
        return 14 * crotchetLength;
    }

    public void execute(Environment env) {
        ArrayList<Note> song = new ArrayList<Note>();
        song.add(Note.lookup("C4"));
        song.add(Note.lookup("C4"));
        song.add(Note.lookup("G4"));
        song.add(Note.lookup("G4"));
        song.add(Note.lookup("A4"));
        song.add(Note.lookup("A4"));
        song.add(Note.lookup("G4"));
        song.add(Note.lookup("F4"));
        song.add(Note.lookup("F4"));
        song.add(Note.lookup("E4"));
        song.add(Note.lookup("E4"));
        song.add(Note.lookup("D4"));
        song.add(Note.lookup("D4"));
        song.add(Note.lookup("C4"));
        env.getPlayer().playNotes(song);
        env.getTranscriptManager().setResult("Playing...");
    }


}
