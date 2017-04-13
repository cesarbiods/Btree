package com.btree;

/**
 * Created by cesar on 4/9/17.
 */
public class NorPokemon {
    public int index;
    private String name;
    public double typeValue;
    private double height;
    private double weight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(double typeValue) {
        this.typeValue = typeValue;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public NorPokemon(String n, double t, double h, double w, int i) {
        name = n;
        typeValue = t;
        height = h;
        weight = w;
        index = i;
    }

    public double compare(NorPokemon b) {
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
