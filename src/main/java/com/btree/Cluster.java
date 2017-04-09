package com.btree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Created by cesar on 4/9/17.
 */
public class Cluster {

    public ArrayList<Pokemon> points;
    public ArrayList<NorPokemon> normalized;
    public Pokemon centroid;
    public int id;

    //Creates a new Cluster
    public Cluster(int id) {
        this.id = id;
        this.points = new ArrayList();
        this.centroid = null;
    }

    public List getPoints() {
        return points;
    }

    public void addPoint(Pokemon point) {
        points.add(point);
    }

    public void setPoints(ArrayList<Pokemon> points) {
        this.points = points;
    }

    public Pokemon getCentroid() {
        return centroid;
    }

    public void setCentroid(Pokemon centroid) {
        this.centroid = centroid;
    }

    public int getId() {
        return id;
    }

    public void clear() {
        points.clear();
    }

    public void NormalizeValues() {
        int typeSum = 0;
        int heightSum = 0;
        int weightSum = 0;

        for (Pokemon point: points) {
            typeSum += point.getValue();
            heightSum += point.getHeight();
            weightSum += point.getWeight();
        }

        int typeMean = typeSum / points.size();
        int heightMean = heightSum / points.size();
        int weightMean = weightSum / points.size();

        int sumType = 0;
        int sumHeight = 0;
        int sumWeight = 0;

        for (Pokemon point: points) {
            sumType += Math.pow(point.getValue() - typeMean, 2);
            sumHeight += Math.pow(point.getHeight() - heightMean, 2);
            sumWeight += Math.pow(point.getWeight() - weightMean, 2);
        }

        int typeSD = sumType / points.size();
        int heightSD = sumHeight / points.size();
        int weightSD = sumWeight / points.size();

        for (Pokemon point: points) {
            String name = point.getName();
            int nType = (point.getValue() - typeMean) / typeSD;
            int nHeight = (point.getHeight() - heightMean) / heightSD;
            int nWeight = (point.getWeight() - weightMean) / weightSD;
            normalized.add(new NorPokemon(name, nType, nHeight, nWeight));
        }
    }

    public void plotCluster() {
        System.out.println("[Cluster: " + id+"]");
        System.out.println("[Centroid: " + centroid + "]");
        System.out.println("[Points: \n");
        for(Pokemon p : points) {
            System.out.println(p);
        }
        System.out.println("]");
    }

}