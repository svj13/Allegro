package seng302.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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


    public UserPageController(){
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





}
