package nightgames.utilities;

/**
 * MathUtils helpers.
 */
public class MathUtils {
    public static int clamp(int number, int min, int max) {
        return java.lang.Math.min(java.lang.Math.max(number, min), max);
    }

    public static double clamp(double number, double min, double max) {
        return java.lang.Math.min(java.lang.Math.max(number, min), max);
    }
}
