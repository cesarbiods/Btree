package com.btree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by cesar on 3/5/17.
 */
public class RAF {
    String fileName = "randomaccessfile.txt";
    RandomAccessFile raf = new RandomAccessFile(fileName, "rw");

    public RAF() throws FileNotFoundException {
    }

    public byte[] readFromFile(int position, int size) throws IOException {
        raf.seek(position);
        byte[] bytes = new byte[size];
        raf.read(bytes);
        raf.close();
        return bytes;
    }

    public void writeToFile(String data, int position) throws IOException {
        raf.seek(position);
        raf.write(data.getBytes());
        raf.close();
    }
}
