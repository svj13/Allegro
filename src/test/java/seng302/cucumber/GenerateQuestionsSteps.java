package seng302.cucumber;

import seng302.Environment;
import seng302.gui.KeySignaturesTutorController;
import seng302.gui.TutorController;
import seng302.managers.TranscriptManager;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;


/**
 * Created by Elliot on 13/07/2016.
 */
public class GenerateQuestionsSteps {
    Environment env;
    TutorController controller;
    private TranscriptManager transcriptManager = new TranscriptManager();
    private TutorController tutorController = new KeySignaturesTutorController();

    @Given("^I am on the tutor pane$")
    public void I_am_on_the_tutor_pane() {
        env = new Environment();
        tutorController.create(env);
        controller = new KeySignaturesTutorController();
        controller.create(env);
    }

    @When("^I press the Go button$")
    public void iPressTheGoButton() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
//        controller.goAction();
    }

    @Then("^The questions should be generated$")
    public void theQuestionsShouldBeGenerated(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions

    }
}
