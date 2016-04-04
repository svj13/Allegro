package seng302.gui;


import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import seng302.Environment;
import seng302.utility.TranscriptManager;

public class RootController implements Initializable {
    Environment env;
    TranscriptManager tm;
    Stage stage;

    String path;
    File fileDir;


    @FXML
    private Pane pane1;

    @FXML
    AnchorPane paneMain;


    @FXML
    private PitchComparisonTutorController PitchComparisonTabController; //pitchController;
    @FXML
    private TranscriptPaneController transcriptController;

    @FXML
    private StackPane stackPane1;

    @FXML
    private MenuItem menuQuit;

    @FXML
    private TextArea txtTranscript;

    @FXML
    private MenuItem menuOpen;

    @FXML
    private MenuItem menuSave;

    @FXML
    private void initialize() {

    }

    public void RootController() {

    }


    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("root controller initialize test");
//
    }


    /**
     * Closes the application, if There are unsaved changes then it prompts the user to save the
     * file.
     */
    @FXML
    private void closeApplication() {
        if (tm.unsavedChanges == true) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Unsaved changes");
            alert.setContentText("Are you sure that you would like to quit?");

            ButtonType saveBtn = new ButtonType("Save");
            ButtonType quitBtn = new ButtonType("Quit");
            ButtonType cancelBtn = new ButtonType("Cancel");

            alert.getButtonTypes().setAll(saveBtn, quitBtn, cancelBtn);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == saveBtn) {
                saveTranscript();
                if (tm.unsavedChanges == false) {
                    System.exit(0);
                }
            } else if (result.get() == quitBtn) {
                System.exit(0);

            } else {
                //do nothing
            }


        } else {
            System.exit(0);
        }
    }

    /**
     * Used to save the transcript to a destination determined by the user, using a filechooser.
     */
    @FXML
    private void saveTranscript() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter textFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(textFilter);
        fileChooser.setInitialDirectory(fileDir);
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            fileDir = file.getParentFile();
            path = file.getAbsolutePath();
            tm.save(path);
        }
    }

    /**
     * Opens a transcript that has been previously saved.
     */
    @FXML
    private void openTranscript() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter textFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(textFilter);
        fileChooser.setInitialDirectory(fileDir);
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            fileDir = file.getParentFile();
            path = file.getAbsolutePath();
            try {
                tm.open(path);
                txtTranscript.setText(tm.convertToText());
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This file is not valid");
                alert.showAndWait();
                System.err.println("Not a valid file");
            }

        }
    }


    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                closeApplication();
                event.consume();

            }
        });
    }

    /**
     * Connects the GUI components with the logic environment
     * @param env
     */
    public void setEnvironment(Environment env) {
        this.env = env;
        tm = env.getTranscriptManager();


        transcriptController.setEnv(env);



        PitchComparisonTabController.create(env);


    }

    public Environment getEnv() {
        return env;
    }

}