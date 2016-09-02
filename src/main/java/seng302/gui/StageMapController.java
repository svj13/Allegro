package seng302.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import seng302.Environment;

/**
 * Created by svj13 on 2/09/16.
 */
public class StageMapController {
    @FXML
    AnchorPane stageMap;

    Environment env;

    public void setEnvironment(Environment env) {
        this.env = env;
    }

    public StageMapController() {

    }



}
