package clustering.algorithms;
import weka.clusterers.*;
import weka.core.SelectedTag;
import weka.core.ManhattanDistance;
import clustering.distance.*;

public class WekaClusteringKmeans extends WekaClustering {
	int clusterNumber;
	String distanceFunction;
	
	public WekaClusteringKmeans(float[][] occurenceTable,int clusterNumber, String distanceFunction){
		super(occurenceTable);
		this.clusterNumber = clusterNumber;
		this.distanceFunction = distanceFunction;
	}
	
	
	protected int[] createClusters() {
		wekaDataset = createWekaData();
		int[] result = null;	
		SimpleKMeans clusterer = new SimpleKMeans();
        clusterer.setPreserveInstancesOrder(true);
        clusterer.setInitializationMethod(new SelectedTag(SimpleKMeans.KMEANS_PLUS_PLUS, SimpleKMeans.TAGS_SELECTION));

		try {
			switch(distanceFunction) {
			case "cosine": 	clusterer.setDistanceFunction(new WekaCosineDistance());											
			break;
			case "modifiedCosine": clusterer.setDistanceFunction(new WekaModifiedCosineDistance());
			break;
			case "manhattan" : clusterer.setDistanceFunction(new ManhattanDistance());
			break;
			case "dot" : clusterer.setDistanceFunction(new WekaDotDistance());
			break;
			}
	        clusterer.setMaxIterations(1000);
			clusterer.setNumClusters(clusterNumber);
        	clusterer.buildClusterer(wekaDataset);
        	result = clusterer.getAssignments();
		} 	
		catch (
			Exception e) {e.printStackTrace();
		}
		
        return result;
	}
}
