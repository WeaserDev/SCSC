package clustering.algorithms;
import clustering.distance.*;

public class WekaClusteringXmeans extends WekaClustering {

		int maxClusterNumber;
		int minClusterNumber;
		
		public WekaClusteringXmeans(float[][] occurenceTable,int maxClusterNumber, int minClusterNumber){
			super(occurenceTable);
			this.maxClusterNumber=maxClusterNumber;
			this.minClusterNumber=minClusterNumber;
		}
		
		protected int[] createClusters() {
			wekaDataset = createWekaData();
			WekaCosineDistance cosineDistance = new WekaCosineDistance();
			WekaModifiedCosineDistance modifiedCosineDistance = new WekaModifiedCosineDistance();
			XMeans clusterer = new XMeans();
			try {
				clusterer.setDistanceF(modifiedCosineDistance);
		        clusterer.setMaxIterations(1000);
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
