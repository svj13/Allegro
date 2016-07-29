package seng302.feature.BluesScales;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng302.Environment;
import seng302.gui.RootController;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by Elliot on 29/07/2016.
 */
public class BluesScalesSteps {
    Environment env;
    String result;

    @Given("I am on the transcript pane")
    public void createEnvironment() {
        env = new Environment();
        RootController rootController = new RootController();
        env.setRootController(rootController);

    }

    @When("I type the command 'play scale (.+) blues'")
    public void executeCommand(final String scaleName) {
        env.getExecutor().executeCommand("quality of " + scaleName);
        result = env.getTranscriptManager().getTranscriptTuples().get(0).getResult();
    }

    @Then("The following is printed to the transcript pane: (.+)")
    public void verifyResult(final String quality) {
        assertThat(result, equalTo(quality));
    }

}
