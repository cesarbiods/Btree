package com.btree;

/**
 * Created by cesar on 4/9/17.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.btree.Pokemon;

public class Kmeans {

    //Number of Clusters. 
    private int NUM_CLUSTERS = 3;
    //Number of Points
    private int NUM_POINTS = 25;

    private ArrayList<Pokemon> primitives;
    private ArrayList<NorPokemon> points;
    private ArrayList<Cluster> clusters;

    public Kmeans() {
        this.primitives = new ArrayList<>();
        this.points = new ArrayList();
        this.clusters = new ArrayList(NUM_CLUSTERS);
    }

    public ArrayList<NorPokemon> normalizeValues(ArrayList<Pokemon> raw) {
        double typeSum = 0;
        double heightSum = 0;
        double weightSum = 0;

        for (Pokemon point: raw) {
            typeSum += point.getValue();
            heightSum += point.getHeight();
            weightSum += point.getWeight();
        }

        double typeMean = typeSum / raw.size();
        double heightMean = heightSum / raw.size();
        double weightMean = weightSum / raw.size();

        double sumType = 0;
        double sumHeight = 0;
        double sumWeight = 0;

        for (Pokemon point: raw) {
            sumType += Math.pow(point.getValue() - typeMean, 2);
            sumHeight += Math.pow(point.getHeight() - heightMean, 2);
            sumWeight += Math.pow(point.getWeight() - weightMean, 2);
        }

        double typeSD = sumType / raw.size();
        double heightSD = sumHeight / raw.size();
        double weightSD = sumWeight / raw.size();
        ArrayList<NorPokemon> normal = new ArrayList<>();

        int index = 0;
        for (Pokemon point: raw) {
            String name = point.getName();
            double nType = (point.getValue() - typeMean) / typeSD;
            double nHeight = (point.getHeight() - heightMean) / heightSD;
            double nWeight = (point.getWeight() - weightMean) / weightSD;
            normal.add(new NorPokemon(name, nType, nHeight, nWeight, ++index));
        }
        return normal;
    }

    public void init(Tree t, int dexMax) {
        ArrayList<NorPokemon> entire = new ArrayList<>();
        try {
            for (int i = 1; i < dexMax; i++) {
                primitives.add(t.search(t.root, i).get());
            }
            entire = normalizeValues(primitives);

            //Create Points
//            int[] randoms = new int[NUM_POINTS];
//            for (int i = 0; i < NUM_POINTS; i++) {
//                Random r = new Random();
//                randoms[i] = r.nextInt(dexMax);
//            }

//            for (int i = 0; i < NUM_POINTS; i++) {
                points.addAll(entire);
//            }
        } catch (IOException e) {
                e.printStackTrace();
        }

        //Create Clusters
        //Set Random Centroids
        for (int i = 1; i <= NUM_CLUSTERS; i++) {
            Cluster cluster = new Cluster(i);
            Random r = new Random();
            NorPokemon centroid = entire.get(r.nextInt(dexMax));
            cluster.setCentroid(centroid);
            clusters.add(cluster);
        }

    }

    public String plotClusters() {
        String plots = "";
        for (int i = 0; i < clusters.size(); i++) {
            plots += "Cluster " + (i + 1) + ": ";
            plots += clusters.get(i).getMembers() + "\n";
        }
        return plots;
    }

    public void calculate() {
        boolean finish = false;
        int iteration = 0;

        // Add in new data, one at a time, recalculating centroids with each new one.
        double distance = 0;
        while (iteration != 10) {
            clearClusters();

            ArrayList<NorPokemon> lastCentroids = getCentroids();

            assignCluster();

            calculateCentroids();

            iteration++;

            ArrayList<NorPokemon> currentCentroids = getCentroids();


            for (int i = 0; i < lastCentroids.size(); i++) {
                distance += lastCentroids.get(i).compare(currentCentroids.get(i));
            }
//            System.out.println("#################");
//            System.out.println("Iteration: " + iteration);
//            System.out.println("Centroid distances: " + distance);

        }
    }

    private void clearClusters() {
        for (Cluster cluster : clusters) {
            cluster.clear();
        }
    }

    private ArrayList<NorPokemon> getCentroids() {
        ArrayList<NorPokemon> centroids = new ArrayList(NUM_CLUSTERS);
        for (Cluster cluster : clusters) {
            NorPokemon aux = cluster.getCentroid();
            NorPokemon point = new NorPokemon(aux.getName(), aux.getTypeValue(), aux.getHeight(), aux.getWeight(), 0);
            centroids.add(point);
        }
        return centroids;
    }

    private void assignCluster() {
        int cluster = 0;
        double distance;

        for (NorPokemon point : points) {
            double max = Double.MAX_VALUE;
            for (int i = 0; i < clusters.size(); i++){
                Cluster c = clusters.get(i);
                distance = point.compare(c.getCentroid());
                if (distance < max){
                    max = distance;
                    cluster = i;
                }
            }
            clusters.get(cluster).addPoint(point);
        }
    }

    private void calculateCentroids() {
        for (Cluster cluster : clusters) {
            double sumValue = 0;
            double sumHeight = 0;
            double sumWeight = 0;
            ArrayList<NorPokemon> list = cluster.getPoints();
            int n_points = list.size();

            for (NorPokemon point : list) {
                sumValue += point.getTypeValue();
                sumHeight += point.getHeight();
                sumWeight += point.getWeight();
            }

            NorPokemon centroid = cluster.getCentroid();
            if (n_points > 0){
                double newValue = sumValue / n_points;
                double newHeight = sumHeight / n_points;
                double newWeight = sumWeight / n_points;
                centroid.setTypeValue(newValue);
                centroid.setHeight(newHeight);
                centroid.setWeight(newWeight);
            }
        }
    }
}