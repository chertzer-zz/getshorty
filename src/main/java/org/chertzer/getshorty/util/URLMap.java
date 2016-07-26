package org.chertzer.getshorty.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple implementation of URLStore to manage the mapping of original URLs
 * to shortened URLs.
 *
 * This implementation is based on ConcurrentHashMap and should support thread-safe
 * writes since putIfAbsent is atomic. No persistence or shared store is provided,
 * so it is not suitable if high availability or scaling beyond a single server is required
 */
public final class URLMap implements URLStore {
    // KEY_LENGTH = 8 provides for for 62^8 =218,340,105,584,896 unique keys
    // Divide by 24*60*60*10,000 = 8,640,000 = daily rate if we get 10,000 hits/second ->
    // Divide by 365 -> this should serve us for 953 years. Something else will likely break down first!
    private static final int KEY_LENGTH = 8;

    // Default initial capacity for ConcurrentHashMap is 16. That's not a good number. This may not be either
    private static final Map<String, String> map = new ConcurrentHashMap<>(10_000, 0.75f);
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
