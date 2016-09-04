package seng302.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import seng302.Environment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jonty on 04-Sep-16.
 */
public class TutorStatsController {

    private Environment env;

    @FXML
    private StackedBarChart stackedBar;

    @FXML
    private VBox stats;

    @FXML
    private Label latestAttempt;

    @FXML
    private Label overallStats;

    @FXML
    private LineChart lineChart;

    @FXML
    private StackedBarChart recentBar;

    String currentTutor;

    public void create(Environment env){
        this.env = env;



    }


     /** creates the most recent tutor record graph and the overall tutor record graph
     *
     * @param tutor the specific tutor that the graphs will getting data from
     */
    protected void displayGraphs(String tutor) {
        currentTutor = tutor;

        //tutorName.setText(tutor);
        Pair<Integer, Integer> correctIncorrectRecent = new Pair<>(0, 0);
        Pair<Integer, Integer> correctIncorrectOverall = new Pair<>(0, 0);
        List<Pair<Date, Float>> dateAndTime = new ArrayList<>();
        dateAndTime.add(new Pair<>(new Date(0), 0f));

        XYChart.Series<Number, String> recentSeries1 = new XYChart.Series<>();
        XYChart.Series<Number, String> recentSeries2 = new XYChart.Series<>();

        XYChart.Series<Number, String> overallSeries1 = new XYChart.Series<>();
        XYChart.Series<Number, String> overallSeries2 = new XYChart.Series<>();


        switch (tutor) {
            case "Pitch Comparison Tutor":
                correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("pitchTutor");
                correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("pitchTutor");
                dateAndTime = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTimeAndScores("pitchTutor");
                break;
            case "Interval Recognition Tutor":
                correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("intervalTutor");
                correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("intervalTutor");
                dateAndTime = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTimeAndScores("intervalTutor");
                break;
            case "Scale Recognition Tutor":
                correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("scaleTutor");
                correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("scaleTutor");
                break;
            case "Musical Terms Tutor":
                correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("musicalTermTutor");
                correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("musicalTermTutor");
                break;
            case "Chord Recognition Tutor":
                correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("chordTutor");
                correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("chordTutor");
                break;
            case "Chord Spelling Tutor":
                correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("chordSpellingTutor");
                correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("chordSpellingTutor");
                break;
            case "Key Signature Tutor":
                correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("keySignatureTutor");
                correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("keySignatureTutor");
                break;
            case "Diatonic Chord Tutor":
                correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("diatonicChordTutor");
                correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("diatonicChordTutor");
                break;
        }

        if (tutor.equals("Summary")) {
            recentBar.setVisible(false);
            overallStats.setVisible(false);
            latestAttempt.setVisible(false);


        } else {

            recentSeries1.getData().add(new XYChart.Data<>(correctIncorrectRecent.getKey(), ""));
            recentSeries2.getData().add(new XYChart.Data<>(correctIncorrectRecent.getValue(), ""));
            recentBar.getData().clear();
            recentBar.setVisible(true);
            latestAttempt.setVisible(true);
            overallStats.setVisible(true);
            recentBar.getData().addAll(recentSeries1, recentSeries2);

            overallSeries1.getData().add(new XYChart.Data<>(correctIncorrectOverall.getKey(), ""));
            overallSeries2.getData().add(new XYChart.Data<>(correctIncorrectOverall.getValue(), ""));
            stackedBar.getData().clear();
            stackedBar.getData().addAll(overallSeries1, overallSeries2);


            makeLineGraph(dateAndTime);
        }

    }

    /**
     * Makes a line graph showing the scores over time. Still figuring out the scale.
     */
    private void makeLineGraph(List<Pair<Date, Float>> dateAndTimeList) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM H:mm:ss");


        XYChart.Series<String, Float> lineSeries = new XYChart.Series<>();
        for (Pair<Date, Float> dateTime : dateAndTimeList) {
            Date date = dateTime.getKey();
            String milli = formatter.format(date);
            XYChart.Data data = new XYChart.Data<>(milli, dateTime.getValue());
            //data.setNode(new UserPageController.hoverPane(date, dateTime.getValue()));
            lineSeries.getData().add(data);
        }
        lineChart.getData().clear();
        lineChart.getData().add(lineSeries);

    }


    class hoverPane extends VBox {
        hoverPane(Date date, float value) {
            setPrefSize(10, 10);
            final Label label = createDataLabel(date, value);
            this.setAlignment(Pos.CENTER);

            setOnMouseEntered(e -> {
                getChildren().setAll(label);
                setCursor(Cursor.NONE);
                toFront();
            });
            setOnMouseExited(e -> {
                getChildren().clear();
                setCursor(Cursor.CROSSHAIR);
            });

        }

        private Label createDataLabel(Date date, float value) {
            String score = String.format("%.0f", value);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YY H:mm");
            String dateformat = formatter.format(date);
            final Label label = new Label(score + "%\n" + dateformat);
            label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
            label.setStyle("-fx-font-size: 8; -fx-font-weight: normal;");
            label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
            label.setMaxWidth(Double.MAX_VALUE);
            label.setAlignment(Pos.CENTER);
            return label;
        }
    }


    @FXML
    void loadTutor() {
        loadTutor(this.currentTutor);
    }


    public void loadTutor(String tutorName){

        env.getRootController().getTutorFactory().openTutor(tutorName);

//        switch (tutorName) {
//            case "Pitch Comparison Tutor":
//                //userView.setVisible(false);
//
//                break;
//            case "Interval Recognition Tutor":
//                //userView.setVisible(false);
//                openIntervalTutor();
//                break;
//            case "Scale Recognition Tutor":
//                //userView.setVisible(false);
//                openScaleTutor();
//                break;
//            case "Musical Terms Tutor":
//                //userView.setVisible(false);
//                openMusicalTermTutor();
//                break;
//            case "Chord Recognition Tutor":
//                //userView.setVisible(false);
//                openChordTutor();
//                break;
//            case "Chord Spelling Tutor":
//                //userView.setVisible(false);
//                openSpellingTutor();
//                break;
//            case "Key Signature Tutor":
//                //userView.setVisible(false);
//                openKeySignatureTutor();
//                break;
//            case "Diatonic Chord Tutor":
//                //userView.setVisible(false);
//                openDiatonicChordTutor();
//                break;
//        }


        //tutorView.setVisible(true);
    }






}
