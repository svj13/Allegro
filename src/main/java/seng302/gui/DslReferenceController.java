package seng302.gui;

import org.controlsfx.control.PopOver;

import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seng302.data.CommandType;

/**
 * This class contains the logic for a popover containing help info of all commands.
 */
public class DslReferenceController {

    private PopOver dslRef;
    private TranscriptPaneController parentController;
    private VBox popoverContent = new VBox();

    public DslReferenceController(TranscriptPaneController parentController) {
        this.parentController = parentController;
        this.dslRef = createDslReference();
    }

    /**
     * Initialises a popover containing help info about popoverContent. Each command, when clicked,
     * is copied to the input text field.
     */
    private PopOver createDslReference() {
        VBox scrollContent = new VBox();
        HBox sortingOptions = getSortingOptions();
        HBox helpfulInfo = getInfoBox();

        sortingOptions.setSpacing(5);
        sortingOptions.setPadding(new Insets(10));

        scrollContent.getChildren().add(sortingOptions);
        scrollContent.getChildren().add(helpfulInfo);
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
        return dslRef;
    }

    /**
     * Creates an HBox with text explaining the syntax of the DSL reference card.
     */
    private HBox getInfoBox() {
        HBox helpfulInfo = new HBox();
        helpfulInfo.setPadding(new Insets(10));
        helpfulInfo.getChildren().add(new Text("Note: Arguments surrounded by ( ) are mandatory." +
                '\n' + "Arguments surrounded by [ ] are optional." + '\n' +
                "Brackets are indicators only, and should be removed."));

        return helpfulInfo;
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
            category.setOnMouseClicked(event -> {
                categoryContent.getChildren().clear();
                for (Map.Entry<String, CommandType> entry : CommandType.getCommands(categoryName).entrySet()) {
                    prepareCommand(entry, categoryContent);
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
        commandInfo.setOnMouseClicked(event -> {
            //copy to input field
            setCommandText(data);
        });
        commandInfo.getChildren().add(content);
        container.getChildren().add(commandInfo);
    }

    /**
     * Given a command, sets the input text field to display information about that command.
     *
     * @param command The command to display information about
     */
    public void setCommandText(CommandType command) {
        parentController.getTxtCommand().clear();
        parentController.getTxtCommand().setText(command.getDisplayText());
    }

    /**
     * Gets the fully made reference popover
     */
    public PopOver getPopover() {
        return dslRef;
    }
}
