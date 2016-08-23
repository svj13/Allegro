package seng302.feature.NewUser;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng302.Environment;
import seng302.Users.User;
import seng302.gui.RootController;

/**
 * Created by emily on 23/08/16.
 */
public class NewUserSteps {
    User user;
    Environment env;

    @Given("I am on the sign in page")
    public void createEnvironment() {
        env = new Environment();
        RootController rootController = new RootController();
        env.setRootController(rootController);
    }

    @When("I register a new user with the name (.+) and the password (.+)")
    public void registerNewUser(String username, String password) {
        env.getUserHandler().createUser(username, password);

    }

    @Then("^The new user has the level (\\d+)$")
    public void the_new_user_has_the_level(int level) {
        // Write code here that turns the phrase above into concrete actions
        user = env.getUserHandler().getCurrentUser();
        assert user.getUserLevel() == 1;
    }
}
