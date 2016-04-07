package seng302.gui;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import seng302.data.MidiNotePair;
import seng302.data.Note;

/**
 * Created by jat157 on 20/03/16.
 */
public class PitchComparisonTutorController {

    Environment env;

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

    Boolean lowerSet = false;
    Boolean upperSet = false;

    int questions = 0;
    int answered = 0;
    int correct = 0;


    @FXML
    private void initialize() {
        System.out.println("pitch comparison initialized.");
        rand = new Random();

    }

    /**
     * The command which is bound to the Go button, or the enter key when the command prompt is
     * active. Checks that both lower and upper notes selected, alerts user if not.
     */
    @FXML
    private void goAction() {
        if (lowerSet && upperSet) {
            questionRows.getChildren().clear();
            questions = Integer.parseInt(txtNotePairs.getText());
            for (int i = 0; i < questions; i++) {
                HBox rowPane = generateQuestionPane();
                questionRows.getChildren().add(rowPane);
                questionRows.setMargin(rowPane, new Insets(10, 10, 10, 10));
            }


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Unselected range");
            alert.setContentText("Please select valid upper and lower ranges");
            alert.setResizable(false);
            alert.showAndWait();
        }
    }

    

    public void create(Environment env) {


        System.out.println("inside pitch comparison tutor");
        this.env = env;
        generateComboValues(cbxLower);
        generateComboValues(cbxUpper);

    }

    @FXML
    private void handleLowerRangeAction() {
        lowerSet = true;
        if (!cbxLower.getSelectionModel().isEmpty()) {
            String selectedMidi = cbxLower.getSelectionModel().getSelectedItem().getMidi();
            System.out.println("Changed to: " + selectedMidi);

            int midiInt = Integer.parseInt(selectedMidi);
            if (cbxUpper.getSelectionModel().isEmpty()) {
                cbxUpper.getItems().clear();
                for (int i = midiInt + 1; i < Note.noteCount; i++) {
                    cbxUpper.getItems().add(new MidiNotePair(String.valueOf(i), Note.lookup(String.valueOf(i)).getNote()));
                }
            } else if (Integer.parseInt(cbxUpper.getSelectionModel().getSelectedItem().getMidi()) < midiInt) {
                MidiNotePair oldVal = cbxUpper.getSelectionModel().getSelectedItem();

                cbxUpper.getItems().clear();
                for (int i = 0; i < midiInt; i++) {
                    cbxUpper.getItems().add(new MidiNotePair(String.valueOf(i), Note.lookup(String.valueOf(i)).getNote()));
                }
                cbxUpper.setValue(oldVal);
            }
        }
    }

    /**
     * Called whenever the upper range selector is interacted with.
     */
    @FXML
    private void handleUpperRangeAction() {
        upperSet = true;
        if (!cbxUpper.getSelectionModel().isEmpty()) {
            String selectedMidi = cbxUpper.getSelectionModel().getSelectedItem().getMidi();
            System.out.println("upper action to: " + selectedMidi);

            int midiInt = Integer.parseInt(selectedMidi);


            System.out.println("selected index.. " + cbxLower.getSelectionModel().getSelectedIndex());
            if (cbxLower.getSelectionModel().isEmpty()) {
                cbxLower.getItems().clear();
                for (int i = 0; i < midiInt; i++) {
                    cbxLower.getItems().add(new MidiNotePair(String.valueOf(i), Note.lookup(String.valueOf(i)).getNote()));
                }
            } else if (Integer.parseInt(cbxLower.getSelectionModel().getSelectedItem().getMidi()) < midiInt) {
                MidiNotePair oldVal = cbxLower.getSelectionModel().getSelectedItem();

                cbxLower.getItems().clear();
                for (int i = 0; i < midiInt; i++) {
                    cbxLower.getItems().add(new MidiNotePair(String.valueOf(i), Note.lookup(String.valueOf(i)).getNote()));
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

            cbx.getItems().add(new MidiNotePair(String.valueOf(i), Note.lookup(String.valueOf(i)).getNote()));
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


        rowPane.getChildren().add(new Label(Note.lookup(String.valueOf(rand.nextInt(128))).getNote()));

        rowPane.getChildren().add(new Label(Note.lookup(String.valueOf(rand.nextInt(128))).getNote()));

        ToggleGroup group = new ToggleGroup();
        ToggleButton higher = new ToggleButton("Higher");
        higher.setToggleGroup(group);
        ToggleButton lower = new ToggleButton("Lower");
        lower.setToggleGroup(group);

        higher.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Note note1 = Note.lookup(((Label) rowPane.getChildren().get(0)).getText());
                Note note2 = Note.lookup(((Label) rowPane.getChildren().get(1)).getText());

                rowPane.getChildren().get(2).setStyle("-fx-text-fill: white;-fx-background-color: blue");
                rowPane.getChildren().get(2).setDisable(true);
                rowPane.getChildren().get(3).setDisable(true);
                if (noteComparison(true, note1, note2)) {
                    rowPane.setStyle("-fx-background-color: red;");
                    env.getPctManager().add(note1.getNote(), note2.getNote(), false);
                } else {
                    rowPane.setStyle("-fx-background-color: green;");
                    env.getPctManager().add(note1.getNote(), note2.getNote(), true);
                    correct += 1;
                }
                answered += 1;
//                System.out.println("Answered: " + answered);
                if (answered == questions) { finished(); }
            }
        });

        lower.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Note note1 = Note.lookup(((Label) rowPane.getChildren().get(0)).getText());
                Note note2 = Note.lookup(((Label) rowPane.getChildren().get(1)).getText());

                rowPane.getChildren().get(3).setStyle("-fx-text-fill: white;-fx-background-color: blue");
                rowPane.getChildren().get(2).setDisable(true);
                rowPane.getChildren().get(3).setDisable(true);
                if (noteComparison(true, note1, note2)) {
                    rowPane.setStyle("-fx-background-color: green;");
                    correct += 1;
                    env.getPctManager().add(note1.getNote(), note2.getNote(), true);

                } else {
                    rowPane.setStyle("-fx-background-color: red;");
                    env.getPctManager().add(note1.getNote(), note2.getNote(), false);
                }
                answered += 1;
//                System.out.println("Answered: " + answered);
                if (answered == questions) { finished(); }
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

    private void finished() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Finished");
        alert.setContentText("You got " + correct + " out of " + questions + ". Well done :)");
        alert.setResizable(false);
        ButtonType retestBtn = new ButtonType("Retest");
        ButtonType clearBtn = new ButtonType("Clear");
        alert.getButtonTypes().setAll(retestBtn, clearBtn);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == clearBtn) {
            questionRows.getChildren().clear();
            btnGo.setText("Retest");
            env.getPctManager().tempIncorrectClear();
        } else if (result.get() == retestBtn) {
            retest();
        }

        questions = 0;
        answered = 0;
        correct = 0;
    }

    private void retest() {


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
