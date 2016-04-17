package seng302.gui;

import java.util.ArrayList;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.Environment;

import seng302.data.Term;
import seng302.utility.MusicalTermsTutorBackEnd;
import seng302.utility.TutorManager;

/**
 * Created by jmw280 on 16/04/16.
 */


public class MusicalTermsTutorController {


    @FXML
    TextField txtNumMusicalTerms;

    @FXML
    VBox questionRows;

    @FXML
    AnchorPane IntervalRecognitionTab;

    @FXML
    ScrollPane paneQuestions;

    @FXML
    Button btnGo;

    Environment env;

    TutorManager manager;

    MusicalTermsTutorBackEnd dataManager;


    public void create(Environment env) {
        this.env = env;
        manager = env.getMttManager();
        dataManager = env.getMttDataManager();
    }

    @FXML
    void goAction(ActionEvent event) {
        manager.questions = Integer.parseInt(txtNumMusicalTerms.getText());
        if (manager.questions >= 1) {
            // Run the tutor
            questionRows.getChildren().clear();
            for (int i = 0; i < manager.questions; i++) {
                HBox questionRow = generateQuestionPane();
                questionRows.getChildren().add(questionRow);
                questionRows.setMargin(questionRow, new Insets(10, 10, 10, 10));
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Number of Musical Terms");
            alert.setContentText("Please select a positive number of intervals");
            alert.setResizable(false);
            alert.showAndWait();
        }
    }


    //generate origin combobox
    private ComboBox<String> generateOriginChoices() {
        ComboBox<String> options = new ComboBox<String>();

        for (Term term : dataManager.getTerms()) {
            options.getItems().add(term.getMusicalTermOrigin());
        }
        return options;
    }

    //generate category combobox
    private ComboBox<String> generateCategoryChoices() {
        ComboBox<String> options = new ComboBox<String>();

        for (Term term : dataManager.getTerms()) {
            options.getItems().add(term.getMusicalTermCategory());
        }
        return options;
    }

    //generate description combobox
    private ComboBox<String> generateDefinitionChoices() {
        ComboBox<String> options = new ComboBox<String>();
        for (Term term : dataManager.getTerms()) {
            options.getItems().add(term.getMusicalTermDefinition());
        }
        return options;
    }


    /**
     * Constructs the question panels.
     */
    private HBox generateQuestionPane() {

        final HBox rowPane = new HBox();

        rowPane.setPadding(new Insets(10, 10, 10, 10));

        rowPane.setSpacing(10);
        rowPane.setStyle("-fx-border-color: #336699; -fx-border-width: 2px;");


        // get musical term

        //make combo box X3

        Label termLabel = new Label("term name");
        Button skip = new Button();
        Image imageSkip = new Image(getClass().getResourceAsStream("/right-arrow.png"), 20, 20, true, true);
        skip.setGraphic(new ImageView(imageSkip));


        skip.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {


            }
        });

        rowPane.getChildren().add(termLabel);
        rowPane.getChildren().add(new Label("Origin"));
        rowPane.getChildren().add(generateOriginChoices());
        rowPane.getChildren().add(new Label("Category"));
        rowPane.getChildren().add(generateCategoryChoices());
        rowPane.getChildren().add(new Label("Definion"));
        rowPane.getChildren().add(generateDefinitionChoices());
        rowPane.getChildren().add(skip);

        rowPane.prefWidthProperty().bind(paneQuestions.prefWidthProperty());


        return rowPane;
    }


}