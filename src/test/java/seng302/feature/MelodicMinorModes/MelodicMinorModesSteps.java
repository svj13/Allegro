package seng302.feature.MelodicMinorModes;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng302.Environment;
import seng302.gui.RootController;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by dominic on 03/09/16.
 */
public class MelodicMinorModesSteps {
    Environment env;
    String result;

    @Given("I am on the transcript pane")
    public void createEnvironment() {
        env = new Environment();
        RootController rootController = new RootController();
        env.setRootController(rootController);
    }

    @When("I type the command 'melodic minor mode of (.+)(.+)'")
    public void executeCommand(final String tonic, final String degree) {
        env.getExecutor().executeCommand("melodic minor mode of " + tonic + " " + degree);
        result = env.getTranscriptManager().getTranscriptTuples().get(0).getResult();
    }

    @When("I type the command 'melodic minor parent of (.+)'")
    public void executeParentCommand(final String scale) {
        env.getExecutor().executeCommand("melodic minor parent of " + scale);
        result = env.getTranscriptManager().getTranscriptTuples().get(0).getResult();
    }

    @When("I type the command 'scale (.+)'")
    public void executeScaleCommand(final String scale) {
        env.getExecutor().executeCommand("scale " + scale);
        result = env.getTranscriptManager().getTranscriptTuples().get(0).getResult();
    }

    @Then("The following is printed to the transcript pane - (.+)")
    public void verifyResult(final String quality) {
        assertThat(result, equalTo(quality));
    }

}
