package seng302.gui;

import seng302.Environment;
import seng302.Users.ProjectHandler;

/**
 * Controller class for the GUI used to change project-based settings.
 */
public class ProjectSettingsController {

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

    }
}
