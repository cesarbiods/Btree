package com.btree;

/**
 * Created by cesar on 2/22/17.
 */

/**
 * This class was made solely to parse the JSON from Pokeapi and retrieve
 * the Pokemon's type correctly
 */
public class ComplexType {
    private Type type;

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
