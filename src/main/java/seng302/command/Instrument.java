package seng302.command;

import seng302.Environment;

/**
 * Instrument command class includes logic for getting and setting the current instrument.
 */
public class Instrument implements Command {

    String type;

    /**
     * Instrument Command Constructor
     * This constructor will be used for setting the instrument.
     * @param instrument the ID number of the selected instrument (if applicable)
     */
    public Instrument(int instrument) {

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
        if (!type.equals(null)) {
            if (type.equals("current")) {
                //Show the currently selected instrument
                env.getTranscriptManager().setResult(env.getPlayer().getInstrument().getName());
            }
        }
    }
}
