package seng302;

import java.util.ArrayList;
import java.util.Collection;

import javax.sound.midi.*;

import seng302.data.Note;

/**
 * The Music Player class handles all sound that is produced by the program.
 */
public class MusicPlayer {
    Sequencer seq;

    /**
     * Default tempo is 120 BPM.
     */
    private int tempo = 120;

    /**
     * Music Player constructor opens the sequencers and synthesizer. It also sets the receiver.
     */
    public MusicPlayer() {
        try {
            this.seq = MidiSystem.getSequencer();
            seq.open();
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            seq.getTransmitter().setReceiver(synthesizer.getReceiver());
        } catch (MidiUnavailableException e) {

            System.err.println("Can't play Midi sound at the moment.");
        }
    }

    /**
     * Plays an array of notes directly after each other.
     *
     * @param notes The notes in the order to be played.
     * @param pause The number of ticks to pause between notes. 16 ticks = 1 crotchet beat.
     */
    public void playNotes(ArrayList<Note> notes, int pause) {
        try {
            int instrument = 1;
            // 16 ticks per crotchet note.
            Sequence sequence = new Sequence(Sequence.PPQ, 16);
            Track track = sequence.createTrack();

            // Set the instrument on channel 0
            ShortMessage sm = new ShortMessage();
            sm.setMessage(ShortMessage.PROGRAM_CHANGE, 0, instrument, 0);
            track.add(new MidiEvent(sm, 0));

            int currenttick = 0;
            for (Note note : notes) {
                addNote(track, currenttick, 16, note.getMidi(), 64);
                currenttick += (16 + pause);
            }
            playSequence(sequence);

        } catch (InvalidMidiDataException e) {
            System.err.println("The notes you are trying to play were invalid");
        }
    }

    /**
     * Convenience method to play notes with no pause.
     *
     * @param notes The notes to play.
     */
    public void playNotes(ArrayList<Note> notes) {
        playNotes(notes, 0);
    }

    /**
     * Plays a collection of notes at the same time.
     *
     * @param notes The notes to be played simultaneously - eg a chord.
     */
    public void playSimultaneousNotes(Collection<Note> notes) {
        try {
            int instrument = 1;
            Sequence sequence = new Sequence(Sequence.PPQ, 16);
            Track track = sequence.createTrack();

            ShortMessage sm = new ShortMessage();
            sm.setMessage(ShortMessage.PROGRAM_CHANGE, 0, instrument, 0);
            track.add(new MidiEvent(sm, 0));

            // Add all notes to the start of the sequence
            for (Note note:notes) {
                addNote(track, 0, 16, note.getMidi(), 64);
            }
            playSequence(sequence);

        } catch (InvalidMidiDataException e) {
            System.err.println("The notes you are trying to play were invalid");
        }

    }

    /**
     * Convenience method to convert a single note into an array list so the method playNotes() can
     * be used.
     *
     * @param note The note to be played.
     */
    public void playNote(Note note) {
        ArrayList<Note> notes = new ArrayList<Note>();
        notes.add(note);
        playNotes(notes);
    }

    /**
     * Temporaily sets the tempo to meet the required duration
     *
     * @param note     The note to play
     * @param duration The duration in milliseconds to play the note for.
     */
    public void playNote(Note note, int duration) {
        ArrayList<Note> notes = new ArrayList<Note>();
        notes.add(note);
        int oldTempo = getTempo();
        setTempo(1000 / duration * 60);
        playNotes(notes);
        setTempo(oldTempo);

    }


    /**
     * A convenience method to add a note to the track on channel 0
     *
     * @param track      The track to add the note to.
     * @param startTick  Tick number to begin the note on.
     * @param tickLength How many ticks to play the note for.
     * @param key        The midi value of the note.
     * @param velocity   The volume the note will be played at.
     */
    private void addNote(Track track, int startTick,
                         int tickLength, int key, int velocity)
            throws InvalidMidiDataException {
        ShortMessage on = new ShortMessage();
        on.setMessage(ShortMessage.NOTE_ON, 0, key, velocity);
        ShortMessage off = new ShortMessage();
        off.setMessage(ShortMessage.NOTE_OFF, 0, key, velocity);
        track.add(new MidiEvent(on, startTick));
        track.add(new MidiEvent(off, startTick + tickLength));
    }

    /**
     * Sets the sequence to be played and the tempo. Then the sequencer starts playing.
     *
     * @param sequence The sequence containing the track to be played.
     */
    private void playSequence(Sequence sequence) {
        try {
            seq.setSequence(sequence);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Can't play Midi sound at the moment.");
        }
        seq.setTempoInBPM(tempo);
        seq.start();
    }

    /**
     * Returns the current tempo.
     *
     * @return current tempo.
     */
    public int getTempo() {
        return tempo;
    }

    /**
     * Sets the tempo to the given int.
     *
     * @param tempo The tempo to be changed to.
     */
    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public void stop() {

        seq.stop();
    }
}
