package clustering.algorithms.kmeansUtils;

import java.util.Arrays;

import clustering.distance.DistanceFunction;

public class KmeansInitializationPlusPlus extends KmeansInitialization {
	DistanceFunction distance;
	
	public KmeansInitializationPlusPlus(DistanceFunction distance){
		this.distance = distance;
	}
	public KmeansInitializationPlusPlus() {
		this.distance = new clustering.distance.CosineDistance();
	}
	
	public float[][] initializeCentroids(float[][] occurenceTable, int clusterNumber) {
		float[][] clusterCentroids= new float[clusterNumber][occurenceTable[0].length];
		int[] selectedCentroids = new int[clusterNumber];
		selectedCentroids[0] = (int)(Math.random()*occurenceTable.length);
		double[] minDistances = new double[occurenceTable.length];
		
		for (int i = 1; i < clusterNumber; i++) {
			double totalWeight = 0;
			for (int k=0;k<occurenceTable.length;k++) { 
				double dist = distance.distance(occurenceTable[k], occurenceTable[selectedCentroids[i-1]]);
				dist = Math.pow(dist, 2);
				if (minDistances[k]>dist||i==1) {
					minDistances[k]=dist;
				}
				totalWeight += minDistances[k];	
			}
			double random = Math.random() * totalWeight;
			
			for (int j = 0; j < occurenceTable.length; j++){
			    random -= minDistances[j];
			    if (random <= 0)
			    {
			        selectedCentroids[i] = j;					
			        break;  
			    }    
			}
			
		}
		for (int i=0;i<clusterNumber;i++) {
			for (int k=0; k<occurenceTable[0].length; k++) {
				clusterCentroids[i][k] = occurenceTable[selectedCentroids[i]][k];
			}	
		}	
		return clusterCentroids;
	}


	public float[] getNextCentroid(float[][] occurenceTable, float[][] centroids) {
		float[] nextCentroid = new float[occurenceTable[0].length];
		double totalDistance = 0;
		double[] minDistances = new double[occurenceTable.length];
		for (int k=0;k<occurenceTable.length;k++) { 
			for (int i=0; i<centroids.length; i++) {	
				double dist = distance.distance(occurenceTable[k], centroids[i]);
				dist = Math.pow(dist, 2);
				if (minDistances[k]>dist||i==0) {
					minDistances[k]=dist;
				}			
			}
			totalDistance += minDistances[k];
			double random = Math.random() * totalDistance;
			
			for (int j = 0; j < occurenceTable.length; j++){
			    random -= minDistances[j];
			    if (random <= 0)
			    {
			        nextCentroid = Arrays.copyOf(occurenceTable[j], occurenceTable[j].length);				
			        break;  
			    }    
			}	
			
		}	
		return nextCentroid;
	}

}
