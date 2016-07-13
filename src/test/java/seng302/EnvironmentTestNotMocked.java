package seng302;

import org.junit.Before;
import org.junit.Test;

import javafx.beans.property.BooleanProperty;
import seng302.managers.TranscriptManager;
import seng302.utility.EditHistory;
import seng302.utility.MusicalTermsTutorBackEnd;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

/**
 * Created by isabelle on 13/07/16.
 */
public class EnvironmentTestNotMocked {
    private Environment env;

    @Before
    public void setUp() throws Exception {
        env = new Environment();
    }

    @Test
    public void testResetEnvironment() {
        DslExecutor ex = env.getExecutor();
        MusicPlayer player = env.getPlayer();
        TranscriptManager tm = env.getTranscriptManager();
        MusicalTermsTutorBackEnd mtt = env.getMttDataManager();
        EditHistory eh = env.getEditManager();

        env.resetEnvironment();

        assertNotSame(env.getExecutor(), ex);
        assertNotSame(env.getPlayer(), player);
        assertNotSame(env.getTranscriptManager(), tm);
        assertNotSame(env.getMttDataManager(), mtt);
        assertNotSame(env.getEditManager(), eh);

    }

    @Test
    public void testShiftProperty() {
        env.setShiftPressed(true);
        BooleanProperty prop = env.shiftPressedProperty();
        assertTrue(prop.getValue());
    }

    @Test
    public void testShiftPressed() {
        env.setShiftPressed(true);
        Boolean prop = env.isShiftPressed();
        assertTrue(prop);
    }

}
