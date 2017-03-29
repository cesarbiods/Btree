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

    public byte[] readFromFile(int size) throws IOException {
        byte[] bytes = new byte[size];
        raf.read(bytes);
        raf.close();
        return bytes;
    }

    public long treeWrite(Tree.Node data, long position) throws IOException {
        raf.seek(position);
        for (int i = 0; i < data.key.length; i ++) {
            raf.writeInt(data.key[i]);
        }
        for (int i = 0; i < data.ps.length; i++) {
            raf.writeLong(data.ps[i]);
        }
        raf.writeInt(data.nKeys);
        raf.writeBoolean(data.isLeaf);
        raf.writeBoolean(data.isRoot);
        for (int i = 0; i < data.pokes.length; i++) {
            pokemonWrite(data.pokes[i]);
        }

        long temp = raf.getFilePointer();
        raf.close();
        return temp;
    }

    public void pokemonWrite(Pokemon mon) throws IOException {
        int nMax = 12;
        int tMax = 17;
        String pName = "";
        String pType = "";
        for (int i = 0; i < (nMax - mon.getName().length()); i++) {
            pName = mon.getName() + " ";
        }
        raf.writeUTF(pName);
        for (int i = 0; i < (tMax - mon.getName().length()); i++) {
            pType = mon.getTypes() + " ";
        }
        raf.writeUTF(pType);
        raf.writeInt(mon.getHeight());
        raf.writeInt(mon.getWeight());
    }
}
