package seng302.gui;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
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
import seng302.data.KeySignature;

/**
 * Created by jmw280 on 22/08/16.
 */
public class UserPageController {


    @FXML
    AnchorPane contentPane;

    @FXML
    SplitPane userView;

//    @FXML
//    AnchorPane tutorView;

//    @FXML
//    VBox stats;

//    @FXML
//    StackedBarChart stackedBar;

//    @FXML
//    StackedBarChart recentBar;

//    @FXML
//    AnchorPane overallPane;

    @FXML
    AnchorPane topPane;

    @FXML
    JFXListView listView;

//    @FXML
//    Label chartTitle;

//    @FXML
//    Label tutorName;

//    @FXML
//    LineChart lineChart;



    @FXML
    ImageView imageDP;

    @FXML
    Label latestAttempt;

    @FXML
    AnchorPane currentPage;

//    @FXML
//    Label overallStats;

    private Environment env;

    private String currentTutor;


    public PitchComparisonTutorController pitchComparisonTutorController;

    public IntervalRecognitionTutorController intervalRecognitionTutorController;

    public ChordSpellingTutorController chordSpellingTutorController;

    public ChordRecognitionTutorController chordRecognitionTutorController;

    public KeySignaturesTutorController keySignaturesTutorController;

    public MusicalTermsTutorController musicalTermsTutorController;

    public DiatonicChordsTutorController diatonicChordsTutorController;

    public ScaleRecognitionTutorController scaleRecognitionTutorController;

    public UserSummaryController userSummaryController;




    public UserPageController() {
    }



    public void setEnvironment(Environment env) {
        this.env = env;
        this.env.setUserPageController(this);
    }

    @FXML
    private void launchUserSettings() {

    }

    @FXML
    public void logOutUser() throws IOException {
//        stage.close();
//        showLoginWindow();
//        //reset();

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

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.showPage((String) newValue);
            currentTutor = (String) newValue;
        });
        listView.getSelectionModel().selectFirst();
        listView.setMaxWidth(200);
        listView.setMinWidth(200);
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


    }

    protected void  showPage(String pageName){
        switch (pageName) {
            case "Pitch Comparison Tutor":
                //correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("pitchTutor");
                //correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("pitchTutor");
                //dateAndTime = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTimeAndScores("pitchTutor");
                break;
            case "Interval Recognition Tutor":
                /*correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("intervalTutor");
                correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("intervalTutor");
                dateAndTime = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTimeAndScores("intervalTutor");
                */break;
            case "Scale Recognition Tutor":
               // correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("scaleTutor");
                //correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("scaleTutor");
                break;
            case "Musical Terms Tutor":
               // correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("musicalTermTutor");
                //correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("musicalTermTutor");
                break;
            case "Chord Recognition Tutor":
               // correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("chordTutor");
               // correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("chordTutor");
                break;
            case "Chord Spelling Tutor":
           //     correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("chordSpellingTutor");
            //    correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("chordSpellingTutor");
                break;
            case "Key Signature Tutor":
              //  correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("keySignatureTutor");
             //   correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("keySignatureTutor");
                break;
            case "Diatonic Chord Tutor":
               // correctIncorrectRecent = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getRecentTutorTotals("diatonicChordTutor");
              //  correctIncorrectOverall = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("diatonicChordTutor");
                break;

            case "Summary":
                showSummaryPage();
        }



    }


    public void showSummaryPage(){

        FXMLLoader summaryLoader = new FXMLLoader(getClass().getResource("/Views/UserSummary.fxml"));

        try {
            AnchorPane summaryPage = summaryLoader.load();

            currentPage.getChildren().setAll(summaryPage);

            UserSummaryController summaryController = summaryLoader.getController();

            summaryController.create(env);



        } catch (IOException e) {
            e.printStackTrace();
        }

    }



