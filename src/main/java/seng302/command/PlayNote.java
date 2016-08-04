package seng302.command;

import seng302.Environment;
import seng302.data.Note;
import seng302.utility.musicNotation.OctaveUtil;

/**
 * Plays a note using the midi library, given a note/midi value, and an optional duration.
 */
public class PlayNote implements Command {

    private String note;
    private String duration;

    /**
     * Constructor for PlayNote
     *
     * @param note Either the note or midi representation of note to be played
     */
    public PlayNote(String note) {
        this.note = note;
    }

    /**
     * Constructor for PlayNote with custom duration
     *
     * @param note     Either the note or midi representation of note to be played
     * @param duration A custom duration to play the note for
     */
    public PlayNote(String note, String duration) {
        this.note = note;
        this.duration = duration;
    }

    public long getLength(Environment env) {
        return 60000 / env.getPlayer().getTempo();
    }

    ;

    /**
     * Uses the music player class to play a given note. Displays errors if the given note or
     * duration is invalid.
     *
     * @param env The environment in which to function.
     */
    public void execute(Environment env) {
        try {
            Note playNote = Note.lookup(OctaveUtil.addDefaultOctave(note));
            if (this.duration == null) {
                env.getPlayer().playNote(playNote);
                env.getTranscriptManager().setResult("Playing " + note + " at "
                        + env.getPlayer().getTempo() + "BPM");
            } else {
                // The duration has been specified by the user
                try {
                    int playDuration = Integer.parseInt(duration);
                    if (playDuration <= 0) {
                        throw new Exception();
                    }
                    env.getPlayer().playNote(playNote, playDuration);
                    env.getTranscriptManager().setResult("Playing " + note + " at "
                            + env.getPlayer().getTempo() + "BPM");
                } catch (Exception e) {
                    // Catches invalid durations
                    env.error("Invalid duration " + duration);
                }
            }
        } catch (Exception e) {
            // Catches invalid notes
            env.error("\'" + note + "\'" + " is not a valid note.");
        }

    }

    public String getHelp() {
        return "When followed by a valid midi number or valid note, " +
                "the corresponding note will be played.";
    }
}

