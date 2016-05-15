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
    private String keyLabel;
    private boolean displayLabel = false;
    private boolean displayLabelOnAction = false;


    public TouchPane(Integer note, Environment env, KeyboardPaneController kpc) {
        super();
        final Note noteToPlay = Note.lookup(String.valueOf(note));
        final Environment environment = env;
        final KeyboardPaneController keyboardPaneController = kpc;
        me = this;
        setHighlightOff();
        this.setAlignment(Pos.BOTTOM_CENTER);
        this.keyLabel = noteToPlay.getNote();

        setOnTouchPressed(new EventHandler<TouchEvent>() {
            public void handle(TouchEvent event) {
                if (touchId == -1) {
                    touchId = event.getTouchPoint().getId();
                    environment.getPlayer().noteOn(noteToPlay);
                    setHighlightOn();

                }
                event.consume();
            }
        });

        setOnTouchReleased(new EventHandler<TouchEvent>() {
            public void handle(TouchEvent event) {
                if (event.getTouchPoint().getId() == touchId) {
                    touchId = -1;
                }
                environment.getPlayer().noteOff(noteToPlay);
                event.consume();
                setHighlightOff();
            }
        });


        setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (keyboardPaneController.getShiftState() == false) {
                    setHighlightOff();
                    if (displayLabelOnAction) {
                        getChildren().clear();
                    }
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
                if (displayLabelOnAction) {
                    getChildren().add(new Text(keyLabel));
                }
            }
        });


    }

    public void setHighlightOn() {
        this.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: darkblue");
    }

    public void setHighlightOff() {
        this.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: white");
        if (displayLabelOnAction) {
            getChildren().clear();
        }
    }

    public void toggleDisplayLabel() {
        if (displayLabelOnAction) {
            toggleDisplayLabelOnAction();
        }
        if (displayLabel) {
            displayLabel = false;
            this.getChildren().clear();
        } else {
            displayLabel = true;
            this.getChildren().add(new Text(keyLabel));
        }

    }

    public void toggleDisplayLabelOnAction() {
        if (displayLabel) {
            toggleDisplayLabel();
        }
        if (displayLabelOnAction) {
            displayLabelOnAction = false;
            this.getChildren().clear();
        } else {
            displayLabelOnAction = true;
        }
    }

    public void stopDisplayNotes() {
        if (displayLabel) {
            toggleDisplayLabel();
        }
        if (displayLabelOnAction) {
            toggleDisplayLabelOnAction();
        }
    }


}
