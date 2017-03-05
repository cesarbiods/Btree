package com.btree;

/**
 * Created by cesar on 2/22/17.
 */

/**
 * This class was made solely to parse the JSON from Pokeapi and retrieve
 * the Pokemon's type correctly. The important variable is the
 * String name, it is the pokemon's type that will be displayed to the user
 * as well as used to calculate similarity
 */

public class Type {
    private String url;
    private String name;

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Assigns a numerical value to each pokemon type
     * <p>
     * Used for comparing types in the similarity metric and
     * determining which types are more alike
     *
     * @return      an int for the numerical representation of a type
     */

    public int value() {
        switch (name) {
            case "normal":
                return 1;
            case "fire":
                return 2;
            case "rock":
                return 3;
            case "ground":
                return 4;
            case "grass":
                return 5;
            case "bug":
                return 6;
            case "poison":
                return 7;
            case "fighting":
                return 8;
            case "dragon":
                return 9;
            case "dark":
                return 10;
            case "ghost":
                return 11;
            case "steel":
                return 12;
            case "electric":
                return 13;
            case "fairy":
                return 14;
            case "psychic":
                return 15;
            case "flying":
                return 16;
            case "ice":
                return 17;
            case "water":
                return 18;
            default: return 0;
        }
    }
}
