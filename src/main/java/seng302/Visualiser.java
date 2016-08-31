package seng302;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;

import seng302.gui.KeyboardPaneController;

/**
 * Created by emily on 31/08/16.
 */
public class Visualiser implements MetaEventListener {


    public KeyboardPaneController keyboardController;

    private Environment env;

    public Visualiser(Environment env) {
        this.env = env;
    }


    @Override
    public void meta(MetaMessage meta) {
        String message = new String(meta.getData());

        String[] messageParts = message.split(" ");

        int midiValue = Integer.parseInt(messageParts[0]);
        boolean isOn = false;

        if (messageParts[1].equals("on")) {
            isOn = true;
        }

        if (isOn) {
            env.getRootController().getKeyboardPaneController().highlightKey(midiValue);
        } else {
            env.getRootController().getKeyboardPaneController().removeHighlight(midiValue);
        }
    }
}
