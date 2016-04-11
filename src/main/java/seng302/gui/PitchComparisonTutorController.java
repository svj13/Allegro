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
import seng302.utility.OutputTuple;
import seng302.utility.PitchComparisonTutorManager;

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


    PitchComparisonTutorManager manager;

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
            manager.questions = Integer.parseInt(txtNotePairs.getText());
            for (int i = 0; i < manager.questions; i++) {
                String noteName1 = Note.lookup(String.valueOf(rand.nextInt(128))).getNote();
                String noteName2 = Note.lookup(String.valueOf(rand.nextInt(128))).getNote();
                HBox rowPane = generateQuestionPane(noteName1,noteName2);
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

        // Set default values to C4 and C5
        cbxLower.getSelectionModel().select(60);
        cbxUpper.getSelectionModel().select(72);
        lowerSet = true;
        upperSet = true;

        manager = env.getPctManager();
    }


    @FXML
    private void handleLowerRangeAction() {
        lowerSet = true;
        if (!cbxLower.getSelectionModel().isEmpty()) {
            String selectedMidi = cbxLower.getSelectionModel().getSelectedItem().getMidi();
            System.out.println("Changed to: " + selectedMidi);

            int midiInt = Integer.parseInt(selectedMidi);
            if (cbxUpper.getSelectionModel().isEmpty()) {
                cbxUpper.getItems().clear();String.valueOf(rand.nextInt(128));
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
    private HBox generateQuestionPane(String noteName1, String noteName2 ) {

        final HBox rowPane = new HBox();

        rowPane.setPadding(new Insets(10, 10, 10, 10));

        rowPane.setSpacing(10);
        rowPane.setStyle("-fx-background-color: #336699;");


        rowPane.getChildren().add(new Label(noteName1));

        rowPane.getChildren().add(new Label(noteName2));

        ToggleGroup group = new ToggleGroup();
        ToggleButton higher = new ToggleButton("Higher");
        higher.setToggleGroup(group);
        ToggleButton same = new ToggleButton("Same");
        same.setToggleGroup(group);
        ToggleButton lower = new ToggleButton("Lower");
        lower.setToggleGroup(group);
        ToggleButton skip = new ToggleButton("Skip");
        skip.setToggleGroup(group);

        higher.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Note note1 = Note.lookup(((Label) rowPane.getChildren().get(0)).getText());
                Note note2 = Note.lookup(((Label) rowPane.getChildren().get(1)).getText());

                rowPane.getChildren().get(2).setStyle("-fx-text-fill: white;-fx-background-color: blue");
                rowPane.getChildren().get(2).setDisable(true);
                rowPane.getChildren().get(3).setDisable(true);
                rowPane.getChildren().get(4).setDisable(true);
                rowPane.getChildren().get(6).setDisable(true);
                if (noteComparison(true, note1, note2)) {
                    rowPane.setStyle("-fx-background-color: red;");
                    manager.add(note1.getNote(), note2.getNote(), false);
                } else {
                    rowPane.setStyle("-fx-background-color: green;");
                    manager.add(note1.getNote(), note2.getNote(), true);
                }
                manager.answered += 1;
                if (manager.answered == manager.questions) {
                    finished();
                }
            }
        });

        lower.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Note note1 = Note.lookup(((Label) rowPane.getChildren().get(0)).getText());
                Note note2 = Note.lookup(((Label) rowPane.getChildren().get(1)).getText());

                rowPane.getChildren().get(4).setStyle("-fx-text-fill: white;-fx-background-color: blue");
                rowPane.getChildren().get(2).setDisable(true);
                rowPane.getChildren().get(3).setDisable(true);
                rowPane.getChildren().get(4).setDisable(true);
                rowPane.getChildren().get(6).setDisable(true);
                if (noteComparison(true, note1, note2)) {
                    rowPane.setStyle("-fx-background-color: green;");
                    manager.add(note1.getNote(), note2.getNote(), true);

                } else {
                    rowPane.setStyle("-fx-background-color: red;");
                    manager.add(note1.getNote(), note2.getNote(), false);
                }
                manager.answered += 1;
                if (manager.answered == manager.questions) {
                    finished();
                }
            }
        });

        same.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Note note1 = Note.lookup(((Label) rowPane.getChildren().get(0)).getText());
                Note note2 = Note.lookup(((Label) rowPane.getChildren().get(1)).getText());
                rowPane.getChildren().get(3).setStyle("-fx-text-fill: white;-fx-background-color: blue");
                rowPane.getChildren().get(2).setDisable(true);
                rowPane.getChildren().get(3).setDisable(true);
                rowPane.getChildren().get(4).setDisable(true);
                rowPane.getChildren().get(6).setDisable(true);
                if (note1 == note2) {
                    rowPane.setStyle("-fx-background-color: green;");
                    manager.add(note1.getNote(), note2.getNote(), true);

                } else {
                    rowPane.setStyle("-fx-background-color: red;");
                    manager.add(note1.getNote(), note2.getNote(), false);
                }
                manager.answered += 1;
                if (manager.answered == manager.questions) { finished(); }
            }
        });


            btnGo.setText("Retest");

        skip.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Note note1 = Note.lookup(((Label) rowPane.getChildren().get(0)).getText());
                Note note2 = Note.lookup(((Label) rowPane.getChildren().get(1)).getText());

                rowPane.getChildren().get(6).setStyle("-fx-text-fill: white;-fx-background-color: blue");
                rowPane.getChildren().get(2).setDisable(true);
                rowPane.getChildren().get(3).setDisable(true);
                rowPane.getChildren().get(4).setDisable(true);
                rowPane.getChildren().get(6).setDisable(true);
                rowPane.setStyle("-fx-background-color: grey;");
                manager.questions -= 1;
                manager.add(note1.getNote(), note2.getNote(), false);
                if (manager.answered == manager.questions) {
                    finished();
                }
            }
        });

        btnGo.setText("Retest");
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
        rowPane.getChildren().add(same);
        rowPane.getChildren().add(lower);
        rowPane.getChildren().add(playBtn);
        rowPane.getChildren().add(skip);

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
     * Creates an alert once all the questions have been answered that allows the user to re-attempt
     * the skipped and incorrect questions or allows them to clear the question set.
     */
    private void finished() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Finished");
        int cor = manager.correct;
        int ques = manager.questions;
        String percentage = toString().format("%d", cor*100/ques);
        alert.setContentText("You got " + (cor) + " out of " + ques
                + ", " + percentage + "%.\nWell done :)");
        alert.setResizable(false);
        ButtonType retestBtn = new ButtonType("Retest");
        ButtonType clearBtn = new ButtonType("Clear");
        alert.getButtonTypes().setAll(retestBtn, clearBtn);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == clearBtn) {
            questionRows.getChildren().clear();
            manager.saveTempIncorrect();
        } else if (result.get() == retestBtn) {
            questionRows.getChildren().clear();
            retest();
        }

        manager.answered = 0;
        manager.correct = 0;
    }


    /**
     * Re generates the questions with the questions that were answered incorrectly
     */
    private void retest() {
        ArrayList<OutputTuple> tempIncorrectResponses = new ArrayList<OutputTuple>(manager.getTempIncorrectResponses());

        manager.clearTempIncorrect();
        for(OutputTuple tuple : tempIncorrectResponses){
            HBox rowPane = generateQuestionPane(tuple.getInput(), tuple.getResult());
            questionRows.getChildren().add(rowPane);
            questionRows.setMargin(rowPane, new Insets(10, 10, 10, 10));
        }
        manager.questions = tempIncorrectResponses.size();

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
