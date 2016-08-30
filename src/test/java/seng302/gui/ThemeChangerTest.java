package seng302.gui;

import cucumber.api.java.Before;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import seng302.Environment;

import java.awt.*;

import static org.mockito.AdditionalMatchers.find;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.hasChildren;

/**
 * Created by Jonty on 30-Aug-16.
 */
public class ThemeChangerTest extends ApplicationTest {

    public Parent getRootNode() {
        UISkinnerController themeScreen = new UISkinnerController();
        AnchorPane root = new AnchorPane();
        themeScreen.create(new Environment(), root);

        return root; // the root StackPane with button
    }

    @Override
    public void start(Stage stage) {
        UISkinnerController uiSkinner = new UISkinnerController();

        Scene scene = new Scene(getRootNode(), 800, 800);
        stage.setScene(scene);
        stage.show();

    }


    @Before
    public void setup() throws Exception {

    }

    /*
        @Test
        public void should_drag_file_into_trashcan() {
            // given:
            rightClickOn("#desktop").moveTo("New").clickOn("Text Document");
            write("myTextfile.txt").push(ENTER);

            // when:
            drag(".file").dropTo("#trash-can");

            // then:
            verifyThat("#desktop", hasChildren(0, ".file"));
        }
    */
    @Test
    public void click_colour_picker() throws InterruptedException {


    }
}
