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
			CosineDistance cosineDistance = new CosineDistance();
			ModifiedCosineDistance modifiedCosineDistance = new ModifiedCosineDistance();
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
        	int[] clusters=createAssignments(clusterer);
			
	        return clusters;
		}
	
}
