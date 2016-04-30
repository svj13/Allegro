package seng302.gui;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.json.simple.JSONArray;
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
    private IntervalRecognitionTutorController IntervalRecognitionTabController;

    @FXML
    private TranscriptPaneController transcriptController;

    @FXML
    private MusicalTermsTutorController MusicalTermsTabController;

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
    private MenuItem menuSaveCommands;

    @FXML
    private Menu menuOpenProjects;

    @FXML
    private TabPane tabPane;

    @FXML
    private void initialize() {

    }

    public void RootController() {

    }

    @FXML
    public void tabChanged(){

    }

    public void initialize(URL location, ResourceBundle resources) {

//        System.out.println("root controller initialize test");
//        tabPane.setOnMouseReleased(new EventHandler<Event>() {
//            public void handle(Event event) {
//                System.out.println("hello");
//            }
//        });

//
    }


    /**
     * Closes the application, if There are unsaved changes then it prompts the user to save the
     * file.
     */
    @FXML
    private void closeApplication() {
        if (tm.unsavedChanges == true || !env.getJson().isSaved()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Unsaved changes");



            ButtonType btnSaveTranscript = new ButtonType("Save Transcript");
            ButtonType btnSaveProject = new ButtonType("Save Project");

            ButtonType quitBtn = new ButtonType("Quit");
            ButtonType cancelBtn = new ButtonType("Cancel");
            String contentString = "";
            alert.getButtonTypes().setAll(quitBtn, cancelBtn);
            if(tm.unsavedChanges){
                alert.getButtonTypes().add(0,btnSaveTranscript);
                contentString += "Unsaved Transcript Changes\n";
            }
            if(!env.getJson().isSaved()){
                alert.getButtonTypes().add(0,btnSaveProject);
                contentString += "Unsaved Project Changes\n";
            }

            contentString += "Are you sure that you would like to quit?";


            alert.setContentText(contentString);



            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == btnSaveTranscript) {
                saveTranscript();
                this.closeApplication();
            } else if (result.get() == quitBtn) {
                System.exit(0);

            }
            else if(result.get() == btnSaveProject){
                env.getJson().saveCurrentProject();
                this.closeApplication();

            }

            else {
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
        File file = generateFileChooser();

        if (file != null) {
            fileDir = file.getParentFile();
            path = file.getAbsolutePath();
            tm.save(path);
        }
    }

    /**
     * Used to save only the commands to a destination determined by user
     */
    @FXML
    private void saveCommands() {
        File file = generateFileChooser();

        if (file != null) {
            fileDir = file.getParentFile();
            path = file.getAbsolutePath();
            tm.saveCommandsOnly(path);
        }

    }

    /**
     * Creates and displays a "save file" file chooser
     * @return The file which the user selects
     */
    private File generateFileChooser() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter textFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(textFilter);
        fileChooser.setInitialDirectory(fileDir);
        File file = fileChooser.showSaveDialog(stage);
        return file;
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

    @FXML
    public void newProject(){

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("New Project");
        dialog.setHeaderText("New Project");
        dialog.setContentText("Please enter the project name:");

    // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            String resultString = result.get().toString();
            Path path = Paths.get("UserData/Projects/" + resultString);
            if(!Files.isDirectory(path)){
                try{
                    Files.createDirectories(path);

                    env.getJson().saveProject(path.toString() + "/"+resultString);
                    //setWindowTitle(resultString);

                }
                catch(IOException e){
                    //Failed to create the directory.
                    e.printStackTrace();
                }


            }
            else{
                System.out.println("Sorry, the path " + resultString + " already exists.");
            }

        }

    // The Java 8 way to get the response value (with lambda expression).


    }

    @FXML
    private void saveProject(){
        env.getJson().saveCurrentProject();
    }

    @FXML
    private void bindOpenObjects(){

        JSONArray projects = env.getJson().getProjectList();
        menuOpenProjects.getItems().clear();

        MenuItem selectItem = new MenuItem("Select Project");
        selectItem.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            public void handle(javafx.event.ActionEvent event) {
                selectProjectDirectory();
            }


        });
        SeparatorMenuItem dividor = new SeparatorMenuItem();
        dividor.setText("Recent Projects..");
        menuOpenProjects.getItems().add(selectItem);
        menuOpenProjects.getItems().add(dividor);
        for(int i = projects.size()-1; i >= 0 ; i--){
            final String projectName = projects.get(i).toString();

            MenuItem projectItem = new MenuItem(projectName);
            projectItem.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                public void handle(javafx.event.ActionEvent event) {
                    env.getJson().loadProject(projectName);
                }


            });

            menuOpenProjects.getItems().add(projectItem); //Add to Open projects menu
            if((projects.size()-1) - i >= 5) break; //Only show the 5 latest projects.

        }

    }


    private void selectProjectDirectory(){
        DirectoryChooser dirChooser = new DirectoryChooser();

        dirChooser.setTitle("Select a project directory");
        Path path = Paths.get("UserData/Projects/");



        dirChooser.setInitialDirectory(path.toFile());


        File folder  = dirChooser.showDialog(stage);

        if(folder != null){
            if(folder.isDirectory()){
                for(File f : folder.listFiles()){

                    if(f.getName().endsWith(".JSON") && f.getName().substring(0, f.getName().length() - 5).equals(folder.getName())){

                        System.out.println("VALID PROJECT");
                        env.getJson().loadProject(folder.getName());
                        return;

                    }

                }
                System.out.println("Not a valid project folder - try again!");
                selectProjectDirectory();
                return;
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
        IntervalRecognitionTabController.create(env);
        MusicalTermsTabController.create(env);


        env.setRootController(this);

    }

    public void setWindowTitle(String text){
        this.stage.setTitle("Allegro    " + text);
    }

    public Environment getEnv() {
        return env;
    }

    /**
     * Allows for dynamic updating of the question slider in Musical Terms tutor.
     * When you load this tab, it checks how many terms are in the current session, and changes
     * the default accordingly - up to 5.
     */
    public void reloadNumberTerms() {
        MusicalTermsTabController.terms = env.getMttDataManager().getTerms().size();
        if (MusicalTermsTabController.terms <= 5) {
            MusicalTermsTabController.numQuestions.setValue(MusicalTermsTabController.terms);
        }
    }

}