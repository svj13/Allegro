package seng302.gui;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

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
import javafx.util.Pair;
import seng302.Environment;

import seng302.data.Term;
import seng302.utility.MusicalTermsTutorBackEnd;
import seng302.utility.TutorManager;

/**
 * Created by jmw280 on 16/04/16.
 */


public class MusicalTermsTutorController extends TutorController{


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

    MusicalTermsTutorBackEnd dataManager;

    Random rand;


    public void create(Environment env) {
        super.create(env);
        dataManager = env.getMttDataManager();
        rand = new Random();
    }

    @FXML
    void goAction(ActionEvent event) {
        manager.questions = Integer.parseInt(txtNumMusicalTerms.getText());
        if (manager.questions >= 1) {
            ArrayList<Term> termArray = dataManager.getTerms();
            // Run the tutor
            questionRows.getChildren().clear();
            if(termArray.size() < 1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("No Musical Terms Added");
                alert.setContentText("There are no terms to be tested on. \nTo add them use the 'add musical term' command");
                alert.showAndWait();

            }else {//if there are terms to display
                for (int i = 0; i < manager.questions; i++) {
                    Term term = termArray.get(rand.nextInt(termArray.size()));
                    HBox questionRow = generateQuestionPane(term);
                    questionRows.getChildren().add(questionRow);
                    questionRows.setMargin(questionRow, new Insets(10, 10, 10, 10));
                }
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
    private HBox generateQuestionPane(Term term) {

        final Term currentTerm = term;
        final HBox rowPane = new HBox();

        rowPane.setPadding(new Insets(10, 10, 10, 10));

        rowPane.setSpacing(10);
        rowPane.setStyle("-fx-border-color: #336699; -fx-border-width: 2px;");


        Label termLabel = new Label(currentTerm.getMusicalTermName());
        Button skip = new Button();
        Image imageSkip = new Image(getClass().getResourceAsStream("/right-arrow.png"), 20, 20, true, true);
        skip.setGraphic(new ImageView(imageSkip));

        final ComboBox<String> originOptions = generateOriginChoices();
        final ComboBox<String> categoryOptions = generateCategoryChoices();
        final ComboBox<String> definitionOptions = generateDefinitionChoices();


        originOptions.setOnAction(new EventHandler<ActionEvent>() {
            // This handler colors the GUI depending on the user's input
            public void handle(ActionEvent event) {
                if (originOptions.getValue().equals(currentTerm.getMusicalTermOrigin())) {
                    originOptions.setStyle("-fx-background-color: green");

                } else {
                    originOptions.setStyle("-fx-background-color: red");
                }

                if(categoryOptions.getValue() != null && definitionOptions.getValue()!= null){
                    if(categoryOptions.getStyle() == "-fx-background-color: red" && definitionOptions.getStyle() == "-fx-background-color: red" && originOptions.getStyle() == "-fx-background-color: red" ){
                        rowPane.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                        manager.add(new Pair(currentTerm.getMusicalTermName(),currentTerm), 0);
                    }else if(categoryOptions.getStyle() == "-fx-background-color: green" && definitionOptions.getStyle() == "-fx-background-color: green" && originOptions.getStyle() == "-fx-background-color: green" ){
                        rowPane.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
                        manager.add(new Pair(currentTerm.getMusicalTermName(),currentTerm), 1);
                    }else{
                        rowPane.setStyle("-fx-border-color: yellow; -fx-border-width: 2px;");
                        manager.add(new Pair(currentTerm.getMusicalTermName(),currentTerm), 0);

                    }
                    rowPane.getChildren().get(7).setDisable(true);
                    manager.answered += 1;
                }

                rowPane.getChildren().get(2).setDisable(true);
                if (manager.answered == manager.questions) {
                    finished();
                }

            }
        });

        categoryOptions.setOnAction(new EventHandler<ActionEvent>() {
            // This handler colors the GUI depending on the user's input
            public void handle(ActionEvent event) {
                if (categoryOptions.getValue().equals(currentTerm.getMusicalTermCategory())) {
                    categoryOptions.setStyle("-fx-background-color: green");
                } else {
                    categoryOptions.setStyle("-fx-background-color: red");;
                }

                if(definitionOptions.getValue() != null && originOptions.getValue()!= null){
                    if(categoryOptions.getStyle() == "-fx-background-color: red" && definitionOptions.getStyle() == "-fx-background-color: red" && originOptions.getStyle() == "-fx-background-color: red" ){
                        rowPane.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                        manager.add(new Pair(currentTerm.getMusicalTermName(),currentTerm), 0);
                    }else if(categoryOptions.getStyle() == "-fx-background-color: green" && definitionOptions.getStyle() == "-fx-background-color: green" && originOptions.getStyle() == "-fx-background-color: green" ){
                        rowPane.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
                        manager.add(new Pair(currentTerm.getMusicalTermName(),currentTerm), 1);
                    }else{
                        rowPane.setStyle("-fx-border-color: yellow; -fx-border-width: 2px;");
                        manager.add(new Pair(currentTerm.getMusicalTermName(),currentTerm), 0);

                    }
                    rowPane.getChildren().get(7).setDisable(true);
                    manager.answered += 1;
                }
                rowPane.getChildren().get(4).setDisable(true);

                if (manager.answered == manager.questions) {
                    finished();
                }

            }
        });

        definitionOptions.setOnAction(new EventHandler<ActionEvent>() {
            // This handler colors the GUI depending on the user's input
            public void handle(ActionEvent event) {
                if (definitionOptions.getValue().equals(currentTerm.getMusicalTermDefinition())) {
                    definitionOptions.setStyle("-fx-background-color: green");;
                } else {
                    definitionOptions.setStyle("-fx-background-color: red");;
                }

                if(categoryOptions.getValue() != null && originOptions.getValue()!= null){
                    if(categoryOptions.getStyle() == "-fx-background-color: red" && definitionOptions.getStyle() == "-fx-background-color: red" && originOptions.getStyle() == "-fx-background-color: red" ){
                        rowPane.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                        manager.add(new Pair(currentTerm.getMusicalTermName(),currentTerm), 0);
                    }else if(categoryOptions.getStyle() == "-fx-background-color: green" && definitionOptions.getStyle() == "-fx-background-color: green" && originOptions.getStyle() == "-fx-background-color: green" ){
                        rowPane.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
                        manager.add(new Pair(currentTerm.getMusicalTermName(),currentTerm), 1);
                    }else{
                        rowPane.setStyle("-fx-border-color: yellow; -fx-border-width: 2px;");
                        manager.add(new Pair(currentTerm.getMusicalTermName(),currentTerm), 0);

                    }
                    rowPane.getChildren().get(7).setDisable(true);
                    manager.answered += 1;
                }

                rowPane.getChildren().get(6).setDisable(true);



                if (manager.answered == manager.questions) {
                    finished();
                }

            }
        });

        skip.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                rowPane.setStyle("-fx-border-color: grey; -fx-border-width: 2px;");
                manager.questions -= 1;
                manager.add(new Pair(currentTerm.getMusicalTermName(),currentTerm), 0);
                rowPane.getChildren().get(2).setDisable(true);
                rowPane.getChildren().get(4).setDisable(true);
                rowPane.getChildren().get(6).setDisable(true);
                rowPane.getChildren().get(7).setDisable(true);
                if (manager.answered == manager.questions) {
                    finished();
                }


            }
        });


        rowPane.getChildren().add(termLabel);
        rowPane.getChildren().add(new Label("Origin"));
        rowPane.getChildren().add(originOptions);
        rowPane.getChildren().add(new Label("Category"));
        rowPane.getChildren().add(categoryOptions);
        rowPane.getChildren().add(new Label("Definion"));
        rowPane.getChildren().add(definitionOptions);
        rowPane.getChildren().add(skip);

        rowPane.prefWidthProperty().bind(paneQuestions.prefWidthProperty());


        return rowPane;
    }


