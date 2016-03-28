package seng302.gui;

import javafx.beans.property.SimpleIntegerProperty;
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

import java.io.File;

/**
 * Created by jat157 on 20/03/16.
 */
public class PitchComparisonTutorController {

    Environment env;

    Stage stage;



    @FXML
    private Pane pane1;






    @FXML
    private void initialize(){

    }





    @FXML
    public void handleKeyPressed(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER) {

        }

        else if(event.getCode() == KeyCode.UP){



        }

        else if(event.getCode() == KeyCode.DOWN){


        }
        else if(event.getCode() == KeyCode.ALPHANUMERIC){

        }



    }








    public void setStage(Stage stage){
        this.stage = stage;

    }
    public void setEnv(Environment env){
        this.env = env;
    }





}
