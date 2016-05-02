package seng302.gui;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seng302.Environment;
import seng302.command.Command;

/**
 * Created by jat157 on 20/03/16.
 */
public class TranscriptPaneController {

    Environment env;

    Stage stage;


    String path;
    File fileDir;


//    @FXML
//    private Pane pane1;

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
    private void initialize() {
        // Text field can only request focus once everything has been loaded.
        Platform.runLater(new Runnable() {
            public void run() {
                txtCommand.requestFocus();
            }
        });
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

    private void executeAndPrintToTranscript(String text) {
        if (text.length() > 0) {
            env.getTranscriptManager().setCommand(text);
            Command command = env.getExecutor().parseCommandString(text);

            env.getExecutor().executeCommand(command);
            txtTranscript.appendText(env.getTranscriptManager().getLastCommand());
            try {
                System.out.println("Waiting" + command.getLength(env));
                Thread.sleep((long) command.getLength(env));
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Thread did not sleep");
            }



        } else {
            txtTranscript.appendText("[ERROR] Cannot submit an empty command.\n");
        }
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
            Platform.runLater(new Runnable() {
                public void run() {
                    txtCommand.positionCaret(9999);
                }
            });


        } else if (event.getCode() == KeyCode.DOWN) {

//            handleScrollDown();
            txtCommand.setText(env.getTranscriptManager().cycleInputDown(txtCommand.getText()));
        } else if (event.getCode() == KeyCode.ALPHANUMERIC) {
            env.getTranscriptManager().resetHistoryLevel();
            // historyLevel = -1;
            //enteredCommand = "";
        }
        Platform.runLater(new Runnable() {
            public void run() {
                txtCommand.positionCaret(9999);
            }
        });

    }

    public void setTranscriptPane(String text){
        System.out.println("txtTranscript text"  +txtTranscript.getText());
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

        playall.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                for (String command:commands) {
                    executeAndPrintToTranscript(command);
                }
                playbackFinished();
            }
        });

        playnext.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    // need to wait for commands to complete
                    executeAndPrintToTranscript(commands.get(0));
                    commands.remove(0);
                    if (commands.get(0) != null) {
                        commandvalue.setText(commands.get(0));
                    } else {
                        commandvalue.setText("-");
                    }
                } catch (Exception e) {
                    playbackFinished();
                }
            }
        });

        stop.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

            }
        });

    }

    public void playbackFinished() {
        playall.setDisable(true);
        playnext.setDisable(true);
        stop.setText("Exit");
        stop.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                hidePlaybackGui();
            }
        });
    }

    private void showPlaybackGui(String firstCommand) {
        AnchorPane.setTopAnchor(txtTranscript, 40.0);
        txtTranscript.setPromptText("");
        txtTranscript.clear();
        txtCommand.setDisable(true);
        btnGo.setDisable(true);
        commandvalue.setText(firstCommand);
    }

    private void hidePlaybackGui() {
        AnchorPane.setTopAnchor(txtTranscript, 0.0);
        txtTranscript.setPromptText("");
        txtTranscript.clear();
        txtCommand.setDisable(false);
        btnGo.setDisable(false);
        commandvalue.setText("");
    }



}
