package seng302.gui;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import javafx.fxml.FXML;
import seng302.Environment;
import seng302.Users.Project;
import seng302.Users.ProjectHandler;
import seng302.command.Tempo;

/**
 * Controller class for the GUI used to change project-based settings.
 */
public class ProjectSettingsController {

    @FXML
    private JFXSlider tempoSlider;

    @FXML
    private JFXTextField tempoText;

    @FXML
    private JFXToggleButton modeToggle;

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
}
