package com.btree;

/**
 * Created by cesar on 4/9/17.
 */
public class NorPokemon {
    private String name;
    public int typeValue;
    private int height;
    private int weight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(int typeValue) {
        this.typeValue = typeValue;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public NorPokemon(String n, int t, int h, int w) {
        name = n;
        typeValue = t;
        height = h;
        weight = w;
    }

    public int compare(NorPokemon b) {
        int wRatio = 1;
        int hRatio = 100;
        int tRatio = 10;
        int result = 0;
        result += Math.abs((this.getHeight() - b.getHeight()) * hRatio);
        result += Math.abs((this.getWeight() - b.getWeight()) * wRatio);
        result += Math.abs((this.getTypeValue() - b.getTypeValue()) * tRatio);
        return Math.abs(result);
    }
}
