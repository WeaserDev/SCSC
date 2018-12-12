package clustering.algorithms;

import java.util.Arrays;

import clustering.algorithms.kmeansUtils.*;
import clustering.distance.DistanceFunction;

public class Kmeans extends Clustering {
	protected int clusterNumber;	
	protected int maxIterations = 100;
	protected DistanceFunction distance;
	protected KmeansInitialization initialize;
	
	
	public Kmeans(float[][] occurenceTable, int clusterNumber, DistanceFunction distance) {
		super(occurenceTable);
		this.clusterNumber = clusterNumber;
		this.distance = distance;
		this.initialize = new KmeansInitializationPlusPlus();
	}
	
	public Kmeans(float[][] occurenceTable, int clusterNumber, DistanceFunction distance, KmeansInitialization initialize) {
		super(occurenceTable);
		this.clusterNumber = clusterNumber;
		this.distance = distance;
		this.initialize = initialize;
	}

	protected int[] createClusters() {
		float[][] clusterCentroids= new float[clusterNumber][occurenceTable[0].length];
		this.clusters = new int[occurenceTable.length];
		clusterCentroids = initialize.initializeCentroids(occurenceTable, clusterNumber, distance);
		int iteration = 0;
		int[] oldClusters = new int[occurenceTable.length];
		
		while (iteration<maxIterations) {
			for (int i=0; i < occurenceTable.length; i++) {
				double minDistance = distance.distance(occurenceTable[i],clusterCentroids[0]);
				int cluster = 0; 
				for (int k=1; k<clusterNumber; k++) {
					double currentDistance = distance.distance(occurenceTable[i],clusterCentroids[k]);
					if (currentDistance<minDistance) {
						minDistance = currentDistance;
						cluster = k;
					}
				}
				clusters[i] = cluster;
			}
			clusterCentroids = calculateCentroids();
			if (Arrays.equals(clusters, oldClusters)) {
				break;
			}
			else {
				for (int i=0; i < occurenceTable.length; i++) {
					oldClusters[i] = clusters[i];
				}
			}	
			iteration+=1;
		}
		
		return clusters;
	}

	
	private float[][] calculateCentroids(){
		float[][] clusterCentroids= new float[clusterNumber][occurenceTable[0].length];
		int[] clusterElements = new int[clusterNumber];
		for (int i=0; i<occurenceTable.length; i++) {
			clusterElements[clusters[i]]+=1;
			for (int k=0; k<occurenceTable[0].length; k++) {
				clusterCentroids[clusters[i]][k] += occurenceTable[i][k];
			}
		}
		
		for (int i=0; i<clusterNumber; i++) {
			for (int k=0; k<clusterCentroids[0].length; k++) {
				clusterCentroids[i][k] = clusterCentroids[i][k]/clusterElements[i];
			}
		}
		
		return clusterCentroids;	
	}
	
}