//
//    /**
//     * Allows for dynamic updating of the question slider in Musical Terms tutor. When you load this
//     * tab, it checks how many terms are in the current session, and changes the default accordingly
//     * - up to 5.
//     */
//    public void reloadNumberTerms() {
//        MusicalTermsTabController.terms = env.getMttDataManager().getTerms().size();
//        if (MusicalTermsTabController.terms <= 5) {
//            MusicalTermsTabController.numQuestions.setValue(MusicalTermsTabController.terms);
//        }
//    }




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




    public void updateImage() {
        final Circle clip = new Circle(imageDP.getFitWidth() - 50.0, imageDP.getFitHeight() - 50.0, 100.0);
        //System.out.println(env.getUserHandler().getCurrentUser());


        //imageDP.setImage(env.getUserHandler().getCurrentUser().getUserPicture());
        System.out.println("made it herer");

        clip.setRadius(50);

        imageDP.setClip(clip);

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = imageDP.snapshot(parameters, null);

        imageDP.setClip(null);
        imageDP.setEffect(new DropShadow(5, Color.BLACK));

        imageDP.setImage(image);


    }

    @FXML
    public void loadTutor(){


        switch (currentTutor) {
            case "Pitch Comparison Tutor":
                //userView.setVisible(false);
                openPitchTutor();
                break;
            case "Interval Recognition Tutor":
                //userView.setVisible(false);
                openIntervalTutor();
                break;
            case "Scale Recognition Tutor":
                //userView.setVisible(false);
                openScaleTutor();
                break;
            case "Musical Terms Tutor":
                //userView.setVisible(false);
                openMusicalTermTutor();
                break;
            case "Chord Recognition Tutor":
                //userView.setVisible(false);
                openChordTutor();
                break;
            case "Chord Spelling Tutor":
                //userView.setVisible(false);
                openSpellingTutor();
                break;
            case "Key Signature Tutor":
                //userView.setVisible(false);
                openKeySignatureTutor();
                break;
            case "Diatonic Chord Tutor":
                //userView.setVisible(false);
                openDiatonicChordTutor();
                break;
        }


        //tutorView.setVisible(true);
    }

    public void loadUserPage(){
        //tutorView.setVisible(false);
        //userView.setVisible(true);
    }


    /**
     * opens the pitch tutor when the pitch tutor menu option is pressed If there is already an open
     * tutor of the same form then it sets focus to the already open tutor
     */
    private void openPitchTutor() {


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/PitchComparisonPane.fxml"));

//            try {
//                tutorView.getChildren().add(loader.load());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


            pitchComparisonTutorController = loader.getController();
            pitchComparisonTutorController.create(env);


    }

    /**
     * opens the interval tutor when the interval menu option is pressed If there is already an open
     * tutor of the same form then it sets focus to the already open tutor
     */
    private void openIntervalTutor() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/IntervalRecognitionPane.fxml"));

//        try {
//            tutorView.getChildren().add(loader.load());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        intervalRecognitionTutorController = loader.getController();
        intervalRecognitionTutorController.create(env);

    }


    /**
     * opens the musical terms tutor when the musical term tutor menu option is pressed If there is
     * already an open tutor of the same form then it sets focus to the already open tutor
     */
    private void openMusicalTermTutor() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/MusicalTermsPane.fxml"));

//        try {
//            tutorView.getChildren().add(loader.load());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        musicalTermsTutorController = loader.getController();
        musicalTermsTutorController.create(env);

    }


    /**
     * opens the scale tutor when the scale menu option is pressed If there is already an open tutor
     * of the same form then it sets focus to the already open tutor
     */
    private void openScaleTutor() {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/ScaleRecognitionPane.fxml"));

//        try {
//            tutorView.getChildren().add(loader.load());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        scaleRecognitionTutorController = loader.getController();
        scaleRecognitionTutorController.create(env);

    }


    /**
     * opens the chord tutor when the chord tutor menu option is pressed If there is already an open
     * tutor of the same form then it sets focus to the already open tutor
     */
    private void openChordTutor() {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/ChordRecognitionPane.fxml"));

//        try {
//            tutorView.getChildren().add(loader.load());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        chordRecognitionTutorController = loader.getController();
        chordRecognitionTutorController.create(env);

    }


    /**
     * Opens the diatonic chord tutor when the diatonic chord tutor menu option is pressed. If there
     * is already an open tutor of the same form then it sets focus to the already open tutor.
     */
    private void openDiatonicChordTutor() {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/DiatonicChordPane.fxml"));

//        try {
//            tutorView.getChildren().add(loader.load());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        diatonicChordsTutorController = loader.getController();
        diatonicChordsTutorController.create(env);

    }


    /**
     * opens the keySignatures tutor when the key signatures tutor menu option is pressed If there
     * is already an open tutor of the same form then it sets focus to the already open tutor
     */
    private void openKeySignatureTutor() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/KeySignaturesPane.fxml"));

//        try {
//            tutorView.getChildren().add(loader.load());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        keySignaturesTutorController = loader.getController();
        keySignaturesTutorController.create(env);

    }


    /**
     * Opens the chord spelling tutor. If this tutor is already open, focus is transferred to it.
     */
    private void openSpellingTutor() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/ChordSpellingPane.fxml"));

//        try {
//            tutorView.getChildren().add(loader.load());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        chordSpellingTutorController = loader.getController();
        chordSpellingTutorController.create(env);

    }


}
