package seng302.gui;

import java.util.ArrayList;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seng302.Environment;
import seng302.data.Note;

/**
 * Created by jat157 on 20/03/16.
 */
public class PitchComparisonTutorController {

    Environment env;

    Stage stage;



    @FXML
    TextField txtNotePairs;

    @FXML ComboBox<String> cbxLower;
    @FXML
    AnchorPane pitchTutorAnchor;

    @FXML ComboBox<String> cbxUpper;
    @FXML
    ScrollPane paneQuestions;

    @FXML
    VBox questionRows;

    @FXML Button btnGo;

    Random rand;


    @FXML
    private void initialize(){
        System.out.println("pitch comparison initialized.");
        rand = new Random();

    }

    /**
     * The command which is binded to the Go button, or the enter key when the command prompt is active.
     */
    @FXML
    private void goAction(){
        ArrayList<FlowPane> panes = new ArrayList<FlowPane>();

        //generateComboValues(cbxUpper);
        questionRows.getChildren().clear();
        for(int i = 0; i < Integer.parseInt(txtNotePairs.getText()); i++){

            FlowPane rowPane = new FlowPane();
            rowPane.getChildren().add(new Label(Note.lookup(String.valueOf(rand.nextInt(128))).getNote() + "    "));

            rowPane.getChildren().add(new Label(Note.lookup(String.valueOf(rand.nextInt(128))).getNote()));
            ToggleGroup group = new ToggleGroup();
            ToggleButton higher = new ToggleButton("Higher");
            higher.setToggleGroup(group);
            ToggleButton lower = new ToggleButton("Lower");
            lower.setToggleGroup(group);

            rowPane.getChildren().add(higher);
            rowPane.getChildren().add(lower);

            rowPane.prefWidthProperty().bind(paneQuestions.prefWidthProperty());

            questionRows.getChildren().add(rowPane);
        }

        paneQuestions.prefWidthProperty().bind(pitchTutorAnchor.prefWidthProperty());



    }
    public void test(){
        System.out.println("Test worked!!");
    }


    public void createA(Environment env){

        System.out.println("inside pitch comparison tutor");
        this.env = env;
        generateComboValues(cbxLower);
        generateComboValues(cbxUpper);


    }

    /**
     *
     * @param cbx
     */
    private void generateComboValues(ComboBox<String> cbx){

        for(int i = 0; i < Note.noteCount; i++){

            String val = i + " : "  + Note.lookup(String.valueOf(i)).getNote();


            assert cbxLower != null : "cbxLower was not injected, check the fxml";


            cbx.getItems().add(val);
            //System.out.println(cbx.getItems().size());
        }
         //TODO Make it so it generates everytime a combobox is selected.
        //So that The upperValues box is generated to contain only values higher than the selected
        //Lowerbox value


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






}
