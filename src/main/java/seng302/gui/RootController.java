package seng302.gui;


import org.json.simple.JSONArray;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
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
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import seng302.Environment;
import seng302.command.UndoRedo;
import seng302.managers.TranscriptManager;
import seng302.utility.FileHandler;
import seng302.utility.OutputTuple;

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
    private KeyboardPaneController keyboardPaneController;

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
    private MenuItem menuPCT;

    @FXML
    private MenuItem menuIRT;

    @FXML
    private MenuItem menuMTT;

    @FXML
    private MenuItem menuSRT;

    @FXML
    private MenuItem menuCRT;

    @FXML
    private Menu menuOpenProjects;

    @FXML
    private TabPane TabPane;


    @FXML
    private void initialize() {


    }

    public void RootController() {

    }

    @FXML
    public void onTranscriptTab(){
        Platform.runLater(new Runnable() {
            public void run() {
                transcriptController.txtCommand.requestFocus();
            }
        });
    }

    public void initialize(URL location, ResourceBundle resources) {
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
            System.exit(0);
        }

        else {

            System.exit(0);
        }


    }

    @FXML
    private void showHideKeyboard() {
        keyboardPaneController.toggleHideKeyboard();
    }


    @FXML
    private void toggleShowKeyboardNotesAlways() {
        keyboardPaneController.toggleShowKeyboardNotesAlways();
    }

    @FXML
    private void toggleShowKeyboardNotesAction() {
        keyboardPaneController.toggleShowKeyboardNotesAction();
    }

    @FXML
    private void stopShowingNotesOnKeyboard() {
        keyboardPaneController.stopShowingNotesOnKeyboard();
    }




    /**
     * Displays a dialog to ask the user whether or not they want to save project changes.
     * @return a boolean - true for save, false for cancel
     */
    public Boolean saveChangesDialog(){
        if (!env.getProjectHandler().isSaved()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Unsaved project changes");

            ButtonType btnSaveProject = new ButtonType("Save");

            ButtonType btnNo = new ButtonType("Don't Save");
            ButtonType btnCancel = new ButtonType("Cancel");

            alert.getButtonTypes().setAll(btnSaveProject, btnNo, btnCancel);

            String contentString = "Unsaved project properties\n\nSave changes to current project?";


            alert.setContentText(contentString);
            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == btnSaveProject){
                checkProjectDirectory();
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
     * Removes all content from the transcript
     */
    @FXML
    private void clearTranscript(){
        tm.setTranscriptContent(new ArrayList<OutputTuple>());
        transcriptController.setTranscriptPane("");
        tm.unsavedChanges = true;
    }


    /**
     * Used to save the transcript to a destination determined by the user, using a filechooser.
     */
    @FXML
    private void saveTranscript() {

        File file = generateSaveFileChooser();

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
        File file = generateSaveFileChooser();

        if (file != null) {
            fileDir = file.getParentFile();
            if(env.getProjectHandler().isProject()){

                fileDir = Paths.get(env.getProjectHandler().getCurrentProjectPath()).toFile();

            }
            path = file.getAbsolutePath();
            tm.saveCommandsOnly(path);
        }

    }

    /**
     * Will be used to undo commands in the transcript
     */
    @FXML
    private void undo() {
        transcriptController.executeAndPrintToTranscript("undo");
    }

    /**
     * Will be used to redo commands in the transcript
     */
    @FXML
    private void redo() {
        transcriptController.executeAndPrintToTranscript("redo");
    }

    /**
     * Creates and displays a "save file" file chooser
     * @return The file which the user selects
     */
    private File generateSaveFileChooser() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter textFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(textFilter);

        if(env.getProjectHandler().isProject()){
            checkProjectDirectory();
            fileDir = Paths.get(env.getProjectHandler().getCurrentProjectPath()).toFile();

        }

        fileChooser.setInitialDirectory(fileDir);
        File file = fileChooser.showSaveDialog(stage);
        return file;
    }

    /**
     * Creates and displays an "open file" file chooser
     * @return The file the user selects
     */
    private File generateOpenFileChooser() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter textFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(textFilter);
        if(env.getProjectHandler().isProject()){
            checkProjectDirectory();
            fileDir = Paths.get(env.getProjectHandler().getCurrentProjectPath()).toFile();

        }
        fileChooser.setInitialDirectory(fileDir);

        File file = fileChooser.showOpenDialog(stage);
        return file;
    }

    /**
     * Opens a transcript that has been previously saved.
     */
    @FXML
    private void importTranscript() {
        File file = generateOpenFileChooser();

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

    /**
     * Opens a file of commands only.
     */
    @FXML
    public void importCommands() {
        File file = generateOpenFileChooser();

        if (file != null) {
            fileDir = file.getParentFile();
            path = file.getAbsolutePath();
            try {
                ArrayList<String> commands = tm.loadCommands(path);
                TabPane.getSelectionModel().selectFirst();
                transcriptController.beginPlaybackMode(commands);
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



    public void checkProjectDirectory(){
        Path path = Paths.get("UserData/Projects/");
        if(!Files.isDirectory(path)){
            try{
                Files.createDirectories(path);

            }
            catch(IOException e){
                //Failed to create the directory.
                e.printStackTrace();
            }


        }

    }

    /**
     * Displays a dialog for the user to create a new project
     */
    @FXML
    public void newProject() {
        env.resetEnvironment();
        env.getProjectHandler().createNewProject();
    }

    /**
     * Saves project information
     */
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
        selectItem.acceleratorProperty().setValue(KeyCombination.keyCombination("Shortcut+O"));
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


    @FXML
    private void openPitchTutor(){


    }

    @FXML
    private void openIntervalTutor(){


    }

    @FXML
    private void openMusicalTermTutor(){


    }

    @FXML
    private void openScaleTutor(){


    }

    @FXML
    private void openChordTutor(){


    }








    /**
     * Displays an error message
     * @param errorMessage The message to be displayed
     */
    public void errorAlert(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(errorMessage);
        alert.showAndWait();

    }


    private void copyFolder(File sourceFolder, File destinationFolder){

    }

    /**
     * Open Project browser.
     */
    private void selectProjectDirectory(){
        DirectoryChooser dirChooser = new DirectoryChooser();

        dirChooser.setTitle("Select a project directory");
        Path path = Paths.get("UserData/Projects/");
        checkProjectDirectory();
        dirChooser.setInitialDirectory(path.toFile());


        File folder  = dirChooser.showDialog(stage);

        if(folder != null){
            if(folder.isDirectory()){
                for(File f : folder.listFiles()){

                    if(f.getName().endsWith(".json") && f.getName().substring(0, f.getName().length() - 5).equals(folder.getName())){

                        if(! new File("userData/Projects/"+folder.getName()).isDirectory()){

                            try{

                                //Copy all files from inside the projects folder.
                                FileHandler.copyFolder(folder,Paths.get(path.toString()+"/"+folder.getName()).toFile());
                            }catch (Exception ce){
                                ce.printStackTrace();
                                errorAlert("Could not Import the project! Maybe it already exists in the Projects folder?");
                            }


                            //project with said name does not exist in the projects directory.. import it.
                            //env.getProjectHandler().importProject(folder);

                        }

                        env.getProjectHandler().loadProject(folder.getName());
                        return;
                    }
                }
                errorAlert("Not a valid Project folder - Try again!");
                selectProjectDirectory();
                return;
            }
        }
    }


    /**
     * Sets the stage for root
     * @param stage
     */
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
        keyboardPaneController.create(env);

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


    /**
     * Resets all tutors and the transcript controller to their default state.
     */
    public void reset() {
        clearTranscript();

        //need to destroy the tutors
        transcriptController.hidePlaybackGui();
        transcriptController.setEnv(env);

        PitchComparisonTabController.clearTutor();
        IntervalRecognitionTabController.clearTutor();
        MusicalTermsTabController.clearTutor();
        ScaleRecognitionTabController.clearTutor();
    }

    public TranscriptPaneController getTranscriptController() {
        return transcriptController;
    }
}