package seng302;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;

/**
 * Created by emily on 31/08/16.
 */
public class TestSynth implements MetaEventListener {

    public TestSynth() {

    }

    @Override
    public void meta(MetaMessage meta) {
        System.out.println(new String(meta.getData()));
    }
}
