package seng302.gui;

import org.controlsfx.control.PopOver;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng302.Environment;
import seng302.command.Command;
import seng302.command.NullCommand;
import seng302.data.CommandType;

/**
 * Created by jat157 on 20/03/16.
 */
public class TranscriptPaneController {

    Environment env;

    Stage stage;


    String path;
    File fileDir;

    @FXML
    public TextField txtCommand;

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
    Button playall;

    @FXML
    Button playnext;

    @FXML
    Button stop;

    @FXML
    Label commandvalue;

    @FXML
    ToolBar playbackToolbar;

    @FXML
    private Button helpButton;

    private VBox popoverContent = new VBox();

    PopOver dslRef;


    @FXML
    private void initialize() {
        createDslReference();
        // Text field can only request focus once everything has been loaded.
        Platform.runLater(() -> txtCommand.requestFocus());
    }

    /**
     * Creates two radio buttons, for sorting the DSL reference popup.
     *
     * @return A javafx HBox containing the radio buttons
     */
    private HBox getSortingOptions() {
        HBox sortButtons = new HBox();
        final ToggleGroup sortingOptions = new ToggleGroup();
        final RadioButton sortAlphabetically = new RadioButton("Sort Alphabetically");
        sortAlphabetically.setToggleGroup(sortingOptions);
        sortAlphabetically.setSelected(true);
        RadioButton sortByGroup = new RadioButton("Sort by Group");
        sortByGroup.setToggleGroup(sortingOptions);
        sortingOptions.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (sortingOptions.getSelectedToggle().equals(sortAlphabetically)) {
                    alphabetiseCommands();
                } else {
                    sortCommands();
                }
            }
        });


        sortButtons.getChildren().add(sortAlphabetically);
        sortButtons.getChildren().add(sortByGroup);
        return sortButtons;
    }

    /**
     * Initialises a popover containing help info about popoverContent. Each command, when clicked, is
     * copied to the input text field.
     */
    private void createDslReference() {
        VBox scrollContent = new VBox();
        HBox sortingOptions = getSortingOptions();
        sortingOptions.setSpacing(5);
        sortingOptions.setPadding(new Insets(10));
        scrollContent.getChildren().add(sortingOptions);
        scrollContent.getChildren().add(popoverContent);

        popoverContent.getChildren().add(new Text("Click a command to copy to input field"));
        popoverContent.setSpacing(5);
        popoverContent.setPadding(new Insets(10));

        ScrollPane commandScrollPane = new ScrollPane();
        commandScrollPane.setPrefSize(500, 200);
        commandScrollPane.setContent(scrollContent);
        alphabetiseCommands();
        dslRef = new PopOver(commandScrollPane);
        dslRef.setHeaderAlwaysVisible(true);
        dslRef.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
        dslRef.setTitle("DSL Reference Card");
    }

    /**
     * Displays all commands in the popover, in alphabetical order.
     */
    private void alphabetiseCommands() {
        popoverContent.getChildren().clear();
        VBox allCommands = new VBox();
        popoverContent.getChildren().add(allCommands);

        for (Map.Entry<String, CommandType> entry : CommandType.allCommands.entrySet()) {
            prepareCommand(entry, allCommands);
        }
    }

    /**
     * Displays all commands in the popover, sorted by category.
     */
    private void sortCommands() {
        popoverContent.getChildren().clear();
        final VBox categories = new VBox();
        final VBox categoryContent = new VBox();
        final HBox categorisedCommands = new HBox();
        categorisedCommands.getChildren().add(categories);
        categorisedCommands.getChildren().add(categoryContent);

        String[] categoryNames = {"Play", "Show", "Special", "Translation", "Terms", "Settings"};

        for (final String categoryName : categoryNames) {
            Text category = new Text(categoryName + " >");
            category.setCursor(Cursor.HAND);
            categories.getChildren().add(category);
            category.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    categoryContent.getChildren().clear();
                    for (Map.Entry<String, CommandType> entry : CommandType.getCommands(categoryName).entrySet()) {
                        prepareCommand(entry, categoryContent);
                    }
                }
            });

        }

        popoverContent.getChildren().add(categorisedCommands);
    }

    /**
     * Creates a text object that contains a single command. When clicked, the command is copied to
     * the input text field.
     *
     * @param entry     A single command
     * @param container Where the command text box will display
     */
    private void prepareCommand(Map.Entry<String, CommandType> entry, VBox container) {
        final CommandType data = entry.getValue();
        HBox commandInfo = new HBox();
        final Text content = new Text("-" + data.getDisplayText());
        commandInfo.setCursor(Cursor.HAND);
        commandInfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                //copy to input field
                setCommandText(data);
            }
        });
        commandInfo.getChildren().add(content);
        container.getChildren().add(commandInfo);
    }

    /**
     * Given a command, sets the input text field to display information about that command.
     *
     * @param command The command to display information about
     */
    private void setCommandText(CommandType command) {
        txtCommand.clear();
        txtCommand.setText(command.getDisplayText());
    }

    private String enteredCommand;

    private int historyLevel;


    /**
     * The command which is binded to the Go button, or the enter key when the command prompt is
     * active.
     */
    @FXML
    private void goAction() {

        String text = txtCommand.getText();
        txtCommand.setText("");
        executeAndPrintToTranscript(text);
    }

    public void executeAndPrintToTranscript(String text) {
        if (text.length() > 0) {
            env.getTranscriptManager().setCommand(text);
            Command command = env.getExecutor().parseCommandString(text);

            env.getExecutor().executeCommand(command);
            txtTranscript.appendText(env.getTranscriptManager().getLastCommand());

        } else {
            txtTranscript.appendText("[ERROR] Cannot submit an empty command.\n");
        }
    }

    private Command execute(String text) {
        if (text.length() > 0) {
            env.getTranscriptManager().setCommand(text);
            Command command = env.getExecutor().parseCommandString(text);
            env.getExecutor().executeCommand(command);
            return command;

        } else {
            return new NullCommand();
        }
    }

    private void printToTranscript() {
        txtTranscript.appendText(env.getTranscriptManager().getLastCommand());
    }

    @FXML
    private void showDslRef() {
        dslRef.show(helpButton);
    }


    /**
     *
     * @param event
     */

    @FXML
    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            goAction();
            env.getTranscriptManager().resetHistoryLevel();
        } else if (event.getCode() == KeyCode.UP) {
            txtCommand.setText(env.getTranscriptManager().cycleInputUp(txtCommand.getText()));
            Platform.runLater(() -> txtCommand.positionCaret(9999));
        } else if (event.getCode() == KeyCode.DOWN) {
            txtCommand.setText(env.getTranscriptManager().cycleInputDown(txtCommand.getText()));
            Platform.runLater(() -> txtCommand.positionCaret(9999));
        } else if (event.getCode() == KeyCode.ALPHANUMERIC) {
            env.getTranscriptManager().resetHistoryLevel();
        }

    }

    public void setTranscriptPane(String text) {
        txtTranscript.setText(text);
    }


    public void setStage(Stage stage) {
        this.stage = stage;

    }

    public void setEnv(Environment env) {
        this.env = env;


    }

    public void beginPlaybackMode(final ArrayList<String> commands) {
        showPlaybackGui(commands.get(0));
        final Task playAllTask = new Task<Void>() {
            @Override
            public Void call() {
                for (final String command : commands) {
                    if (isCancelled()) {
                        updateMessage("Cancelled");
                        break;
                    }
                    Command cmd = execute(command);
                    printToTranscript();
                    try {
                        Thread.sleep((long) cmd.getLength(env) + 1000);
                    } catch (InterruptedException e) {
                        updateMessage("Cancelled");
                        break;
                    }
                }
                Platform.runLater(() -> playbackFinished());
                return null;
            }
        };
        final Thread th = new Thread(playAllTask);
        th.setDaemon(true);

        playall.setOnAction(event -> {
            //Plays commands one by one, with a second's pause in between
            playnext.setDisable(true);
            th.start();
        });


        playnext.setOnAction(event -> {
            env.getPlayer().stop();

            try {
                Task playNextTask = new Task<Void>() {
                    @Override
                    public Void call() {
                        Command cmd = execute(commands.get(0));
                        printToTranscript();
                        try {
                            commands.remove(0);

                            Thread.sleep((long) cmd.getLength(env) + 100);
                        } catch (InterruptedException e) {
                            updateMessage("Cancelled");
                        }
                        return null;
                    }
                };
                Thread nextThread = new Thread(playNextTask);
                nextThread.setDaemon(true);
                nextThread.start();
                if (commands.get(1) != null) {
                    commandvalue.setText(commands.get(1));
                } else {
                    commandvalue.setText("-");
                }

            } catch (Exception e) {
                playbackFinished();
            }
        });

        stop.setOnAction(event -> {
            playAllTask.cancel();
            env.getPlayer().stop();
            hidePlaybackGui();
        });

    }

    public void playbackFinished() {
        playall.setDisable(true);
        playnext.setDisable(true);

    }

    private void showPlaybackGui(String firstCommand) {
        AnchorPane.setTopAnchor(txtTranscript, 40.0);
        txtCommand.setDisable(true);
        txtTranscript.setPromptText("");
        btnGo.setDisable(true);
        playall.setDisable(false);
        playnext.setDisable(false);
        commandvalue.setText(firstCommand);
    }

    public void hidePlaybackGui() {
        AnchorPane.setTopAnchor(txtTranscript, 0.0);
        txtTranscript.setPromptText("");
        txtCommand.setDisable(false);
        btnGo.setDisable(false);
        commandvalue.setText("");
    }

    public void giveFocus() {
        txtCommand.requestFocus();
    }


}
