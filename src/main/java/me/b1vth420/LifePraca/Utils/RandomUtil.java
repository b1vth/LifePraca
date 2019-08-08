package me.b1vth420.LifePraca.Utils;

import org.apache.commons.lang.Validate;

import java.util.Random;

public final class RandomUtil
{
    private static final Random rand;

    public static int getRandInt(final int min, final int max) throws IllegalArgumentException {
        Validate.isTrue(max >= min, "Max can't be smaller than min!");
        return rand.nextInt(max - min + 1) + min;
    }

    public static Double getRandDouble(final double min, final double max) throws IllegalArgumentException {
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return rand.nextDouble() * (max - min) + min;
    }

    public static Float getRandFloat(final float min, final float max) throws IllegalArgumentException {
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return rand.nextFloat() * (max - min) + min;
    }

    public static boolean getChance(final double chance) {
        return chance >= 100.0 || chance >= getRandDouble(0.0, 100.0);
    }

    public static void main(final String[] args) {
    }

    static {
        rand = new Random();
    }
}
