package seng302.gui;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class RootController {

    @FXML
    private Button btnGo;

    @FXML
    private TextArea txtTranscript;


    @FXML
    private void initialize(){

        txtTranscript.setText("hello");
    }

    @FXML
    private void goAction(){
        System.out.println("button clicked");
    }
}