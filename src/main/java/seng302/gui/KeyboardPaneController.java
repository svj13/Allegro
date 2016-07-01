package seng302.gui;

import org.controlsfx.control.PopOver;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seng302.Environment;
import seng302.data.Note;
import seng302.utility.NoteRangeSlider;

/**
 * Created by team 5 on 13/05/16.
 */
public class KeyboardPaneController {

    /**
     * Contains the keyboardBox (white keys) and the blackKeys AnchorPane.
     */
    @FXML
    private StackPane keyboardStack;

    /**
     * Contains the white keys (TouchPanes)
     */
    @FXML
    private HBox keyboardBox;

    /**
     * Contains the black keys (TouchPanes)
     */
    @FXML
    private AnchorPane blackKeys;

    @FXML
    private Button settingsButton;

    /**
     * Current Environment (to access music player etc.)
     */
    private Environment env;

    /**
     * Settings pop over.
     */
    private PopOver pop;

    /**
     * Bottom note of keyboard.
     */
    private Integer bottomNote;

    /**
     * Top note of keyboard.
     */
    private Integer topNote;

    /**
     * Is the keyboard currently hidden?
     */
    private boolean hidden = false;

    /**
     * Note labels radio buttons.
     */
    private RadioButton noteLabelsAlways;
    private RadioButton noteLabelsClick;
    private RadioButton noteLabelsOff;

    /**
     * Is the shift key currently down?
     */
    private boolean shift;

    /**
     * Current notes that have been clicked, since shift key was held.
     */
    private List<Note> multiNotes;

    /**
     * Current keys that have been clicked, since the shift key was held.
     */
    private List<TouchPane> clicked;

    /**
     * Set up keyboard, by creating settings popOver and TouchPanes(keys) for the keyboard.
     */
    @FXML
    private void initialize() {
        keyboardBox.setMaxHeight(200);
        keyboardBox.setMinHeight(200);
        blackKeys.setMaxHeight(130);

        // Picking is computed by intersecting with the geometric shape of the node,
        // instead of the bounds.
        keyboardStack.setPickOnBounds(false);

        // Default note range when keyboard opens.
        bottomNote = 48;
        topNote = 72;

        createSettingsPop();

    }

