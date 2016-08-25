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
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import seng302.Environment;
import seng302.Users.User;

import java.awt.*;
import java.awt.Color;
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
    private JFXPasswordField txtPassword;

    @FXML
    private JFXButton btnReturn;

    @FXML
    private JFXButton btnRegister;

    @FXML
    private JFXTextField txtfname;

    @FXML
    private JFXTextField txtlname;





    Environment env;

    RequiredFieldValidator passwprdValidator, usernameValidator;


    public UserRegisterController() {

    }

    public void setEnv(Environment env) {
        this.env = env;
    }


    @FXML
    public void initialize() {


        usernameValidator = new RequiredFieldValidator();
        passwprdValidator = new RequiredFieldValidator();

        //validator.setAwsomeIcon(new Icon(AwesomeIcon.WARNING,"2em",";","error"));
        txtPasswordConfirm.getValidators().add(passwprdValidator);
        txtUsername.getValidators().add(usernameValidator);

        txtUsername.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (checkUserNameExists()) {
                usernameValidator.setMessage("User already exists!");

            }
            txtUsername.validate();

        });


    }

    private Boolean checkUserNameExists() {
        if (env.getUserHandler().getUserNames().contains(txtUsername.getText())) {
            //If the User already exists!

            usernameValidator.setMessage(String.format("user '%s' already exists.", txtUsername.getText()));
            txtUsername.clear();
            txtUsername.validate();
            txtUsername.setFocusColor(javafx.scene.paint.Color.RED);
            txtUsername.requestFocus();

            return true;
        }
        txtUsername.setFocusColor(Paint.valueOf("#4059a9"));
        return false;

    }


    private Boolean validCredentials() {

        Boolean valid = true;
        //Validating username
        if (txtUsername.getText().length() > 0) {
            if (env.getUserHandler().getUserNames().contains(txtUsername.getText())) {
                //If the User already exists!

                valid = !checkUserNameExists();

            }
        } else { //username needs to be atleast 1 character.
            usernameValidator.setMessage("Username must be atleast 1 character.");
            txtUsername.validate();
            txtUsername.setFocusColor(javafx.scene.paint.Color.RED);
            return false;
        }

        //Validating password

        if (txtPassword.getText().length() > 0 && txtPasswordConfirm.getText().length() > 0) {
            if (!txtPassword.getText().equals(txtPasswordConfirm.getText())) {
                //Passwords didn't match.
                passwprdValidator.setMessage("Password and Confirm password didn't match!");
                txtPassword.clear();
                txtPasswordConfirm.clear();

                txtPassword.validate();

                return false;
            }
        } else {
            passwprdValidator.setMessage("Password must be atleast 1 character.");
            txtPassword.clear();
            txtPasswordConfirm.clear();
            txtPassword.validate();
            txtPassword.requestFocus();
            return false;


        }


        return valid;


    }

    @FXML
    protected void register() {


        if (!(env.getUserHandler().getUserNames().contains(txtUsername.getText())) && validCredentials()) {
            env.getUserHandler().createUser(txtUsername.getText(), txtPassword.getText());

            if (env.getUserHandler().userPassExists(txtUsername.getText(), txtPassword.getText())) {


                env.getUserHandler().setCurrentUser(txtUsername.getText());

                env.getUserHandler().getCurrentUser().setUserFirstName(txtfname.getText());
                env.getUserHandler().getCurrentUser().setUserLastName(txtlname.getText());


                env.getRootController().showWindow(true);

            }



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





}
