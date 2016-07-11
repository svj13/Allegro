package seng302.gui;

import org.controlsfx.control.PopOver;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
     * Contains the white keys (TouchPanes).
     */
    @FXML
    private HBox keyboardBox;

    /**
     * Contains the black keys (TouchPanes).
     */
    @FXML
    private AnchorPane blackKeys;

    /**
     * Button to show settings pop over.
     */
    @FXML
    private Button settingsButton;


    @FXML
    private StackPane rightStack;

    /**
     * Current Environment (to access music player etc.).
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
    private boolean hidden = true;

    /**
     * Radio button to always show note labels.
     */
    private RadioButton noteLabelsAlways;

    /**
     * Radio button to show note labels on click.
     */
    private RadioButton noteLabelsClick;

    /**
     * Radio button to turn off note labels.
     */
    private RadioButton noteLabelsOff;


    /**
     * Current notes that have been clicked, since shift key was held.
     */
    private List<Note> multiNotes;

    /**
     * Current keys that have been clicked, since the shift key was held.
     */
    private List<TouchPane> clicked;

    @FXML
    private TitledPane keyPane;


    /**
     * Set up keyboard, by creating settings popOver and TouchPanes(keys) for the keyboard.
     */
    @FXML
    private void initialize() {
        keyboardBox.setMaxHeight(200);
        keyboardBox.setMinHeight(200);
        blackKeys.setMaxHeight(130);
        HBox.setHgrow(rightStack, Priority.ALWAYS);

        // Picking is computed by intersecting with the geometric
        // shape of the node, instead of the bounds.
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
        NoteRangeSlider slider = new NoteRangeSlider(notes, 12, bottomNote, topNote);

        // Add to the settings VBox
        settings.getChildren().add(slider);

        // Style settings button.
        Image cog = new Image(getClass().getResourceAsStream
                ("/images/gear-1119298_960_720.png"), 10, 10, true, true);
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
                    resetKeyboard();
                }
        );

        slider.highValueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    topNote = newValue.intValue();
                    resetKeyboard();
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
     * Each time the keyboard notes are change the keyboard needs to be remade, black keys
     * repositioned and labels added if the setting is on.
     */
    private void resetKeyboard() {
        setUpKeyboard();
        positionBlackKeys();
        checkLabelStatusForNewNotes();
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
     *
     * @param env Environment of the application.
     */
    public void create(Environment env) {
        this.env = env;
        toggleHideKeyboard();
        multiNotes = new ArrayList<>();
        clicked = new ArrayList<>();
        Platform.runLater(() -> {
            setUpKeyboard();
            keyboardStack.requestFocus();
            positionBlackKeys();
        });


        // This change listener is registered to the width property of the application.
        // The task and timer scheduling is required because calling positionBlackKeys() for every
        // change to the widthProperty caused the resizing to lag, and be inaccurate.
        // Now it only repositions the black keys if the width has not changed in the last 50
        // milliseconds.
        final ChangeListener<Number> listener = new ChangeListener<Number>() {
            final Timer timer = new Timer(); // uses a timer to call the resize method
            TimerTask task = null; // task to execute after defined delay
            final long delayTime = 50; // delay that has to pass in order to consider an operation done

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, final Number newValue) {
                if (task != null) { // there was already a task scheduled from the previous operation ...
                    task.cancel(); // cancel it, we have a new size to consider
                }

                task = new TimerTask() // create new task that calls the resize operation
                {
                    @Override
                    public void run() {
                        positionBlackKeys();
                    }
                };
                // schedule new task
                timer.schedule(task, delayTime);
            }
        };

        // Register the listener.
        this.env.getRootController().paneMain.widthProperty().addListener(listener);
        this.env.shiftPressedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                env.getPlayer().playSimultaneousNotes(multiNotes);
                multiNotes.clear();
                for (TouchPane clickedPane : clicked) {
                    clickedPane.setHighlightOff();
                }
                clicked.clear();
            }
        });

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
     * Add a note to the current set of notes pressed, while shift is pressed.
     *
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
            keyPane.setExpanded(false);
            keyboardBox.requestFocus();
            hidden = false;
        } else {
            keyPane.setExpanded(true);
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
