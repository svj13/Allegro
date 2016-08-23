package seng302.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jmw280 on 21/07/16.
 */
public class UserRegisterController {

    @FXML
    HBox recentUsersHbox;

    @FXML
    TextField usernameInput;


    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXPasswordField txtPasswordConfirm;

    @FXML
    private JFXButton btnReturn;

    @FXML
    private JFXButton btnRegister;

    @FXML
    private JFXPasswordField txtPassword;

    Environment env;


    public UserRegisterController() {

    }

    public void setEnv(Environment env) {
        this.env = env;
    }


    private Boolean validCredentials(String username, String password, String confirmPassword) {

        if (password.equals(confirmPassword)) {
            if (username.length() > 0 && password.length() > 0) //Must be atleast one character.
                return true;

        }

        return false;


    }

    @FXML
    protected void register() {


        if (!(env.getUserHandler().getUserNames().contains(txtUsername.getText())) && validCredentials(txtUsername.getText(), txtPassword.getText(), txtPasswordConfirm.getText())) {
            env.getUserHandler().createUser(txtUsername.getText(), txtPassword.getText());
            logIn();
        } else {

//            labelError.setText("User already exists!");
//            labelError.setTextFill(javafx.scene.paint.Color.RED);
        }
    }


    @FXML
    protected void Return() {
        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getResource("/Views/userLogin.fxml"));

        Parent root1 = null;
        try {
            root1 = loader1.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene1 = new Scene(root1);
        Stage loginStage = (Stage) btnRegister.getScene().getWindow();
        loginStage.setTitle("Allegro - Login");
        loginStage.setScene(scene1);


        loginStage.setOnCloseRequest(event -> {
            System.exit(0);
            event.consume();
        });


        loginStage.show();
        UserLoginController userLoginController = loader1.getController();
        userLoginController.setEnv(env);
        userLoginController.displayRecentUsers();
    }


    @FXML
    protected void logIn() {


        if (env.getUserHandler().userPassExists(txtUsername.getText(), txtPassword.getText())) {


            env.getUserHandler().setCurrentUser(txtUsername.getText());

            env.getRootController().showWindow(true);
        } else {


        }

    }

}
