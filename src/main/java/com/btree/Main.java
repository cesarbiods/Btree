package com.btree;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by cesar on 3/5/17.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Tree t = new Tree();
        Cache cache = new Cache();


        //SwingUtilities.invokeLater(()-> new GUI());
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

        int dexMax = 100;
        for (int i = 1; i <= dexMax; i++) {
            String purl = url.concat(Integer.toString(i));
            String output  = cr.getUrlContents(purl);
            cache.add(purl, output);
            Pokemon poke = gson.fromJson(cache.get(purl), Pokemon.class);
            t.insert(i, poke);
            System.out.println(t.search(t.root, i).get().toString());
        }
    }
}
