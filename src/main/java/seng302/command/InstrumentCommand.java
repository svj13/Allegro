package seng302.command;

import java.util.ArrayList;

import javax.sound.midi.Instrument;

import seng302.Environment;
import seng302.MusicPlayer;

/**
 * InstrumentCommand command class includes logic for getting and setting the current instrument.
 */
public class InstrumentCommand implements Command {

    private String type;

    private String instrumentName;

    private int instrumentId;

    private boolean lookupByName = false;

    /**
     * InstrumentCommand Command Constructor This constructor will be used for setting the
     * instrument.
     *
     * @param type       states that we are setting the instrument
     * @param instrument the name or number of the desired instrument
     */
    public InstrumentCommand(Boolean type, ArrayList<String> instrument) {
        this.type = "setting";

        try {
            instrumentId = Integer.parseInt(String.join("", instrument));
        } catch (Exception e) {
            //Wasn't a number
            lookupByName = true;
            instrumentName = String.join(" ", instrument);
        }

    }

    /**
     * InstrumentCommand Command Constructor This constructor will be used for displaying either the
     * current instrument, or all available instruments.
     *
     * @param type Whether we are displaying the current instrument, or all available instruments.
     */
    public InstrumentCommand(String type) {
        if (type.equals("current")) {
            this.type = "current";
        } else {
            this.type = "all";
        }

    }

    /**
     * Saves the change of instrument to the environment's edit history
     *
     * @param instrument The instrument that has been newly selected
     * @param env        The environment in which to save the change
     */
    private void saveInstrumentEditHistory(Instrument instrument, Environment env) {
        ArrayList<String> editHistoryArray = new ArrayList<String>();
        editHistoryArray.add(env.getPlayer().getInstrument().getName());
        editHistoryArray.add(instrument.getName());
        env.getEditManager().addToHistory("4", editHistoryArray);
    }

    /**
     * Shows all instruments that the user can currently select from for playback.
     *
     * @param env The environment in which the list of instruments is being fetched from
     */
    private void showAllInstruments(Environment env) {
        Instrument[] instruments = env.getPlayer().getAvailableInstruments();
        String instrumentList = "";
        for (int i = 0; i < instruments.length; i++) {
            instrumentList += String.format("%d: %s\n", i, instruments[i].getName());
        }
        env.getTranscriptManager().setResult(instrumentList);
    }

    /**
     * Shows whatever instrument is currently used for playing sound.
     * @param env The environment in which to get the instrument from
     */
    private void showCurrentInstrument(Environment env) {
        Instrument currentInstrument = env.getPlayer().getInstrument();
        env.getTranscriptManager().setResult(currentInstrument.getName());
    }

    /**
     * Used when the user wants to change the playback instrument.
     * If the provided instrument was valid, it is changed. Otherwise, an error is displayed
     * @param env The environment in which the instrument is being changed.
     */
    private void setInstrument(Environment env) {
        Instrument[] instruments = env.getPlayer().getAvailableInstruments();
        Instrument chosenInstrument = null;
        MusicPlayer player = env.getPlayer();
        if (lookupByName) {
            for (Instrument instrument : instruments) {
                if (instrument.getName().equals(instrumentName)) {
                    //use this one
                    chosenInstrument = instrument;
                }
            }
            try {
                env.getTranscriptManager().setResult("Selected InstrumentCommand: " + chosenInstrument.getName());
                saveInstrumentEditHistory(chosenInstrument, env);
                player.setInstrument(chosenInstrument);
            } catch (Exception e) {
                env.error("Invalid instrument name");
            }
        } else {
            //getting the instrument by its number
            try {
                chosenInstrument = player.getAvailableInstruments()[instrumentId];
                env.getTranscriptManager().setResult("Selected InstrumentCommand: " + chosenInstrument.getName());
                saveInstrumentEditHistory(chosenInstrument, env);
                player.setInstrument(chosenInstrument);
            } catch (Exception e) {
                env.error("Invalid instrument number");
            }
        }
    }

    @Override
    /**
     * The execute function delegates to different functions, depending on which type of instrument
     * command has been chosen.
     */
    public void execute(Environment env) {

        if (type.equals("current")) {
            showCurrentInstrument(env);
        }
        if (type.equals("all")) {
            showAllInstruments(env);
        }
        if (type.equals("setting")) {
            setInstrument(env);
        }

    }
}
