package seng302.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng302.Environment;
import seng302.Users.UserHandler;
import seng302.utility.FileHandler;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;


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
    private Button uploadPhotoButton;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField firstNameField;

    private Environment env;

    public UserSettingsController() {

    }


    public void create(Environment env) {
        this.env = env;
        this.imageDP.setImage(env.getUserHandler().getCurrentUser().getUserPicture());
    }

    @FXML
    private void launchPhotoChooser() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("All Images", "*.*");
        fileChooser.getExtensionFilters().add(imageFilter);

        Stage stage = new Stage();


        File file = fileChooser.showOpenDialog(stage);

        UserHandler userHandler = env.getUserHandler();
        Path userPath = userHandler.getCurrentUserPath();
        System.out.println(userPath.toString());
        Path filePath = Paths.get(userPath.toString()+"/profilePicture");

        try {
            FileHandler.copyFolder(file, filePath.toFile());
            userHandler.getCurrentUser().setUserPicture(new Image(filePath.toUri().toString()));
            imageDP.setImage(userHandler.getCurrentUser().getUserPicture());
            env.getRootController().updateImage();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("nooooooooooooooooooooooooooooooooooooooooooo");
        }
    }
}