package org.chertzer.getshorty.util;

import org.chertzer.getshorty.util.Base62IdGenerator;
import org.junit.Test;

/**
 * Created by chertzer on 7/21/16.
 */
public class Base62Test {

    @Test
    public void teststringToBase62() {
        Base62IdGenerator encoder = new Base62IdGenerator();
        String key1a = encoder.getRandomBase62(5);
        assert(key1a.length() == 5);
        String key1b = encoder.getRandomBase62(5);
        assert(key1b.length() == 5);
        assert(!key1a.equals(key1b));
        String key2 = encoder.getRandomBase62(10);
        assert(key2.length() == 10);
    }
}