package clustering.algorithms.kmeansUtils;

import clustering.distance.DistanceFunction;

public class KmeansInitializationPlusPlus extends KmeansInitialization {


	public float[][] initializeCentroids(float[][] occurenceTable, int clusterNumber, DistanceFunction distance) {
		float[][] clusterCentroids= new float[clusterNumber][occurenceTable[0].length];
		int[] selectedCentroids = new int[clusterNumber];
		selectedCentroids[0] = (int)(Math.random()*occurenceTable.length);
		double[] squaredDistances = new double[occurenceTable.length];
		
		for (int i = 1; i < clusterNumber; i++) {
			double totalWeight = 0;
			for (int k=0;k<occurenceTable.length;k++) { 
				double dist = distance.distance(occurenceTable[k], occurenceTable[selectedCentroids[i-1]]);
				dist = Math.pow(dist, 2);
				if (squaredDistances[k]>dist||i==1) {
					squaredDistances[k]=dist;
				}
				totalWeight += squaredDistances[k];	
			}
			double random = Math.random() * totalWeight;
			
			for (int j = 0; j < occurenceTable.length; j++){
			    random -= squaredDistances[j];
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

}
