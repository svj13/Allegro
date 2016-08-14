package seng302.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import seng302.Environment;
import seng302.Users.User;

import java.io.IOException;
import java.util.ArrayList;

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

    @FXML
    Button btnRegister;

    @FXML
    Button btnLogIn;

    Environment env;

    ArrayList<RecentUserController> recentUsers = new ArrayList<>();


    public UserLoginController(){

    }

    public void setEnv(Environment env){
        this.env = env;
    }



    protected void deselectUsers(){
        for(RecentUserController recentUser: recentUsers){
            System.out.println(recentUser.getStyle());
            recentUser.deselect();
        }
    }

    protected void onRecentSelect(String username){
        usernameInput.setText(username);
        passwordInput.clear();
        passwordInput.requestFocus();
    }


    private Node generateRecentUser(String username, Image image){

        Node recentUser;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/recentUser.fxml"));
        try {
            recentUser = loader.load();
            RecentUserController recentUserController = loader.getController();
            recentUserController.setParentController(this);

            recentUserController.setUsername(username);
            recentUserController.setUserPic(image);

            recentUsers.add(recentUserController);
            return recentUser;

        }catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }



    public void displayRecentUsers(){
        String name;
        Image image = new Image(getClass().getResourceAsStream
                ("/images/gear-1119298_960_720.png"), 10, 10, true, true);

        System.out.println(env.getUserHandler().getRecentUsers());
        for(User user: env.getUserHandler().getRecentUsers()) {
            name = user.getUserName();


            recentUsersHbox.getChildren().add(generateRecentUser(name, image));
        }


    }

    @FXML
    protected void register(){

        if (!(env.getUserHandler().getUserNames().contains(usernameInput))){
            env.getUserHandler().createUser(usernameInput.getText(), passwordInput.getText());
        }
        else{
            System.out.println("user already exists");
        }
    }

    @FXML
    protected void logIn(){
        System.out.println("login");

        if(env.getUserHandler().userPassExists(usernameInput.getText(), passwordInput.getText())){
            System.out.println("sucsess");

            env.getUserHandler().setCurrentUser(usernameInput.getText());

            //Close login window.
            Stage stage = (Stage) btnLogIn.getScene().getWindow();
            stage.close();

            env.getRootController().showWindow(true);
        }else{
            System.out.println("incorrect login info");
        }

    }

}
