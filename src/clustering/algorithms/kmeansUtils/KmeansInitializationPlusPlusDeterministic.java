package clustering.algorithms.kmeansUtils;

import java.util.Arrays;

import clustering.distance.DistanceFunction;

public class KmeansInitializationPlusPlusDeterministic extends KmeansInitialization {
	int weightPower;
	DistanceFunction distance;
	
	public KmeansInitializationPlusPlusDeterministic(DistanceFunction distance,int weightPower) {
		this.distance = distance;
		this.weightPower = weightPower;
	}
	public float[][] initializeCentroids(float[][] occurenceTable, int clusterNumber) {
		float[][] clusterCentroids= new float[clusterNumber][occurenceTable[0].length];
		double[] minDistances = new double[occurenceTable.length];
		float maxFeaturesSum = 0;	
		int maxFeaturesIndex = 0;
		for (int i=0; i<occurenceTable.length; i++) {
			float featuresSum = 0;
			for (int k=0; k<occurenceTable[0].length;k++) {
				featuresSum += Math.pow(occurenceTable[i][k], 2);
			}
			if (featuresSum>maxFeaturesSum) {
				maxFeaturesSum = featuresSum;
				maxFeaturesIndex = i;
			}
		}
		clusterCentroids[0] = Arrays.copyOf(occurenceTable[maxFeaturesIndex], occurenceTable[maxFeaturesIndex].length);
		for (int i=1; i<clusterNumber; i++) {
			float centroids[][] = Arrays.copyOf(clusterCentroids, i);
			double totalDistance = 0;
			boolean distancesChanged = false;
			for (int k=0;k<occurenceTable.length;k++) {
				double dist = distance.distance(occurenceTable[k], centroids[i-1]);
				dist = Math.pow(dist, 2);
				if (Double.isNaN(dist)) {
					System.out.println("Distance is nan");
					System.out.println("OccurenceTable: " + Arrays.toString(occurenceTable[k]));
					System.out.println("CentroidsTable: " + Arrays.toString(centroids[i-1]));
				}
				if (minDistances[k]>dist||i==1) {
					minDistances[k]=dist;
					distancesChanged = true;
				}
				totalDistance += minDistances[k];	
			}
			clusterCentroids[i] = getNextCentroid(occurenceTable, centroids, minDistances, totalDistance);		
			if (Arrays.equals(clusterCentroids[i], clusterCentroids[i-1])) {
				System.out.println("same centroid");
			}
			if (!distancesChanged) System.out.println("distances unchaged");
		}	
		return clusterCentroids;
	}
	
	private float[] getNextCentroid(float[][] occurenceTable,  float[][] centroids, double[] minDistances, double totalDistance) {
		float[] nextCentroid = new float[occurenceTable[0].length];
		double[] selectionWeight = new double[minDistances.length];
		double totalWeight=0;
		if (weightPower>=100) {
			double maxDistance = minDistances[0];
			int maxDistanceIndex = 0;
			for (int i=1;i<minDistances.length;i++) {
				if (minDistances[i]>maxDistance) {
					maxDistance = minDistances[i];
					maxDistanceIndex = i;
				}
			}
			nextCentroid = Arrays.copyOf(occurenceTable[maxDistanceIndex], occurenceTable[maxDistanceIndex].length);
		} 
		else {
			for (int i=0; i<minDistances.length;i++) {
				selectionWeight[i] = Math.pow(minDistances[i]/totalDistance, weightPower);
				totalWeight +=selectionWeight[i];
			}
			
			for (int k=0;k<occurenceTable[0].length;k++){
				double next=0;
				for (int i=0; i<occurenceTable.length;i++) {
					next+= selectionWeight[i]*occurenceTable[i][k]/totalWeight;				
				}
				nextCentroid[k]=(float)next;
			}
		}
		return nextCentroid;
	}
	
	public float[] getNextCentroid(float[][] occurenceTable,  float[][] centroids) {
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
				if (minDistances[k]==0d) System.out.println("axa");
			}
			totalDistance += minDistances[k];			
		}		
		nextCentroid = getNextCentroid(occurenceTable, centroids, minDistances, totalDistance);
		return nextCentroid;	
	}
	

}
