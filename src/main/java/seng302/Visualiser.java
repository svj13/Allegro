package seng302;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;

import seng302.gui.KeyboardPaneController;

/**
 * Created by emily on 31/08/16.
 */
public class Visualiser implements MetaEventListener {

    private Environment env;

    private KeyboardPaneController keyboardController;

    public Visualiser(Environment env) {
        this.env = env;
        this.keyboardController = this.env.getRootController().getKeyboardPaneController();
    }

    @Override
    public void meta(MetaMessage meta) {
        System.out.println(new String(meta.getData()));
    }
}
