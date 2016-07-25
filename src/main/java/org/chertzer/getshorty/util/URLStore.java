package org.chertzer.getshorty.util;

/**
 * Created by chertzer on 7/23/16.
 */
public interface URLStore {
    public String getURL(String shortURL);
    public String setURL(String originalURL);

}
