package seng302.gui;

import java.io.File;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import seng302.Environment;

/**
 * Created by jat157 on 20/03/16.
 */
public class TranscriptPaneController {

    Environment env;

    Stage stage;


    String path;
    File fileDir;


//    @FXML
//    private Pane pane1;

    @FXML
    private TextField txtCommand;

    @FXML
    private Button btnGo;

    @FXML
    private MenuItem menuQuit;

    @FXML
    private TextArea txtTranscript;

    @FXML
    private MenuItem menuOpen;

    @FXML
    private MenuItem menuSave;

    @FXML
    private SimpleIntegerProperty history;


    @FXML
    private void initialize() {
        // Text field can only request focus once everything has been loaded.
        Platform.runLater(new Runnable() {
            public void run() {
                txtCommand.requestFocus();
            }
        });
    }

    private String enteredCommand;

    private int historyLevel;


    /**
     * The command which is binded to the Go button, or the enter key when the command prompt is
     * active.
     */
    @FXML
    private void goAction() {

        String text = txtCommand.getText();
        txtCommand.setText("");
        if (text.length() > 0) {
            env.getTranscriptManager().setCommand(text);
            env.getExecutor().executeCommand(text);
            txtTranscript.appendText(env.getTranscriptManager().getLastCommand());


        } else {
            txtTranscript.appendText("[ERROR] Cannot submit an empty command.\n");
        }
    }


    /**
     *
     * @param event
     */

    @FXML
    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            goAction();
            env.getTranscriptManager().resetHistoryLevel();
        } else if (event.getCode() == KeyCode.UP) {

            //handleScrollUp();
            txtCommand.setText(env.getTranscriptManager().cycleInputUp(txtCommand.getText()));

        } else if (event.getCode() == KeyCode.DOWN) {

//            handleScrollDown();
            txtCommand.setText(env.getTranscriptManager().cycleInputDown(txtCommand.getText()));
        } else if (event.getCode() == KeyCode.ALPHANUMERIC) {
            env.getTranscriptManager().resetHistoryLevel();
            // historyLevel = -1;
            //enteredCommand = "";
        }


    }

    public void setTranscriptPane(String text){
        System.out.println("txtTranscript text"  +txtTranscript.getText());
        txtTranscript.setText(text);
    }


    public void setStage(Stage stage) {
        this.stage = stage;

    }

    public void setEnv(Environment env) {
        this.env = env;



    }



}
