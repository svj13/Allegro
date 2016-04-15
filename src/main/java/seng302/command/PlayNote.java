package seng302.command;


import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.Checker;
import seng302.utility.OctaveUtil;

/**
 * Plays a note using the midi library
 */
public class PlayNote implements Command {

    private String note;
    private String duration;

    public PlayNote(String note) {
        this.note = note;
    }

    public PlayNote(String note, String duration) {
        this.note = note;
        this.duration = duration;
    }


    public void execute(Environment env) {
        try {
            Note playNote = Note.lookup(OctaveUtil.addDefaultOctave(note));
            if (this.duration == null) {
                env.getPlayer().playNote(playNote);
            } else {
                try {
                    int playDuration = Integer.parseInt(duration);
                    if (playDuration <= 0) {
                        throw new Exception();
                    }
                    env.getPlayer().playNote(playNote, playDuration);
                } catch (Exception e) {
                    env.error("Invalid duration " + duration);
                }
            }
        } catch (Exception e) {
            env.error("\'" + note + "\'" + " is not a valid note.");
        }

    }
}

