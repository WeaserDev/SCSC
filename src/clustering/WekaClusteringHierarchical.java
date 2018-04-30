package clustering;

import weka.clusterers.HierarchicalClusterer;

public class WekaClusteringHierarchical extends WekaClustering {

	
	
	public WekaClusteringHierarchical(float[][] occurenceTable) {
		super(occurenceTable);	
	}

	protected int[] createClusters() {
		wekaDataset = createWekaData();
		
		HierarchicalClusterer clusterer = new HierarchicalClusterer();
		try {
			clusterer.setOptions(new String[] {"-L", "COMPLETE"});
			clusterer.setDebug(true);
			clusterer.setNumClusters(5);
			clusterer.setDistanceIsBranchLength(true);

			clusterer.buildClusterer(wekaDataset);

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		int[] clusters=createAssignments(clusterer);

		return clusters;
	}

}
