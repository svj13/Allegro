package seng302.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import seng302.Environment;
import seng302.data.Note;
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
    private boolean shift;
    ArrayList<Note> multiNotes;
    private boolean hidden = false;


    @FXML
    private void initialize() {
        keyboardBox.setMaxHeight(200);
        keyboardBox.setMinHeight(200);
    }

    public void create(Environment env) {
        this.env = env;
        setUpKeyboard();
        multiNotes = new ArrayList<Note>();

    }

    private void setUpKeyboard() {
        for (Integer i = 60; i < numberOfKeys + 60; i++) {
            Pane key = new TouchPane(i, env, this);

            key.setPrefWidth(100);
//            key.setStyle("-fx-pref-width: " + String.valueOf(100/numberOfKeys) + "%;");
            keyboardBox.setHgrow(key, Priority.ALWAYS);
            key.setMaxWidth(Double.MAX_VALUE);
            this.keyboardBox.getChildren().add(key);
        }

        keyboardBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SHIFT) {
                    shift = true;
                    System.out.println("pressed shift");
                }

            }
        });

        keyboardBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SHIFT) {
                    System.out.println("lifted shift");
                    env.getPlayer().playNotes(multiNotes);
                    shift = false;
                    multiNotes.clear();
                }

            }
        });


    }

    public boolean getShiftState() {
        return shift;
    }

    public void addMultiNote(Note note) {
        multiNotes.add(note);

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
