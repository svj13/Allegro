package seng302.gui;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.PieChart;
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



    public void populateUserOptions(){

        ArrayList<String> options = new ArrayList<>();
        options.add("Pitch Comparison Tutor");
        options.add("Interval Recognition Tutor");
        options.add("Scale Recognition Tutor");

        Image lockImg = new Image(getClass().getResourceAsStream("/images/lock.png"), 10, 10, false, false);


        for(String option: options){
            Button optionBtn;
            if(option.equals("Scale Recognition Tutor")){
                optionBtn = new Button(option, new ImageView(lockImg));
                optionBtn.setDisable(true);

            }else{
                optionBtn = new Button(option);
                optionBtn.setOnAction(event -> {
                    displayGraph(option);
                });

            }
            optionBtn.setPrefWidth(191);
            scrollPaneVbox.getChildren().add(optionBtn);

        }


    }

    private void displayGraph(String tutor) {
        Pair<Integer, Integer> correctIncorrect = new Pair<>(0, 0);
        chartTitle.setText(tutor);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        if (tutor.equals("Pitch Comparison Tutor")) {
            correctIncorrect = env.getUserHandler().getCurrentUser().getProjectHandler().getCurrentProject().tutorHandler.getTutorTotals("pitchTutor");
        }

        pieChartData.add(new PieChart.Data("Correct", correctIncorrect.getKey()));
        pieChartData.add(new PieChart.Data("Incorrect", correctIncorrect.getValue()));
        pieChart.dataProperty().setValue(pieChartData);



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
