package seng302.gui;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import seng302.Environment;

/**
 *  Handles and Creates Users.
 */
public class UserPageController {


    @FXML
    AnchorPane contentPane;

    @FXML
    StackPane stageMap;

    @FXML
    AnchorPane summaryPage;

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
    Label txtFullName;

    @FXML
    ImageView imageDP2;

    @FXML
    Label latestAttempt;

    @FXML
    AnchorPane currentPage;

//    @FXML
//    Label overallStats;

    @FXML
    VBox tutors;

    @FXML
    AnchorPane summary;

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


    /**
     * Pretty much a constructor - loads userPage relevant data.
     */
    protected void load(){
        populateUserOptions();


        imageDP2.setImage(env.getUserHandler().getCurrentUser().getUserPicture());
        imageDP2.setOnMouseClicked(e -> env.getRootController().launchSettings());

        try{

            txtFullName.setText(env.getUserHandler().getCurrentUser().getUserFirstName() + " "
                    + env.getUserHandler().getCurrentUser().getUserLastName());
        }
        catch(NullPointerException e){
            //txtFullName not initialized yet.
        }

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
            //currentTutor = (String) newValue;
        });
        listView.getSelectionModel().selectFirst();
        listView.setDepthProperty(1);


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

    public void showPage(String pageName) {


        if(pageName.equals("Summary")){
            showSummaryPage();
        }
        else{
            showTutorStats(pageName);
        }


    }


    public void showSummaryPage(){
        listView.getSelectionModel().selectFirst();

        FXMLLoader summaryLoader = new FXMLLoader(getClass().getResource("/Views/UserSummary.fxml"));

        try {
            AnchorPane summaryPage = summaryLoader.load();
            currentPage.getChildren().setAll(summaryPage);

            currentPage.setLeftAnchor(summaryPage, 0.0);
            currentPage.setTopAnchor(summaryPage, 0.0);
            currentPage.setBottomAnchor(summaryPage, 0.0);
            currentPage.setRightAnchor(summaryPage, 0.0);

            UserSummaryController summaryController = summaryLoader.getController();
            summaryController.create(env);




        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showTutorStats(String tutor){

        FXMLLoader tutorStatsLoader = new FXMLLoader(getClass().getResource("/Views/TutorStats.fxml"));

        try {
            AnchorPane stats = tutorStatsLoader.load();

            currentPage.getChildren().setAll(stats);
            currentPage.setLeftAnchor(stats, 0.0);
            currentPage.setTopAnchor(stats, 0.0);
            currentPage.setBottomAnchor(stats, 0.0);
            currentPage.setRightAnchor(stats, 0.0);
            TutorStatsController statsController = tutorStatsLoader.getController();

            statsController.create(env);
            statsController.displayGraphs(tutor);

            listView.getSelectionModel().select(tutor);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setCurrentTutor(String currentTutor) {
        this.currentTutor = currentTutor;

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











}
