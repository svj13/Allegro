package seng302.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import seng302.Environment;
import seng302.command.Enharmonic;

/**
 * Created by team 5 on 13/05/16.
 */
public class KeyboardPaneController {


    @FXML
    private AnchorPane basePane;

    @FXML
    private HBox keyboardBox;

    Environment env;

    private Integer numberOfKeys = 24;


    @FXML
    private void initialize() {
        for (Integer i = 0; i < numberOfKeys; i++) {
            Pane key = new Pane();
            key.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
            key.setPrefWidth(100);
//            key.setStyle("-fx-pref-width: " + String.valueOf(100/numberOfKeys) + "%;");
            keyboardBox.setHgrow(key, Priority.ALWAYS);
            key.setMaxWidth(Double.MAX_VALUE);
            this.keyboardBox.getChildren().add(key);
        }

    }

    public void setEnv(Environment env) {
        this.env = env;
    }


}
