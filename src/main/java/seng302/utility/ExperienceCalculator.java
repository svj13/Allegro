package seng302.utility;

/**
 * Calculates how much experience a user has earned
 */
public class ExperienceCalculator {

    public static int calculateExperience(int correctQuestions, int answeredQuestions) {
        int totalExperience = 0;

        //User gets 100 points for every correct question
        totalExperience += correctQuestions * 100;

        float percentage = correctQuestions / answeredQuestions;

        //User gets different bonuses based on their overall percentage
        int bonus = 0;
        if (percentage > 90) {
            //If the user gets 90% to 100%, they get a ~10% point bonus
            bonus = (int) (0.1 * totalExperience);
        } else if (percentage > 75) {
            //If the user gets 75% to 90%, they get a ~5% point bonus
            bonus = (int) (0.05 * totalExperience);
        }

        totalExperience += bonus;

        return totalExperience;
    }

}

