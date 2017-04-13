package com.btree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Created by cesar on 4/9/17.
 */
public class Cluster {

    public ArrayList<NorPokemon> normalized;
    public NorPokemon centroid;
    public int id;

    //Creates a new Cluster
    public Cluster(int i) {
        this.normalized = new ArrayList<>(1442);
        this.centroid = null;
        id = i;
    }

    public ArrayList<NorPokemon> getPoints() {
        return normalized;
    }

    public void addPoint(NorPokemon point) {
        normalized.add(point);
    }

    public void setPoints(ArrayList<NorPokemon> points) {
        this.normalized = points;
    }

    public NorPokemon getCentroid() {
        return centroid;
    }

    public void setCentroid(NorPokemon centroid) {
        this.centroid = centroid;
    }

    public void clear() {
        normalized.clear();
    }

    public String getMembers() {
        String members = "";
        for (int i = 0; i < 5; i++) {
            members += normalized.get(i).index + " ";
        }
        return members.trim();
    }

}