package org.chertzer.getshorty.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Manage the mapping of original URLs to shortened URLs
 */
public final class URLMap implements URLStore {
    // KEY_LENGTH = 8 provides for for 62^8 =218,340,105,584,896 unique keys
    // Divide by 24*60*60*10,000 = 8,640,000 = daily rate if we get 10,000 hits/second ->
    // Divide by 365 -> 953 years
    private static final int KEY_LENGTH = 8;
    private static final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    private static volatile URLMap INSTANCE = new URLMap();

    private URLMap(){}

    public static URLMap getInstance() {
        return INSTANCE;
    }

    private static final Base62IdGenerator gen = new Base62IdGenerator();

    /**
     * Given a short URL key, lookup the original URL value
     * @param shortURL
     * @return String representation of the original URL
     */
    public String getURL(String shortURL) {
        return map.get(shortURL);
    }

    /**
     * Creates a mapping to the originalURL keyed by a generated, unique shortURL.
     * If there is already a mapping for the shortURL (i.e. the key generator generated
     * a duplicate) we generate a new key to guarrantee uniqueness in the map.
     *
     * @param originalURL
     * @return the shortURL
     */
    public String setURL(String originalURL) {
        System.out.println();
        String shortURL = gen.getRandomBase62(KEY_LENGTH);

        // in the event of a collision try again
        while(map.putIfAbsent(shortURL, originalURL) != null){
            shortURL = gen.getRandomBase62(KEY_LENGTH);
        }
        return shortURL;
    }
}
