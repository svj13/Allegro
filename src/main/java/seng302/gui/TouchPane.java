package seng302.gui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Pane;
import seng302.Environment;
import seng302.data.Note;

/**
 * Created by isabelle on 13/05/16.
 */

public class TouchPane extends Pane {
    private long touchId = -1;
    double touchx, touchy;


    public TouchPane(Integer note, Environment env) {
        super();
        final Note noteToPlay = Note.lookup(String.valueOf(note));
        final Environment environment = env;
        setHighlightOff();

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
                environment.getPlayer().playNote(noteToPlay);


            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                setHighlightOff();
            }
        });

        setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                setHighlightOn();
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
