package seng302.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
    List<TouchPane> clicked;
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
        clicked = new ArrayList<TouchPane>();
        Platform.runLater(new Runnable() {
            public void run() {
                keyboardBox.requestFocus();
            }
        });

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
                    for (TouchPane clickedPane : clicked) {
                        clickedPane.setHighlightOff();
                    }
                    clicked.clear();
                }

            }
        });


    }

    public boolean getShiftState() {
        return shift;
    }

    public void addMultiNote(Note note, TouchPane pane) {
        multiNotes.add(note);
        clicked.add(pane);

    }

    public void toggleHideKeyboard() {
        if (hidden) {
            keyboardBox.setMaxHeight(200);
            keyboardBox.setMinHeight(200);
            keyboardBox.requestFocus();
            hidden = false;
        } else {
            keyboardBox.setMaxHeight(0);
            keyboardBox.setMinHeight(0);
            hidden = true;
            env.getRootController().getTranscriptController().giveFocus();
        }
    }

    public void toggleShowKeyboardNotesAlways() {
        ObservableList<Node> keys = keyboardBox.getChildren();
        for (Node key : keys) {
            if (key instanceof TouchPane) {
                ((TouchPane) key).toggleDisplayLabel();
            }
        }
    }

    public void toggleShowKeyboardNotesAction() {
        ObservableList<Node> keys = keyboardBox.getChildren();
        for (Node key : keys) {
            if (key instanceof TouchPane) {
                ((TouchPane) key).toggleDisplayLabelOnAction();
            }
        }
    }

    public void stopShowingNotesOnKeyboard() {
        ObservableList<Node> keys = keyboardBox.getChildren();
        for (Node key : keys) {
            if (key instanceof TouchPane) {
                ((TouchPane) key).stopDisplayNotes();
            }
        }
    }
}
