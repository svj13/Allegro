package seng302.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.util.Pair;
import seng302.Environment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jonty on 04-Sep-16.
 */
public class UserSummaryController {


    @FXML
    AnchorPane summaryStats;

    private Environment env;


    @FXML
    StackPane stageMap;




    /**
     * Initializes Controller dependant data i.e. the environment.
     * @param env
     */
    public void create(Environment env){
        this.env = env;


        FXMLLoader statsLoader = new FXMLLoader(getClass().getResource("/Views/TutorStats.fxml"));

        try {

            AnchorPane stats = statsLoader.load();

            TutorStatsController statsController = statsLoader.getController();



            summaryStats.getChildren().setAll(stats);
            summaryStats.setLeftAnchor(stats, 0.0);
            summaryStats.setTopAnchor(stats, 0.0);
            summaryStats.setBottomAnchor(stats, 0.0);
            summaryStats.setRightAnchor(stats, 0.0);


            statsController.create(env);

            statsController.displayGraphs("Summary");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Loads the stage map into the summary page
     */
    public void loadStageMap() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/StageMapPane.fxml"));

        try {
            stageMap.getChildren().add(loader.load());
        } catch (Exception e) {
            System.err.println("Failed to load stage map");
            System.out.println(e.getStackTrace());
            e.printStackTrace();
        }

        StageMapController controller = loader.getController();
        controller.setEnvironment(env);
    }







}
