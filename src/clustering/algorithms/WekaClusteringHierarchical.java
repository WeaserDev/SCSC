package clustering.algorithms;

import weka.clusterers.HierarchicalClusterer;
import clustering.distance.*;
/**
 * Uses weka's Hierarchical clustering algorithm
 * @author Lefas Aristeidis
 */
public class WekaClusteringHierarchical extends WekaClustering {

	
	
	public WekaClusteringHierarchical(float[][] occurenceTable) {
		super(occurenceTable);	
	}

	protected int[] createClusters() {
		wekaDataset = createWekaData();
		WekaCosineDistance cosineDistance = new WekaCosineDistance();
		HierarchicalClusterer clusterer = new HierarchicalClusterer();
		try {
			clusterer.setDistanceFunction(cosineDistance);
			clusterer.setOptions(new String[] {"-L", "CENDROID"});
			clusterer.setDebug(true);
			clusterer.setNumClusters(8);
			clusterer.setDistanceIsBranchLength(true);

			clusterer.buildClusterer(wekaDataset);

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		int[] clusters=createClusterAssignments(clusterer);

		return clusters;
	}

}
