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

/**
 * Created by Elliot on 8/06/2016.
 */

public class PlayNoteSteps {
    Environment env;
    @Mock
    private TranscriptManager transcriptManager;

    @Given("^I am on the transcript pane$")
    public void I_am_on_the_transcript_pane() throws Throwable {
        env = new Environment();
        env.setTranscriptManager(transcriptManager);
    }

//    @When("^I type the command \"([^\"]*)\"$")
//    public void I_type_the_command(String note) throws Throwable {
////        transcriptManager.setCommand("Play C");
//        env.getExecutor().executeCommand("play C");
//    }
//
//    @Then("^The note C(\\d+) should be played$")
//    public void The_note_C_should_be_played(int arg1) throws Throwable {
//        assertEquals(transcriptManager.getLastCommand(), "Play C");
//    }

    @When("^I type the command 'play C'$")
    public void iTypeTheCommandPlayC() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        new PlayNote("C").execute(env);
    }

    @Then("^The note 'C' should be played$")
    public void theNoteCShouldBePlayed() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(transcriptManager.getLastCommand(), "play C");
    }
}
