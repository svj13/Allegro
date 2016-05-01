package seng302.gui;


import org.json.simple.JSONArray;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import seng302.Environment;
import seng302.managers.TranscriptManager;

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
    private ScaleRecognitionTutorController ScaleRecognitionTabController;

    @FXML
    private StackPane stackPane1;

    @FXML
    private MenuItem menuQuit;



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
        if (!env.getProjectHandler().isSaved()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Unsaved changes");


            ButtonType btnSaveProject = new ButtonType("Save project");

            ButtonType btnQuit = new ButtonType("Quit");
            ButtonType btnCancel = new ButtonType("Cancel");

            alert.getButtonTypes().setAll(btnSaveProject, btnQuit, btnCancel);

            String contentString = "Unsaved Project properties\n\nAre you sure that you would like to quit?";


            alert.setContentText(contentString);
            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == btnSaveProject){
                env.getProjectHandler().saveCurrentProject();
                System.exit(0);
            }
            else if (result.get() == btnQuit) {
                System.exit(0);
            }
            else if(result.get() == btnCancel){

            }


        }
        else if( env.getProjectHandler().isProject() && env.getTranscriptManager().unsavedChanges){
            env.getProjectHandler().saveCurrentProject();
        }

        else {

            System.exit(0);
        }


    }

    public Boolean saveChangesDialog(){
        if (!env.getProjectHandler().isSaved()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Unsaved changes");

            ButtonType btnSaveProject = new ButtonType("Save");

            ButtonType btnNo = new ButtonType("Don't Save");
            ButtonType btnCancel = new ButtonType("Cancel");

            alert.getButtonTypes().setAll(btnSaveProject, btnNo, btnCancel);

            String contentString = "Unsaved project properties\n\nSave changes to current project?";


            alert.setContentText(contentString);
            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == btnSaveProject){
                env.getProjectHandler().saveCurrentProject();

            }
            else if(result.get() == btnCancel){
                return false;
            }


        }
        else if(env.getProjectHandler().isProject() &&  env.getTranscriptManager().unsavedChanges){

            env.getProjectHandler().saveCurrentProject();
        }
        return true;
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
    private void importTranscript() {
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

                transcriptController.setTranscriptPane(tm.convertToText());
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This file is not valid");
                alert.showAndWait();
                System.err.println("Not a valid file");
            }

        }
    }

    public void setTranscriptPaneText(String text){

        transcriptController.setTranscriptPane(text);

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

                    env.getProjectHandler().saveProject(path.toString() + "/"+resultString);
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
        env.getProjectHandler().saveCurrentProject();
    }

    @FXML
    private void bindOpenObjects(){

        JSONArray projects = env.getProjectHandler().getProjectList();
        menuOpenProjects.getItems().clear();

        MenuItem selectItem = new MenuItem("Select Project");
        selectItem.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            public void handle(javafx.event.ActionEvent event) {

                if(saveChangesDialog())  selectProjectDirectory();
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
                    if(saveChangesDialog())  env.getProjectHandler().loadProject(projectName);
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

                    if(f.getName().endsWith(".managers") && f.getName().substring(0, f.getName().length() - 5).equals(folder.getName())){

                        System.out.println("VALID PROJECT");
                        env.getProjectHandler().loadProject(folder.getName());
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
        ScaleRecognitionTabController.create(env);

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