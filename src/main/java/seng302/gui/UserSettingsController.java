package seng302.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import seng302.Environment;


public class UserSettingsController {

    @FXML
    private Button editFirstNameButton;

    @FXML
    private Button editLastNameButton;

    @FXML
    private ImageView imageDP;

    @FXML
    private AnchorPane chordSpellingAnchor;

    @FXML
    private Button editDpButton;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField firstNameField;

    private Environment env;

    public UserSettingsController() {

    }


    public void create(Environment env) {
        this.env = env;
    }
}