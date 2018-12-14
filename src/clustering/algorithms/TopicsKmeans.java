package clustering.algorithms;

import clustering.distance.DistanceFunction;

public class TopicsKmeans extends Clustering {
	protected int clusterNumber;
	protected int topicsNumber;
	protected DistanceFunction distance;
	
	public TopicsKmeans(float[][] occurenceTable,int topicsNumber, int clusterNumber, DistanceFunction distance) {
		super(occurenceTable);	
		this.topicsNumber = topicsNumber;
		this.clusterNumber = clusterNumber;
		this.distance = distance;
	}
	
	protected int[] createClusters() {
		Kmeans topicsKmeans = new Kmeans(occurenceTable,topicsNumber, distance);
		clusters = topicsKmeans.createClusters();
		float topicsCentroids[][] = calculateCentroids();
		float topicDistanceTable[][] = calculateTopicDistanceTable(topicsCentroids);
		Kmeans kmeans = new Kmeans(topicDistanceTable,clusterNumber, distance);
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
