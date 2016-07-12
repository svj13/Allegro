package seng302.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;
import seng302.Environment;
import seng302.data.Term;
import seng302.utility.MusicalTermsTutorBackEnd;
import seng302.utility.TutorRecord;

/**
 * Created by jmw280 on 16/04/16.
 */


public class MusicalTermsTutorController extends TutorController {

    @FXML
    VBox questionRows;

    @FXML
    AnchorPane IntervalRecognitionTab;

    @FXML
    Button btnGo;

    /**
     * Stores the terms that have been saved
     */
    MusicalTermsTutorBackEnd dataManager;

    Random rand;

    ArrayList<Term> termsBeingViewed;


    //Number of terms to choose from
    int terms = 0;

    /**
     * sets up the class and initialises the main variables
     */
    public void create(Environment env) {
        super.create(env);
        dataManager = env.getMttDataManager();

        rand = new Random();
        initialiseQuestionSelector();
    }

    /**
     * Run when the user clicks the "Go" button. Generates and displays a new set of questions.
     *
     * @param event The mouse click that initiated the method.
     */
    @FXML
    void goAction(ActionEvent event) {
        paneQuestions.setVisible(true);
        paneResults.setVisible(false);
        record = new TutorRecord();
        manager.resetEverything();
        manager.questions = selectedQuestions;
        if (manager.questions >= 1) {
            termsBeingViewed = new ArrayList<Term>(dataManager.getTerms());
            // Run the tutor
            questionRows.getChildren().clear();
            if (termsBeingViewed.size() < 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("No Musical Terms Added");
                alert.setContentText("There are no terms to be tested on. \nTo add them use the 'add musical term' command");
                alert.showAndWait();

            } else {//if there are terms to display
                for (int i = 0; i < manager.questions; i++) {
                    if (termsBeingViewed.size() < 1) {
                        termsBeingViewed = new ArrayList<Term>(dataManager.getTerms());
                    }
                    int randomNumber = rand.nextInt(termsBeingViewed.size());
                    Term term = termsBeingViewed.get(randomNumber);
                    termsBeingViewed.remove(randomNumber);
                    Pair<String, Term> dummyPair = new Pair<String, Term>("", term);
                    HBox questionRow = generateQuestionPane(dummyPair);
                    questionRows.getChildren().add(questionRow);
                    VBox.setMargin(questionRow, new Insets(10, 10, 10, 10));

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
     */
    private ComboBox<String> generateOriginChoices(Term currentTerm) {
        Random rand = new Random();
        int correctPosition = rand.nextInt(5);
        ComboBox<String> options = new ComboBox<>();
        Collections.shuffle(dataManager.getTerms());
        int i = 0;
        Boolean alreadyAdded = false;
        while (options.getItems().size() < 5 && i < dataManager.getTerms().size()) {
            if (correctPosition == i && !alreadyAdded) {
                if (!(options.getItems().contains(currentTerm.getMusicalTermOrigin()))) {
                    options.getItems().add(currentTerm.getMusicalTermOrigin());
                }
                i -= 1;
                alreadyAdded = true;

            } else {
                if (!(options.getItems().contains(dataManager.getTerms().get(i).getMusicalTermOrigin()))) {
                    options.getItems().add(dataManager.getTerms().get(i).getMusicalTermOrigin());
                }
            }
            i += 1;
        }
        return options;
    }

    /**
     * Generates and populates The category combo box
     */
    private ComboBox<String> generateCategoryChoices(Term currentTerm) {
        Random rand = new Random();
        int correctPosition = rand.nextInt(5);
        ComboBox<String> options = new ComboBox<String>();
        Collections.shuffle(dataManager.getTerms());
        int i = 0;
        Boolean alreadyAdded = false;
        while (options.getItems().size() < 5 && i < dataManager.getTerms().size()) {
            if (correctPosition == i && !alreadyAdded) {
                if (!(options.getItems().contains(currentTerm.getMusicalTermCategory()))) {
                    options.getItems().add(currentTerm.getMusicalTermCategory());
                }
                i -= 1;
                alreadyAdded = true;

            } else {
                if (!(options.getItems().contains(dataManager.getTerms().get(i).getMusicalTermCategory()))) {
                    options.getItems().add(dataManager.getTerms().get(i).getMusicalTermCategory());
                }
            }
            i += 1;
        }
        return options;
    }

    /**
     * Generates and populates The definition combo box
     */
    private ComboBox<String> generateDefinitionChoices(Term currentTerm) {
        Random rand = new Random();
        int correctPosition = rand.nextInt(5);
        ComboBox<String> options = new ComboBox<String>();
        Collections.shuffle(dataManager.getTerms());
        int i = 0;
        Boolean alreadyAdded = false;
        while (options.getItems().size() < 5 && i < dataManager.getTerms().size()) {
            if (correctPosition == i && !alreadyAdded) {
                if (!(options.getItems().contains(currentTerm.getMusicalTermDefinition()))) {
                    options.getItems().add(currentTerm.getMusicalTermDefinition());
                }
                i -= 1;
                alreadyAdded = true;

            } else {
                if (!(options.getItems().contains(dataManager.getTerms().get(i).getMusicalTermDefinition()))) {
                    options.getItems().add(dataManager.getTerms().get(i).getMusicalTermDefinition());
                }
            }
            i += 1;
        }
        return options;
    }


    /**
     * Constructs the question panels.
     */
    public HBox generateQuestionPane(Pair dummyPair) {

        final Term currentTerm = (Term) dummyPair.getValue();
        final HBox rowPane = new HBox();

        formatQuestionRow(rowPane);

        Label termLabel = new Label(currentTerm.getMusicalTermName());
        termLabel.setFont(Font.font("System Bold", FontWeight.BOLD, 13));
        Button skip = new Button("Skip");
        Image imageSkip = new Image(getClass().getResourceAsStream("/images/right-arrow.png"), 20, 20, true, true);
        skip.setGraphic(new ImageView(imageSkip));

        final ComboBox<String> originOptions = generateOriginChoices(currentTerm);
        originOptions.setPrefSize(100, 30);
        final ComboBox<String> categoryOptions = generateCategoryChoices(currentTerm);
        categoryOptions.setPrefSize(100, 30);
        final ComboBox<String> definitionOptions = generateDefinitionChoices(currentTerm);
        definitionOptions.setPrefSize(100, 30);


        originOptions.setOnAction(event -> {
            // This handler colors the GUI depending on the user's input
            if (originOptions.getValue().equals(currentTerm.getMusicalTermOrigin())) {
                originOptions.setStyle("-fx-background-color: green");

            } else {
                originOptions.setStyle("-fx-background-color: red");
                ((HBox) ((VBox) (rowPane.getChildren().get(0))).getChildren().get(1)).getChildren().add(new Label(currentTerm.getMusicalTermOrigin()));
            }

            // Adds to record
            String[] question = new String[]{
                    String.format("Origin of term %s", currentTerm.getMusicalTermName()),
                    originOptions.getValue(),
                    Boolean.toString(originOptions.getValue().equals(currentTerm.getMusicalTermOrigin()))
            };
            projectHandler.saveTutorRecords("musicalTerm", record.addQuestionAnswer(question));
            env.getRootController().setTabTitle("musicalTermTutor", true);

            styleAnswer(rowPane, currentTerm, originOptions, categoryOptions, definitionOptions);

            ((HBox) ((VBox) (rowPane.getChildren().get(0))).getChildren().get(1)).getChildren().get(1).setDisable(true);
            if (manager.answered == manager.questions) {
                finished();
            }
        });

        categoryOptions.setOnAction(event -> {
            // This handler colors the GUI depending on the user's input
            if (categoryOptions.getValue().equals(currentTerm.getMusicalTermCategory())) {
                categoryOptions.setStyle("-fx-background-color: green");
            } else {
                categoryOptions.setStyle("-fx-background-color: red");
                ((HBox) ((VBox) (rowPane.getChildren().get(0))).getChildren().get(2)).getChildren().add(new Label(currentTerm.getMusicalTermCategory()));
            }

            // Adds to record
            String[] question = new String[]{
                    String.format("Category of term %s", currentTerm.getMusicalTermName()),
                    categoryOptions.getValue(),
                    Boolean.toString(categoryOptions.getValue().equals(currentTerm.getMusicalTermCategory()))
            };
            projectHandler.saveTutorRecords("musicalTerm", record.addQuestionAnswer(question));
            env.getRootController().setTabTitle("musicalTermTutor", true);

            styleAnswer(rowPane, currentTerm, categoryOptions, definitionOptions, originOptions);

            ((HBox) ((VBox) (rowPane.getChildren().get(0))).getChildren().get(2)).getChildren().get(1).setDisable(true);

            if (manager.answered == manager.questions) {
                finished();
            }
        });

        definitionOptions.setOnAction(event -> {
            // This handler colors the GUI depending on the user's input
            if (definitionOptions.getValue().equals(currentTerm.getMusicalTermDefinition())) {
                definitionOptions.setStyle("-fx-background-color: green");
            } else {
                definitionOptions.setStyle("-fx-background-color: red");
                ((HBox) ((VBox) (rowPane.getChildren().get(0))).getChildren().get(3)).getChildren().add(new Label(currentTerm.getMusicalTermDefinition()));
            }

            // Adds to record
            String[] question = new String[]{
                    String.format("Definition of term %s", currentTerm.getMusicalTermName()),
                    definitionOptions.getValue(),
                    Boolean.toString(definitionOptions.getValue().equals(currentTerm.getMusicalTermDefinition()))
            };
            projectHandler.saveTutorRecords("musicalTerm", record.addQuestionAnswer(question));
            env.getRootController().setTabTitle("musicalTermTutor", true);

            styleAnswer(rowPane, currentTerm, definitionOptions, categoryOptions, originOptions);

            ((HBox) ((VBox) (rowPane.getChildren().get(0))).getChildren().get(3)).getChildren().get(1).setDisable(true);

            if (manager.answered == manager.questions) {
                finished();
            }
        });

        skip.setOnAction(event -> {
            // Re-write this to be more specific
            String[] question = new String[]{
                    String.format("Information about %s", currentTerm.getMusicalTermName()),
                    currentTerm.getMusicalTermName()
            };
            projectHandler.saveTutorRecords("musicalTerm", record.addSkippedQuestion(question));
            env.getRootController().setTabTitle("musicalTermTutor", true);

            formatSkippedQuestion(rowPane);
            manager.questions -= 1;
            manager.add(new Pair(currentTerm.getMusicalTermName(), currentTerm), 2);
            ((HBox) ((VBox) (rowPane.getChildren().get(0))).getChildren().get(1)).getChildren().get(1).setDisable(true);
            ((HBox) ((VBox) (rowPane.getChildren().get(0))).getChildren().get(2)).getChildren().get(1).setDisable(true);
            ((HBox) ((VBox) (rowPane.getChildren().get(0))).getChildren().get(3)).getChildren().get(1).setDisable(true);
            ((VBox) (rowPane.getChildren().get(0))).getChildren().get(4).setDisable(true); //disable skip
            if (manager.answered == manager.questions) {
                finished();
            }
        });

        VBox qLayout = new VBox();
        qLayout.setSpacing(5);
        qLayout.getChildren().add(termLabel);

        HBox origin = new HBox();
        origin.setSpacing(24);
        origin.getChildren().add(new Label("Origin:"));
        origin.getChildren().add(originOptions);

        HBox category = new HBox();
        category.setSpacing(9);
        category.getChildren().add(new Label("Category:"));
        category.getChildren().add(categoryOptions);

        HBox def = new HBox();
        def.setSpacing(5);
        def.getChildren().add(new Label("Definition:"));
        def.getChildren().add(definitionOptions);

        qLayout.getChildren().add(origin);
        qLayout.getChildren().add(category);
        qLayout.getChildren().add(def);
        qLayout.getChildren().add(skip);

        rowPane.getChildren().add(qLayout);
        rowPane.prefWidthProperty().bind(paneQuestions.prefWidthProperty());


        return rowPane;
    }

    /**
     * If all parts of a question has been answered then the border is coloured
     */
    private void styleAnswer(HBox rowPane, Term currentTerm, ComboBox currentSelection, ComboBox secondBox, ComboBox thirdBox) {
        if (secondBox.getValue() != null && thirdBox.getValue() != null) {
            if (secondBox.getStyle() == "-fx-background-color: red" && thirdBox.getStyle() == "-fx-background-color: red" && currentSelection.getStyle() == "-fx-background-color: red") {
                // All parts incorrect
                formatIncorrectQuestion(rowPane);
                manager.add(new Pair(currentTerm.getMusicalTermName(), currentTerm), 0);
            } else if (secondBox.getStyle() == "-fx-background-color: green" && thirdBox.getStyle() == "-fx-background-color: green" && currentSelection.getStyle() == "-fx-background-color: green") {
                // All parts correct
                formatCorrectQuestion(rowPane);
                manager.add(new Pair(currentTerm.getMusicalTermName(), currentTerm), 1);
            } else {
                // Some parts correct, some parts incorrect
                formatPartiallyCorrectQuestion(rowPane);
                manager.add(new Pair(currentTerm.getMusicalTermName(), currentTerm), 0);

            }
            ((VBox) (rowPane.getChildren().get(0))).getChildren().get(4).setDisable(true);
            manager.answered += 1;
        }
    }


    public void resetInputs() {
        dataManager = env.getMttDataManager();
        numQuestions.setValue(1);
    }

    /**
     * This function is run once a tutoring session has been completed.
     */
    public void finished() {
        env.getPlayer().stop();
        userScore = getScore(manager.correct, manager.answered);
        outputText = String.format("You have finished the tutor.\n" +
                        "You answered %d questions, and skipped %d questions.\n" +
                        "You answered %d questions correctly, %d questions incorrectly.\n" +
                        "This gives a score of %.2f percent.",
                manager.questions, manager.skipped,
                manager.correct, manager.incorrect, userScore);

        if (projectHandler.currentProjectPath != null) {
            projectHandler.saveSessionStat("musicalTerm", record.setStats(manager.correct, manager.getTempIncorrectResponses().size(), userScore));
            projectHandler.saveCurrentProject();
            outputText += "\nSession auto saved";
        }
        env.getRootController().setTabTitle("musicalTermTutor", false);
        // Sets the finished view
        resultsContent.setText(outputText);

        paneQuestions.setVisible(false);
        paneResults.setVisible(true);
        questionRows.getChildren().clear();

        Button retestBtn = new Button("Retest");
        Button clearBtn = new Button("Clear");
        final Button saveBtn = new Button("Save");


        clearBtn.setOnAction(event -> {
            manager.saveTempIncorrect();
            paneResults.setVisible(false);
            paneQuestions.setVisible(true);

        });
        paneResults.setPadding(new Insets(10, 10, 10, 10));
        retestBtn.setOnAction(event -> {
            paneResults.setVisible(false);
            paneQuestions.setVisible(true);
            retest();

        });
        saveBtn.setOnAction(event -> saveRecord());


        if (manager.getTempIncorrectResponses().size() > 0) {
            //Can re-test
            buttons.getChildren().setAll(retestBtn, clearBtn, saveBtn);
        } else {
            //Perfect score
            buttons.getChildren().setAll(clearBtn, saveBtn);
        }

        HBox.setMargin(retestBtn, new Insets(10, 10, 10, 10));
        HBox.setMargin(clearBtn, new Insets(10, 10, 10, 10));
        HBox.setMargin(saveBtn, new Insets(10, 10, 10, 10));
        // Clear the current session
        manager.resetStats();
    }

}