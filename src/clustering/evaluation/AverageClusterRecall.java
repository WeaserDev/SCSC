package clustering.evaluation;

public class AverageClusterRecall extends SupervisedEvaluation {

	public AverageClusterRecall(int[] authoritativeClusters) {
		super(authoritativeClusters);
	}

	@Override
	public float evaluate(int[] clusters, float[][] occurenceTable) {
		int[] clusterIntrapairs=new int[clusterNumber(authoritativeClusters)];
		int[] commonIntrapairs=new int[clusterNumber(authoritativeClusters)];
		float clusterRecall = 0;
		for (int i=0; i<clusters.length; i++) {
			for (int k=i+1; k<clusters.length; k++) {
				if (authoritativeClusters[i]==authoritativeClusters[k]) {
					clusterIntrapairs[authoritativeClusters[i]]+=1;
					if (clusters[i]==clusters[k]) commonIntrapairs[authoritativeClusters[i]]+=1;
				}		
			}
		}
		for (int i=0; i<clusterNumber(authoritativeClusters);i++) {
			if (clusterIntrapairs[i]!=0) {
				clusterRecall += (float) commonIntrapairs[i]/clusterIntrapairs[i];
			}
 		}
		return clusterRecall/clusterNumber(authoritativeClusters);
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
