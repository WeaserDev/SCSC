package clustering.algorithms;

import java.util.Arrays;

import clustering.distance.DistanceFunction;

public class Kmeans extends Clustering {
	int clusterNumber;	
	int maxIterations = 100;
	DistanceFunction distance;
	
	
	public Kmeans(float[][] occurenceTable, int clusterNumber, DistanceFunction distance) {
		super(occurenceTable);
		this.clusterNumber = clusterNumber;
		this.distance = distance;
	}

	protected int[] createClusters() {
		float[][] clusterCentroids= new float[clusterNumber][occurenceTable[0].length];
		this.clusters = new int[occurenceTable.length];
		clusterCentroids = initializeCentroids();
		int iteration = 0;
		int[] oldClusters = new int[occurenceTable.length];
		
		while (iteration<maxIterations) {
			for (int i=0; i < occurenceTable.length; i++) {
				double minDistance = distance.distance(occurenceTable[i],clusterCentroids[0]);
				int cluster = 0; 
				for (int k=0; k<clusterNumber; k++) {
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
		System.out.println(iteration);
		
		
		return clusters;
	}
	
	private float[][] initializeCentroids(){
		float[][] clusterCentroids= new float[clusterNumber][occurenceTable[0].length];
		int[] randomElements = new int[clusterNumber];
		for (int i = 0; i < clusterNumber; i++) {
			randomElements[i] = (int)(Math.random()*occurenceTable.length);
		    for (int j = 0; j < i; j++) {
		        if (randomElements[i] == randomElements[j]) {
		            i--; 
		            break;
		        }
		    }  
		}
		for (int i=0;i<clusterNumber;i++) {
			for (int k=0; k<occurenceTable[0].length; k++) {
				clusterCentroids[i][k] = occurenceTable[randomElements[i]][k];
			}	
		}
		
		return clusterCentroids;
		
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
				clusterCentroids[clusters[i]][k] = clusterCentroids[clusters[i]][k]/clusterElements[i];
			}
		}
		
		return clusterCentroids;	
	}
	
}
