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

    public PlayNote(String note){
        this.note = Note.lookup(OctaveUtil.addDefaultOctave(note));
    }


    public void execute(Environment env) {
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            Receiver synthReceiver = synth.getReceiver();
            synth.open();
            ShortMessage myMessage = new ShortMessage();
            myMessage.setMessage(ShortMessage.NOTE_ON, note.getMidi(), 127);
            synthReceiver.send(myMessage, -1);
        } catch (Exception e){
            env.error(e.getMessage());
        }
    }
}

