package clustering;
import weka.clusterers.*;
import weka.core.SelectedTag;
import weka.core.ManhattanDistance;
public class WekaClusteringKmeans extends WekaClustering {
	int clusterNumber;
	
	public WekaClusteringKmeans(float[][] occurenceTable,int clusterNumber){
		super(occurenceTable);
		this.clusterNumber=clusterNumber;
	}
	
	protected int[] createClusters() {
		wekaDataset = createWekaData();
		int[] result = null;
		CosineDistance cosineDistance = new CosineDistance();
		ModifiedCosineDistance modifiedCosineDistance = new ModifiedCosineDistance();
		ManhattanDistance manhattanDistance = new ManhattanDistance();
		SimpleKMeans clusterer = new SimpleKMeans();
        clusterer.setPreserveInstancesOrder(true);
        clusterer.setInitializationMethod(new SelectedTag(SimpleKMeans.KMEANS_PLUS_PLUS, SimpleKMeans.TAGS_SELECTION));
		try {
			clusterer.setDistanceFunction(modifiedCosineDistance);
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
