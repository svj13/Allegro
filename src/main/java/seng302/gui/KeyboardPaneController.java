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


}
