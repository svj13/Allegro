package seng302.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import seng302.Environment;
import seng302.Users.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jmw280 on 21/07/16.
 */
public class UserLoginController {

    @FXML
    HBox recentUsersHbox;

    @FXML
    JFXTextField usernameInput;

    @FXML
    JFXPasswordField passwordInput;

    @FXML
    JFXButton btnRegister;

    @FXML
    Label labelError;

    @FXML
    JFXButton btnLogin;

    Environment env;
    RequiredFieldValidator passwordValidator;

    ArrayList<RecentUserController> recentUsers = new ArrayList<>();


    public UserLoginController(){

    }

    @FXML
    public void initialize() {


        passwordValidator = new RequiredFieldValidator();

        //validator.setAwsomeIcon(new Icon(AwesomeIcon.WARNING,"2em",";","error"));
        passwordInput.getValidators().add(passwordValidator);
        passwordInput.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                passwordValidator.setMessage("Password Required");
                passwordInput.validate();
            }

        });
    }

    public void setEnv(Environment env){
        this.env = env;
    }



    protected void deselectUsers(){
        for(RecentUserController recentUser: recentUsers){

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
        //Image image = new Image(getClass().getResourceAsStream
                //("/images/gear-1119298_960_720.png"), 10, 10, true, true);


        for(User user: env.getUserHandler().getRecentUsers()) {
            name = user.getUserName();

            Image image = user.getUserPicture();
            recentUsersHbox.getChildren().add(generateRecentUser(name, image));
        }


    }

    private Boolean validCredentials(String username, String password){
        if(username.length() > 0 && password.length() > 0) //Must be atleast one character.
            return true;

        return false;


    }

    @FXML
    protected void register(){


//        if (!(env.getUserHandler().getUserNames().contains(usernameInput.getText())) && validCredentials(usernameInput.getText(), passwordInput.getText())){
//            env.getUserHandler().createUser(usernameInput.getText(), passwordInput.getText());
//            logIn();
//        }
//        else{
//
//            labelError.setText("User already exists!");
//            labelError.setTextFill(javafx.scene.paint.Color.RED);
//        }

        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getResource("/Views/UserRegistration.fxml"));

        Parent root1 = null;
        try {
            root1 = loader1.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene1 = new Scene(root1);
        Stage registerStage = (Stage) btnLogin.getScene().getWindow();

        registerStage.setTitle("Register new user");
        registerStage.setScene(scene1);


        registerStage.setOnCloseRequest(event -> {
            System.exit(0);
            event.consume();
        });


        registerStage.show();
        UserRegisterController userRegisterController = loader1.getController();
        userRegisterController.setEnv(env);





    }

    @FXML
    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            logIn();
        }
    }


    @FXML
    protected void logIn(){



        if(env.getUserHandler().userPassExists(usernameInput.getText(), passwordInput.getText())){


            env.getUserHandler().setCurrentUser(usernameInput.getText());

            //Close login window.
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.close();

            env.getRootController().showWindow(true);
        }else{

//            final Label message = new Label("");
//            labelError.setText("Invalid username or password.");
//            labelError.setTextFill(javafx.scene.paint.Color.RED);
            passwordValidator.setMessage("Invalid username or password.");
            passwordInput.clear();
            passwordInput.validate();
            passwordInput.requestFocus();



        }

    }

}
