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
    private boolean hidden = false;


    @FXML
    private void initialize() {
        keyboardBox.setMaxHeight(200);
        keyboardBox.setMinHeight(200);
    }

    public void create(Environment env) {
        this.env = env;
        setUpKeyboard();

    }

    private void setUpKeyboard() {
        for (Integer i = 60; i < numberOfKeys + 60; i++) {
            Pane key = new TouchPane(i, env);

            key.setPrefWidth(100);
//            key.setStyle("-fx-pref-width: " + String.valueOf(100/numberOfKeys) + "%;");
            keyboardBox.setHgrow(key, Priority.ALWAYS);
            key.setMaxWidth(Double.MAX_VALUE);
            this.keyboardBox.getChildren().add(key);
        }
    }

    public void toggleHideKeyboard() {
        if (hidden) {
            keyboardBox.setMaxHeight(200);
            keyboardBox.setMinHeight(200);
            hidden = false;
        } else {
            keyboardBox.setMaxHeight(0);
            keyboardBox.setMinHeight(0);
            hidden = true;
        }
    }

}
