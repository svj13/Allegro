package seng302.gui;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import seng302.Environment;

/**
 * Handles and Creates Users.
 */
public class UserPageController {


    @FXML
    AnchorPane contentPane;

    @FXML
    SplitPane userView;

    @FXML
    JFXListView listView;


    @FXML
    Label txtFullName;

    @FXML
    ImageView imageDP2;

    @FXML
    Label latestAttempt;

    @FXML
    AnchorPane currentPage;

    @FXML
    private Slider timeSlider;

    StringConverter convert;

    private TutorStatsController statsController;

    private Environment env;


    public void setEnvironment(Environment env) {
        this.env = env;
        this.env.setUserPageController(this);

    }


    /**
     * Pretty much a constructor - loads userPage relevant data.
     */
    protected void load() {
        populateUserOptions();


        imageDP2.setImage(env.getUserHandler().getCurrentUser().getUserPicture());
        imageDP2.setOnMouseClicked(e -> env.getRootController().launchSettings());

        try {

            txtFullName.setText(env.getUserHandler().getCurrentUser().getUserFirstName() + " "
                    + env.getUserHandler().getCurrentUser().getUserLastName());
        } catch (NullPointerException e) {
            //txtFullName not initialized yet.
        }

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
        listView.setDepthProperty(1);


        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showPage((String) newValue);
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

//                } else if (!tutor.equals("Summary") && modeManager.tutorNumUnlocksMap.get(tutor) > modeManager.currentUnlocks) {
//                    setDisable(true);
//                    setMouseTransparent(true);
//
//                }
                }
            }
        });

    }


    private void setupTimeSlider() {
        timeSlider.setMaxWidth(200);

        convert = new StringConverter<Double>() {
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
    }

    private void updateGraphs(String timePeriod) {
        statsController.displayGraphs((String) listView.getSelectionModel().getSelectedItem(), timePeriod);

    }

    public void showPage(String pageName) {

        setupTimeSlider();
        if (pageName.equals("Summary")) {
            showSummaryPage();
        } else {
            showTutorStats(pageName);
        }


    }


    public void showSummaryPage() {
        env.getRootController().setHeader("Summary");
        listView.getSelectionModel().selectFirst();

        FXMLLoader summaryLoader = new FXMLLoader(getClass().getResource("/Views/UserSummary.fxml"));

        try {
            AnchorPane summaryPage = summaryLoader.load();
            currentPage.getChildren().setAll(summaryPage);

            AnchorPane.setLeftAnchor(summaryPage, 0.0);
            AnchorPane.setTopAnchor(summaryPage, 0.0);
            AnchorPane.setBottomAnchor(summaryPage, 0.0);
            AnchorPane.setRightAnchor(summaryPage, 0.0);

            UserSummaryController summaryController = summaryLoader.getController();
            summaryController.create(env);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showTutorStats(String tutor) {

        env.getRootController().setHeader(tutor);
        FXMLLoader tutorStatsLoader = new FXMLLoader(getClass().getResource("/Views/TutorStats.fxml"));

        try {
            AnchorPane stats = tutorStatsLoader.load();
            currentPage.getChildren().setAll(stats);
            AnchorPane.setLeftAnchor(stats, 0.0);
            AnchorPane.setTopAnchor(stats, 0.0);
            AnchorPane.setBottomAnchor(stats, 0.0);
            AnchorPane.setRightAnchor(stats, 0.0);
            statsController = tutorStatsLoader.getController();

            statsController.create(env);
            statsController.displayGraphs(tutor, convert.toString(timeSlider.getValue()));

            listView.getSelectionModel().select(tutor);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getTimePeriod() {
        return convert.toString(timeSlider.getValue());
    }

}
