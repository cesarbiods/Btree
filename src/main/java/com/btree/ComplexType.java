package com.btree;

/**
 * Created by cesar on 2/22/17.
 */

/**
 * This class was made solely to parse the JSON from Pokeapi and retrieve
 * the Pokemon's type correctly
 */
public class ComplexType {
    private int slot;
    private Type type;

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getSlot() {
        return slot;
    }

    public Type getType() {
        return type;
    }
}
