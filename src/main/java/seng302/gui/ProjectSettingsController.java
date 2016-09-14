package seng302.gui;

import seng302.Environment;
import seng302.Users.ProjectHandler;

/**
 * Created by emily on 14/09/16.
 */
public class ProjectSettingsController {

    private Environment env;

    private ProjectHandler projectHandler;

    public void create(Environment env) {
        this.env = env;
        env.getRootController().setHeader("Project Settings");
        projectHandler = env.getUserHandler().getCurrentUser().getProjectHandler();

    }
}
