package clustering.algorithms;
import weka.clusterers.*;
import weka.core.SelectedTag;
import weka.core.DistanceFunction;
import weka.core.ManhattanDistance;
import clustering.distance.*;

public class WekaClusteringKmeans extends WekaClustering {
	int clusterNumber;
	DistanceFunction distanceFunction;
	
	public WekaClusteringKmeans(float[][] occurenceTable,int clusterNumber, DistanceFunction distanceFunction){
		super(occurenceTable);
		this.clusterNumber = clusterNumber;
		this.distanceFunction = distanceFunction;
	}
	
	
	protected int[] createClusters() {
		wekaDataset = createWekaData();	
		SimpleKMeans clusterer = new SimpleKMeans();
        clusterer.setPreserveInstancesOrder(true);
        //clusterer.setInitializationMethod(new SelectedTag(SimpleKMeans.KMEANS_PLUS_PLUS, SimpleKMeans.TAGS_SELECTION));

		try {
			clusterer.setDistanceFunction(distanceFunction);
	        clusterer.setMaxIterations(100);
			clusterer.setNumClusters(clusterNumber);
        	clusterer.buildClusterer(wekaDataset);
        	
		} 	
		catch (
			Exception e) {e.printStackTrace();
		}
		int[] result = createClusterAssignments(clusterer);
        return result;
	}
}
