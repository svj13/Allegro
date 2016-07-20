package seng302.command;

import seng302.Environment;

/**
 * Instrument command class includes logic for getting and setting the current instrument.
 */
public class Instrument implements Command {

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

    }

    @Override
    public void execute(Environment env) {

    }
}
