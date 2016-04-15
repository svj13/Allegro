package seng302.command;


import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.OctaveUtil;

/**
 * Plays a note using the midi library
 */
public class PlayNote implements Command {

    private Note note;
    private Integer duration;
    private String error = "";

    public PlayNote(String note) {
        this.note = Note.lookup(OctaveUtil.addDefaultOctave(note));
    }

    public PlayNote(String note, String duration) {
        this.note = Note.lookup(OctaveUtil.addDefaultOctave(note));
        try {
            this.duration = Integer.parseInt(duration);
        } catch (Exception e) {
            error = "Invalid duration: " + duration;
        }
    }


    public void execute(Environment env) {
        if (!error.equals("")) {
            env.error(error);
        } else if (this.duration == null) {
            env.getPlayer().playNote(note);
        } else {
            env.getPlayer().playNote(note, duration);
        }
    }
}

