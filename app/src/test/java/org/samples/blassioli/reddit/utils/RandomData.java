package org.samples.blassioli.reddit.utils;

import java.util.Random;

public class RandomData {
    private static final Random random = new Random();

    /**
     * Returns a pseudo-random alpha-numeric string with a random length from 1 to maxLength, inclusive.
     *
     * @param maxLength
     * @return String with random length from 1 to maxLength, inclusive
     */
    public static String randomString(int maxLength) {
        return new RandomString(randomInt(1, maxLength)).nextString();
    }

    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randomInt(int min, int max) {
        int randomNum = random.nextInt((max - min) + 1) + min;
        return randomNum;
    }

}
