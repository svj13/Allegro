package seng302.gui;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by Jonty on 01-Sep-16.
 */
public class BaseSettingsController {

    @FXML
    UserSettingsController userSettingsC;

    @FXML
    UISkinnerController themeC;

    FXMLLoader userSettingsLoader, themeLoader;


    @FXML
    private VBox sidePane;

    @FXML
    private AnchorPane settingsPane;

    @FXML
    private JFXButton btnProjectSettings;

    @FXML
    private JFXButton btnUserSettings;
    @FXML
    private JFXButton btnThemeSettings;


    @FXML
    private void initialize() {

        //Load user settings controller.
        userSettingsLoader = new FXMLLoader();
        userSettingsLoader.setLocation(getClass().getResource("/Views/UserSettings.fxml"));
        userSettingsC = userSettingsLoader.getController();

        //Load theme controller
        themeLoader = new FXMLLoader();
        themeLoader.setLocation(getClass().getResource("/Views/UISkinner.fxml"));
        themeC = themeLoader.getController();

    }

    @FXML
    void openUserSettings(ActionEvent event) {

        try {
            //settingsPane.setContent((Node) loader.load());
            userSettingsLoader.setRoot(null);
            userSettingsLoader.setController(userSettingsC);
            settingsPane.getChildren().setAll((Node) userSettingsLoader.load());
            btnUserSettings.setStyle("-fx-background-color: #223768");
            btnThemeSettings.setStyle("");
            btnProjectSettings.setStyle("");

        } catch (IOException e) {
            e.printStackTrace();

        }

        //UserSettingsTabController = loader.getController();
        //UserSettingsTabController.create(env);

    }

    @FXML
    void onThemeSettings(ActionEvent event) {
        try {
            //settingsPane.setContent((Node) loader.load());
            themeLoader.setRoot(null);
            themeLoader.setController(themeC);
            settingsPane.getChildren().setAll((Node) themeLoader.load());

            btnThemeSettings.setStyle("-fx-background-color: #223768");
            btnProjectSettings.setStyle("");
            btnUserSettings.setStyle("");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openProjectSettings(ActionEvent event) {


        btnProjectSettings.setStyle("-fx-background-color: #223768");
        btnThemeSettings.setStyle("");
        btnUserSettings.setStyle("");
    }


}
