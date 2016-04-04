package seng302.gui;

import java.util.ArrayList;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seng302.Environment;
import seng302.utility.MidiNotePair;
import seng302.data.Note;

/**
 * Created by jat157 on 20/03/16.
 */
public class PitchComparisonTutorController {

    Environment env;

    Stage stage;


    @FXML
    TextField txtNotePairs;

    @FXML
    ComboBox<MidiNotePair> cbxLower;
    @FXML
    AnchorPane pitchTutorAnchor;

    @FXML
    ComboBox<MidiNotePair> cbxUpper;
    @FXML
    ScrollPane paneQuestions;

    @FXML
    VBox questionRows;

    @FXML
    Button btnGo;

    @FXML
    FlowPane headerPane;

    Random rand;


    @FXML
    private void initialize() {
        System.out.println("pitch comparison initialized.");
        rand = new Random();


    }

    /**
     * The command which is binded to the Go button, or the enter key when the command prompt is
     * active.
     */
    @FXML
    private void goAction() {
        ArrayList<FlowPane> panes = new ArrayList<FlowPane>();

        //generateComboValues(cbxUpper);
        questionRows.getChildren().clear();
        for (int i = 0; i < Integer.parseInt(txtNotePairs.getText()); i++) {

            HBox rowPane = generateQuestionPane();

            questionRows.getChildren().add(rowPane);
            questionRows.setMargin(rowPane, new Insets(10, 10, 10, 10));

        }
        paneQuestions.prefWidthProperty().bind(pitchTutorAnchor.prefWidthProperty());


    }

    

    public void create(Environment env) {


        System.out.println("inside pitch comparison tutor");
        this.env = env;
        generateComboValues(cbxLower);
        generateComboValues(cbxUpper);


    }

    @FXML
    private void handleLowerRangeAction() {
        if (!cbxLower.getSelectionModel().isEmpty()) {
            int selectedMidi = cbxLower.getSelectionModel().getSelectedItem().getMidi();
            System.out.println("Changed to: " + selectedMidi);

            int midiInt = selectedMidi;
            //int midiInt = Integer.parseInt(selectedMidi);
            cbxUpper.getItems().clear();
            for (int i = midiInt + 1; i < Note.noteCount; i++) {
                cbxUpper.getItems().add(new MidiNotePair(i, Note.lookup(String.valueOf(i)).getNote()));
            }
        }


    }

    /**
     * Called whenever the upper range selector is interacted with.
     */
    @FXML
    private void handleUpperRangeAction() {
        if (!cbxUpper.getSelectionModel().isEmpty()) {
            int midiInt = cbxUpper.getSelectionModel().getSelectedItem().getMidi();
//            System.out.println("upper action to: " + selectedMidi);

            //int midiInt = Integer.parseInt(selectedMidi);


            //
            System.out.println("selected index.. " + cbxLower.getSelectionModel().getSelectedIndex());
            if (cbxLower.getSelectionModel().isEmpty()) {
                cbxLower.getItems().clear();
                for (int i = 0; i < midiInt; i++) {
                    cbxLower.getItems().add(new MidiNotePair(i, Note.lookup(String.valueOf(i)).getNote()));
                }
            } else if (cbxLower.getSelectionModel().getSelectedItem().getMidi() < midiInt) {
                MidiNotePair oldVal = cbxLower.getSelectionModel().getSelectedItem();

                cbxLower.getItems().clear();
                for (int i = 0; i < midiInt; i++) {
                    cbxLower.getItems().add(new MidiNotePair(i, Note.lookup(String.valueOf(i)).getNote()));
                }
                cbxLower.setValue(oldVal);
            }


        }


    }

    /**
     * Generates the lower and upper range combobox values.
     * @param cbx
     */
    private void generateComboValues(ComboBox<MidiNotePair> cbx) {

        for (int i = 0; i < Note.noteCount; i++) {

            String val = i + " : " + Note.lookup(String.valueOf(i)).getNote();


            assert cbxLower != null : "cbxLower was not injected, check the fxml";

            cbx.getItems().add(new MidiNotePair(i, Note.lookup(String.valueOf(i)).getNote()));
            //System.out.println(cbx.getItems().size());
        }
        //TODO Make it so it generates everytime a combobox is selected.
        //So that The upperValues box is generated to contain only values higher than the selected
        //Lowerbox value


    }

    /**
     * Constructs the question panels.
     * @return
     */
    private HBox generateQuestionPane() {

        final HBox rowPane = new HBox();

        rowPane.setPadding(new Insets(10, 10, 10, 10));

        rowPane.setSpacing(10);
        rowPane.setStyle("-fx-background-color: #336699;");

        int lowerPitchBound = cbxLower.getSelectionModel().getSelectedItem().getMidi();

        int upperPitchBound = cbxUpper.getSelectionModel().getSelectedItem().getMidi();

        int difference = upperPitchBound - lowerPitchBound;


        rowPane.getChildren().add(new Label(Note.lookup(String.valueOf(lowerPitchBound + rand.nextInt(difference))).getNote()));

        rowPane.getChildren().add(new Label(Note.lookup(String.valueOf(lowerPitchBound + rand.nextInt(difference))).getNote()));

        ToggleGroup group = new ToggleGroup();
        ToggleButton higher = new ToggleButton("Higher");
        higher.setToggleGroup(group);
        ToggleButton lower = new ToggleButton("Lower");
        lower.setToggleGroup(group);

        higher.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Note note1 = Note.lookup(((Label) rowPane.getChildren().get(0)).getText());
                Note note2 = Note.lookup(((Label) rowPane.getChildren().get(1)).getText());

                if (noteComparison(true, note1, note2)) {
                    rowPane.setStyle("-fx-background-color: red;");
                } else {
                    rowPane.setStyle("-fx-background-color: green;");
                }
            }
        });

        lower.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Note note1 = Note.lookup(((Label) rowPane.getChildren().get(0)).getText());
                Note note2 = Note.lookup(((Label) rowPane.getChildren().get(1)).getText());


                if (noteComparison(true, note1, note2)) {
                    rowPane.setStyle("-fx-background-color: green;");
                } else {
                    rowPane.setStyle("-fx-background-color: red;");
                }
            }
        });

        Button playBtn = new Button();
        playBtn.setText("Play");
        playBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Note note1 = Note.lookup(((Label) rowPane.getChildren().get(0)).getText());
                Note note2 = Note.lookup(((Label) rowPane.getChildren().get(1)).getText());

                note1.playNote(env.getTempo());
                try {
                    Thread.sleep(1000L);
                } catch (Exception e) {
                }

                note2.playNote(env.getTempo());
            }


        });


        rowPane.getChildren().add(higher);
        rowPane.getChildren().add(lower);
        rowPane.getChildren().add(playBtn);

        rowPane.prefWidthProperty().bind(paneQuestions.prefWidthProperty());


        return rowPane;
    }


    /**
     * Note comparison
     * @param isHigher
     * @param note1
     * @param note2
     * @return
     */
    private boolean noteComparison(boolean isHigher, Note note1, Note note2) {

        if (isHigher) {
            if (note1.getMidi() > note2.getMidi()) return true;

            else return false;
        } else {
            if (note1.getMidi() < note2.getMidi()) return true;
            else return false;
        }
    }


    /**
     * Key event binder. No functionality at this point.
     * @param event
     */
    @FXML
    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {

        } else if (event.getCode() == KeyCode.UP) {


        } else if (event.getCode() == KeyCode.DOWN) {


        } else if (event.getCode() == KeyCode.ALPHANUMERIC) {

        }


    }




}
