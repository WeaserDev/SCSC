package clustering;
import weka.clusterers.*;

public class WekaClusteringKmeans extends WekaClustering {
	
	public WekaClusteringKmeans(float[][] occurenceTable){
		super(occurenceTable);
	}
	
	protected void createClusters() {
		createWekaData();
		int k=5;
		SimpleKMeans kmeans = new SimpleKMeans();
        
		try {
			kmeans.setNumClusters(k);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		
        kmeans.setPreserveInstancesOrder(true);
        
        try {
			kmeans.buildClusterer(wekaDataset);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        try {
			assignments = kmeans.getAssignments();
		} catch (Exception e) {
			e.printStackTrace();
		}
         
	}
}
