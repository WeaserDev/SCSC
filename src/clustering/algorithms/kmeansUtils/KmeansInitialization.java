package clustering.algorithms.kmeansUtils;
/**
 * The abstract class for methods that initialize k-means algorithm.
 * Class that extend this class should implement {@link #initializeCentroids(float[][], int)} and {@link #getNextCentroid(float[][], float[][])} methods.
 * @see clustering.algorithms.Kmeans Kmeans
 * @author Lefas Aristeidis
 */
public abstract class KmeansInitialization {
	/**
	 * Calculates the initial centroids
	 * @param occurenceTable The document-term table.
	 * @param clusterNumber The requested number of cluster centroids.
	 * @return A table where each row represents a cluster centroid.
	 */
	public abstract float[][] initializeCentroids(float[][] occurenceTable, int clusterNumber);
	/**
	 * Calculates the next centroid.
	 * @param occurenceTable The document-term table.
	 * @param centroids The centroids that are already selected.
	 * @return A vector representing the next centroid to be chosen.
	 */
	public abstract float[] getNextCentroid(float[][] occurenceTable, float[][] centroids);
}
