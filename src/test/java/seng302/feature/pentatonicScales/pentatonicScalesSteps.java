package seng302.feature.pentatonicScales;

import java.util.HashMap;


import cucumber.api.CucumberOptions;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng302.Environment;
import seng302.command.Scale;
import seng302.managers.TranscriptManager;

import static org.junit.Assert.assertEquals;


/**
 * Created by dominicjarvis on 5/08/16.
 */
@CucumberOptions()
public class pentatonicScalesSteps {

    Environment env = new Environment();
    TranscriptManager transcriptManager;

    @Given("^I am on the transcript pane$")
    public void iAmOnTheTranscriptPane() throws Throwable {
        transcriptManager = env.getTranscriptManager();

    }

    @When("^I type the command '([^\"]*) scale ([^\"]*) pentatonic ([^\"]*)'$")
    public void iTypeTheCommandScale(String arg0, String arg1, String arg2) throws Throwable {
        HashMap<String, String> testMap = new HashMap<String, String>();
        testMap.put("scale_type", arg2.concat(" pentatonic"));
        testMap.put("note", arg1);
        new Scale(testMap, arg0).execute(env);

    }

    @Then("^The following is printed to the transcript pane - ([^\"]*)$")
    public void theFollowingIsPrintedToTheTranscriptPane(String arg0) throws Throwable {
        String result = env.getTranscriptManager().getTranscriptTuples().get(0).getResult();
        System.out.println(env.getTranscriptManager().getTranscriptTuples().get(0));
        assertEquals(result, arg0);
    }

}
