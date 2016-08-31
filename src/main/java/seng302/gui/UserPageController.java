package seng302.gui;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;
import javafx.util.StringConverter;
import seng302.Environment;
import seng302.utility.LevelCalculator;

/**
 * Created by jmw280 on 22/08/16.
 */
public class UserPageController {


    @FXML
    private VBox stats;

    @FXML
    private VBox levelVBox;

    @FXML
    private StackedBarChart stackedBar;

    @FXML
    private JFXListView listView;


    @FXML
    private LineChart lineChart;

    @FXML
    private StackedBarChart levelBar;

    @FXML
    private ImageView imageDP;

    @FXML
    private Label latestAttempt;

    @FXML
    private Label overallStats;

    @FXML
    private Rectangle correct;

    @FXML
    private Rectangle incorrect;

    @FXML
    private Rectangle overallCorrect;

    @FXML
    private Rectangle overallIncorrect;

    @FXML
    private Slider timeSlider;

    @FXML
    private Label recentIncorrectLabel;

    @FXML
    private Label recentCorrectLabel;

    @FXML
    private Label overallIncorrectLabel;

    @FXML
    private Label overallCorrectLabel;

    @FXML
    private Line classAverage;

    @FXML
    private Rectangle progressBar;

    @FXML
    private Label highXp;

    @FXML
    ProgressBar pbLevel;

    private Environment env;


    public UserPageController() {


    }


    public void setEnvironment(Environment env) {
        this.env = env;
    }


    public void populateUserOptions() {

        ArrayList<String> options = new ArrayList<>();
        options.add("Summary");
        options.add("Musical Terms Tutor");
        options.add("Pitch Comparison Tutor");
        options.add("Scale Recognition Tutor");
        options.add("Chord Recognition Tutor");
        options.add("Interval Recognition Tutor");
        options.add("Chord Spelling Tutor");
        options.add("Key Signature Tutor");
        options.add("Diatonic Chord Tutor");
        //options.add("Modes Tutor");

        Image lockImg = new Image(getClass().getResourceAsStream("/images/lock.png"), 20, 20, false, false);

        listView.getItems().addAll(FXCollections.observableArrayList(options));


        listView.setMaxWidth(200);
        listView.setMinWidth(200);
        timeSlider.setMaxWidth(200);

        StringConverter convert = new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                if (object == 0) {
                    return "Last 24 Hours";
                } else if (object == 1) {
                    return "Last Week";
                } else if (object == 2) {
                    return "Last Month";
                } else if (object == 3) {
                    return "Last Six Months";
                } else if (object == 4) {
                    return "Last Year";
                } else if (object == 5) {
                    return "All Time";
                }
                return null;

            }

