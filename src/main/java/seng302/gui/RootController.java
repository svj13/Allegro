package seng302.gui;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import seng302.Environment;
import seng302.utility.TranscriptManager;

public class RootController {
    Environment env;
    TranscriptManager tm;



    @FXML
    private TextField txtCommand;

    @FXML
    private Button btnGo;

    @FXML
    private TextArea txtTranscript;


    @FXML
    private void initialize(){

        txtTranscript.setText("hello");
        //env = new Environment();

    }

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

    public void setEnvironment(Environment env){
        this.env = env;
        tm = env.getTranscriptManager();


    }

}