package clustering.algorithms;

import java.util.Arrays;
import java.util.HashSet;

import clustering.algorithms.kmeansUtils.*;
import clustering.distance.DistanceFunction;

public class Kmeans extends OccurenceClustering {
	protected int clusterNumber;	
	protected int maxIterations = 10000;
	protected DistanceFunction distance;
	protected KmeansInitialization initialize;
	
	
	public Kmeans(float[][] occurenceTable, int clusterNumber, DistanceFunction distance) {
		super(occurenceTable);
		this.clusterNumber = clusterNumber;
		this.distance = distance;
		this.initialize = new KmeansInitializationPlusPlusDeterministic(distance, 100);
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
		clusterCentroids = initialize.initializeCentroids(occurenceTable, clusterNumber);
		int iteration = 0;
		int[] oldClusters = new int[occurenceTable.length];
		
		while (iteration<maxIterations) {
			for (int i=0; i < occurenceTable.length; i++) {
				double minDistance = distance.distance(occurenceTable[i],clusterCentroids[0]);
				int cluster = 0; 
				for (int k=1; k<clusterNumber; k++) {
					double currentDistance = distance.distance(occurenceTable[i],clusterCentroids[k]);
					if (currentDistance<=minDistance) {
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
		HashSet<Integer> emptyClusters = new HashSet<Integer>(); 
		for (int i=0; i<occurenceTable.length; i++) {
			clusterElements[clusters[i]]+=1;
			for (int k=0; k<occurenceTable[0].length; k++) {
				clusterCentroids[clusters[i]][k] += occurenceTable[i][k];
			}
		}
		
		for (int i=0; i<clusterNumber; i++) {
			if (clusterElements[i]!=0) {
				for (int k=0; k<clusterCentroids[0].length; k++) {		
					clusterCentroids[i][k] = clusterCentroids[i][k]/clusterElements[i];
				}
			} else {
				emptyClusters.add(i);
			}
		}
		if (emptyClusters.size()>0) {
			for (int cluster=0;cluster<clusterNumber;cluster++) {
				if (emptyClusters.contains(cluster)) {
					float nonEmptyClusterCentroids[][] = new float[clusterNumber-emptyClusters.size()][occurenceTable[0].length];
					int k=0;
					for (int i=0; i<clusterNumber;i++) {
						if (!emptyClusters.contains(i)) {
							nonEmptyClusterCentroids[k] = Arrays.copyOf(clusterCentroids[i],occurenceTable[0].length);
							k+=1;
						} 
					}
					clusterCentroids[cluster] = initialize.getNextCentroid(occurenceTable, nonEmptyClusterCentroids);
					emptyClusters.remove(cluster);
				}
			}
		}
		return clusterCentroids;	
	}
	
}
