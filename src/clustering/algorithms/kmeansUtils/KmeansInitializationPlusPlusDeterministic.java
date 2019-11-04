package clustering.algorithms.kmeansUtils;

import java.util.Arrays;

import clustering.distance.DistanceFunction;
/**
 * Deterministic alternative of k-means++ initialization method
 * @see clustering.algorithms.Kmeans Kmeans
 * @author Lefas Aristeidis
 */
public class KmeansInitializationPlusPlusDeterministic extends KmeansInitialization {
	int kClosestCentroids;
	DistanceFunction distance;
	/**
	 * The basic constructor of the KmeansInitializationPlusPlusDeterministic class
	 * @param distance The distance function used to calculate the closest centroids
	 * @param kClosestCentroid The number of closest centroids taken into consideration for next centroid calculation
	 */
	public KmeansInitializationPlusPlusDeterministic(DistanceFunction distance,int kClosestCentroids) {
		this.distance = distance;
		this.kClosestCentroids = kClosestCentroids;
	}
	public float[][] initializeCentroids(float[][] occurenceTable, int clusterNumber) {
		float[][] clusterCentroids= new float[clusterNumber][occurenceTable[0].length];
		double[][] kMinDistances = new double[occurenceTable.length][kClosestCentroids];
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
			for (int k=0;k<occurenceTable.length;k++) {
				if (Double.isInfinite(minDistances[k])||Arrays.equals(centroids[i-1], occurenceTable[k])) {
					minDistances[k] = Double.NEGATIVE_INFINITY;
				}
				else {
					double dist = distance.distance(occurenceTable[k], centroids[i-1]);
					dist = Math.pow(dist, 2);
					if (i-1<kClosestCentroids) {
						kMinDistances[k][i-1] = dist;
						minDistances[k]+=dist;
						for (int j=i-1;j>0;j--) {
							if(kMinDistances[k][j]<kMinDistances[k][j-1]) {
								double temp = kMinDistances[k][j];
								kMinDistances[k][j] = kMinDistances[k][j-1];
								kMinDistances[k][j-1] = temp;
							} 
							else {
								break;
							}
						}
					} else {
						if(dist<kMinDistances[k][kClosestCentroids-1]) {
							minDistances[k]+= dist-kMinDistances[k][kClosestCentroids-1];
							kMinDistances[k][kClosestCentroids-1] = dist;
							for (int j=kClosestCentroids-1;j>0;j--) {
								if(kMinDistances[k][j]<kMinDistances[k][j-1]) {
									double temp = kMinDistances[k][j];
									kMinDistances[k][j] = kMinDistances[k][j-1];
									kMinDistances[k][j-1] = temp;
								} 
								else {
									break;
								}
							}
						}
					}	
				}
			}		
			clusterCentroids[i] = getNextCentroid(occurenceTable, minDistances);
			for (int j=i-1;j>0;j--) {
				if (Arrays.equals(clusterCentroids[i], clusterCentroids[j])) {
				System.out.println("same centroid");
				}
			}
		
		}	
		return clusterCentroids;
	}
	
	private float[] getNextCentroid(float[][] occurenceTable, double[] minDistances) {
		float[] nextCentroid = new float[occurenceTable[0].length];
		double maxDistance = minDistances[0];
		int maxDistanceIndex = 0;
		for (int i=1;i<minDistances.length;i++) {
			if (minDistances[i]>maxDistance) {
				maxDistance = minDistances[i];
				maxDistanceIndex = i;
			}
		}
		nextCentroid = Arrays.copyOf(occurenceTable[maxDistanceIndex], occurenceTable[maxDistanceIndex].length);		
		return nextCentroid;
	}
	
	public float[] getNextCentroid(float[][] occurenceTable,  float[][] centroids) {
		float[] nextCentroid = new float[occurenceTable[0].length];
		double[] minDistances = new double[occurenceTable.length];
		double[][] kMinDistances = new double[occurenceTable.length][kClosestCentroids];
		for (int k=0;k<occurenceTable.length;k++) { 
			for (int i=0; i<centroids.length; i++) {
				if (Double.isInfinite(minDistances[k])||Arrays.equals(centroids[i], occurenceTable[k])) {
					minDistances[k] = Double.NEGATIVE_INFINITY;
				}
				else {
					double dist = distance.distance(occurenceTable[k], centroids[i]);
					dist = Math.pow(dist, 2);
					if (i<kClosestCentroids) {
						kMinDistances[k][i] = dist;
						minDistances[k]+=dist;
						for (int j=i;j>0;j--) {
							if(kMinDistances[k][j]<kMinDistances[k][j-1]) {
								double temp = kMinDistances[k][j];
								kMinDistances[k][j] = kMinDistances[k][j-1];
								kMinDistances[k][j-1] = temp;
							} 
							else {
								break;
							}
						}
					}
					else {
						if(dist<kMinDistances[k][kClosestCentroids-1]) {
							minDistances[k]+= dist-kMinDistances[k][kClosestCentroids-1];
							kMinDistances[k][kClosestCentroids-1] = dist;
							for (int j=kClosestCentroids-1;j>0;j--) {
								if(kMinDistances[k][j]<kMinDistances[k][j-1]) {
									double temp = kMinDistances[k][j];
									kMinDistances[k][j] = kMinDistances[k][j-1];
									kMinDistances[k][j-1] = temp;
								} 
								else {
									break;
								}
							}
						}
					}
				}
			}		
		}		
		nextCentroid = getNextCentroid(occurenceTable, minDistances);
		return nextCentroid;	
	}
	

}
