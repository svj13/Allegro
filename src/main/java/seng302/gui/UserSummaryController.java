package seng302.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import seng302.Environment;
import seng302.utility.LevelCalculator;

/**
 * Created by Jonty on 04-Sep-16.
 */
public class UserSummaryController {


    @FXML
    private AnchorPane summaryStats;

    @FXML
    private VBox levelVBox;

    @FXML
    private Label highXp;

    @FXML
    ProgressBar pbLevel;

    private Environment env;

    /**
     * Initializes Controller dependant data i.e. the environment.
     */
    public void create(Environment env) {
        this.env = env;


        FXMLLoader statsLoader = new FXMLLoader(getClass().getResource("/Views/TutorStats.fxml"));

        try {
            VBox stats = statsLoader.load();

            summaryStats.getChildren().setAll(stats);
            AnchorPane.setLeftAnchor(stats, 0.0);
            AnchorPane.setTopAnchor(stats, 0.0);
            AnchorPane.setBottomAnchor(stats, 0.0);
            AnchorPane.setRightAnchor(stats, 0.0);

            TutorStatsController statsController = statsLoader.getController();

            statsController.create(env);

            statsController.displayGraphs("Summary", env.getUserPageController().getTimePeriod());
            updateProgressBar();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void updateProgressBar() {
        int userXp = env.getUserHandler().getCurrentUser().getUserExperience();
        int userLevel = env.getUserHandler().getCurrentUser().getUserLevel();
        int minXp = LevelCalculator.getTotalExpForLevel(userLevel);
        int maxXp = LevelCalculator.getTotalExpForLevel(userLevel + 1);
        highXp.setText(Integer.toString(maxXp - userXp) + "XP to level " + Integer.toString(userLevel + 1));
        float percentage = 100 * (userXp - minXp) / (maxXp - minXp);
        pbLevel.setProgress(percentage / 100);
    }


}
