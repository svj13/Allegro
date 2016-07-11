package seng302.cucumber;

import org.mockito.Mock;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import seng302.Environment;

import static org.junit.Assert.*;

import seng302.command.PlayNote;
import seng302.managers.TranscriptManager;

import static org.mockito.Mockito.verify;


/**
 * Created by Elliot on 8/06/2016.
 */

public class PlayNoteSteps {
    Environment env;

    private TranscriptManager transcriptManager = new TranscriptManager();

    @Given("^I am on the transcript pane$")
    public void I_am_on_the_transcript_pane() {
        env = new Environment();
        env.setTranscriptManager(transcriptManager);
    }

    @When("^I type the command 'play \"([^\"]*)\"'$")
    public void iTypeTheCommandPlay(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        new PlayNote(arg0).execute(env);
    }

    @Then("^The note '\"([^\"]*)\"' should be played$")
    public void theNoteShouldBePlayed(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println(transcriptManager.getCommandHistory());
        String last_command = transcriptManager.getLastCommand().substring(4);
        assertEquals(last_command, "Playing " + arg0 + " at 120BPM\n");
    }
}