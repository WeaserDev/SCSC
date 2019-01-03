package clustering.evaluation;

import java.io.File;

public class AverageClusterAdjustedPrecision extends AdjustedPrecision {

	public AverageClusterAdjustedPrecision(File rootDirectory) {
		super(rootDirectory);
	}
	
	@Override
	public float evaluate(int[] clusters, float[][] occurenceTable) {
		if (foldersList.isEmpty()) {
			foldersList = createFoldersList();
		}
		int[] clusterIntrapairs = new int[clusterNumber(clusters)];
		float[] clusterPrecision = new float[clusterNumber(clusters)];
		float precision = 0;
		for (int i=0; i<clusters.length; i++) {
			for (int k=i+1; k<clusters.length; k++) {
				if (clusters[i]==clusters[k]) {
					clusterIntrapairs[clusters[i]]+=1;
					int commonFolders = 0;
					for (int j=0; (j<foldersList.get(i).size())&&(j<foldersList.get(k).size());j++) {
						if (foldersList.get(i).get(j).equals(foldersList.get(k).get(j))) {
							commonFolders++;
						}
						else {
							break;
						}
					}
					clusterPrecision[clusters[i]] += (float)commonFolders / (foldersList.get(i).size()+foldersList.get(k).size()-commonFolders);	
				}		
			}
		}
		for (int i=0; i<clusterNumber(clusters);i++) {
			if (clusterIntrapairs[i]!=0) {
				clusterPrecision[i]=clusterPrecision[i]/clusterIntrapairs[i];
				precision+=clusterPrecision[i];
			}
		}
		return precision/clusterNumber(clusters);
	}
	private int clusterNumber(int[] clusters) {
		int max = clusters[0];
		for (int i=1; i < clusters.length ; i++) {
			if (clusters[i]>max) {
				max=clusters[i];
			}
		}
		max+=1;
		return max;
	}
}
