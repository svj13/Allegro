package seng302.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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

    /**
     * Initializes Controller dependant data i.e. the environment.
     * @param env
     */
    public void create(Environment env){
        this.env = env;


        FXMLLoader statsLoader = new FXMLLoader(getClass().getResource("/Views/TutorStats.fxml"));

        try {
            AnchorPane stats = statsLoader.load();

            summaryStats.getChildren().setAll(stats);

            TutorStatsController statsController = statsLoader.getController();

            statsController.create(env);

            statsController.displayGraphs("Summary");



        } catch (IOException e) {
            e.printStackTrace();
        }


    }







}
