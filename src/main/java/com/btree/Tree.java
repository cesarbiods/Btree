package com.btree;

import java.util.Optional;

/**
 * Created by cesar on 3/23/17.
 */
public class Tree {
    static int order = 32;
    static int t = order / 2;
    Node root;

    public Tree() {
        root = new Node();
        root.isRoot = true;
        root.isLeaf = true;
        root.nKeys = 0;
        //raf write
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

        Node() {
            key = new int[order - 1];
            child = new Node[order];
            ps = new long[order];
            nKeys = 0;
            isLeaf = false;
            isRoot = false;
            pokes = new Pokemon[order - 1];
            ps = new long[order];
        }
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
            //raf write
            return search(n.child[i - 1], key);
        }
    }

    public void splitChild(Node n, int cIndex) {
        Node extra = new Node();
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
        //raf write n
        //rad write original
        //raf write extra
    }

    public void insert(int key) {
        Node r = root;
        if (r.nKeys == (2*t - 1)) {
            Node s = new Node();
            root = s;
            s.isLeaf = false;
            s.nKeys = 0;
            s.child[1 - 1] = r;
            splitChild(s, 1);
            insertNonfull(s, key);
        } else {
            insertNonfull(r, key);
        }
    }

    public void insertNonfull(Node x, int key) {
        int i = x.nKeys;
        if (x.isLeaf) {
            while (i >= 1 && key < x.key[i - 1]) {
                x.key[i + 1 - 1] = x.key[i - 1];
                i--;
            }
            x.key[i + 1 - 1] = key;
            x.nKeys++;
            //raf write(x)
        } else {
            while (i >=1 && key < x.key[i - 1]) {
                i--;
            }
            i++;
            //raf read(x.child[i])
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
