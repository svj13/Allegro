package seng302.gui;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import seng302.Environment;
import seng302.data.Note;

/**
 * Created by isabelle on 13/05/16.
 */

public class TouchPane extends StackPane {
    private long touchId = -1;
    double touchx, touchy;
    KeyboardPaneController kpc;
    TouchPane me;


    public TouchPane(Integer note, Environment env, KeyboardPaneController kpc) {
        super();
        final Note noteToPlay = Note.lookup(String.valueOf(note));
        final Environment environment = env;
        final KeyboardPaneController keyboardPaneController = kpc;
        me = this;
        setHighlightOff();

        this.getChildren().add(new Text(noteToPlay.getNote()));
        this.setAlignment(Pos.BOTTOM_CENTER);

        setOnTouchPressed(new EventHandler<TouchEvent>() {
            public void handle(TouchEvent event) {
                if (touchId == -1) {
                    touchId = event.getTouchPoint().getId();
                    touchx = event.getTouchPoint().getSceneX() - getTranslateX();
                    touchy = event.getTouchPoint().getSceneY() - getTranslateY();
                    environment.getPlayer().playNote(noteToPlay);

                }
                event.consume();
            }
        });

        setOnTouchReleased(new EventHandler<TouchEvent>() {
            public void handle(TouchEvent event) {
                if (event.getTouchPoint().getId() == touchId) {
                    touchId = -1;
                }
                event.consume();
            }
        });

        setOnTouchMoved(new EventHandler<TouchEvent>() {
            public void handle(TouchEvent event) {
                if (event.getTouchPoint().getId() == touchId) {
                    setTranslateX(event.getTouchPoint().getSceneX() - touchx);
                    setTranslateY(event.getTouchPoint().getSceneY() - touchy);
                }
                event.consume();
            }
        });

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {



            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (keyboardPaneController.getShiftState() == false) {
                    setHighlightOff();
                }

            }
        });

        setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (keyboardPaneController.getShiftState() == true) {
                    System.out.println("add note");
                    keyboardPaneController.addMultiNote(noteToPlay, me);
                    setHighlightOn();
                } else {
                    environment.getPlayer().playNote(noteToPlay);
                    setHighlightOn();
                }

            }
        });


    }

    public void setHighlightOn() {
        this.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: darkblue");
    }

    public void setHighlightOff() {
        this.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: white");
    }
}
