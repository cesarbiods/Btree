package com.btree;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

/**
 * Created by cesar on 3/5/17.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Tree t = new Tree();
        System.out.println("Hi");
        //t.search(t.root, 1);



//        Cache hashmap = new Cache();
//        ConnectionReader cr = new ConnectionReader();
//        String url = "https://pokeapi.co/api/v2/pokemon/";
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        System.out.println("Loading the pokedex...");

        /**
         * Var dexMax
         * <p>
         * This variable determines how many Pokemon are loaded onto
         * the Cache at the start.
         */

//        int dexMax = 3;
//        for (int i = 1; i <= dexMax; i++) {
//            String purl = url.concat(Integer.toString(i));
//            String output  = cr.getUrlContents(purl);
//            Pokemon poke = gson.fromJson(output, Pokemon.class);
//            hashmap.add(Integer.toString(i), poke);
//        }
//        System.out.println("Success!");
    }
}
