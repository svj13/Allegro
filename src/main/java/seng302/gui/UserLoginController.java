package seng302.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Created by jmw280 on 21/07/16.
 */
public class UserLoginController {

    @FXML
    HBox recentUsersHbox;

    @FXML
    TextField usernameInput;

    @FXML
    PasswordField passwordInput;


    public UserLoginController(){
    }





    private Node generateRecentUser(String username, Image image){

        Node recentUser;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/recentUser.fxml"));
        try {
            recentUser = loader.load();
            RecentUserController recentUserController = loader.getController();
            System.out.println(recentUser);
            recentUserController.setUsername(username);
            recentUserController.setUserPic(image);
            return recentUser;

        }catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }



    private void displayRecentUsers(){
        String name = "joseph";
        Image image = new Image(getClass().getResourceAsStream
                ("/images/gear-1119298_960_720.png"), 10, 10, true, true);


//        for ... {
//
//            recentUsersHbox.getChildren().add(generateRecentUser(name, image));
//        }


    }

    public void create(){
        displayRecentUsers();
    }

    private void checkUsernamePassword(){

    }



}
