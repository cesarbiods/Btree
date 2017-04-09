package com.btree;

import java.io.FileNotFoundException;

/**
 * Created by cesar on 3/5/17.
 */
public class Cache {
    private static Entry defaultEntry = new Entry(null, null, 0);
    int startingSize = (int) Math.pow(2, 11);

    public Cache() throws FileNotFoundException {
    }

    static class Entry {
        final String url;
        String json;
        int hash;

        Entry(String k, String j, int h) {
            url = k;
            json = j;
            hash = h;
        }
    }

    /**
     * Tests whether a given object is contained in the Cache
     * <p>
     * The method checks every Entry object in the Cache and its next
     * value until it finds the object mapped to the url. If next is found
     * to be null then the object was not found.
     *
     * @param  url  a unique url that is mapped to a specific object
     * @return      a boolean signifying whether the object was found
     */

    public boolean contains(String url) {
        int h = url.hashCode();
        int i = h & (startingSize - 1);

        return CacheRAF.entryRead(i, url).isPresent();
    }

    /**
     * Adds a pokemon and its url to the Cache, resizes if needed
     * <p>
     * The method checks every Entry object in the Cache and its next
     * value until it finds a null spot to place the pokemon object.
     * <p>
     * After an add the methods checks to see if the Cache is mostly full.
     * If this is found to be the case then a new Cache is created of twice
     * and all the Entry objects are moved over to the new Cache
     *
     * @param  url  a unique url that is mapped to a specific object
     * @param  json  a unique url that is mapped to a specific object
     */

    public void add(String url, String json) {
        int h = url.hashCode();
        int i = h & (startingSize - 1);
        CacheRAF.entryWrite(new Entry(url, json, h), i);
    }

    /**
     * Retrieves the pokemon object mapped to the specified url
     * <p>
     * The method checks every Entry object and its next
     * values in the Cache until it finds the Pokemon mapped to the url.
     * It then returns that Pokemon object.
     *
     * @param  url  a unique url that is mapped to a specific object
     * @return      a Pokemon object mapped to the supplied url
     */

    public String get(String url) {
        int h = url.hashCode();
        int i = h & (startingSize - 1);

        return CacheRAF.entryRead(i, url).orElse(defaultEntry).json;
    }
}
