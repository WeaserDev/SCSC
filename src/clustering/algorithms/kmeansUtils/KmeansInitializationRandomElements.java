package clustering.algorithms.kmeansUtils;

import java.util.Arrays;

import clustering.distance.DistanceFunction;
/**
 * K-means random initialization method
 * @see clustering.algorithms.Kmeans Kmeans
 * @author Lefas Aristeidis
 */
public class KmeansInitializationRandomElements extends KmeansInitialization {

	public float[][] initializeCentroids(float[][] occurenceTable, int clusterNumber) {
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

	public float[] getNextCentroid(float[][] occurenceTable, float[][] centroids) {
		boolean duplicateCentroid = false;
		do {
			int randomElement = (int)(Math.random()*occurenceTable.length);	
			float nextCentroid[] = Arrays.copyOf(occurenceTable[randomElement], occurenceTable[randomElement].length);
			for (int i=0; i<centroids.length;i++) {
				if(Arrays.equals(nextCentroid, centroids[i])) duplicateCentroid = true;
			}
		} while (duplicateCentroid);
		return null;
	}
	
	

}
