package clustering.algorithms.kmeansUtils;


public abstract class KmeansInitialization {
	public abstract float[][] initializeCentroids(float[][] occurenceTable, int clusterNumber);
	public abstract float[] getNextCentroid(float[][] occurenceTable, float[][] centroids);
}
