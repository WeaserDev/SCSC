package clustering;
import weka.clusterers.*;

public class WekaClusteringKmeans extends WekaClustering {
	
	public WekaClusteringKmeans(float[][] occurenceTable){
		super(occurenceTable);
	}
	
	protected int[] createClusters() {
		wekaDataset = createWekaData();
		int[] result = null;
		int k = 5;
		
		SimpleKMeans kmeans = new SimpleKMeans();
        kmeans.setPreserveInstancesOrder(true);

		try {
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
