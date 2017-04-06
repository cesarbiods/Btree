package com.btree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by cesar on 4/5/17.
 */
public class CashRAF {
    RandomAccessFile cRaf;

    public CashRAF() throws FileNotFoundException {
        cRaf = new RandomAccessFile("cashRaf", "rw");
    }

    public Tree.Node treeRead(int order, long position) throws IOException {
        Tree.Node n = new Tree.Node();
        n.key = new int[order - 1];
        n.child = new Tree.Node[order];
        n.ps = new long[order];
        n.pokes = new Pokemon[order - 1];

        tRaf.seek(position);
        for (int i = 0; i < n.key.length; i++) {
            int temp = tRaf.readInt();
            n.key[i] = temp;
        }
        for (int i = 0; i < n.ps.length; i++) {
            long temp = tRaf.readLong();
            n.ps[i] = temp;
        }
        n.nKeys = tRaf.readInt();
        n.isLeaf = tRaf.readBoolean();
        if (!n.isLeaf) {
            for (int i = 0; i < n.nKeys + 1; i++) {
                n.child[i] = treeRead(order, n.ps[i]);
            }
        }
        n.rafPosition = position;
        return n;
    }

    public long treeWrite(Tree.Node data, long position) throws IOException {
        tRaf.seek(position);
        for (int i = 0; i < data.key.length; i++) {
            tRaf.writeInt(data.key[i]);
        }
        tRaf.writeInt(data.nKeys);
        tRaf.writeBoolean(data.isLeaf);
        for (int i = 0; i < data.pokes.length; i++) {
            Pokemon derp = new Pokemon();
            //pokemonWrite(derp);
            //data.pokes[i] = derp;
        }
        tRaf.writeLong(position);

        long temp = tRaf.getFilePointer();
        return temp;
    }
}
