package org.chertzer.getshorty.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * Quick/dirty property management
 */
public final class Properties {
    private static java.util.Properties props;

    public Properties(){
        props = new java.util.Properties();
        InputStream resourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
        try {
            props.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProp(String key) {
        return props.getProperty(key);
    }
    public static int getIntProp(String key) {
        return Integer.parseInt(getProp(key));
    }

}
