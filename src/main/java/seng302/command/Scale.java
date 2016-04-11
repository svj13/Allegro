package seng302.command;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import javax.sound.midi.*;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.Checker;
import seng302.utility.OctaveUtil;

/**
 * Created by emily on 18/03/16.
 */
public class Scale implements Command {
    String search;
    String type;
    String outputType;
    private boolean octaveSpecified;
    private Note note;
    private char[] letters;
    private char currentLetter;
    private String direction = "up";
    private Environment env;


    public Scale(String a, String b, String outputType) {
        this.search = a;
        this.type = b;
        this.outputType = outputType;
        this.letters = "ABCDEFG".toCharArray();
        currentLetter = Character.toUpperCase(search.charAt(0));


    }

    public Scale(String a, String b, String outputType, String direction) {
        this.search = a;
        this.type = b;
        this.outputType = outputType;
        this.letters = "ABCDEFG".toCharArray();
        currentLetter = Character.toUpperCase(search.charAt(0));
        this.direction = direction;
    }

    private void updateLetter() {
        int index = "ABCDEFG".indexOf(currentLetter);
        if (index + 1 > 6) {
            index = -1;
        }
        currentLetter = "ABCDEFG".charAt(index + 1);
    }

    public void execute(Environment env) {
        this.env = env;
        if (Checker.isDoubleFlat(search) || Checker.isDoubleSharp(search)) {
            env.error("Invalid scale: '" + search + ' ' + type + "'.");
        } else {
            try {
                if (OctaveUtil.octaveSpecifierFlag(this.search)) {
                    octaveSpecified = true;
                    this.note = Note.lookup(search);
                } else {
                    octaveSpecified = false;
                    this.note = Note.lookup(OctaveUtil.addDefaultOctave(search));
                }
                try {
                    if (this.outputType.equals("note")) {
                        env.getTranscriptManager().setResult(scaleToString(note.getScale(type)));
                    } else if (this.outputType.equals("midi")) {
                        env.getTranscriptManager().setResult(scaleToMidi(note.getScale(type)));
                    } else { // Play scale.
                        ArrayList<Note> notesToPlay = note.getScale(type);
                        playScale(notesToPlay);
                    }
                } catch (IllegalArgumentException i) {
                    env.error(i.getMessage());
                }
            } catch (Exception e) {
                env.error("Note is not contained in the MIDI library.");
            }
        }
    }

    private String scaleToString(ArrayList<Note> scaleNotes) {
        String notesAsText = "";
        for (Note note : scaleNotes) {
            String currentNote = note.getEnharmonicWithLetter(currentLetter);
            if (octaveSpecified) {
                notesAsText += currentNote + " ";
            } else {
                notesAsText += OctaveUtil.removeOctaveSpecifier(currentNote) + " ";
            }
            updateLetter();
        }
        return notesAsText.trim();
    }

    private String scaleToMidi(ArrayList<Note> scaleNotes) {
        String midiValues = "";
        for (Note note : scaleNotes) {
            midiValues += note.getMidi() + " ";
        }
        return midiValues.trim();
    }

    private void playScale(ArrayList<Note> notes) throws InvalidMidiDataException {
        // We need to alter the notes if it's down or up/down
        if (direction.equals("down")) {
            Collections.reverse(notes);
        }
        if (direction.equals("updown")) {
            ArrayList<Note> reversedNotes = new ArrayList<Note>(notes);
            Collections.reverse(reversedNotes);
            notes.addAll(reversedNotes);
        }

        int instrument = 0;
        // 16 ticks per crotchet note.
        Sequence seq = new Sequence(Sequence.PPQ, 16);
        Track track = seq.createTrack();

        // Set the instrument on channel 0
        ShortMessage sm = new ShortMessage();
        sm.setMessage(ShortMessage.PROGRAM_CHANGE, 0, instrument, 0);
        track.add(new MidiEvent(sm, 0));

        //Problem here: if notesToPlay is updown, the program can't ID what the notes should look like
        //env.getTranscriptManager().setResult(scaleToString(notesToPlay));
        int currenttick = 0;
        for (Note note : notes) {
            addNote(track, currenttick, 16, note.getMidi(), 64);
            currenttick += 16;
        }
        try {
            playScaleSequence(seq);
        } catch (Exception e) {
            env.error("Couldn't play scale.");
        }
    }

    // A convenience method to add a note to the track on channel 0
    public static void addNote(Track track, int startTick,
                               int tickLength, int key, int velocity)
            throws InvalidMidiDataException {
        ShortMessage on = new ShortMessage();
        on.setMessage(ShortMessage.NOTE_ON, 0, key, velocity);
        ShortMessage off = new ShortMessage();
        off.setMessage(ShortMessage.NOTE_OFF, 0, key, velocity);
        track.add(new MidiEvent(on, startTick));
        track.add(new MidiEvent(off, startTick + tickLength));
    }

    private void playScaleSequence(Sequence sequence) throws MidiUnavailableException {
        // Set up the Sequencer and Synthesizer objects
        int tempo = env.getTempo();
        Sequencer sequencer = MidiSystem.getSequencer();
        sequencer.open();
        Synthesizer synthesizer = MidiSystem.getSynthesizer();
        synthesizer.open();
        sequencer.getTransmitter().setReceiver(synthesizer.getReceiver());

        // Specify the sequence to play, and the tempo to play it at
        try {
            sequencer.setSequence(sequence);
        } catch (Exception e) {
            env.error("Couldn't play scale.");
        }
        sequencer.setTempoInBPM(tempo);

//        // Let us know when it is done playing
//        sequencer.addMetaEventListener(new MetaEventListener( ) {
//            public void meta(MetaMessage m) {
//                // A message of this type is automatically sent
//                // when we reach the end of the track
//                if (m.getType( ) == END_OF_TRACK) System.exit(0);
//            }
//        });

        // And start playing now.
        sequencer.start();
    }
}
