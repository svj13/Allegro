package seng302.feature.pentatonicScales;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng302.Environment;
import seng302.command.Scale;

/**
 * Created by dominicjarvis on 26/07/16.
 */
public class MajorPentatonicSteps {
    Environment env = new Environment();

    @Given("^I am on the transcript pane$")
    public void iAmOnTheTranscriptPane() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I type the command 'scale \"([^\"]*)\" major pentatonic'$")
    public void iTypeTheCommandScaleMajorPentatonic(String arg0) throws Throwable {
        new Scale(arg0, "major", "pentatonic").execute(env);

    }

    @Then("^The following is printed to the transcript pane - \"([^\"]*)\"$")
    public void theFollowingIsPrintedToTheTranscriptPane(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


}
