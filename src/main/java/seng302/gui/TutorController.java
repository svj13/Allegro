package seng302.gui;

import java.awt.event.ActionEvent;

import javafx.scene.layout.HBox;
import javafx.util.Pair;
import seng302.Environment;
import seng302.utility.TutorManager;
import seng302.utility.TutorRecord;

public class TutorController {

    public Environment env;

    public TutorManager manager;

    public TutorRecord record;

    public TutorController() {

    }

    public void create(Environment env){
        this.env = env;
        manager = env.getIrtManager();
    }

}