package com.btree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by cesar on 4/5/17.
 */
public class CacheRAF {
    RandomAccessFile eRaf;
    String placeholder = "entryRaf/";
    RandomAccessFile cRaf;

    public CacheRAF() throws FileNotFoundException {
        cRaf = new RandomAccessFile("countRaf", "rw");
    }

    public long entryWrite(Cache.Entry e, long position) {

    }

    public Cache.Entry entryRead(long position) {

    }

    public void countsWrite(int[] counts) throws IOException {
        cRaf.seek(0);
        for (int i = 0; i < counts.length; i++) {
            cRaf.writeInt(counts[i]);
        }
    }
    public int[] countsRead(int size) throws IOException {
        int[] counts = new int[size];
        cRaf.seek(0);
        for (int i = 0; i < size; i++) {
            counts[i] = cRaf.readInt();
        }
        return counts;
    }
}
