package org.chertzer.getshorty.util;

import java.util.concurrent.ConcurrentHashMap;
import static org.chertzer.getshorty.util.Properties.*;

/**
 * Manage the mapping of original URLs to shortened URLs
 */
public final class URLMap implements URLStore {
    private final int KEY_LENGTH = getIntProp("getshorty.URLMap.keyLength");
    private final static ConcurrentHashMap<String, String> map = new ConcurrentHashMap();

    private URLMap(){}

    private static class LazyInitURLMap {
        private static final URLMap INSTANCE = new URLMap();
    }

    public static URLMap getInstance() {
        return LazyInitURLMap.INSTANCE;
    }
    private Base62IdGenerator gen = new Base62IdGenerator();

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
