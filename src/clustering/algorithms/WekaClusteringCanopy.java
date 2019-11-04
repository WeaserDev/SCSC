package clustering.algorithms;

import weka.clusterers.Canopy;;
/**
 * Uses weka's Canopy clustering algorithm
 * @author Lefas Aristeidis
 */
public class WekaClusteringCanopy extends WekaClustering {
	
	public WekaClusteringCanopy(float[][] occurenceTable) {
		super(occurenceTable);	
	}

	protected int[] createClusters() {
		wekaDataset = createWekaData();
		
		Canopy clusterer = new Canopy();
		try {
			clusterer.setOptions(new String[] {"-L", "COMPLETE"});
			clusterer.setDebug(true);
			clusterer.setNumClusters(2);

			clusterer.buildClusterer(wekaDataset);

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		int[] clusters=createClusterAssignments(clusterer);

		return clusters;
		
		
	}
}
