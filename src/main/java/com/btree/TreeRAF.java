package com.btree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cesar on 3/5/17.
 */
public class TreeRAF {
    RandomAccessFile tRaf;
    RandomAccessFile pRaf;

    public TreeRAF() throws FileNotFoundException {
        tRaf = new RandomAccessFile("treeRaf", "rw");
        pRaf = new RandomAccessFile("pokeRaf", "rw");
    }

    public Tree.Node treeRead(int order,long position) throws IOException {
        Tree.Node n = new Tree.Node();
        n.key = new int[order - 1];
        n.child = new Tree.Node[order];
        n.ps = new long[order];
        n.pokes = new Pokemon[order - 1];
        n.pps = new long[order - 1];

        tRaf.seek(position);
        for (int i = 0; i < n.key.length; i ++) {
            n.key[i] = tRaf.readInt();
        }
        for (int i = 0; i < n.ps.length; i++) {
            n.ps[i] = tRaf.readLong();
        }
        n.nKeys = tRaf.readInt();
        n.isLeaf = tRaf.readBoolean();
        for (int i = 0; i < n.pps.length; i++) {
            n.pps[i] = tRaf.readLong();
        }
        n.rafPosition = position;

        if (!n.isLeaf) {
            for (int i = 0; i < n.nKeys + 1; i++) {
                n.child[i] = treeRead(order, n.ps[i]);
            }
        }
        return n;
    }

    public long treeWrite(Tree.Node data, long position) throws IOException {
        tRaf.seek(position);
        for (int i = 0; i < data.key.length; i ++) {
            tRaf.writeInt(data.key[i]);
        }
        for (int i = 0; i < data.ps.length; i++) {
            tRaf.writeLong(data.ps[i]);
        }
        tRaf.writeInt(data.nKeys);
        tRaf.writeBoolean(data.isLeaf);
        for (int i = 0; i < data.pps.length; i++) {
            tRaf.writeLong(data.pps[i]);
        }
        tRaf.writeLong(position);

        return tRaf.getFilePointer();
    }

    public long pokemonWrite(Pokemon mon, long position) throws IOException {
        int nMax = 12;
        int tMax = 8;
        String pName = "";
        String pType = "";

        pRaf.seek(position);
        for (int i = 0; i < (nMax - mon.getName().length()); i++) {
            pName = mon.getName() + " ";
        }
        pRaf.writeUTF(pName);
        if (mon.types.size() == 2) {
            for (ComplexType c: mon.types) {
                for (int i = 0; i < (tMax - c.getType().getName().length()); i++) {
                    pType = c.getType().getName() + " ";
                }
                pRaf.writeUTF(pType);
            }
        } else {
            for (ComplexType c: mon.types) {
                for (int i = 0; i < (tMax - c.getType().getName().length()); i++) {
                    pType = c.getType().getName() + " ";
                }
                pRaf.writeUTF(pType);
            }
            pRaf.writeUTF(pType);
        }
        pRaf.writeInt(mon.getHeight());
        pRaf.writeInt(mon.getWeight());
        pRaf.writeLong(position);

        return pRaf.getFilePointer();
    }

    public Pokemon pokemonRead(long position) throws IOException {
        Pokemon mon = new Pokemon();

        pRaf.seek(position);
        mon.setName(pRaf.readUTF().trim());
        String type1 = pRaf.readUTF().trim();
        String type2 = pRaf.readUTF().trim();
        if (type1.equals(type2)) {
            Type mono = new Type();
            mono.setName(type1);
            ComplexType a = new ComplexType();
            a.setType(mono);
            mon.setType((List<ComplexType>) a);
        } else {
            Type a = new Type();
            Type b = new Type();
            a.setName(type1);
            b.setName(type2);
            ComplexType x = new ComplexType();
            ComplexType y = new ComplexType();
            x.setType(a);
            y.setType(b);
            List<ComplexType> types = Arrays.asList(x,y);
            mon.setType(types);
        }
        mon.setHeight(pRaf.readInt());
        mon.setWeight(pRaf.readInt());
        mon.setRafPosition(position);
        return mon;
    }
}
