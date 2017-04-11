package com.btree;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by cesar on 3/5/17.
 */
public class Loader {

    public static void main(String[] args) throws IOException {
        Tree t = new Tree();
        t.createTree();
        Cache cache = new Cache();

        ConnectionReader cr = new ConnectionReader();
        String url = "https://pokeapi.co/api/v2/pokemon/";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("Loading the pokedex...");

        /**
         * Var dexMax
         * <p>
         * This variable determines how many Pokemon are loaded onto
         * the Cache at the start.
         */

        int dexMax = 721;
        for (int i = 1; i <= dexMax; i++) {
            String purl = url.concat(Integer.toString(i));
            String output  = cr.getUrlContents(purl);
            cache.add(purl, output);
            Pokemon poke = gson.fromJson(cache.get(purl), Pokemon.class);
            t.insert(i, poke);
            System.out.println(t.search(t.root, i).get().toString());
        }

        Random r;
        Pokemon temp1 = null;
        Pokemon temp2 = null;
        String nName = "";
        ArrayList<ComplexType> nType = new ArrayList<>();
        int nHeight = 0;
        int nWeight = 0;
        Pokemon ran = null;
        for (int i = dexMax + 1; i <= (dexMax * 2); i++) {
            r = new Random();
            int ran1 = r.nextInt(dexMax);
            int ran2 = r.nextInt(dexMax);
            if (ran1 == ran2) ran2++;
            if (ran1 == 0) ran1++;
            if (ran2 == 0) ran2++;
            temp1 = t.search(t.root, ran1).get();
            temp2 = t.search(t.root, ran2).get();
            nName = temp1.getName().substring(0, temp1.getName().length() / 2).concat(temp2.getName().substring(temp2.getName().length() / 2));
            nType = temp1.mergeTypes(temp2);
            nHeight = (temp1.getHeight() + temp2.getHeight()) / 2;
            nWeight = (temp1.getWeight() + temp2.getWeight()) / 2;
            ran = new Pokemon(nName, nType, nHeight, nWeight);
            t.insert(i, ran);
            nType.clear();
        }
    }
}
