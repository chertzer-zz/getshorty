package org.chertzer.getshorty.util;

import java.util.Random;

/**
 * Generates a random Base62IdGenerator-encoded string to serve as a unique key
 * Reference
 * http://stackoverflow.com/questions/9543715/generating-human-readable-usable-short-but-unique-ids
 *
 * No decoder is provided. The IDs are intended to be used as keys in a lookup table.
 */
public final class Base62IdGenerator {
    private static Random random;
    private static final char[] base62chars =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    public Base62IdGenerator() {
        random = new Random();
    }

    /**
     * Generate a random Base62 key having numChars
     *
     * @param numChars length of the key
     * @return String unique key
     */
    public String getRandomBase62(int numChars) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numChars; i++) {
            sb.append(base62chars[random.nextInt(62)]);
        }
        return sb.toString();
    }

}
