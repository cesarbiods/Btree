package com.btree;

/**
 * Created by cesar on 4/9/17.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.btree.Pokemon;

public class KMeans {

    //Number of Clusters. 
    private int NUM_CLUSTERS = 3;
    //Number of Points
    private int NUM_POINTS = 15;

    private ArrayList<Pokemon> points;
    private ArrayList<Cluster> clusters;

    public KMeans() {
        this.points = new ArrayList();
        this.clusters = new ArrayList();
    }

    public static void main(String[] args) {

        KMeans kmeans = new KMeans();
        kmeans.init();
        kmeans.calculate();
    }

    //Initializes the process
    public void init() {
        //Create Points
        Random r = new Random();


        //Create Clusters
        //Set Random Centroids
        for (int i = 1; i <= NUM_CLUSTERS; i++) {
            Cluster cluster = new Cluster(i);
            Pokemon centroid = Pokemon.createRandomPoint(MIN_COORDINATE,MAX_COORDINATE);
            cluster.setCentroid(centroid);
            clusters.add(cluster);
        }

        //Print Initial state
        plotClusters();
    }

    private void plotClusters() {
        for (int i = 1; i <= NUM_CLUSTERS; i++) {
            Cluster c = clusters.get(i);
            c.plotCluster();
        }
    }

    //The process to calculate the K Means, with iterating method.
    public void calculate() {
        boolean finish = false;
        int iteration = 0;

        // Add in new data, one at a time, recalculating centroids with each new one.
        while(!finish) {
            //Clear cluster state
            clearClusters();

            List lastCentroids = getCentroids();

            //Assign points to the closer cluster
            assignCluster();

            //Calculate new centroids.
            calculateCentroids();

            iteration++;

            List currentCentroids = getCentroids();

            //Calculates total distance between new and old Centroids
            double distance = 0;
            for(int i = 0; i &lt; lastCentroids.size(); i++) {
                distance += Pokemon.distance(lastCentroids.get(i),currentCentroids.get(i));
            }
            System.out.println("#################");
            System.out.println("Iteration: " + iteration);
            System.out.println("Centroid distances: " + distance);
            plotClusters();

            if(distance == 0) {
                finish = true;
            }
        }
    }

    private void clearClusters() {
        for(Cluster cluster : clusters) {
            cluster.clear();
        }
    }

    private List getCentroids() {
        List centroids = new ArrayList(NUM_CLUSTERS);
        for(Cluster cluster : clusters) {
            Pokemon aux = cluster.getCentroid();
            Pokemon point = new Pokemon(aux.getX(),aux.getY());
            centroids.add(point);
        }
        return centroids;
    }

    private void assignCluster() {
        double max = Double.MAX_VALUE;
        double min = max;
        int cluster = 0;
        double distance = 0.0;

        for(Pokemon point : points) {
            min = max;
            for(int i = 0; i &lt; NUM_CLUSTERS; i++) {
                Cluster c = clusters.get(i);
                distance = Pokemon.distance(point, c.getCentroid());
                if(distance &lt; min){
                    min = distance;
                    cluster = i;
                }
            }
            point.setCluster(cluster);
            clusters.get(cluster).addPoint(point);
        }
    }

    private void calculateCentroids() {
        for(Cluster cluster : clusters) {
            double sumX = 0;
            double sumY = 0;
            List list = cluster.getPoints();
            int n_points = list.size();

            for(Pokemon point : list) {
                sumX += point.getX();
                sumY += point.getY();
            }

            Pokemon centroid = cluster.getCentroid();
            if(n_points &gt; 0) {
                double newX = sumX / n_points;
                double newY = sumY / n_points;
                centroid.setX(newX);
                centroid.setY(newY);
            }
        }
    }
}