package seng302.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Pair;
import seng302.Environment;

import seng302.data.Term;
import seng302.utility.MusicalTermsTutorBackEnd;
import seng302.utility.TutorRecord;

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
    Button btnGo;

    /**
    Stores the terms that have been saved
     */
    MusicalTermsTutorBackEnd dataManager;

    Random rand;

    int partialMarks;

    /**
     * sets up the class and initialises the main variables
     * @param env
     */
    public void create(Environment env) {
        super.create(env);
        dataManager = env.getMttDataManager();
        rand = new Random();
    }

    /**
     * Run when the user clicks the "Go" button.
     * Generates and displays a new set of questions.
     * @param event The mouse click that initiated the method.
     */
    @FXML
    void goAction(ActionEvent event) {
        partialMarks = 0;
        paneQuestions.setVisible(true);
        paneResults.setVisible(false);
        record = new TutorRecord(new Date(), "Musical Terms");
        manager.questions = (Integer.parseInt(txtNumMusicalTerms.getText()));
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


    /**
     * Generates and populates The Origin combo box
     * @return
     */
    private ComboBox<String> generateOriginChoices() {
        ComboBox<String> options = new ComboBox<String>();
        Collections.shuffle(dataManager.getTerms());
        for (Term term : dataManager.getTerms()) {
            options.getItems().add(term.getMusicalTermOrigin());
        }
        return options;
    }

    /**
     * Generates and populates The category combo box
     * @return
     */
    private ComboBox<String> generateCategoryChoices() {
        ComboBox<String> options = new ComboBox<String>();
        Collections.shuffle(dataManager.getTerms());
        for (Term term : dataManager.getTerms()) {
            options.getItems().add(term.getMusicalTermCategory());
        }
        return options;
    }

    /**
     * Generates and populates The definition combo box
     * @return
     */
    private ComboBox<String> generateDefinitionChoices() {
        ComboBox<String> options = new ComboBox<String>();
        Collections.shuffle(dataManager.getTerms());
        for (Term term : dataManager.getTerms()) {
            options.getItems().add(term.getMusicalTermDefinition());
        }
        return options;
    }


    /**
     * Constructs the question panels.
     */
    public HBox generateQuestionPane(Term term) {

        final Term currentTerm = term;
        final HBox rowPane = new HBox();
        formatQuestionRow(rowPane);

        Label termLabel = new Label(currentTerm.getMusicalTermName());
        termLabel.setFont(Font.font("System Bold", 13));
        Button skip = new Button("Skip");
        Image imageSkip = new Image(getClass().getResourceAsStream("/images/right-arrow.png"), 20, 20, true, true);
        skip.setGraphic(new ImageView(imageSkip));

        final ComboBox<String> originOptions = generateOriginChoices();
        originOptions.setPrefHeight(30);
        final ComboBox<String> categoryOptions = generateCategoryChoices();
        categoryOptions.setPrefHeight(30);
        final ComboBox<String> definitionOptions = generateDefinitionChoices();
        definitionOptions.setPrefHeight(30);


        originOptions.setOnAction(new EventHandler<ActionEvent>() {
            // This handler colors the GUI depending on the user's input
            public void handle(ActionEvent event) {
                if (originOptions.getValue().equals(currentTerm.getMusicalTermOrigin())) {
                    originOptions.setStyle("-fx-background-color: green");
                    partialMarks+=1;

                } else {
                    originOptions.setStyle("-fx-background-color: red");
                }

                // Adds to record
                String[] question = new String[]{
                        String.format("Origin of term %s", currentTerm.getMusicalTermOrigin()),
                        originOptions.getValue(),
                        Boolean.toString(originOptions.getValue().equals(currentTerm.getMusicalTermOrigin()))
                };
                record.addQuestionAnswer(question);

                styleAnswer(rowPane, currentTerm, originOptions, categoryOptions, definitionOptions);

                rowPane.getChildren().get(2).setDisable(true);
                if (manager.answered == (manager.questions*3)) {
                    finished();
                }

            }
        });

        categoryOptions.setOnAction(new EventHandler<ActionEvent>() {
            // This handler colors the GUI depending on the user's input
            public void handle(ActionEvent event) {
                if (categoryOptions.getValue().equals(currentTerm.getMusicalTermCategory())) {
                    categoryOptions.setStyle("-fx-background-color: green");
                    partialMarks+=1;
                } else {
                    categoryOptions.setStyle("-fx-background-color: red");
                }

                // Adds to record
                String[] question = new String[]{
                        String.format("Category of term %s", currentTerm.getMusicalTermCategory()),
                        categoryOptions.getValue(),
                        Boolean.toString(categoryOptions.getValue().equals(currentTerm.getMusicalTermCategory()))
                };
                record.addQuestionAnswer(question);

                styleAnswer(rowPane, currentTerm, categoryOptions, definitionOptions, originOptions);

                rowPane.getChildren().get(4).setDisable(true);

                if (manager.answered == (manager.questions*3)) {
                    finished();
                }

            }
        });

        definitionOptions.setOnAction(new EventHandler<ActionEvent>() {
            // This handler colors the GUI depending on the user's input
            public void handle(ActionEvent event) {

                if (definitionOptions.getValue().equals(currentTerm.getMusicalTermDefinition())) {
                    definitionOptions.setStyle("-fx-background-color: green");
                    partialMarks+=1;
                } else {
                    definitionOptions.setStyle("-fx-background-color: red");
                }

                // Adds to record
                String[] question = new String[]{
                        String.format("Definition of term %s", currentTerm.getMusicalTermDefinition()),
                        definitionOptions.getValue(),
                        Boolean.toString(definitionOptions.getValue().equals(currentTerm.getMusicalTermDefinition()))
                };
                record.addQuestionAnswer(question);

                styleAnswer(rowPane, currentTerm, definitionOptions, categoryOptions, originOptions);

                rowPane.getChildren().get(6).setDisable(true);



                if (manager.answered == (manager.questions*3)) {
                    System.out.println(partialMarks);
                    finished();
                }

            }
        });

        skip.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Re-write this to be more specific
                String[] question = new String[]{
                        String.format("Information about %s", currentTerm.getMusicalTermName()),
                        currentTerm.getMusicalTermName()
                };
                record.addSkippedQuestion(question);

                formatSkippedQuestion(rowPane);
                manager.questions -= 3;
                manager.add(new Pair(currentTerm.getMusicalTermName(),currentTerm), 2);
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
        rowPane.getChildren().add(new Label("Origin:"));
        rowPane.getChildren().add(originOptions);
        rowPane.getChildren().add(new Label("Category:"));
        rowPane.getChildren().add(categoryOptions);
        rowPane.getChildren().add(new Label("Definition:"));
        rowPane.getChildren().add(definitionOptions);
        rowPane.getChildren().add(skip);

        rowPane.prefWidthProperty().bind(paneQuestions.prefWidthProperty());


        return rowPane;
    }

    /**
     * If all parts of a question has been answered then the border is coloured
     */
    private void styleAnswer(HBox rowPane, Term currentTerm, ComboBox currentSelection, ComboBox secondBox, ComboBox thirdBox) {
        if(secondBox.getValue() != null && thirdBox.getValue()!= null){
            if(secondBox.getStyle() == "-fx-background-color: red" && thirdBox.getStyle() == "-fx-background-color: red" && currentSelection.getStyle() == "-fx-background-color: red" ){
                // All parts incorrect
                formatIncorrectQuestion(rowPane);
                manager.add(new Pair(currentTerm.getMusicalTermName(),currentTerm), 0);
            }else if(secondBox.getStyle() == "-fx-background-color: green" && thirdBox.getStyle() == "-fx-background-color: green" && currentSelection.getStyle() == "-fx-background-color: green" ){
                // All parts correct
                formatCorrectQuestion(rowPane);
                manager.add(new Pair(currentTerm.getMusicalTermName(),currentTerm), 1);
            }else{
                // Some parts correct, some parts incorrect
                formatPartiallyCorrectQuestion(rowPane);
                manager.add(new Pair(currentTerm.getMusicalTermName(),currentTerm), 0);

            }
            rowPane.getChildren().get(7).setDisable(true);
            manager.answered += 3;
        }
    }

}