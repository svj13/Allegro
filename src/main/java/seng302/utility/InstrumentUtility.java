package seng302.utility;

import javax.sound.midi.Instrument;

import seng302.Environment;

/**
 * Created by emily on 29/07/16.
 */
public class InstrumentUtility {

    public static Instrument getInstrumentByName(String instrumentName, Environment env) {
        for (Instrument instrument : env.getPlayer().getAvailableInstruments()) {
            if (instrument.getName().equals(instrumentName)) {
                return instrument;
            }
        }
        return null;
    }

    public static Instrument getInstrumentById(int id, Environment env) {
        return env.getPlayer().getAvailableInstruments()[id];
    }

    public static Instrument getDefaultInstrument(Environment env) {
        return env.getPlayer().getAvailableInstruments()[0];
    }
}
