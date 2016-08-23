package seng302.utility;

/**
 * Utility class for calculating information related to user levels.
 */
public class LevelCalculator {

    /**
     * Checks if a user is able to level up, based on their current level and experience
     *
     * @param currentLevel      The level the user is currently on
     * @param currentExperience The amount of experience points the user currently has
     * @return True if the user can increase to the next level, false otherwise.
     */
    public static boolean isLevelUp(int currentLevel, int currentExperience) {

        if (currentExperience >= getTotalExpForLevel(currentLevel + 1)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Recursive function to calculate the total amount of experience required to reach any given
     * level.
     *
     * @param level The level whose required experience is to be found
     * @return The required experience, represented as an integer
     */
    private static int getTotalExpForLevel(int level) {
        if (level == 1) {
            return 1;
        } else {
            return (int) (Math.pow(level + 5, 2) + getTotalExpForLevel(level - 1));
        }
    }

}
