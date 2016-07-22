package seng302.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jmw280 on 21/07/16.
 */
public class RecentUserController {


    @FXML
    Label username;

    @FXML
    ImageView userPic;

    public RecentUserController() {

    }

    public void setUsername(String name){
        username.setText(name);
    }

    public void setUserPic(Image image){
        userPic.setImage(image);
    }
}
