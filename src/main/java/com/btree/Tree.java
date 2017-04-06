package com.btree;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by cesar on 3/23/17.
 */
public class Tree {
    static int order = 4;
    static int t = order / 2;
    Node root;
    long nNextPos = 0;
    long pNextPos = 0;
    TreeRAF treeRaf = new TreeRAF();

    public Tree() throws IOException {
        root = allocate();
        root.isLeaf = true;
        root.nKeys = 0;
        root.rafPosition = nNextPos;
        nNextPos = treeRaf.treeWrite(root, root.rafPosition);
    }

    static class Node {
        int key[];
        Node child[];
        long ps[];
        int nKeys;
        boolean isLeaf;
        Pokemon pokes[];
        long pps[];
        long rafPosition;
    }

    public Node allocate() {
        Node n = new Node();
        n.key = new int[order - 1];
        n.child = new Node[order];
        n.ps = new long[order];
        n.nKeys = 0;
        n.isLeaf = false;
        n.pokes = new Pokemon[order - 1];
        n.pps = new long[order - 1];
        n.rafPosition = nNextPos;
        return n;
    }

    public Optional<Pokemon> search(Node n, int key) throws IOException {
        int i = 1;
        while (i <= n.nKeys && key > n.key[i - 1]) {
            i = i++;
        }

        if (i <= n.nKeys && key == n.key[i - 1]) {
            return Optional.of(n.pokes[key - 1]);
        } else if (n.isLeaf) {
            return Optional.empty();
        } else {
            n.child[i - 1] = treeRaf.treeRead(order, n.ps[i - 1]);
            for (int j = 0; j < n.child[i - 1].nKeys; j++) {
                n.child[i - 1].pokes[j] = treeRaf.pokemonRead(n.child[i - 1].pps[j]);
            }
            return search(n.child[i - 1], key);
        }
    }

    public void splitChild(Node top, int cIndex) throws IOException {
        Node right = allocate();
        Node left = top.child[cIndex - 1];
        right.isLeaf = left.isLeaf;
        right.nKeys = t - 1;

        for (int j = 1; j <= t - 1; j++) { //moves the necessary keys from left to right
            right.key[j - 1] = left.key[j + t - 1];
            left.key[j + t - 1] = 0;
            right.pokes[j - 1] = left.pokes[j + t - 1];
            right.pps[j - 1] = left.pps[j + t- 1];
            left.pokes[j + t - 1] = null;
            left.pps[j + t - 1] = 0;
        }
        if (!left.isLeaf) {
            for (int j = 1; j <= t; j++) {
                right.child[j - 1] = left.child[j + t - 1]; //should reassign necessary children from left to right like above
                right.ps[j - 1] = left.child[j + t - 1].rafPosition;
                left.child[j + t - 1] = null; // Remove child from left
                left.ps[j + t - 1] = 0;
            }
        }
        left.nKeys = t - 1;
        for (int j = top.nKeys + 1; j >= cIndex + 1; j--) {
            top.child[j + 1 - 1] = top.child[j - 1];
            top.ps[j + 1 - 1] = top.child[j - 1].rafPosition;
        }
        top.child[cIndex + 1 - 1] = right; //connects right and top so it's a child
        top.ps[cIndex + 1 - 1] = right.rafPosition;
        for (int j = top.nKeys; j >= cIndex; j--) {
            top.key[j + 1 - 1] = top.key[j - 1];
            top.key[j - 1] = 0; //weird
            top.pokes[j + 1 - 1] = top.pokes[j - 1];
            top.pokes[j - 1] = null;
        }
        top.key[cIndex - 1] = left.key[t - 1];
        top.pokes[cIndex - 1] = left.pokes[t - 1];
        left.key[t - 1] = 0;
        left.pokes[t - 1] = null;
        top.nKeys = top.nKeys + 1;
        treeRaf.treeWrite(left, left.rafPosition);
        nNextPos = treeRaf.treeWrite(right, right.rafPosition);
        treeRaf.treeWrite(top, top.rafPosition);
    }

    public boolean isFull(Node n) {
        for (int i = 0; i < n.key.length; i++) {
            if (n.key[i] == 0) {
                return false;
            }
        }
        return true;
    }

    public void insert(int key, Pokemon poke) throws IOException {
        Node r = root;
        if (isFull(r)) {
            Node s = allocate();
            root = s;
            s.isLeaf = false;
            s.nKeys = 0;
            s.child[1 - 1] = r;
            s.ps[1 -1] = r.rafPosition;
            nNextPos = treeRaf.treeWrite(s, s.rafPosition);
            splitChild(s, 1);
            insertNonfull(s, key, poke);
        } else {
            insertNonfull(r, key, poke);
        }
    }

    public void insertNonfull(Node x, int key, Pokemon poke) throws IOException {
        int i = x.nKeys;
        if (x.isLeaf) {
            while (i >= 1 && key < x.key[i - 1]) {
                x.key[i + 1 - 1] = x.key[i - 1];
                i--;
            }
            x.key[i + 1 - 1] = key;
            x.pokes[i + 1 - 1] = poke;
            x.nKeys++;
            poke.setRafPosition(pNextPos);
            x.pps[i + 1 - 1] = poke.getRafPosition();
            treeRaf.treeWrite(x, x.rafPosition);
            pNextPos = treeRaf.pokemonWrite(poke, poke.getRafPosition());
        } else {
            while (i >=1 && key < x.key[i - 1]) {
                i--;
            }
            i++;
            x.child[i - 1] = treeRaf.treeRead(order, x.ps[i - 1]);
            for (int j = 0; j < x.child[i - 1].nKeys; j++) {
                x.child[i - 1].pokes[j] = treeRaf.pokemonRead(x.child[i - 1].pps[j]);
            }
            if (isFull(x.child[i - 1])) {
                splitChild(x, i);
                if (key > x.key[i - 1]) {
                    i++;
                }
            }
            insertNonfull(x.child[i - 1], key, poke);
        }
    }
}
