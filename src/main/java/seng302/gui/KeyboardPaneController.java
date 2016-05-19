package seng302.gui;

import org.controlsfx.control.PopOver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import javafx.application.Platform;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seng302.Environment;
import seng302.data.Note;
import seng302.command.Enharmonic;

/**
 * Created by team 5 on 13/05/16.
 */
public class KeyboardPaneController {

    @FXML
    private HBox keyboardBox;

    @FXML
    private StackPane keyboardStack;

    @FXML
    private TitledPane titlePane;

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

//        BorderPane titleAndMenu = new BorderPane();
//        titleAndMenu.setLeft(new Label("Keyboard"));
//        Button settingsButton = new Button("Settings");
//
//        titleAndMenu.setCenter(new Label(" "));
//
//        titleAndMenu.setRight(settingsButton);
//        titleAndMenu.prefWidthProperty().bind(titlePane.widthProperty());
//        titleAndMenu.setManaged(true);

//        HBox titleAndMenu = new HBox();
//        Label keyboardTitle = new Label("Keyboard");
//        Button settings = new Button("Settings");
//        Region blankSpace = new Region();
//        blankSpace.setMaxWidth(Double.MAX_VALUE);
//        titleAndMenu.getChildren().add(keyboardTitle);
//        titleAndMenu.getChildren().add(blankSpace);
//        titleAndMenu.getChildren().add(settings);
//        titleAndMenu.setHgrow(blankSpace, Priority.ALWAYS);
//        titlePane.setGraphic(titleAndMenu);











        this.env = env;
        setUpKeyboard();
        multiNotes = new ArrayList<Note>();
        clicked = new ArrayList<TouchPane>();
        Platform.runLater(new Runnable() {
            public void run() {
                keyboardBox.requestFocus();
            }
        });
        env.getPlayer().initKeyboardTrack();

    }

    private void setUpKeyboard() {
        for (Integer i = 60; i < numberOfKeys + 60; i++) {
            Pane key = new TouchPane(i, env, this);

            key.setPrefWidth(100);
            keyboardBox.setHgrow(key, Priority.ALWAYS);
            key.setMaxWidth(Double.MAX_VALUE);
            keyboardBox.getChildren().add(key);




        }

        keyboardBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SHIFT) {
                    shift = true;
                }

            }
        });

        keyboardBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SHIFT) {
                    env.getPlayer().playSimultaneousNotes(multiNotes);
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
