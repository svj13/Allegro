package seng302.gui;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import seng302.Environment;
import seng302.data.Note;

/**
 * Created by isabelle on 13/05/16.
 */

public class TouchPane extends StackPane {
    private long touchId = -1;
    TouchPane me;
    private String keyLabel;
    private boolean displayLabel = false;
    private boolean displayLabelOnAction = false;
    Note noteToPlay;
    private boolean isblackKey;


    public TouchPane(Integer note, Environment env, KeyboardPaneController kpc) {
        super();
        noteToPlay = Note.lookup(String.valueOf(note));
        final Environment environment = env;
        final KeyboardPaneController keyboardPaneController = kpc;
        me = this;
        setHighlightOff();
        this.setFocusTraversable(false);
        this.setAlignment(Pos.BOTTOM_CENTER);
        this.keyLabel = noteToPlay.getNote();
        this.isblackKey = false;

        EventHandler<TouchEvent> touchPress = event -> {
            if (touchId == -1) {
                touchId = event.getTouchPoint().getId();
                if (kpc.isPlayMode()) {
                    environment.getPlayer().noteOn(noteToPlay);
                    setHighlightOn();
                } else {
                    String prev = env.getRootController().getTranscriptController().txtCommand.getText();
                    String newText = prev + " " + this.getNoteValue().getNote();
                    env.getRootController().getTranscriptController().txtCommand.setText(newText);
                }
            }
            event.consume();
        };

        EventHandler<TouchEvent> touchRelease = event -> {
            if (event.getTouchPoint().getId() == touchId) {
                touchId = -1;
                if (kpc.isPlayMode()) {
                    environment.getPlayer().noteOff(noteToPlay);
                    setHighlightOff();
                } else {
                    env.getRootController().getTranscriptController().giveFocus();
                }
            }
            event.consume();
        };

        setOnTouchReleased(touchRelease);
        setOnTouchPressed(touchPress);

        setOnMouseReleased(event -> {
            if (kpc.isPlayMode()) {
                if (!environment.isShiftPressed()) {
                    environment.getPlayer().noteOff(noteToPlay);
                    setHighlightOff();
                    if (displayLabelOnAction) {
                        getChildren().clear();
                    }
                }
            } else {
                env.getRootController().getTranscriptController().giveFocus();
            }
        });

        setOnMousePressed(event -> {
            if (kpc.isPlayMode()) {
                if (environment.isShiftPressed()) {
                    keyboardPaneController.addMultiNote(noteToPlay, me);
                    setHighlightOn();
                } else {
                    environment.getPlayer().noteOn(noteToPlay);
                    setHighlightOn();
                }
                if (displayLabelOnAction) {
                    getChildren().add(new Text(keyLabel));
                }
            } else {
                String prev = env.getRootController().getTranscriptController().txtCommand.getText();
                String newText = prev + " " + this.getNoteValue().getNote();
                env.getRootController().getTranscriptController().txtCommand.setText(newText);
            }
        });

    }

    /**
     * Turn click highlight on.
     */
    public void setHighlightOn() {
        this.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: #0093ff");
    }

    /**
     * Turn click highlight off.
     */
    public void setHighlightOff() {
        if (this.isblackKey) {
            this.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: #b0faaf");
        } else {
            this.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: white");
        }
        if (displayLabelOnAction) {
            getChildren().clear();
        }
    }

    /**
     * displays the keynote name on the keyboard when display label is selected
     */
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

    /**
     * displays a picture corresponding to a particular scale on the keys. Used by
     * toggleScaleKeys() in KeyBoardPaneController
     */
    public void toggleScaleNotes(String imagePath, String imageId) {

        Image symbol = new Image(getClass().getResourceAsStream(imagePath), 10, 10, true, true);
        Node image = new ImageView(symbol);
        image.setId(imageId);
        this.getChildren().add(image);

    }

    /**
     * Searches for and removes all children with the given ID This ID belongs to scale indicator
     * images
     *
     * @param id Either "firstScale" or "secondScale" - the scale indicators to be removed.
     */
    public void removeScaleImage(String id) {
        while (this.getChildren().contains(this.lookup("#" + id))) {
            this.getChildren().removeAll(this.lookup("#" + id));
        }
    }

    /**
     * displays the label of the key on click when show on click is selected
     */

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

    public Note getNoteValue() {
        return noteToPlay;
    }

    public void setBlackKey(boolean value) {
        this.isblackKey = value;
    }

}
