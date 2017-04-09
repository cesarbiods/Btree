package com.btree;

import java.util.List;

/**
 * Created by cesar on 2/8/17.
 */

/**
 * This class describes a Pokemon object to be created from the Pokeapi
 * nd loaded on the Cache by dex numbers. Every Pokemon has a name,
 * height, weight, and a single or a dual type.
 */

public class Pokemon {
    private String name;
    public List<ComplexType> types;
    private int height;
    private int weight;
    private long rafPosition;

    public Pokemon(String n, List<ComplexType> t, int h, int w) {
        name = n;
        types = t;
        height = h;
        weight = w;
    }
    public Pokemon() {}

    public String getName() {
        return name;
    }

    /**
     * Concatenates a String if the pokemon has a dual type
     * <p>
     * Concatenates a String of the pokemon's type to be displayed
     * on the GUI
     *
     * @return      a String for the concatenation of the type(s)
     */

    public String getTypes() {
        String type = "";
        for (ComplexType temp: types) {
            type += temp.getType().getName();
            type += "/";
        }
        String actual = type.substring(0, type.length() - 1);
        return actual;
    }

    /**
     * Gets the numerical value of the Pokemon's type(s)
     * <p>
     * This method exists in case a pokemon has a dual type
     * so as to add the two numerical values together
     *
     * @return      an int for the numerical value of the Pokemon's type(s)
     */

    public int getValue() {
        int value = 0;
        for (ComplexType temp: types) {
            value += temp.getType().value();
        }
        return value;
    }

    public void translateValue(int value) {

    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(List<ComplexType> type) {
        this.types = type;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public long getRafPosition() {
        return rafPosition;
    }

    public void setRafPosition(long rafPosition) {
        this.rafPosition = rafPosition;
    }

    public String toString() {
        return "Pokemon: \n" + "Name: " + name + "\n" + "Type: " + getTypes() + "\n" + "Height: " + height + "\n" + "Weight: " + weight;
    }

    /**
     * Similarity metric
     * <p>
     * This method compares a Pokemon being passed as a parameter
     * and the one the method is being called on. It subtracts the weight,
     * height, and type value, multiplies each by a ratio, and adds the
     * results to a a result variable signifying the similarity of two pokemon,
     * the smaller the number the more alike they are.
     *
     * @param  b  a Pokemon object to be compared against
     * @return      an int for the similarity of this Pokemon and the one being compared
     */

    public int compare(Pokemon b) {
        int wRatio = 1;
        int hRatio = 100;
        int tRatio = 10;
        int result = 0;
        result += Math.abs((this.getHeight() - b.getHeight()) * hRatio);
        result += Math.abs((this.getWeight() - b.getWeight()) * wRatio);
        result += Math.abs((this.getValue() - b.getValue()) * tRatio);
        return Math.abs(result);
    }
}
