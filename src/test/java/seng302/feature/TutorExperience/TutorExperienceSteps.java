package seng302.feature.TutorExperience;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import javafx.fxml.FXMLLoader;
import seng302.Environment;
import seng302.Users.User;
import seng302.gui.PitchComparisonTutorController;
import seng302.gui.RootController;


/**
 * Created by Emily - needs more work
 */
public class TutorExperienceSteps {
    Environment env;
    PitchComparisonTutorController pitchCtrl;
    User currentUser;

    @Given("I am in the pitch recognition tutor")
    public void createTutoringSession() {
        env = new Environment();
        RootController rootController = new RootController();
        env.setRootController(rootController);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/PitchComparisonPane.fxml"));
        env.getRootController().PitchComparisonTabController = loader.getController();
        env.getRootController().PitchComparisonTabController.create(env);
        env.getRootController().PitchComparisonTabController.setTabID("pitchTutor");
        pitchCtrl = env.getRootController().PitchComparisonTabController;

        env.getUserHandler().setCurrentUser("test");
        currentUser = env.getUserHandler().getCurrentUser();
    }

    @When("I answer (.+) questions correctly out of (.+) questions")
    public void iAnswerCorrectQuestionsCorrectlyOutOfTotalQuestions(String correct, String answered) {
        pitchCtrl.manager.questions = Integer.parseInt(answered);
        pitchCtrl.manager.correct = Integer.parseInt(correct);
        //pitchCtrl.finished();
    }

    @Then("I am given the experience (.+)")
    public void iAmGivenTheExperienceScore(String score) {
        assert currentUser.getUserExperience() == Integer.parseInt(score);
    }
}