    /**
     * Setup the settings pop over - with title, keyboard range slider, note labels radio buttons.
     */
    private void createSettingsPop() {
        // VBox goes inside settings pop over.
        VBox settings = new VBox();

        // HBox for the title and the dynamic keyboard range label.
        HBox rangeHeading = new HBox();
        rangeHeading.setSpacing(5);
        Label keyboardRange = new Label("Keyboard Range:");
        rangeHeading.getChildren().add(keyboardRange);

        // Add to the settings VBox.
        settings.getChildren().add(rangeHeading);

        // Dynamic label for range of notes currently showing.
        Label notes = new Label("");

        // Slider to select note range to show.
        NoteRangeSlider slider = new NoteRangeSlider(notes, 12);

        // Add to the settings VBox
        settings.getChildren().add(slider);

        // Style settings button.
        Image cog = new Image(getClass().getResourceAsStream("/images/gear-1119298_960_720.png"), 20, 20, true, true);
        settingsButton.setGraphic(new ImageView(cog));
        settingsButton.setText(null);

        // Select whether to show note names.
        settings.getChildren().add(new Label("Note names:"));
        ToggleGroup notenames = new ToggleGroup();
        noteLabelsOff = new RadioButton("Never show");
        noteLabelsOff.setOnAction(event -> stopShowingNotesOnKeyboard());
        noteLabelsOff.setToggleGroup(notenames);
        noteLabelsOff.setSelected(true);
        noteLabelsClick = new RadioButton("Show on click");
        noteLabelsClick.setToggleGroup(notenames);
        noteLabelsClick.setOnAction(event -> toggleShowKeyboardNotesAction());
        noteLabelsAlways = new RadioButton("Always show");
        noteLabelsAlways.setToggleGroup(notenames);
        noteLabelsAlways.setOnAction(event -> toggleShowKeyboardNotesAlways());

        // Generate Keyboard notes based on value of range slider.
        slider.lowValueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    bottomNote = newValue.intValue();
                    setUpKeyboard();
                    positionBlackKeys();
                    checkLabelStatusForNewNotes();
                }
        );

        slider.highValueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    topNote = newValue.intValue();
                    setUpKeyboard();
                    positionBlackKeys();
                    checkLabelStatusForNewNotes();
                }
        );

        rangeHeading.getChildren().add(notes);

        settings.setSpacing(5);
        settings.setPadding(new Insets(10));

        pop = new PopOver(settings);
        pop.setTitle("Keyboard Settings");

        settings.getChildren().add(noteLabelsOff);
        settings.getChildren().add(noteLabelsClick);
        settings.getChildren().add(noteLabelsAlways);
    }

    /**
     * When a note is created, check if it should be showing a label and toggle as needed.
     */
    private void checkLabelStatusForNewNotes() {
        if (noteLabelsAlways.isSelected()) {
            toggleShowKeyboardNotesAlways();
        } else if (noteLabelsClick.isSelected()) {
            toggleShowKeyboardNotesAction();
        }
    }

    /**
     * Hides and shows the settings pop over when the settings Button is pressed.
     */
    @FXML
    private void toggleSettings() {
        if (pop.isShowing()) {
            pop.hide();
        } else {
            pop.show(settingsButton);
        }
    }

    /**
     * Sets environment and initialises things.
     */
    public void create(Environment env) {
        this.env = env;
        multiNotes = new ArrayList<>();
        clicked = new ArrayList<>();
        Platform.runLater(() -> {
            setUpKeyboard();
                keyboardStack.requestFocus();
            positionBlackKeys();
            }
        );
        env.getPlayer().initKeyboardTrack();

    }

    /**
     * Create each key from bottomNote to topNote.
     */
    private void setUpKeyboard() {
        keyboardBox.getChildren().clear();
        keyboardBox.setFocusTraversable(false);
        blackKeys.getChildren().clear();
        blackKeys.setFocusTraversable(false);

        for (Integer i = bottomNote; i <= topNote; i++) {
            TouchPane key = new TouchPane(i, env, this);
            key.setMaxWidth(Double.MAX_VALUE);
            key.setPrefSize(100, 200);
            if (Note.lookup(i.toString()).getNote().contains("#")) {
                key.setBlackKey(true);
                key.setHighlightOff();
                key.setPrefSize(100, 130);
                AnchorPane.setLeftAnchor(key, 0.0);
                blackKeys.getChildren().add(key);
            } else {
                HBox.setHgrow(key, Priority.ALWAYS);
                keyboardBox.getChildren().add(key);
            }

        }

        keyboardStack.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SHIFT) {
                shift = true;
            }
        });

        keyboardStack.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SHIFT) {
                env.getPlayer().playSimultaneousNotes(multiNotes);
                shift = false;
                multiNotes.clear();
                for (TouchPane clickedPane : clicked) {
                    clickedPane.setHighlightOff();
                }
                clicked.clear();
            }
        });
    }

    /**
     * Position black keys dynamically according to the width of the white keys.
     */
    private void positionBlackKeys() {
        Double width = keyboardBox.getWidth();
        ObservableList<Node> keys = blackKeys.getChildren();
        Integer numWhiteNotes = topNote - bottomNote - keys.size() + 1;
        Double whiteWidth = width / (double) numWhiteNotes;
        Double blackWidth = whiteWidth / 2.0;
        Integer prevBlackKey = -1;
        Double currentPos = 0.0;
        for (Node key : keys) {
            TouchPane keyPane = (TouchPane) key;
            //keyPane.getStyleClass().add("blackkey");
            keyPane.setPrefWidth(blackWidth);
            keyPane.setMinWidth(blackWidth);
            keyPane.setMaxWidth(blackWidth);
            if (prevBlackKey == -1) {
                prevBlackKey = keyPane.getNoteValue().getMidi();
                if (prevBlackKey - bottomNote == 1) {
                    AnchorPane.setLeftAnchor(key, whiteWidth * 0.75);
                    currentPos = whiteWidth * 0.75 + blackWidth;
                } else if (prevBlackKey - bottomNote == 2) {
                    AnchorPane.setLeftAnchor(key, whiteWidth * 1.75);
                    currentPos = whiteWidth * 1.75 + blackWidth;
                } else {
                    AnchorPane.setLeftAnchor(key, -blackWidth * 0.5);
                    currentPos = blackWidth * 0.5;
                }
            } else {
                if (keyPane.getNoteValue().getMidi() - prevBlackKey == 2) {
                    AnchorPane.setLeftAnchor(key, currentPos + whiteWidth * 0.5);
                    currentPos = currentPos + whiteWidth * 0.5 + blackWidth;
                } else if (keyPane.getNoteValue().getMidi() - prevBlackKey == 3) {
                    AnchorPane.setLeftAnchor(key, currentPos + whiteWidth * 1.5);
                    currentPos = currentPos + whiteWidth * 1.5 + blackWidth;
                }
                prevBlackKey = keyPane.getNoteValue().getMidi();
            }

        }

    }


    /**
     * Whether shift is currently pressed
     * @return
     */
    public boolean getShiftState() {
        return shift;
    }

    /**
     * Add a note to the current set of notes pressed, while shift is pressed
     * @param note The note
     * @param pane The key
     */
    public void addMultiNote(Note note, TouchPane pane) {
        multiNotes.add(note);
        clicked.add(pane);
    }

    /**
     * Hide or show the keyboard.
     */
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

    /**
     * Show/hide labels on keyboard notes.
     */
    public void toggleShowKeyboardNotesAlways() {
        ObservableList<Node> keys = keyboardBox.getChildren();
        for (Node key : keys) {
            if (key instanceof TouchPane) {
                ((TouchPane) key).toggleDisplayLabel();
            }
        }
    }

    /**
     * Show/hide labels on keyboard notes when clicked.
     */
    public void toggleShowKeyboardNotesAction() {
        ObservableList<Node> keys = keyboardBox.getChildren();
        for (Node key : keys) {
            if (key instanceof TouchPane) {
                ((TouchPane) key).toggleDisplayLabelOnAction();
            }
        }
    }

    /**
     * Stop showing labels on notes.
     */
    public void stopShowingNotesOnKeyboard() {
        ObservableList<Node> keys = keyboardBox.getChildren();
        for (Node key : keys) {
            if (key instanceof TouchPane) {
                ((TouchPane) key).stopDisplayNotes();
            }
        }
    }
}
