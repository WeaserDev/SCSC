package clustering;

import clustering.DBSCAN.*;

public class WekaClusteringDBSCAN extends WekaClustering {

	
	
	public WekaClusteringDBSCAN(float[][] occurenceTable) {
		super(occurenceTable);	
	}

	protected int[] createClusters() {
		wekaDataset = createWekaData();

		DBSCAN clusterer = new DBSCAN();
		try {
			clusterer.buildClusterer(wekaDataset);

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		int[] clusters=createAssignments(clusterer);

		return clusters;
	}

}
