package seng302.cucumber;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import seng302.Environment;
import seng302.managers.TranscriptManager;

/**
 * Created by Elliot on 8/06/2016.
 */

public class PlayNoteStepDef {
    @Given("^I am on the transcript pane$")
    public void I_am_on_the_transcript_pane() throws Throwable {
        // Express the Regexp above with the code you wish you had
        env = new Environment();
        env.setTranscriptManager();
        throw new PendingException();
    }

    @When("^I type the command \"([^\"]*)\"$")
    public void I_type_the_command(String note) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    @Then("^The note C(\\d+) should be played$")
    public void The_note_C_should_be_played(int arg1) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }
}
