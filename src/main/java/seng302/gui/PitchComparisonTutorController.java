package seng302.gui;

import org.controlsfx.control.RangeSlider;
import org.controlsfx.control.spreadsheet.StringConverterWithFormat;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
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
import seng302.data.Note;
import seng302.utility.MidiNotePair;
import seng302.utility.TutorRecord;

/**
 * Created by jat157 on 20/03/16.
 */
public class PitchComparisonTutorController extends TutorController{

    @FXML
    ComboBox<MidiNotePair> cbxLower;

    @FXML
    AnchorPane pitchTutorAnchor;

    @FXML
    VBox sliderBox;

    @FXML
    ComboBox<MidiNotePair> cbxUpper;

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

    @FXML
    private void initialize() {

        rand = new Random();

    }

    /**
     * The command which is bound to the Go button, or the enter key when the command prompt is
     * active. Checks that both lower and upper notes selected, alerts user if not.
     */
    @FXML
    private void goAction() {
//        manager.questions = 0;
        paneQuestions.setVisible(true);
        paneResults.setVisible(false);
        record = new TutorRecord(new Date(), "Pitch Comparison");
        manager.answered = 0;

        if (lowerSet && upperSet) {
            questionRows.getChildren().clear();
            manager.questions = selectedQuestions;
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

                Pair<String, String> midis = new Pair<String, String>(midiOne, midiTwo);
                HBox rowPane = generateQuestionPane(midis);
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
        super.create(env);
        initaliseQuestionSelector();
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
        ChangeListener<Number> updateLabelLower = new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if ((Double) newValue > rangeSlider.getHighValue() - 12) {
                    rangeSlider.setLowValue(rangeSlider.getHighValue() - 12);
                }
                notes.setText(rangeSlider.getLabelFormatter().toString(rangeSlider.getLowValue()) + " - "
                        + rangeSlider.getLabelFormatter().toString(rangeSlider.getHighValue()));
            }
        };
        ChangeListener<Number> updateLabelHigher = new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if ((Double) newValue < rangeSlider.getLowValue() + 12) {
                    rangeSlider.setHighValue(rangeSlider.getLowValue() + 12);
                }
                notes.setText(rangeSlider.getLabelFormatter().toString(rangeSlider.getLowValue()) + " - "
                        + rangeSlider.getLabelFormatter().toString(rangeSlider.getHighValue()));
            }
        };
        rangeSlider.lowValueProperty().addListener(updateLabelLower);
        rangeSlider.highValueProperty().addListener(updateLabelHigher);

        // Set default values to C4 and C5
