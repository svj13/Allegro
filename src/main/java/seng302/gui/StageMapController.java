package seng302.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import seng302.Environment;
import javafx.scene.control.Button;


/**
 * Created by svj13 on 2/09/16.
 */
public class StageMapController {

    @FXML
    private Button scaleRecognitionTutorButton;

    @FXML
    private Button keySignaturesTutorButton;

    @FXML
    private AnchorPane stageMap;

    @FXML
    private Button diatonicChordsTutorButton;

    @FXML
    private Button scaleRecognitionAdvancedTutorButton;

    @FXML
    private Button majorModesTutorButton;

    @FXML
    private Button musicalTermsTutorButton;

    @FXML
    private Button chordRecognitionTutorButton;

    @FXML
    private Button pitchTutorButton;

    @FXML
    private Button chordSpellingTutorButton;

    @FXML
    private Button chordRecognitionAdvancedTutorButton;

    @FXML
    private Button intervalRecognitionButton;




    Environment env;

    public void setEnvironment(Environment env) {
        this.env = env;
    }

    public StageMapController() {


        //when each button is pushed, it will take the user to the corresponding tutor tab

        musicalTermsTutorButton.setOnAction(event-> {

        });

        pitchTutorButton.setOnAction(event-> {

        });

        scaleRecognitionTutorButton.setOnAction(event-> {

        });

        chordRecognitionTutorButton.setOnAction(event-> {

        });

        intervalRecognitionButton.setOnAction(event-> {

        });

        scaleRecognitionAdvancedTutorButton.setOnAction(event-> {

        });

        chordRecognitionAdvancedTutorButton.setOnAction(event-> {

        });

        chordSpellingTutorButton.setOnAction(event-> {

        });

        keySignaturesTutorButton.setOnAction(event-> {

        });

        diatonicChordsTutorButton.setOnAction(event-> {

        });

        majorModesTutorButton.setOnAction(event-> {


        });

    }

    private void openTutorTab(String tutor) {

    }





}
