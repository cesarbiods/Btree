package com.btree;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by cesar on 3/23/17.
 */
public class Tree {
    static int order = 32;
    static int t = order / 2;
    Node root;
    long nextPos = 0;
    RAF treeRaf = new RAF("treeRaf.txt");

    public Tree() throws IOException {
        root = allocate();
        root.isRoot = true;
        root.isLeaf = true;
        root.nKeys = 0;
        root.rafPosition = nextPos;
        nextPos = treeRaf.treeWrite(root, root.rafPosition);
    }

    static class Node {
        int key[];
        Node child[];
        long ps[];
        int nKeys;
        boolean isLeaf;
        boolean isRoot;
        Pokemon pokes[];
        long rafPosition;
    }

    public Node allocate() {
        Node n = new Node();
        n.key = new int[order - 1];
        n.child = new Node[order];
        n.ps = new long[order];
        n.nKeys = 0;
        n.isLeaf = false;
        n.isRoot = false;
        n.pokes = new Pokemon[order - 1];
        n.rafPosition = nextPos;
        return n;
    }

    public Optional<Pokemon> search(Node n, int key) {
        int i = 1;
        while (i <= n.nKeys && key > n.key[i - 1]) {
            i = i++;
        }

        if (i <= n.nKeys && key == n.key[i - 1]) {
            return Optional.of(n.pokes[key - 1]);
        } else if (n.isLeaf) {
            return Optional.empty();
        } else {
            //raf read(n.child[i-1])
            return search(n.child[i - 1], key);
        }
    }

    public void splitChild(Node n, int cIndex) throws IOException {
        Node extra = allocate();
        Node original = n.child[cIndex - 1];
        extra.isLeaf = original.isLeaf;
        extra.nKeys = t - 1;

        for (int j = 1; j < t - 1; j++) {
            extra.key[j - 1] = original.key[j + t - 1];
        }
        if (!original.isLeaf) {
            for (int j = 1; j < t; j++) {
                extra.child[j - 1] = original.child[j + t - 1];
            }
        }
        original.nKeys = t - 1;
        for (int j = n.nKeys + 1; j > cIndex + 1; j--) {
            n.child[j + 1 - 1] = n.child[j - 1];
        }
        n.child[cIndex + 1 - 1] = extra;
        for (int j = n.nKeys; j > cIndex; j--) {
            n.key[j + 1 - 1] = n.key[j - 1];
        }
        n.key[cIndex - 1] = original.key[t - 1];
        n.nKeys = n.nKeys + 1;
        treeRaf.treeWrite(original, original.rafPosition);
        nextPos = treeRaf.treeWrite(extra, extra.rafPosition);
        treeRaf.treeWrite(n, n.rafPosition);
    }

    public void insert(int key) throws IOException {
        Node r = root;
        if (r.nKeys == (2*t - 1)) {
            Node s = allocate();
            root = s;
            s.isLeaf = false;
            s.nKeys = 0;
            s.child[1 - 1] = r;
            nextPos = treeRaf.treeWrite(s, s.rafPosition);
            splitChild(s, 1);
            insertNonfull(s, key);
        } else {
            insertNonfull(r, key);
        }
    }

    public void insertNonfull(Node x, int key) throws IOException {
        int i = x.nKeys;
        if (x.isLeaf) {
            while (i >= 1 && key < x.key[i - 1]) {
                x.key[i + 1 - 1] = x.key[i - 1];
                i--;
            }
            x.key[i + 1 - 1] = key;
            x.nKeys++;
            treeRaf.treeWrite(x, x.rafPosition);
        } else {
            while (i >=1 && key < x.key[i - 1]) {
                i--;
            }
            i++;
            //raf read(x.child[i - 1])
            if (x.child[i - 1].nKeys == (2*t - 1)) {
                splitChild(x, i);
                if (key > x.key[i - 1]) {
                    i++;
                }
            }
            insertNonfull(x.child[i - 1], key);
        }
    }
}
