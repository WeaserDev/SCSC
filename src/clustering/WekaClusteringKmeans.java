package clustering;
import weka.clusterers.*;

public class WekaClusteringKmeans extends WekaClustering {
	
	public WekaClusteringKmeans(float[][] occurenceTable){
		super(occurenceTable);
	}
	
	protected int[] createClusters() {
		wekaDataset = createWekaData();
		int[] result = null;
		int k=4;
		SimpleKMeans kmeans = new SimpleKMeans();
        
		try {kmeans.setNumClusters(k);} 	
		catch (Exception e) {e.printStackTrace();}
		
		
        kmeans.setPreserveInstancesOrder(true);
        
        try {kmeans.buildClusterer(wekaDataset);}
        catch (Exception e) {e.printStackTrace();}
        
        try {result = kmeans.getAssignments();} 	
        catch (Exception e) {e.printStackTrace();}
        
        return result;
	}
}