//        cbxLower.getSelectionModel().select(60);
//        handleLowerRangeAction();
//        cbxUpper.getSelectionModel().select(12);
//        handleUpperRangeAction();
        lowerSet = true;
        upperSet = true;
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


    private int questionResponse(HBox row, String m1, String m2){

        Note note1 = Note.lookup(m1);
        Note note2 = Note.lookup(m2);

        disableButtons(row, 1, 5);

        int correctChoice = 0;


        if (((ToggleButton) row.getChildren().get(1)).isSelected()) { //Higher\
            row.getChildren().get(1).setStyle("-fx-text-fill: white;-fx-background-color: black");
            if (noteComparison(true, note1, note2)) correctChoice = 1;
            String[] question = new String[]{
                    String.format("Is %s higher or lower than %s", note2.getNote(), note1.getNote()),
                    "Higher",
                    Boolean.toString(getAnswer(note1, note2).equals("Higher"))
            };
            record.addQuestionAnswer(question);
        } else if (((ToggleButton) row.getChildren().get(2)).isSelected()) { //Same
            row.getChildren().get(2).setStyle("-fx-text-fill: white;-fx-background-color: black");
            if (note1 == note2) correctChoice = 1;
            String[] question = new String[]{
                    String.format("Is %s higher or lower than %s", note2.getNote(), note1.getNote()),
                    "Same",
                    Boolean.toString(getAnswer(note1, note2).equals("Same"))
            };
            record.addQuestionAnswer(question);
        } else if (((ToggleButton) row.getChildren().get(3)).isSelected()) { //Lower
            row.getChildren().get(3).setStyle("-fx-text-fill: white;-fx-background-color: black");
            if (noteComparison(false, note1, note2)) {
                correctChoice = 1;
            }
            String[] question = new String[]{
                    String.format("Is %s higher or lower than %s", note2.getNote(), note1.getNote()),
                    "Lower",
                    Boolean.toString(getAnswer(note1, note2).equals("Lower"))
            };
            record.addQuestionAnswer(question);
        } else if (((ToggleButton) row.getChildren().get(4)).isSelected()) { //Skip
            row.getChildren().get(4).setStyle("-fx-text-fill: white;-fx-background-color: black");
            //row.getChildren().get(4).setStyle("-fx-border-color: black; -fx-border-radius: 2px; -fx-border-width: 2px;");
            correctChoice = 2;
            manager.questions -= 1;

            String[] question = new String[]{
                    String.format("Is %s higher or lower than %s", note2.getNote(), note1.getNote()),
                    getAnswer(note1, note2)
            };
            record.addSkippedQuestion(question);
        }

    if(correctChoice == 1) {
        formatCorrectQuestion(row);
        manager.answered += 1;
    } else if (correctChoice == 2) formatSkippedQuestion(row);
    else {
        formatIncorrectQuestion(row);
        manager.answered += 1;
    }
    manager.add(new Pair<String, String>(note1.getNote(), note2.getNote()), correctChoice);

        if (manager.answered == manager.questions) {
            finished();
        }

    return correctChoice;
    }

    /**
     * Constructs the question panels.
     * @return
     */
    public HBox generateQuestionPane(Pair midis) {

        final HBox rowPane = new HBox();
        formatQuestionRow(rowPane);
        final String midiOne = midis.getKey().toString();
        final String midiTwo = midis.getValue().toString();
        final Label correctAnswer = correctAnswer(getAnswer(Note.lookup(midiOne), Note.lookup(midiTwo)));

        ToggleGroup group = new ToggleGroup();
        ToggleButton higher = new ToggleButton("Higher");
        Image imageUp = new Image(getClass().getResourceAsStream("/images/up-arrow.png"), 20, 20, true, true);
        higher.setGraphic(new ImageView(imageUp));
        higher.setToggleGroup(group);
        ToggleButton same = new ToggleButton("Same");
        Image imageSame = new Image(getClass().getResourceAsStream("/images/minus-symbol.png"), 20, 20, true, true);
        same.setGraphic(new ImageView(imageSame));
        same.setToggleGroup(group);
        ToggleButton lower = new ToggleButton("Lower");
        Image imageLower = new Image(getClass().getResourceAsStream("/images/download-arrow-1.png"), 20, 20, true, true);
        lower.setGraphic(new ImageView(imageLower));
        lower.setToggleGroup(group);
        ToggleButton skip = new ToggleButton("Skip");
        Image imageSkip = new Image(getClass().getResourceAsStream("/images/right-arrow.png"), 20, 20, true, true);
        skip.setGraphic(new ImageView(imageSkip));
        skip.setToggleGroup(group);

        higher.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                int responseValue = questionResponse(rowPane, midiOne, midiTwo);
                if (responseValue == 0) {
                    correctAnswer.setVisible(true);
                }

            }
        });

        lower.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                int responseValue = questionResponse(rowPane, midiOne, midiTwo);
                if (responseValue == 0) {
                    correctAnswer.setVisible(true);
                }
            }
        });

        same.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                int responseValue = questionResponse(rowPane, midiOne, midiTwo);
                if (responseValue == 0) {
                    correctAnswer.setVisible(true);
                }
            }
        });


        skip.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                int responseValue = questionResponse(rowPane, midiOne, midiTwo);
                if (responseValue == 0) {
                    correctAnswer.setVisible(true);
                }
            }
        });


        Button playBtn = new Button();
        Image imagePlay = new Image(getClass().getResourceAsStream("/images/play-icon.png"), 20, 20, true, true);
        playBtn.setGraphic(new ImageView(imagePlay));
        //playBtn.setStyle("-fx-base: #40a927;");

        playBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Note note1 = Note.lookup(midiOne);
                Note note2 = Note.lookup(midiTwo);
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
        rowPane.getChildren().add(correctAnswer);

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

    private String getAnswer(Note note1, Note note2) {
        if (note1.getMidi() == note2.getMidi()) {
            return "Same";
        } else if (note1.getMidi() < note2.getMidi()) {
            return "Higher";
        } else {
            return "Lower";
        }
    }


    private void finished2() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Finished");
        int cor = manager.correct;
        int ques = manager.questions;


        ButtonType retestBtn = new ButtonType("Retest");
        ButtonType clearBtn  = new ButtonType("Clear");

        Panel pnlComplete;

        manager.resetStats();
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
