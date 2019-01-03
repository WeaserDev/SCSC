package clustering.evaluation;

public class AverageClusterPrecision extends SupervisedEvaluation {

	public AverageClusterPrecision(int[] authoritativeClusters) {
		super(authoritativeClusters);
	}

	@Override
	public float evaluate(int[] clusters, float[][] occurenceTable) {
		int[] clusterIntrapairs=new int[clusterNumber(clusters)];
		int[] commonIntrapairs=new int[clusterNumber(clusters)];
		float clusterPrecision = 0;
		
 		for (int i=0; i<clusters.length; i++) {
			for (int k=i+1; k<clusters.length; k++) {
				if (clusters[i]==clusters[k]) {
					clusterIntrapairs[clusters[i]]+=1;
					if (authoritativeClusters[i]==authoritativeClusters[k]) commonIntrapairs[clusters[i]]+=1;
				}		
			}
		}
 		for (int i=0; i<clusterNumber(clusters);i++) {
 			if (clusterIntrapairs[i]!=0) {
 				clusterPrecision += (float) commonIntrapairs[i]/clusterIntrapairs[i];
 			}
 		}		
		return clusterPrecision/clusterNumber(clusters);
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
