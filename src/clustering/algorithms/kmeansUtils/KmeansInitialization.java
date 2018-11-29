package clustering.algorithms.kmeansUtils;

import clustering.distance.DistanceFunction;

public abstract class KmeansInitialization {
	public abstract float[][] initializeCentroids(float[][] occurenceTable, int clusterNumber, DistanceFunction distance);
}
