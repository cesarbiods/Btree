package com.btree;

/**
 * Created by cesar on 3/5/17.
 */
public class Hashmap {
    int startingSize = 30;
    Entry[] tab = new Entry[startingSize];
    int count = 0;
    //RAF r;

    static class Entry {
        final String key;
        String json;
        Entry next;
        int hash;

        Entry(String k, String j, Entry n, int h) {
            key = k;
            json = j;
            next = n;
            hash = h;
        }
    }

    /**
     * Tests whether a given object is contained in the Hashmap
     * <p>
     * The method checks every Entry object in the Hashmap and its next
     * value until it finds the object mapped to the key. If next is found
     * to be null then the object was not found.
     *
     * @param  key  a unique key that is mapped to a specific object
     * @return      a boolean signifying whether the object was found
     */

    public boolean contains(String key) {
        int h = key.hashCode();
        Entry[] t = tab;
        int i = h & (t.length - 1);
        for (Entry e = t[i]; e != null; e = e.next) {
            if (e.hash == h && key.equals(e.key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a pokemon and its key to the Hashmap, resizes if needed
     * <p>
     * The method checks every Entry object in the Hashmap and its next
     * value until it finds a null spot to place the pokemon object.
     * <p>
     * After an add the methods checks to see if the Hashmap is mostly full.
     * If this is found to be the case then a new Hashmap is created of twice
     * and all the Entry objects are moved over to the new Hashmap
     *
     * @param  key  a unique key that is mapped to a specific object
     * @param  poke  a unique key that is mapped to a specific object
     */

    public void add(String key, String json) {
        int h = key.hashCode();
        Entry[] t = tab;
        int i = h & (tab.length) - 1;

        for (Entry e = t[i]; e != null; e = e.next) {
            if (e.hash == h && key.equals(e.key)) {
                e.json = json;
                //r.writeToFile();
                return;
            }
        }

        //check if full and evict
    }

    /**
     * Removes a pokemon and its key from the Hashmap
     * <p>
     * The method checks every Entry object in the Hashmap until it finds it
     * and removes it from the Hashmap. It also adjusts next values of the neighbors
     * after a remove.
     *
     * @param  key  a unique key that is mapped to a specific object
     */

    public void remove(String key) {
        int h = key.hashCode();
        Entry[] t = tab;
        int i = h & (t.length - 1);
        Entry pred = null;
        Entry p = t[i];
        while (p != null) {
            if (p.hash == h && key.equals(p.key)) {
                if (pred == null) {
                    t[i] = p.next;
                } else {
                    pred.next = p.next;
                }
                --count;
                return;
            }
            pred = p;
            p = p.next;
        }
    }

    /**
     * Retrieves the pokemon object mapped to the specified key
     * <p>
     * The method checks every Entry object and its next
     * values in the Hashmap until it finds the Pokemon mapped to the key.
     * It then returns that Pokemon object.
     *
     * @param  key  a unique key that is mapped to a specific object
     * @return      a Pokemon object mapped to the supplied key
     */

    public String get(String key) {
        int h = key.hashCode();
        Entry[] t = tab;
        int i = h & (t.length - 1);
        String found = "";
        for (Entry e = t[i]; e != null; e = e.next) {
            if (e.hash == h && key.equals(e.key)) {
                found = e.json;
            }
        }
        return found;
    }
}
