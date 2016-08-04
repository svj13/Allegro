package seng302.feature.pentatonicScales;

import java.util.HashMap;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng302.Environment;
import seng302.command.Scale;
import seng302.managers.TranscriptManager;

import static org.junit.Assert.*;

/**
 * Created by dominicjarvis on 29/07/16.
 */
public class pentatonicScalesNoteSteps {

    Environment env = new Environment();
    TranscriptManager transcriptManager;


//    @Given("^I am on the transcript pane$")
//    public void iAmOnTheTranscriptPane() throws Throwable {
//        transcriptManager = env.getTranscriptManager();
//
//    }

    @When("^I type the command 'pentatonic scale ([^\"]*) ([^\"]*)'$")
    public void iTypeTheCommandPentatonicScale(String arg0, String arg1) throws Throwable {
        HashMap<String, String> testMap = new HashMap<String, String>();
        testMap.put("scale_type", arg1);
        testMap.put("note", arg0);
        new Scale(testMap, "pentatonic_note").execute(env);

    }

//    @Then("^The following is printed to the transcript pane - ([^\"]*)$")
//    public void theFollowingIsPrintedToTheTranscriptPane(String arg0) throws Throwable {
//        String result = env.getTranscriptManager().getTranscriptTuples().get(0).getResult();
//        System.out.println(env.getTranscriptManager().getTranscriptTuples().get(0));
//        assertEquals(result, arg0);
//    }
}
