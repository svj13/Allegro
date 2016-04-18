package seng302.gui;

import org.controlsfx.control.RangeSlider;
import org.controlsfx.control.spreadsheet.StringConverterWithFormat;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import seng302.Environment;
import seng302.utility.MidiNotePair;
import seng302.data.Note;
import seng302.utility.TutorManager;

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
    HBox sliderBox;

    @FXML
    ComboBox<MidiNotePair> cbxUpper;
    @FXML
    ScrollPane paneQuestions;

    @FXML
    VBox questionRows;

    @FXML
    Button btnGo;

    @FXML
    HBox settings;

    @FXML
    RangeSlider rangeSlider;

    @FXML
    Label notes;

    Random rand;

    Boolean lowerSet = false;
    Boolean upperSet = false;


    TutorManager manager;

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
//        manager.questions = 0;
        manager.answered = 0;

        if (lowerSet && upperSet) {
            questionRows.getChildren().clear();
            manager.questions = Integer.parseInt(txtNotePairs.getText());
            for (int i = 0; i < manager.questions; i++) {
                //String noteName1 = Note.lookup(String.valueOf(rand.nextInt(128))).getNote();
                //String noteName2 = Note.lookup(String.valueOf(rand.nextInt(128))).getNote();

                //int lowerPitchBound = cbxLower.getSelectionModel().getSelectedItem().getMidi();
                //int upperPitchBound = cbxUpper.getSelectionModel().getSelectedItem().getMidi();
                int lowerPitchBound = ((Double) rangeSlider.getLowValue()).intValue();
                int upperPitchBound = ((Double) rangeSlider.getHighValue()).intValue();
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


    /**
     * Fills the pitch combo boxes and sets them to default values. Also sets the env and manager.
     *
     * @param env The environment of the app.
     */
    public void create(Environment env) {
        this.env = env;
        //generateComboValues(cbxLower);
        //generateComboValues(cbxUpper);
        rangeSlider = new RangeSlider(0, 127, 60, 72);
        rangeSlider.setBlockIncrement(1);
        rangeSlider.setMajorTickUnit(12);
        rangeSlider.setPrefWidth(340);
        rangeSlider.setShowTickLabels(true);
        rangeSlider.setLabelFormatter(new StringConverterWithFormat<Number>() {
            @Override
            public String toString(Number object) {
                Integer num = object.intValue();
                return Note.lookup(String.valueOf(num)).getNote();
            }

            @Override
            public Number fromString(String string) {
                return Note.lookup(string).getMidi();
            }
        });

        sliderBox.getChildren().add(0, rangeSlider);
        notes.setText(rangeSlider.getLabelFormatter().toString(rangeSlider.getLowValue()) + " - "
                + rangeSlider.getLabelFormatter().toString(rangeSlider.getHighValue()));
        ChangeListener<Number> updateLabel = new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                notes.setText(rangeSlider.getLabelFormatter().toString(rangeSlider.getLowValue()) + " - "
                        + rangeSlider.getLabelFormatter().toString(rangeSlider.getHighValue()));
            }
        };
        rangeSlider.lowValueProperty().addListener(updateLabel);
        rangeSlider.highValueProperty().addListener(updateLabel);


        // Set default values to C4 and C5
//        cbxLower.getSelectionModel().select(60);
//        handleLowerRangeAction();
//        cbxUpper.getSelectionModel().select(12);
//        handleUpperRangeAction();
        lowerSet = true;
        upperSet = true;

        manager = env.getPctManager();
    }


    /**
     * This function is triggered when the lower range of the pitch changes. If something has been
     * selected, then the upper selection box is changed to only contain notes greater than or equal
     * to the selected note.
     */
    @FXML
    private void handleLowerRangeAction() {
        lowerSet = true;
        if (!cbxLower.getSelectionModel().isEmpty()) {
            int selectedMidi = cbxLower.getSelectionModel().getSelectedItem().getMidi();

            if (cbxUpper.getSelectionModel().isEmpty()) {
                cbxUpper.getItems().clear();
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
     * Called whenever the upper range pitch is changed. If something has been selected,
     * Then the lower range selector removes any options that are above the upeer range.
     */
    @FXML
    private void handleUpperRangeAction() {
        upperSet = true;
        if (!cbxUpper.getSelectionModel().isEmpty()) {
            int midiInt = cbxUpper.getSelectionModel().getSelectedItem().getMidi();

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



        row.getChildren().get(3).setDisable(true);
        row.getChildren().get(4).setDisable(true);
        row.getChildren().get(5).setDisable(true);
        row.getChildren().get(6).setDisable(true);


        int correctChoice = 0;

        if(((ToggleButton)row.getChildren().get(3)).isSelected()){ //Higher\
            row.getChildren().get(3).setStyle("-fx-text-fill: white;-fx-background-color: blue");
            if (noteComparison(true, note1, note2)) correctChoice = 1;
        }
        else  if(((ToggleButton)row.getChildren().get(4)).isSelected()){ //Same
            row.getChildren().get(4).setStyle("-fx-text-fill: white;-fx-background-color: blue");
            if (note1 == note2) correctChoice = 1;
        }
        else  if(((ToggleButton)row.getChildren().get(5)).isSelected()){ //Lower
            row.getChildren().get(5).setStyle("-fx-text-fill: white;-fx-background-color: blue");
            if (noteComparison(false, note1, note2)) {
                correctChoice = 1;
            }
        }
        else if(((ToggleButton)row.getChildren().get(6)).isSelected()) { //Skip
            row.getChildren().get(6).setStyle("-fx-text-fill: white;-fx-background-color: blue");
            correctChoice = 2;
            manager.questions -= 1;
        }

    if(correctChoice == 1) {
        row.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
        manager.answered += 1;
    } else if (correctChoice == 2) row.setStyle("-fx-border-color: grey; -fx-border-width: 2px;");
    else {
        row.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        manager.answered += 1;
    }
    manager.add(new Pair<String, String>(note1.getNote(), note2.getNote()), correctChoice);

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
        rowPane.setStyle("-fx-border-color: #336699; -fx-border-width: 2px;");



        rowPane.getChildren().add(new Label(String.valueOf(midiOne)));
        rowPane.getChildren().add(new Label(String.valueOf(midiTwo)));

        ToggleGroup group = new ToggleGroup();
        ToggleButton higher = new ToggleButton("Higher");
        Image imageUp = new Image(getClass().getResourceAsStream("/up-arrow.png"), 20, 20, true, true);
        higher.setGraphic(new ImageView(imageUp));
        higher.setToggleGroup(group);
        ToggleButton same = new ToggleButton("Same");
        Image imageSame = new Image(getClass().getResourceAsStream("/minus-symbol.png"), 20, 20, true, true);
        same.setGraphic(new ImageView(imageSame));
        same.setToggleGroup(group);
        ToggleButton lower = new ToggleButton("Lower");
        Image imageLower = new Image(getClass().getResourceAsStream("/download-arrow-1.png"), 20, 20, true, true);
        lower.setGraphic(new ImageView(imageLower));
        lower.setToggleGroup(group);
        ToggleButton skip = new ToggleButton("Skip");
        Image imageSkip = new Image(getClass().getResourceAsStream("/right-arrow.png"), 20, 20, true, true);
        skip.setGraphic(new ImageView(imageSkip));
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
        Image imagePlay = new Image(getClass().getResourceAsStream("/play-button.png"), 20, 20, true, true);
        playBtn.setGraphic(new ImageView(imagePlay));
        playBtn.setStyle("-fx-base: #6EFF73;");

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

        rowPane.getChildren().add(playBtn);
        rowPane.getChildren().add(higher);
        rowPane.getChildren().add(same);
        rowPane.getChildren().add(lower);
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
            if (note1.getMidi() < note2.getMidi()) return true;
            else return false;
        } else {
            if (note1.getMidi() > note2.getMidi()) return true;
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

        float score = manager.getScore();


        ButtonType retestBtn = new ButtonType("Retest");
        ButtonType clearBtn  = new ButtonType("Clear");
        if (manager.questions == 0){
          alert.setContentText("It appears you skipped every question. Would you like to reattempt?");
          alert.getButtonTypes().setAll(retestBtn, clearBtn);
        } else if(manager.getTempIncorrectResponses().size() > 0){
            alert.setContentText("You got " + (cor) + " out of " + ques + ", " + score +
                    "%.\nWell done :)");
            alert.getButtonTypes().setAll(retestBtn, clearBtn);
        }
        else {
            alert.setContentText("Congratulations!\nYou got " + (cor) + " out of " + ques
                    + ", " + score + "%.");
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


    private void finished2() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Finished");
        int cor = manager.correct;
        int ques = manager.questions;


        ButtonType retestBtn = new ButtonType("Retest");
        ButtonType clearBtn  = new ButtonType("Clear");

        Panel pnlComplete;





        manager.answered = 0;
        manager.correct = 0;
    }


    /**
     * Re generates the questions with the questions that were answered incorrectly
     */
    private void retest() {
        ArrayList<Pair> tempIncorrectResponses = new ArrayList<Pair>(manager.getTempIncorrectResponses());
        manager.clearTempIncorrect();
        for(Pair pair : tempIncorrectResponses){
            HBox rowPane = generateQuestionPane((String)pair.getKey(), (String) pair.getValue());
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
