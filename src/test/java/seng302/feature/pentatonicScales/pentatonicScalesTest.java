package seng302.feature.pentatonicScales;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


/**
 * Created by dominicjarvis on 29/07/16.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/seng302/feature/pentatonicScales", tags = "@pentatonicMidi")
public class pentatonicScalesTest {
}
