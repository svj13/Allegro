package seng302.gui;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
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


    @FXML
    private Pane pane1;

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
    private SimpleStringProperty enteredCommand;

    @FXML
    private void initialize(){

    }

    private String enteredText;


    @FXML
    private void goAction(){

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





    @FXML
    public void handleKeyPressed(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER) {
            goAction();
        }

        else if(event.getCode() == KeyCode.UP){

            int size = env.getTranscriptManager().getTranscriptTuples().size();
            if(env.getTranscriptManager().historyLevel == -1 && size > 0){
                env.getTranscriptManager().historyLevel = 1;

                enteredText = txtCommand.getText();

            }
            else{
                if(env.getTranscriptManager().historyLevel < size){
                    env.getTranscriptManager().historyLevel ++;
                }


            }

            txtCommand.setText(env.getTranscriptManager().getTranscriptTuples().get(size-env.getTranscriptManager().historyLevel).getCommand());
            System.out.println(enteredText);
        }

        else if(event.getCode() == KeyCode.DOWN){
            int hl = env.getTranscriptManager().historyLevel;
            int size = env.getTranscriptManager().getTranscriptTuples().size();

            System.out.println(hl);
            if(hl > 1){

                env.getTranscriptManager().historyLevel --;

                txtCommand.setText(env.getTranscriptManager().getTranscriptTuples().get(size-env.getTranscriptManager().historyLevel).getCommand());
            }
            else if(env.getTranscriptManager().historyLevel > 0){
                env.getTranscriptManager().historyLevel --;
                txtCommand.setText(enteredText);


            }
            else if(env.getTranscriptManager().historyLevel == 0){
                txtCommand.setText("");
                env.getTranscriptManager().historyLevel = -1;
            }
        }



    }





    public void setStage(Stage stage){
        this.stage = stage;

    }
    public void setEnv(Environment env){
        this.env = env;
    }





}
