package seng302.command;

import java.util.ArrayList;

import seng302.Environment;
import seng302.MusicPlayer;

/**
 * Instrument command class includes logic for getting and setting the current instrument.
 */
public class Instrument implements Command {

    String type;

    String instrumentName;

    int instrumentId;

    boolean lookupByName = false;

    /**
     * Instrument Command Constructor
     * This constructor will be used for setting the instrument.
     * @param type states that we are setting the instrument
     * @param instrument the name or number of the desired instrument
     */
    public Instrument(Boolean type, ArrayList<String> instrument) {
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
     * Instrument Command Constructor This constructor will be used for displaying either the
     * current instrument, or all available instruments.
     *
     * @param type Whether we are displaying the current instrument, or all available instruments.
     */
    public Instrument(String type) {
        if (type.equals("current")) {
            this.type = "current";
        } else {
            this.type = "all";
        }

    }

    @Override
    public void execute(Environment env) {
        MusicPlayer player = env.getPlayer();
        javax.sound.midi.Instrument[] instruments = player.getAvailableInstruments();
        if (type.equals("current")) {
            //Show the currently selected instrument
            env.getTranscriptManager().setResult(player.getInstrument().getName());
        }
        if (type.equals("all")) {
                String instrumentList = "";
                for (int i = 0; i < instruments.length; i++) {
                    instrumentList += String.format("%d: %s\n", i, instruments[i].getName());
                }
                env.getTranscriptManager().setResult(instrumentList);
        }
        if (type.equals("setting")) {
            javax.sound.midi.Instrument chosenInstrument = null;
            if (lookupByName) {
                for (javax.sound.midi.Instrument instrument : instruments) {
                    if (instrument.getName().equals(instrumentName)) {
                        //use this one
                        chosenInstrument = instrument;
                    }
                }
                try {
                    env.getTranscriptManager().setResult("Selected Instrument: " + chosenInstrument.getName());
                    player.setInstrument(chosenInstrument);
                } catch (Exception e) {
                    env.error("Invalid instrument name");
                }
            } else {
                //getting the instrument by its number
                try {
                    chosenInstrument = player.getAvailableInstruments()[instrumentId];
                    env.getTranscriptManager().setResult("Selected Instrument: " + chosenInstrument.getName());
                    player.setInstrument(chosenInstrument);
                } catch (Exception e) {
                    env.error("Invalid instrument number");
                }
            }

        }

    }
}
