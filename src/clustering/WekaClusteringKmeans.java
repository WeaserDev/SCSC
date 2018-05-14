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
		ManhattanDistance manhattanDistance = new ManhattanDistance();
		SimpleKMeans kmeans = new SimpleKMeans();
        kmeans.setPreserveInstancesOrder(true);
        kmeans.setInitializationMethod(new SelectedTag(SimpleKMeans.KMEANS_PLUS_PLUS, SimpleKMeans.TAGS_SELECTION));
		try {
			kmeans.setDistanceFunction(cosineDistance);
	        kmeans.setMaxIterations(10);
			kmeans.setNumClusters(clusterNumber);
        	kmeans.buildClusterer(wekaDataset);
        	result = kmeans.getAssignments();
		} 	
		catch (
			Exception e) {e.printStackTrace();
		}
		
        return result;
	}
}
