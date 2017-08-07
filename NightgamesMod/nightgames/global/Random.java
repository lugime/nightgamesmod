package nightgames.global;

import java.util.List;
import java.util.Optional;

public class Random {
    public static int random(int start, int end) {
        return Global.rng.nextInt(end - start) + start;
    }

    public static int random(int d) {
        if (d <= 0) {
            return 0;
        }
        return Global.rng.nextInt(d);
    }// finds a centered random number from [0, d] (inclusive)

    public static int centeredrandom(int d, double center, double sigma) {
        int val = 0;
        center = Math.max(0, Math.min(d, center));
        for (int i = 0; i < 10; i++) {
            double f = Global.rng.nextGaussian() * sigma + center;
            val = (int) Math.round(f);
            if (val >= 0 && val <= d) {
                return val;
            }
        }
        return Math.max(0, Math.min(d, val));
    }

    public static float randomfloat() {
        return (float) Global.rng.nextDouble();
    }

    @SafeVarargs public static <T> Optional<T> pickRandom(T... arr) {
        if (arr == null || arr.length == 0)
            return Optional.empty();
        return Optional.of(arr[Random.random(arr.length)]);
    }

    public static <T> Optional<T> pickRandom(List<T> list) {
        if (list == null || list.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(list.get(random(list.size())));
        }
    }

    public static double randomdouble() {
        return Global.rng.nextDouble();
    }

    public static double randomdouble(double to) {
        return Global.rng.nextDouble() * to;
    }

    public static long randomlong() {
        return Global.rng.nextLong();
    }

    /**
     * TODO Huge hack to freeze status descriptions.
     */
    public static void freezeRNG() {
        Global.FROZEN_RNG = Global.rng;
        Global.rng = new java.util.Random(0);
    }

    /**
     * TODO Huge hack to freeze status descriptions.
     */
    public static void unfreezeRNG() {
        Global.FROZEN_RNG = new java.util.Random();
        Global.rng = Global.FROZEN_RNG;
    }
}
