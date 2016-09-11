package seng302.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import seng302.Environment;

/**
 * Created by Jonty on 04-Sep-16.
 */
public class UserSummaryController {


    @FXML
    AnchorPane summaryStats;

    private Environment env;

    /**
     * Initializes Controller dependant data i.e. the environment.
     */
    public void create(Environment env) {
        this.env = env;


        FXMLLoader statsLoader = new FXMLLoader(getClass().getResource("/Views/TutorStats.fxml"));

        try {
            AnchorPane stats = statsLoader.load();

            summaryStats.getChildren().setAll(stats);
            AnchorPane.setLeftAnchor(stats, 0.0);
            AnchorPane.setTopAnchor(stats, 0.0);
            AnchorPane.setBottomAnchor(stats, 0.0);
            AnchorPane.setRightAnchor(stats, 0.0);

            TutorStatsController statsController = statsLoader.getController();

            statsController.create(env);

            statsController.displayGraphs("Summary", env.getUserPageController().getTimePeriod());
            statsController.updateProgressBar();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
