package clustering;
import weka.clusterers.*;
import weka.core.SelectedTag;

public class WekaClusteringKmeans extends WekaClustering {
	
	public WekaClusteringKmeans(float[][] occurenceTable){
		super(occurenceTable);
	}
	
	protected int[] createClusters() {
		wekaDataset = createWekaData();
		int[] result = null;
		int k=12;
		
		SimpleKMeans kmeans = new SimpleKMeans();
        kmeans.setPreserveInstancesOrder(true);
        kmeans.setInitializationMethod(new SelectedTag(SimpleKMeans.KMEANS_PLUS_PLUS, SimpleKMeans.TAGS_SELECTION));
		try {
	        kmeans.setMaxIterations(5);
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
