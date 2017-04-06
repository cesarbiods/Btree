package com.btree;

import java.io.FileNotFoundException;

/**
 * Created by cesar on 3/5/17.
 */
public class Cache {
    int startingSize = 30;
    Entry[] tab = new Entry[startingSize];
    //int count = 0;
    CacheRAF raf = new CacheRAF();
    int[] counts = new int[startingSize];

    public Cache() throws FileNotFoundException {
    }

    static class Entry {
        final String url;
        String path;
        Entry next;
        int hash;

        Entry(String k, String j, Entry n, int h) {
            url = k;
            path = j;
            next = n;
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
        Entry[] t = tab;
        int i = h & (t.length - 1);
        for (Entry e = t[i]; e != null; e = e.next) {
            if (e.hash == h && url.equals(e.url)) {
                return true;
            }
        }
        return false;
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
     * @param  path  a unique url that is mapped to a specific object
     */

    public void add(String url, String path) {
        int h = url.hashCode();
        Entry[] t = tab;
        int i = h & (tab.length) - 1;

        for (Entry e = t[i]; e != null; e = e.next) {
            if (e.hash == h && url.equals(e.url)) {
                e.path = path;
                //r.writeToFile();
                return;
            }
        }

        //check if full and evict
    }

    /**
     * Removes a pokemon and its url from the Cache
     * <p>
     * The method checks every Entry object in the Cache until it finds it
     * and removes it from the Cache. It also adjusts next values of the neighbors
     * after a remove.
     *
     * @param  url  a unique url that is mapped to a specific object
     */

    public void remove(String url) {
        int h = url.hashCode();
        Entry[] t = tab;
        int i = h & (t.length - 1);
        Entry pred = null;
        Entry p = t[i];
        while (p != null) {
            if (p.hash == h && url.equals(p.url)) {
                if (pred == null) {
                    t[i] = p.next;
                } else {
                    pred.next = p.next;
                }
                //--count;
                return;
            }
            pred = p;
            p = p.next;
        }
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
        Entry[] t = tab;
        int i = h & (t.length - 1);
        String found = "";
        for (Entry e = t[i]; e != null; e = e.next) {
            if (e.hash == h && url.equals(e.url)) {
                found = e.path;
            }
        }
        return found;
    }
}
