package com.btree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cesar on 3/5/17.
 */
public class RAF {
    String fileName = "";
    RandomAccessFile raf;

    public RAF(String name) throws FileNotFoundException {
        fileName = name;
        raf = new RandomAccessFile(fileName, "rw");
    }

    public Tree.Node treeRead(int order,long position) throws IOException {
        Tree.Node n = new Tree.Node();
        n.key = new int[order - 1];
        n.child = new Tree.Node[order];
        n.ps = new long[order];
        n.pokes = new Pokemon[order - 1];

        raf.seek(position);
        for (int i = 0; i < n.key.length; i ++) {
            int temp = raf.readInt();
            //if (temp != 0) {
                n.key[i] = temp;
            //}
        }
        for (int i = 0; i < n.ps.length; i++) {
            long temp = raf.readLong();
            if (temp != 0L) {
                n.ps[i] = temp;
            }
        }
        n.nKeys = raf.readInt();
        n.isLeaf = raf.readBoolean();
        if (!n.isLeaf) {
            for (int i = 0; i < n.child.length; i++) {
                for (int j = 0; j < n.ps.length; j++) {
                    n.child[i] = treeRead(order, n.ps[j]);
                }
            }
        }
        for (int i = 0; i < n.pokes.length; i++) {
            //n.pokes[i] = pokemonRead(new Pokemon());
        }
        return n;
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
        for (int i = 0; i < data.pokes.length; i++) {
            Pokemon derp = new Pokemon();
            //pokemonWrite(derp);
            //data.pokes[i] = derp;
        }

        long temp = raf.getFilePointer();
        return temp;
    }

    public void pokemonWrite(Pokemon mon) throws IOException {
        int nMax = 12;
        int tMax = 8;
        String pName = "";
        String pType = "";

        for (int i = 0; i < (nMax - mon.getName().length()); i++) {
            pName = mon.getName() + " ";
        }
        raf.writeUTF(pName);
        if (mon.types.size() == 2) {
            for (ComplexType c: mon.types) {
                for (int i = 0; i < (tMax - c.getType().getName().length()); i++) {
                    pType = c.getType().getName() + " ";
                }
                raf.writeUTF(pType);
            }
        } else {
            for (ComplexType c: mon.types) {
                for (int i = 0; i < (tMax - c.getType().getName().length()); i++) {
                    pType = c.getType().getName() + " ";
                }
                raf.writeUTF(pType);
            }
            raf.writeUTF(pType);
        }
        raf.writeInt(mon.getHeight());
        raf.writeInt(mon.getWeight());
    }

    public Pokemon pokemonRead(Pokemon mon) throws IOException {
        mon.setName(raf.readUTF().trim());
        String type1 = raf.readUTF();
        String type2 = raf.readUTF();
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
        mon.setHeight(raf.readInt());
        mon.setWeight(raf.readInt());
        return mon;
    }
}
