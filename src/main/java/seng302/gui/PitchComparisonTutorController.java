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
import seng302.utility.MidiNotePair;
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
                //String noteName1 = Note.lookup(String.valueOf(rand.nextInt(128))).getNote();
                //String noteName2 = Note.lookup(String.valueOf(rand.nextInt(128))).getNote();

                int lowerPitchBound = cbxLower.getSelectionModel().getSelectedItem().getMidi();
                int upperPitchBound = cbxUpper.getSelectionModel().getSelectedItem().getMidi();
                int pitchRange = upperPitchBound - lowerPitchBound;
                String midiOne =  String.valueOf(lowerPitchBound + rand.nextInt(pitchRange + 1));
                String midiTwo = String.valueOf(lowerPitchBound + rand.nextInt(pitchRange + 1));

                HBox rowPane = generateQuestionPane(midiOne, midiTwo);
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
        paneQuestions.prefWidthProperty().bind(pitchTutorAnchor.prefWidthProperty());


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
            int selectedMidi = cbxLower.getSelectionModel().getSelectedItem().getMidi();


           // int midiInt = Integer.parseInt(selectedMidi);
            if (cbxUpper.getSelectionModel().isEmpty()) {
                cbxUpper.getItems().clear();String.valueOf(rand.nextInt(128));
                for (int i = selectedMidi + 1; i < Note.noteCount; i++) {
                    cbxUpper.getItems().add(new MidiNotePair(i, Note.lookup(String.valueOf(i)).getNote()));
                }
            } else if (cbxUpper.getSelectionModel().getSelectedItem().getMidi() > selectedMidi) {
                MidiNotePair oldVal = cbxUpper.getSelectionModel().getSelectedItem();

                cbxUpper.getItems().clear();
                for (int i = selectedMidi; i < Note.noteCount; i++) {
                    cbxUpper.getItems().add(new MidiNotePair(i, Note.lookup(String.valueOf(i)).getNote()));
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
            int midiInt = cbxUpper.getSelectionModel().getSelectedItem().getMidi();

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
        else{

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


    private void questionResponse(HBox row, String m1, String m2){

        Note note1 = Note.lookup(m1);
        Note note2 = Note.lookup(m2);



        row.getChildren().get(2).setDisable(true);
        row.getChildren().get(3).setDisable(true);
        row.getChildren().get(4).setDisable(true);
        row.getChildren().get(6).setDisable(true);


        boolean correctChoice = false;

        if(((ToggleButton)row.getChildren().get(2)).isSelected()){ //Higher\
            row.getChildren().get(2).setStyle("-fx-text-fill: white;-fx-background-color: blue");
            System.out.println("higher is pressed");
            if (noteComparison(true, note1, note2)) correctChoice = true;
        }
        else  if(((ToggleButton)row.getChildren().get(3)).isSelected()){ //Same
            row.getChildren().get(3).setStyle("-fx-text-fill: white;-fx-background-color: blue");
            if (note1 == note2) correctChoice = true;
        }
        else  if(((ToggleButton)row.getChildren().get(4)).isSelected()){ //Lower
            row.getChildren().get(4).setStyle("-fx-text-fill: white;-fx-background-color: blue");
            if (noteComparison(false, note1, note2)) {
                correctChoice = true;
            }
        }

    if(correctChoice) row.setStyle("-fx-background-color: green;");
    else row.setStyle("-fx-background-color: red;");
    manager.add(note1.getNote(), note2.getNote(), correctChoice);

        manager.answered += 1;
        if (manager.answered == manager.questions) {
            finished();
        }




    }

    /**
     * Constructs the question panels.
     * @return
     */
    private HBox generateQuestionPane(final String midiOne, final String midiTwo) {

        final HBox rowPane = new HBox();

        rowPane.setPadding(new Insets(10, 10, 10, 10));

        rowPane.setSpacing(10);
        rowPane.setStyle("-fx-background-color: #336699;");



        rowPane.getChildren().add(new Label(String.valueOf(midiOne)));
        rowPane.getChildren().add(new Label(String.valueOf(midiTwo)));

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

                questionResponse(rowPane, midiOne, midiTwo);

            }
        });

        lower.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {


                questionResponse(rowPane, midiOne, midiTwo);
            }
        });

        same.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                questionResponse(rowPane, midiOne, midiTwo);
            }
        });



        skip.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                questionResponse(rowPane, midiOne, midiTwo);
            }
        });


        Button playBtn = new Button();
        playBtn.setText("Play");
        playBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Note note1 = Note.lookup(((Label) rowPane.getChildren().get(0)).getText());
                Note note2 = Note.lookup(((Label) rowPane.getChildren().get(1)).getText());
                ArrayList<Note> notes = new ArrayList<Note>();
                notes.add(note1);
                notes.add(note2);
                env.getPlayer().playNotes(notes, 48);
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


        ButtonType retestBtn = new ButtonType("Retest");
        ButtonType clearBtn  = new ButtonType("Clear");
        if(manager.getTempIncorrectResponses().size() > 0){
            alert.setContentText("You got " + (cor) + " out of " + ques + ", " + percentage + "%.\nWell done :)");
            alert.getButtonTypes().setAll(retestBtn, clearBtn);
        }
        else{


            alert.setContentText("Congratulations!\nYou got " + (cor) + " out of " + ques
                    + ", " + percentage + "%.");
            alert.getButtonTypes().setAll(clearBtn);

        }


        alert.setResizable(false);


        Optional<ButtonType> result = alert.showAndWait();
        if(manager.getTempIncorrectResponses().size() > 0){


            if (result.get() == clearBtn) {
                questionRows.getChildren().clear();
                manager.saveTempIncorrect();
            } else if (result.get() == retestBtn) {
                questionRows.getChildren().clear();
                retest();
            }
        }
        else{
            //alert.getButtonTypes().setAll(clearBtn);
            if (result.get() == clearBtn) {
                questionRows.getChildren().clear();
                manager.saveTempIncorrect();

            }
        }




        manager.answered = 0;
        manager.correct = 0;
    }


    /**
     * Re generates the questions with the questions that were answered incorrectly
     */
    private void retest() {
        ArrayList<OutputTuple> tempIncorrectResponses = manager.getTempIncorrectResponses();

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
