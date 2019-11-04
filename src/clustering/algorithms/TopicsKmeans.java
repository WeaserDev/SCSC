package clustering.algorithms;

import clustering.algorithms.kmeansUtils.KmeansInitialization;
import clustering.algorithms.kmeansUtils.KmeansInitializationPlusPlusDeterministic;
import clustering.distance.DistanceFunction;
/**
 * This class extends the OccurenceClustering abstract class. It uses the {@link Kmeans} clustering algorithm.
 * It produces a number of clusters and produces an occurenceTable where the features are the distances from those cluster centroids.
 * Then it produces the final clustering based on this table.
 * @author Lefas Aristeidis
 */
public class TopicsKmeans extends OccurenceClustering {
	protected int clusterNumber;
	protected int topicsNumber;
	protected DistanceFunction distance;
	protected KmeansInitialization initialize;

	public TopicsKmeans(float[][] occurenceTable,int topicsNumber, int clusterNumber, DistanceFunction distance,KmeansInitialization initialize) {
		super(occurenceTable);	
		this.topicsNumber = topicsNumber;
		this.clusterNumber = clusterNumber;
		this.distance = distance;
		this.initialize = initialize;
	}
	
	protected int[] createClusters() {
		Kmeans topicsKmeans = new Kmeans(occurenceTable,topicsNumber, distance, initialize);
		clusters = topicsKmeans.createClusters();
		float topicsCentroids[][] = calculateCentroids();
		float topicDistanceTable[][] = calculateTopicDistanceTable(topicsCentroids);
		Kmeans kmeans = new Kmeans(topicDistanceTable,clusterNumber, distance, initialize);
		clusters = kmeans.createClusters();
		return clusters;
	}
	
	private float[][] calculateCentroids(){
		float[][] clusterCentroids= new float[topicsNumber][occurenceTable[0].length];
		int[] clusterElements = new int[topicsNumber];
		for (int i=0; i<occurenceTable.length; i++) {
			clusterElements[clusters[i]]+=1;
			for (int k=0; k<occurenceTable[0].length; k++) {
				clusterCentroids[clusters[i]][k] += occurenceTable[i][k];
			}
		}
		
		for (int i=0; i<topicsNumber; i++) {
			for (int k=0; k<clusterCentroids[0].length; k++) {
				clusterCentroids[i][k] = clusterCentroids[i][k]/clusterElements[i];
			}
		}
		return clusterCentroids;	
	}
	
	private float[][] calculateTopicDistanceTable(float topicsCentroids[][]){
		float[][] topicDistanceTable= new float[occurenceTable.length][topicsNumber];
		for (int i=0;i<occurenceTable.length; i++) {
			for (int k=0; k<topicsNumber; k++) {
				topicDistanceTable[i][k] = (float)distance.distance(topicsCentroids[k], occurenceTable[i]); 
			}
		}
		
		return topicDistanceTable;
	}
 
}
