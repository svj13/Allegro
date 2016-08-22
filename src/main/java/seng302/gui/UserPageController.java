package seng302.gui;

import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import seng302.Environment;

import java.util.ArrayList;

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
    ImageView imageDP;

    private Environment env;


    public UserPageController() {
    }


    public void setEnviroment(Environment env){
        this.env = env;
    }



    public void populateUserOptions(){

        ArrayList<String> options = new ArrayList();
        options.add("Pitch Comparison Tutor");
        options.add("Interval Recognition Tutor");
        options.add("Scale Recognition Tutor");

        Image lockImg = new Image(getClass().getResourceAsStream("/images/lock.png"), 10, 10, false, false);


        for(String option: options){
            Button optionBtn;
            if(option.equals("Scale Recognition Tutor")){
                optionBtn = new Button(option, new ImageView(lockImg));
            }else{
                optionBtn = new Button(option);

            }
            optionBtn.setPrefWidth(191);
            scrollPaneVbox.getChildren().add(optionBtn);

        }


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
