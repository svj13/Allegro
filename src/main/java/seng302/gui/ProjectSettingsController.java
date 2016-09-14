package seng302.gui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Instrument;

import javafx.fxml.FXML;
import seng302.Environment;
import seng302.Users.Project;
import seng302.Users.ProjectHandler;
import seng302.command.Tempo;
import seng302.utility.InstrumentUtility;

/**
 * Controller class for the GUI used to change project-based settings.
 * TODO: add undo support?
 */
public class ProjectSettingsController {

    @FXML
    private JFXSlider tempoSlider;

    @FXML
    private JFXTextField tempoText;

    @FXML
    private JFXToggleButton modeToggle;

    @FXML
    private JFXComboBox instrumentSelector;

    @FXML
    private JFXComboBox rhythmSelector;

    private Environment env;

    private ProjectHandler projectHandler;

    /**
     * Links the project settings controller to the environment, so it has access to the current
     * project and settings.
     */
    public void create(Environment env) {
        this.env = env;
        env.getRootController().setHeader("Project Settings");
        projectHandler = env.getUserHandler().getCurrentUser().getProjectHandler();

        modeToggle.getStyleClass().remove(0);

        try {
            modeToggle.setSelected(projectHandler.getCurrentProject().getIsCompetitiveMode());
        } catch (Exception e) {
            // Default to competition mode
            modeToggle.setSelected(true);
        }

        // The listener for the number of questions selected
        tempoSlider.valueProperty().addListener((observable, newValue, oldValue) -> {
            tempoText.setText(Integer.toString(newValue.intValue()));
            new Tempo(Integer.toString(newValue.intValue()), false).execute(env);
            env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().checkChanges("tempo");
        });

        setupInstrumentSelector();

        setupRhythmSelector();
    }

    @FXML
    private void toggleBetweenModes() {
        Project currentProject = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject();
        if (modeToggle.isSelected()) {
            // Competition Mode
            currentProject.setIsCompetitiveMode(true);
        } else {
            // Practice mode
            currentProject.setIsCompetitiveMode(false);
        }
    }

    private void setupInstrumentSelector() {
        Instrument[] instruments = env.getPlayer().getAvailableInstruments();

        List<String> textInstruments = new ArrayList<>();

        for (Instrument instrument : instruments) {
            textInstruments.add(instrument.getName());

        }

        instrumentSelector.getItems().setAll(textInstruments);
        instrumentSelector.getSelectionModel().select(env.getPlayer().getInstrument().getName());

        instrumentSelector.valueProperty().addListener((observable, oldValue, newValue) -> {
            env.getPlayer().setInstrument(InstrumentUtility.getInstrumentByName((String) newValue, env));
            projectHandler.getCurrentProject().checkChanges("instrument");
        });
    }

    private void setupRhythmSelector() {
        String[] rhythmOptions = {"Straight", "Light", "Medium", "Heavy", "Custom"};
        rhythmSelector.getItems().setAll(rhythmOptions);

        rhythmSelector.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Custom")) {
                // Handle this separately
            } else {
                String rhythmStyle = newValue.toString();
                float[] divisions;
                if (rhythmStyle.equals("Heavy")) {
                    divisions = new float[]{3.0f / 4.0f, 1.0f / 4.0f};
                } else if (rhythmStyle.equals("Medium")) {
                    divisions = new float[]{2.0f / 3.0f, 1.0f / 3.0f};
                } else if (rhythmStyle.equals("Light")) {
                    divisions = new float[]{5.0f / 8.0f, 3.0f / 8.0f};
                } else if (rhythmStyle.equals("Straight")) {
                    divisions = new float[]{0.5f};
                    env.getPlayer().getRhythmHandler().setRhythmTimings(divisions);
                }
            }
        });
    }
}
