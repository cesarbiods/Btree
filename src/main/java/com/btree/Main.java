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
        t.insert(15);
        t.insert(23);
        t.insert(42);
        t.insert(13);
        t.insert(56);
        t.insert(78);
        t.insert(8);
        t.insert(31);
        t.insert(1);
        t.insert(35);
        t.insert(6);
        t.insert(99);
        t.insert(38);
        t.insert(4);
        t.insert(88);
        t.insert(90);
        t.insert(91);
        t.insert(2);
        t.insert(3);
        t.insert(5);
        t.insert(21);
        System.out.println("Hi");
        //t.search(t.root, 1);



//        Hashmap hashmap = new Hashmap();
//        connectionReader cr = new connectionReader();
//        String url = "https://pokeapi.co/api/v2/pokemon/";
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        System.out.println("Loading the pokedex...");

        /**
         * Var dexMax
         * <p>
         * This variable determines how many Pokemon are loaded onto
         * the Hashmap at the start.
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
