package clustering.algorithms;
import clustering.distance.*;
import weka.core.DistanceFunction;

public class WekaClusteringXmeans extends WekaClustering {

		int maxClusterNumber;
		int minClusterNumber;
		DistanceFunction distance;
		
		public WekaClusteringXmeans(float[][] occurenceTable,int maxClusterNumber, int minClusterNumber, DistanceFunction distance){
			super(occurenceTable);
			this.maxClusterNumber=maxClusterNumber;
			this.minClusterNumber=minClusterNumber;
			this.distance = distance;
		}
		
		protected int[] createClusters() {
			wekaDataset = createWekaData();
			XMeans clusterer = new XMeans();
			try {
			clusterer.setDistanceF(distance);
		        clusterer.setMaxIterations(100);
				clusterer.setMaxNumClusters(maxClusterNumber);
				clusterer.setMinNumClusters(minClusterNumber);
	        	clusterer.buildClusterer(wekaDataset);
			} 	
			catch (
				Exception e) {e.printStackTrace();
			}
        	int[] clusters=createClusterAssignments(clusterer);
	        return clusters;
		}
	
}
