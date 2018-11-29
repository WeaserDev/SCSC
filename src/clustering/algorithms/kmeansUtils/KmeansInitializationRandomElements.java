package clustering.algorithms.kmeansUtils;

import clustering.distance.DistanceFunction;

public class KmeansInitializationRandomElements extends KmeansInitialization {


	public float[][] initializeCentroids(float[][] occurenceTable, int clusterNumber, DistanceFunction distance) {
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

}
