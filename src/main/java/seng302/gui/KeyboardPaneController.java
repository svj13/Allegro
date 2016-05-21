package seng302.gui;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.RangeSlider;
import org.controlsfx.control.spreadsheet.StringConverterWithFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
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
import seng302.utility.NoteRangeSlider;

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

    @FXML
    private Button settingsButton;

    Environment env;
    PopOver pop;
    RangeSlider rangeSlider;
    Label notes;

    private Integer numberOfKeys = 24;
    private boolean shift;
    ArrayList<Note> multiNotes;
    List<TouchPane> clicked;
    private boolean hidden = false;
    Integer bottomNote;
    Integer topNote;


    @FXML
    private void initialize() {
        keyboardBox.setMaxHeight(200);
        keyboardBox.setMinHeight(200);
        bottomNote = 60;
        topNote = 72;
        VBox settings = new VBox();
        HBox rangeHeading = new HBox();
        rangeHeading.setSpacing(5);
        Label keyboardRange = new Label("Keyboard Range:");
        rangeHeading.getChildren().add(keyboardRange);
        settings.getChildren().add(rangeHeading);
        notes = new Label("");
        NoteRangeSlider slider = new NoteRangeSlider(notes, 12);
        settings.getChildren().add(slider);

        /**
         * Generate Keyboard notes based on value of range slider.
         */
        slider.lowValueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                bottomNote = newValue.intValue();
                setUpKeyboard();
            }
        });
        slider.highValueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                topNote = newValue.intValue();
                setUpKeyboard();
            }
        });
        rangeHeading.getChildren().add(notes);

        settings.setSpacing(5);
        settings.setPadding(new Insets(10));


        pop = new PopOver(settings);


        settings.getChildren().add(new Label("Note names:"));
        ToggleGroup notenames = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Never show");
        rb1.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == true) {
                    stopShowingNotesOnKeyboard();
                }
            }
        });
        rb1.setToggleGroup(notenames);
        rb1.setSelected(true);
        RadioButton rb2 = new RadioButton("Show on click");
        rb2.setToggleGroup(notenames);
        rb2.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == true) {
                    toggleShowKeyboardNotesAction();
                }
            }
        });
        RadioButton rb3 = new RadioButton("Always show");
        rb3.setToggleGroup(notenames);
        rb3.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == true) {
                    toggleShowKeyboardNotesAlways();
                }
            }
        });
        settings.getChildren().add(rb1);
        settings.getChildren().add(rb2);
        settings.getChildren().add(rb3);
        pop.setTitle("Keyboard Settings");

    }


    @FXML
    private void toggleSettings() {
        if (pop.isShowing()) {
            pop.hide();
        } else {
            pop.show(settingsButton);
        }
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
        env.getPlayer().initKeyboardTrack();

    }

    private void setUpKeyboard() {
        keyboardBox.getChildren().clear();
        for (Integer i = bottomNote; i <= topNote; i++) {
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