            @Override
            public Double fromString(String string) {
                if (string.equals("Last 24 Hours")) {
                    return 0d;
                } else if (string.equals("Last Week")) {
                    return 1d;
                } else if (string.equals("Last Month")) {
                    return 2d;
                } else if (string.equals("Last Six Months")) {
                    return 3d;
                } else if (string.equals("Last Year")) {
                    return 4d;
                } else if (string.equals("All Time")) {
                    return 5d;
                }
                return null;
            }
        };
        timeSlider.setLabelFormatter(convert);
        timeSlider.valueProperty().addListener(((observable1, oldValue1, newValue1) -> {
            String result = convert.toString(timeSlider.getValue());
            if (result != null) {
                updateGraphs(result);
            }
        }));

        timeSlider.setOnMouseReleased(e -> {
            updateGraphs(convert.toString(timeSlider.getValue()));
        });

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            displayGraphs((String) newValue, convert.toString(timeSlider.valueProperty().get()));
        });

        // Set after the listener so it loads user summary correctly
        listView.getSelectionModel().selectFirst();


        // This allows images to be displayed in the listview. Still trying to
        // make the text centered and the height and width the same as the others.
        listView.setCellFactory(listView -> new JFXListCell<String>() {

            @Override
            public void updateItem(String tutor, boolean empty) {
                super.updateItem(tutor, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else if (tutor.equals("Scale Recognition Tutor")) {
//                    imageView.setImage(lockImg);
//                    setText(tutor);
//                    setGraphic(imageView);
                    setDisable(true);
                    setMouseTransparent(true);

                }
            }
        });

        updateProgressBar();

    }

    private void updateProgressBar() {
        int userXp = env.getUserHandler().getCurrentUser().getUserExperience();
        int userLevel = env.getUserHandler().getCurrentUser().getUserLevel();
        int minXp = LevelCalculator.getTotalExpForLevel(userLevel);
        int maxXp = LevelCalculator.getTotalExpForLevel(userLevel + 1);
        highXp.setText(Integer.toString(maxXp - userXp) + "XP to level " + Integer.toString(userLevel + 1));
        float percentage = 100 * (userXp - minXp) / (maxXp - minXp);
        pbLevel.setProgress(percentage / 100);
    }

    private void updateGraphs(String timePeriod) {
        displayGraphs((String) listView.getSelectionModel().getSelectedItem(), timePeriod);

    }

    /**
     * Makes a line graph showing the scores over time. Still figuring out the scale.
     */
    private void makeLineGraph(List<Pair<Date, Float>> dateAndTimeList, String timePeriod) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM H:mm:ss");


        XYChart.Series<String, Float> lineSeries = new XYChart.Series<>();
        for (Pair<Date, Float> dateTime : dateAndTimeList) {
            Date date = dateTime.getKey();
            String milli = formatter.format(date);
            XYChart.Data data = new XYChart.Data<>(milli, dateTime.getValue());
            data.setNode(new hoverPane(date, dateTime.getValue()));
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


    /**
     * creates the most recent tutor record graph and the overall tutor record graph
     *
     * @param tutor the specific tutor that the graphs will getting data from
     */
    private void displayGraphs(String tutor, String timePeriod) {
        Pair<Integer, Integer> correctIncorrectRecent = new Pair<>(0, 0);
        Pair<Integer, Integer> correctIncorrectOverall = new Pair<>(0, 0);
        List<Pair<Date, Float>> dateAndTime = new ArrayList<>();
        dateAndTime.add(new Pair<>(new Date(0), 0f));


        try {
            switch (tutor) {
                case "Pitch Comparison Tutor":
                    correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("pitchTutor");
                    correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("pitchTutor", timePeriod);
                    dateAndTime = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTimeAndScores("pitchTutor", timePeriod);
                    break;
                case "Interval Recognition Tutor":
                    correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("intervalTutor");
                    correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("intervalTutor", timePeriod);
                    dateAndTime = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTimeAndScores("intervalTutor", timePeriod);
                    break;
                case "Scale Recognition Tutor":
                    correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("scaleTutor");
                    correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("scaleTutor", timePeriod);
                    dateAndTime = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTimeAndScores("scaleTutor", timePeriod);
                    break;
                case "Musical Terms Tutor":
                    correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("musicalTermTutor");
                    correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("musicalTermTutor", timePeriod);
                    dateAndTime = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTimeAndScores("musicalTermTutor", timePeriod);
                    break;
                case "Chord Recognition Tutor":
                    correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("chordTutor");
                    correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("chordTutor", timePeriod);
                    dateAndTime = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTimeAndScores("chordTutor", timePeriod);
                    break;
                case "Chord Spelling Tutor":
                    correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("chordSpellingTutor");
                    correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("chordSpellingTutor", timePeriod);
                    dateAndTime = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTimeAndScores("chordSpellingTutor", timePeriod);
                    break;
                case "Key Signature Tutor":
                    correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("keySignatureTutor");
                    correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("keySignatureTutor", timePeriod);
                    dateAndTime = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTimeAndScores("keySignatureTutor", timePeriod);
                    break;
                case "Diatonic Chord Tutor":
                    correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("diatonicChordTutor");
                    correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("diatonicChordTutor", timePeriod);
                    dateAndTime = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTimeAndScores("diatonicChordTutor", timePeriod);
                    break;
            }

            if (tutor.equals("Summary")) {
                overallStats.setVisible(false);
                latestAttempt.setVisible(false);
                Pair<Integer, Integer> totals = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTotalsForAllTutors(timePeriod);

                // Show summary
                levelVBox.setVisible(true);
                levelVBox.setPrefHeight(levelVBox.getMaxHeight());

            } else {

                latestAttempt.setVisible(true);
                overallStats.setVisible(true);

                // Set up most recent graph and labels.

                double total = correctIncorrectRecent.getKey() + correctIncorrectRecent.getValue();
                double widthCorrect = 500 * (correctIncorrectRecent.getKey() / total);
                Timeline correctAnim = new Timeline(
                        new KeyFrame(Duration.millis(800), new KeyValue(correct.widthProperty(), widthCorrect, Interpolator.EASE_OUT)));
                correctAnim.play();
                correct.setWidth(widthCorrect);
                correct.setFill(Color.web("00b004"));
                double widthIncorrect = 500 * (correctIncorrectRecent.getValue() / total);
                Timeline incorrectAnim = new Timeline(
                        new KeyFrame(Duration.millis(800), new KeyValue(incorrect.widthProperty(), widthIncorrect, Interpolator.EASE_OUT)));
                incorrectAnim.play();
                incorrect.setWidth(widthIncorrect);
                incorrect.setFill(Color.GRAY);
                recentCorrectLabel.setText(correctIncorrectRecent.getKey() + " \ncorrect");
                recentIncorrectLabel.setText(correctIncorrectRecent.getValue() + " \nincorrect");

                // Set up Overall graph and labels.

                double overallTotal = correctIncorrectOverall.getKey() + correctIncorrectOverall.getValue();
                double overallWidthCorrect = 500 * (correctIncorrectOverall.getKey() / overallTotal);
                Timeline overallCorrectAnim = new Timeline(
                        new KeyFrame(Duration.millis(800), new KeyValue(overallCorrect.widthProperty(), overallWidthCorrect, Interpolator.EASE_OUT)));
                overallCorrectAnim.play();
                overallCorrect.setWidth(widthCorrect);
                overallCorrect.setFill(Color.web("00b004"));
                double overallWidthIncorrect = 500 * (correctIncorrectOverall.getValue() / overallTotal);
                Timeline overallIncorrectAnim = new Timeline(
                        new KeyFrame(Duration.millis(800), new KeyValue(overallIncorrect.widthProperty(), overallWidthIncorrect, Interpolator.EASE_OUT)));
                overallIncorrectAnim.play();
                overallIncorrect.setWidth(widthIncorrect);
                overallIncorrect.setFill(Color.GRAY);
                overallCorrectLabel.setText(correctIncorrectOverall.getKey() + " \ncorrect");
                overallIncorrectLabel.setText(correctIncorrectOverall.getValue() + " \nincorrect");

                // TODO: replace this value with the actual class average
                double averageClassScore = 0.6;
                StackPane.setMargin(classAverage, new Insets(0, 0, 0, 500 * averageClassScore - 30));

            makeLineGraph(dateAndTime, timePeriod);

            //Hide summary
                levelVBox.setVisible(false);
                levelVBox.setPrefHeight(0);

            }
        } catch (IndexOutOfBoundsException e) {
            //There are no records for the selected tutor.
            System.err.println("There are no records for the" + tutor);
        }


    }

    public void updateImage() {
        final Circle clip = new Circle(imageDP.getFitWidth() - 50.0, imageDP.getFitHeight() - 50.0, 100.0);

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