    public void allPartsOfQuestionAnswered() {


    }


    /**
     * Calculates a user's score after a tutoring session
     *
     * @param correct  The number of questions the user answered correctly
     * @param answered The number of questions the user answered, correctly or incorrectly
     * @return the user's score as a percentage value
     */
    private float getScore(int correct, int answered) {
        float score = 0;
        if (answered > 0) {
            score = (float) correct / (float) answered * 100;
        }
        return score;

    }

    /**
     * This function is run once a tutoring session has been completed.
     */
    private void finished() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Finished");
        float userScore = getScore(manager.correct, manager.answered);
        String outputText = String.format("You have finished the tutor. You got %d out of %d. This is a score of %.2f percent", manager.correct, manager.answered, userScore);
        alert.setContentText(outputText);


        ButtonType retestBtn = new ButtonType("Retest");
        ButtonType clearBtn = new ButtonType("Clear");

        if (manager.getTempIncorrectResponses().size() > 0) {
            //Can re-test
            alert.getButtonTypes().setAll(retestBtn, clearBtn);
        } else {
            //Perfect score
            alert.getButtonTypes().setAll(clearBtn);
        }
        Optional<ButtonType> result = alert.showAndWait();
        questionRows.getChildren().clear();

        if (result.get() == clearBtn) {
            manager.saveTempIncorrect();
        } else if (result.get() == retestBtn) {
            retest();
        }

        // Clear the current session
        manager.answered = 0;
        manager.correct = 0;

    }

    /**
     * If the user chooses to re-test their self on their failed questions, this function
     * sets up the tutoring environment for that.
     */
    private void retest() {
        ArrayList<Pair> tempIncorrectResponses = new ArrayList<Pair>(manager.getTempIncorrectResponses());
        manager.clearTempIncorrect();
        manager.questions = tempIncorrectResponses.size();
        for(Pair<String, Term> pair : tempIncorrectResponses){
            HBox questionRow = generateQuestionPane(pair.getValue());
            questionRows.getChildren().add(questionRow);
            questionRows.setMargin(questionRow, new Insets(10, 10, 10, 10));
        }

    }
}