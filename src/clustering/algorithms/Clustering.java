package clustering.algorithms;
/**
 * Represents a method to generate clusters. Implementations of the {@link #returnClusters()} function should return the cluster assignments.
 * @author Lefas Aristeidis
 */
public interface Clustering {
	/**
	 * Generates the clusters and returns their assignments.
	 * @return An array containing the cluster assignment of each entity.
	 */
	int[] returnClusters();
}
