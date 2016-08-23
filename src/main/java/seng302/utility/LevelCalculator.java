package seng302.utility;

/**
 * Created by emily on 23/08/16.
 */
public class LevelCalculator {

    public static boolean isLevelUp(int currentLevel, int currentExperience) {
        double nextLevelExp = Math.pow(2, currentLevel + 1);

        if (currentExperience >= nextLevelExp) {
            return true;
        } else {
            return false;
        }
    }
}
