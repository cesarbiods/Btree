package com.btree;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by cesar on 3/5/17.
 */
public class Main {

    public static void main(String[] args) {
        Hashmap hashmap = new Hashmap();
        connectionReader cr = new connectionReader();
        String url = "https://pokeapi.co/api/v2/pokemon/";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("Loading the pokedex...");

        /**
         * Var dexMax
         * <p>
         * This variable determines how many Pokemon are loaded onto
         * the Hashmap at the start.
         */

        int dexMax = 50;
        for (int i = 1; i <= dexMax; i++) {
            String purl = url.concat(Integer.toString(i));
            String output  = cr.getUrlContents(purl);
            Pokemon poke = gson.fromJson(output, Pokemon.class);
            hashmap.add(Integer.toString(i), poke);
        }
        System.out.println("Success!");
    }
}
