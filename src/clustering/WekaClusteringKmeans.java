package clustering;
import weka.clusterers.*;
import weka.core.SelectedTag;
import weka.core.ManhattanDistance;
public class WekaClusteringKmeans extends WekaClustering {
	
	public WekaClusteringKmeans(float[][] occurenceTable){
		super(occurenceTable);
	}
	
	protected int[] createClusters() {
		wekaDataset = createWekaData();
		int[] result = null;
		int k=7;
		CosineDistance cosineDistance = new CosineDistance();
		ManhattanDistance manhattanDistance = new ManhattanDistance();
		SimpleKMeans kmeans = new SimpleKMeans();
        kmeans.setPreserveInstancesOrder(true);
        kmeans.setInitializationMethod(new SelectedTag(SimpleKMeans.KMEANS_PLUS_PLUS, SimpleKMeans.TAGS_SELECTION));
		try {
			kmeans.setDistanceFunction(cosineDistance);
	        kmeans.setMaxIterations(100);
			kmeans.setNumClusters(k);
        	kmeans.buildClusterer(wekaDataset);
        	result = kmeans.getAssignments();
		} 	
		catch (
			Exception e) {e.printStackTrace();
		}
		
        return result;
	}
}
