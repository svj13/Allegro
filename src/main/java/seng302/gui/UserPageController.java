package seng302.gui;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;
import seng302.Environment;

/**
 * Created by jmw280 on 22/08/16.
 */
public class UserPageController {


    @FXML
    AnchorPane contentPane;

    @FXML
    VBox stats;

    @FXML
    StackedBarChart stackedBar;

    @FXML
    StackedBarChart recentBar;

    @FXML
    AnchorPane topPane;

    @FXML
    VBox scrollPaneVbox;

    @FXML
    Label chartTitle;

    @FXML
    PieChart pieChart;

    @FXML
    ImageView imageDP;

    private Environment env;


    public UserPageController() {
    }


    public void setEnvironment(Environment env) {
        this.env = env;
    }


    public void populateUserOptions() {

        ArrayList<String> options = new ArrayList<>();
        options.add("Musical Terms Tutor");
        options.add("Pitch Comparison Tutor");
        options.add("Scale Recognition Tutor");
        options.add("Chord Recognition Tutor");
        options.add("Interval Recognition Tutor");
        options.add("Chord Spelling Tutor");
        options.add("Key Signature Tutor");
        options.add("Diatonic Chord Tutor");
        //options.add("Modes Tutor");

        Image lockImg = new Image(getClass().getResourceAsStream("/images/lock.png"), 10, 10, false, false);


        for (String option : options) {
            Button optionBtn;
            if (option.equals("Scale Recognition Tutor")) {
                optionBtn = new Button(option, new ImageView(lockImg));
                optionBtn.setDisable(true);

            } else {
                optionBtn = new Button(option);
                optionBtn.setOnAction(event -> {
                    displayGraphs(option);

                    //displayGraphInLine(option);
                    //displayScoresOverTime(option);
                });

            }
            optionBtn.setPrefWidth(191);
            scrollPaneVbox.getChildren().add(optionBtn);

        }


    }

    private void displayScoresOverTime(String tutor) {

    }

    /**
     * creates the most recent tutor record graph and the overall tutor record graph
     * @param tutor the specific tutor that the graphs will getting data from
     */
    private void displayGraphs(String tutor) {
        Pair<Integer, Integer> correctIncorrectRecent = new Pair<>(0, 0);
        Pair<Integer, Integer> correctIncorrectOverall = new Pair<>(0, 0);

        XYChart.Series<Number, String> recentSeries1 = new XYChart.Series<>();
        XYChart.Series<Number, String> recentSeries2 = new XYChart.Series<>();

        XYChart.Series<Number, String> overallSeries1 = new XYChart.Series<>();
        XYChart.Series<Number, String> overallSeries2 = new XYChart.Series<>();


        if (tutor.equals("Pitch Comparison Tutor")) {
            correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("pitchTutor");
            correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("pitchTutor");
        } else if (tutor.equals("Interval Recognition Tutor")) {
            correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("intervalTutor");
            correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("intervalTutor");
        } else if (tutor.equals("Scale Recognition Tutor")) {
            correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("scaleTutor");
            correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("scaleTutor");
        } else if (tutor.equals("Musical Terms Tutor")) {
            correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("musicalTermTutor");
            correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("musicalTermTutor");
        } else if (tutor.equals("Chord Recognition Tutor")) {
            correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("chordTutor");
            correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("chordTutor");
        } else if (tutor.equals("Chord Spelling Tutor")) {
            correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("chordSpellingTutor");
            correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("chordSpellingTutor");
        } else if (tutor.equals("Key Signature Tutor")) {
            correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("keySignatureTutor");
            correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("keySignatureTutor");
        } else if (tutor.equals("Diatonic Chord Tutor")) {
            correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("diatonicChordTutor");
            correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("diatonicChordTutor");
        }

        recentSeries1.getData().add(new XYChart.Data<>(correctIncorrectRecent.getKey(), ""));
        recentSeries2.getData().add(new XYChart.Data<>(correctIncorrectRecent.getValue(), ""));
        recentBar.getData().clear();
        recentBar.getData().addAll(recentSeries1, recentSeries2);

        overallSeries1.getData().add(new XYChart.Data<>(correctIncorrectOverall.getKey(), ""));
        overallSeries2.getData().add(new XYChart.Data<>(correctIncorrectOverall.getValue(), ""));
        stackedBar.getData().clear();
        stackedBar.getData().addAll(overallSeries1, overallSeries2);


    }

    public void updateImage(){
        final Circle clip = new Circle(imageDP.getFitWidth()-50.0, imageDP.getFitHeight()-50.0, 100.0);

        imageDP.setImage(env.getUserHandler().getCurrentUser().getUserPicture());


        clip.setRadius(50);

        imageDP.setClip(clip);

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = imageDP.snapshot(parameters, null);

        imageDP.setClip(null);
        imageDP.setEffect(new DropShadow(5, Color.BLACK));

        imageDP.setImage(image);


    }


}
