package seng302.gui;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
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
import seng302.utility.musicNotation.OctaveUtil;

import static seng302.utility.musicNotation.Checker.isValidNormalNote;

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

    /**
     * Button to show display scales pop over.
     */
    @FXML
    private Button displayScalesButton;


    @FXML
    private StackPane rightStack;

    @FXML
    private StackPane scalesStackPane;

    /**
     * Current Environment (to access music player etc.).
     */
    private Environment env;

    /**
     * Settings pop over.
     */
    private PopOver pop;

    /**
     * Display Scales pop over
     */
    private PopOver displayScalesPop;

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

    private Boolean playMode;


    /**
     * Set up keyboard, by creating settings popOver and TouchPanes(keys) for the keyboard.
     */
    @FXML
    private void initialize() {
        keyboardBox.setMaxHeight(200);
        keyboardBox.setMinHeight(200);
        blackKeys.setMaxHeight(130);
        playMode = true;
        HBox.setHgrow(rightStack, Priority.ALWAYS);

        // Picking is computed by intersecting with the geometric
        // shape of the node, instead of the bounds.
        keyboardStack.setPickOnBounds(false);

        // Default note range when keyboard opens.
        bottomNote = 48;
        topNote = 72;

        createSettingsPop();
        createDisplayScalesPop();


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

        final ToggleGroup group = new ToggleGroup();
        HBox modes = new HBox();
        ToggleButton play = new ToggleButton("Play");
        play.setUserData(true);
        play.setToggleGroup(group);
        play.setSelected(true);

        ToggleButton text = new ToggleButton("Text Input");
        text.setUserData(false);
        text.setToggleGroup(group);
        group.selectedToggleProperty().addListener((observable, newValue, oldValue) -> {
            if (group.getSelectedToggle() == null) {
                play.setSelected(true);
            } else {
                playMode = (Boolean) group.getSelectedToggle().getUserData();
            }
        });



        settings.getChildren().add(new Label("Keyboard Mode:"));
        modes.getChildren().add(play);
        modes.getChildren().add(text);
        settings.getChildren().add(modes);

    }



    /**
     * display scales pop up option on keyboard. Will enable user to display 1 or more scales. They can select
     * note of scale, what type of scale and its octave. Clear buttons for each scale to clear fields.
     * OK button to confirm and execute and close window. Cancel button to cancel and close window.
     * Error handling
     */
    private void createDisplayScalesPop() {

        // Vbox goes inside display scales pop over
        VBox displayScales = new VBox();
        // Hbox for label of scale 1
        HBox scale1Label = new HBox();
        scale1Label.setSpacing(5);
        Label selectScale1Label = new Label("Select scale:");
        scale1Label.getChildren().add(selectScale1Label);

        // Hbox for scale 1
        HBox scale1 = new HBox();
        scale1.setSpacing(5);

        // Hbox for label of scale 2
        HBox scale2Label = new HBox();
        scale2Label.setSpacing(5);
        Label selectScale2Label = new Label("Select 2nd scale (optional):");
        scale2Label.getChildren().add(selectScale2Label);

        // Hbox for scale 2
        HBox scale2 = new HBox();
        scale2.setSpacing(5);


        //text input field so user can type in the note for the scale of interest.
        //for scale 1
        TextField scale1NoteInput = new TextField();
        scale1NoteInput.setPrefColumnCount(3); //setting col size (user can input 3 characters)

        //for optional scale 2
        TextField scale2NoteInput = new TextField();
        scale2NoteInput.setPrefColumnCount(3); // setting column size (user can input 3 characters)

        // TODO make major default. add the rest of the scale options
        ObservableList<String> typeOptions =
                FXCollections.observableArrayList(
                        "Major",
                        "Minor",
                        "Blues"
                );

        // HBox for the options of the first scale
        HBox scaleOneTypeOptions = new HBox();

        //drop down for type of first scale
        ComboBox<String> typeScale1 = new ComboBox(typeOptions);
        typeScale1.setValue("Major"); //setting major as the default value
        scaleOneTypeOptions.getChildren().add(typeScale1);


        //HBox for the options of the optional second scale
        HBox scaleTwoTypeOptions = new HBox();


        //drop down for type of first scale
        ComboBox<String> typeScale2 = new ComboBox(typeOptions);
        typeScale2.setValue("Major"); //setting major as the default value
        scaleTwoTypeOptions.getChildren().add(typeScale2);

        /**
         * clear, OK and cancel button. OK and cancel are going into their own HBOX. clear is going to
         * be added to both the scale 1 and scale 2 hboxes
         */

        //closes the pop out and cancels any inputted information
        Button cancelButton = new Button("Cancel"); //closes the pop toggleScale(scale1Notes);out window without actioning
        cancelButton.setOnAction(event->{
            scale1NoteInput.clear();
            typeScale1.setValue("Major");
            scale2NoteInput.clear();
            typeScale2.setValue("Major");
            toggleDisplayScales();
            displayScalesButton.setText("Display Scales");

        });



        //binding action event to OK button so when OK is clicked, the information in the corresponding
        //fields will fetch the scale notes
        Button okButton = new Button("OK"); //actions the fields selected and highlights corresponding keys
        okButton.setOnAction(event->{
            String scale1Note =scale1NoteInput.getText();
            String scale2Note = scale2NoteInput.getText();
            boolean isValidNote1 = isValidNormalNote(scale1Note);
            boolean isValidNote2 = isValidNormalNote(scale2Note);

            //if scale 2 is filled out but not scale 1
            if (scale2Note != null && !scale2Note.equals("") && scale1Note.equals("") && isValidNote2) {
                System.out.println("You filled out scale 2 but not scale 1");
                scale1NoteInput.setStyle("-fx-border-color: red;");
            //if scale 1 is filled out
            } else if (scale1Note != null && !scale1Note.equals("") && isValidNote1){
                ArrayList<Note> scale1Notes = fetchScaleNotes(scale1NoteInput.getText(), typeScale1.getValue());
                toggleScaleKeys(scale1Notes, true); //displays pic on first key of scale
                scale1NoteInput.setStyle("-fx-border-color: lightgray;");
                System.out.println(scale1Notes);
                cancelButton.setText("Close Scales"); //changes the value of cancel to promt turn off the scales
                displayScalesButton.setText("Close Scales"); //changes the name of display scales to prompt turn off scales
                //TODO add in code to turn off the scales here

                //if the optional scale 2 is filled out
                if (scale2Note != null && !scale2Note.equals("") && isValidNote2) {
                    ArrayList<Note> scale2Notes = fetchScaleNotes(scale2NoteInput.getText(), typeScale2.getValue());
                    toggleScaleKeys(scale2Notes, false); //displays pic on first key of scale
                    scale2NoteInput.setStyle("-fx-border-color: lightgray;");
                    System.out.println(scale2Notes);
                }


            //if the fields are left blank
            } else {
                System.out.println("You didn't give me an input. Derp");
                scale1NoteInput.setStyle("-fx-border-color: red;");
                scale2NoteInput.setStyle("-fx-border-color: red;");

            }
        });


        Button clearButtonScale1 = new Button("Clear"); //clears the fields of the corresponding drop down
        clearButtonScale1.setOnAction(event->{
            ArrayList<Note> scaleNotes = Note.lookup(OctaveUtil.addDefaultOctave(scale1NoteInput.getText())).getScale(typeScale1.getValue(), true);
            ArrayList<String> scaleNotesText = new ArrayList<String>();

            for (Note note : scaleNotes) {
                scaleNotesText.add(OctaveUtil.removeOctaveSpecifier(note.getNote()));
            }

            scale1NoteInput.clear();
            typeScale1.setValue("Major");
            scale1NoteInput.setStyle("-fx-border-color: lightgray;");

            clearScaleIndicators("firstScale");


        });


        Button clearButtonScale2 = new Button("Clear"); //clears the fields of the corresponding drop down
        clearButtonScale2.setOnAction(event->{
            scale2NoteInput.clear();
            typeScale2.setValue("Major");
            scale2NoteInput.setStyle("-fx-border-color: lightgray;");

            clearScaleIndicators("secondScale");
        });





        //HBox for the OK and Cancel button
        HBox actionButtonBox = new HBox();
        actionButtonBox.getChildren().add(okButton);
        actionButtonBox.getChildren().add(cancelButton);

        // add note input field, type drop down and clear button to scale 1 HBox
        scale1.getChildren().add(scale1NoteInput);
        scale1.getChildren().add(scaleOneTypeOptions);
        scale1.getChildren().add(clearButtonScale1);

        // add note input field, type drop down and clear button to scale 1 HBox
        scale2.getChildren().add(scale2NoteInput);
        scale2.getChildren().add(scaleTwoTypeOptions);
        scale2.getChildren().add(clearButtonScale2);


        // Add Hboxes to the display scales vbox
        displayScales.getChildren().add(scale1Label);
        displayScales.getChildren().add(scale1);
        displayScales.getChildren().add(scale2Label);
        displayScales.getChildren().add(scale2);
        displayScales.getChildren().add(actionButtonBox);


        // used the spacing etc from settings to see if it will come out nicely. Subject to change
        displayScales.setSpacing(10);
        displayScales.setPadding(new Insets(10));

        displayScalesPop = new PopOver(displayScales);
        displayScalesPop.setTitle("Display Scales");


    }

    /**
     * Clear scale images of either the first or second scale from the keyboard
     *
     * @param scaleId Whether we are clearing the first or second scale
     */
    private void clearScaleIndicators(String scaleId) {
        //clear the images off the keys
        ObservableList<Node> keys = keyboardBox.getChildren();
        for (Node key : keys) {
            if (key instanceof TouchPane) {
                ((TouchPane) key).removeScaleImage(scaleId);
            }
        }

        ObservableList<Node> bKeys = blackKeys.getChildren();
        for (Node key : bKeys) {
            if (key instanceof TouchPane) {
                ((TouchPane) key).removeScaleImage(scaleId);
            }
        }
    }

    /**
     * Takes the scale note specified in the text input field and option from drop down menu in Display Scales
     * and fetches the notes of the relevant scale
     */
    private ArrayList<Note> fetchScaleNotes(String scaleNote, String scaleType) {
        scaleNote = OctaveUtil.addDefaultOctave(scaleNote);
        Note scaleStartNote = Note.lookup(scaleNote);
        ArrayList<Note> scaleNotes = scaleStartNote.getScale(scaleType, true);
        return scaleNotes;

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
     * Hides and shows the display scales pop over when the display scales Button is pressed.
     */
    @FXML
    private void toggleDisplayScales() {
        if (displayScalesPop.isShowing()) {
            displayScalesPop.hide();
        } else {
            displayScalesPop.show(displayScalesButton);
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
     * Show/Hide first note of scale on keyboard notes
     */
    public void toggleScaleKeys(ArrayList<Note> scaleNotes, Boolean isFirstScale) {
        ObservableList<Node> whiteKeys = keyboardBox.getChildren();
        ObservableList<Node> bKeys = blackKeys.getChildren();

        //Turns the start note of the scale into a string so it can be compared
        Note startNote = scaleNotes.get(0);
        String startNoteString = startNote.getNote();
        startNoteString = OctaveUtil.removeOctaveSpecifier(startNoteString);

        //Iterates through the white keys
        for (Node whiteKey : whiteKeys) {
            if (whiteKey instanceof TouchPane) {
                //for white keys
                String keyString = ((TouchPane) whiteKey).getNoteValue().getNote();
                keyString = OctaveUtil.removeOctaveSpecifier(keyString);

                if (keyString.equals(startNoteString)) {
                    if (isFirstScale) {
                        ((TouchPane) whiteKey).toggleScaleNotes("/images/triangle.png", "firstScale");
                    } else {
                        ((TouchPane) whiteKey).toggleScaleNotes("/images/play-icon.png", "secondScale");
                    }
                }
            }
        //iterates through the black keys
        } for (Node blackKey : bKeys) {
            if (bKeys instanceof TouchPane) {

                //for black keys
                String keyString = ((TouchPane) blackKey).getNoteValue().getNote();
                keyString = OctaveUtil.removeOctaveSpecifier(keyString);

                if (keyString.equals(startNoteString)) {
                    if (isFirstScale) {
                        ((TouchPane) blackKey).toggleScaleNotes("/images/triangle.png", "firstScale");
                    } else {
                        ((TouchPane) blackKey).toggleScaleNotes("/images/play-icon.png", "secondScale");
                    }
                }
            }

        //Gets the rest of the notes from the scale and turns them into strings to be compared
        } for (int i = 1; i < scaleNotes.size() - 1; i++) {
            Note currentNote = scaleNotes.get(i);
            System.out.println(currentNote.getNote());
            String currentNoteString = currentNote.getNote();
            currentNoteString = OctaveUtil.removeOctaveSpecifier(currentNoteString);

            //iterates through the white keys
            for (Node whiteKey : whiteKeys) {
                if (whiteKey instanceof TouchPane) {
                    String keyString = ((TouchPane) whiteKey).getNoteValue().getNote();
                    keyString = OctaveUtil.removeOctaveSpecifier(keyString);

                    if (keyString.equals(currentNoteString)) {
                        if (isFirstScale) {
                            ((TouchPane) whiteKey).toggleScaleNotes("/images/up-arrow.png", "firstScale");
                        } else {
                            ((TouchPane) whiteKey).toggleScaleNotes("/images/download-arrow-1.png", "secondScale");
                        }
                    }
                }
            }

            //iterates through the black keys
            for (Node blackKey : bKeys) {
                if (blackKey instanceof TouchPane) {
                    String keyString = ((TouchPane) blackKey).getNoteValue().getNote();
                    keyString = OctaveUtil.removeOctaveSpecifier(keyString);

                    if (keyString.equals(currentNoteString)) {
                        if (isFirstScale) {
                            ((TouchPane) blackKey).toggleScaleNotes("/images/up-arrow.png", "firstScale");
                        } else {
                            ((TouchPane) blackKey).toggleScaleNotes("/images/download-arrow-1.png", "secondScale");
                        }
                    }
                }
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

    public Boolean isPlayMode() {
        return playMode;
    }
}
