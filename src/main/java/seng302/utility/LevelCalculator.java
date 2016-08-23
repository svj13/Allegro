package seng302.utility;

/**
 * Created by emily on 23/08/16.
 */
public class LevelCalculator {

    public static boolean isLevelUp(int currentLevel, int currentExperience) {

        if (currentExperience >= getTotalExpForLevel(currentLevel + 1)) {
            return true;
        } else {
            return false;
        }
    }

    private static double getTotalExpForLevel(int level) {
        if (level == 1) {
            return 1;
        } else {
            return Math.pow(level + 5, 2) + getTotalExpForLevel(level - 1);
        }
    }

}
