package clustering.evaluation;

public class Recall extends SupervisedEvaluation {

	public Recall(int[] authoritativeClusters) {
		super(authoritativeClusters);
	}

	public float evaluate(int[] clusters, float occurenceTable[][]) {
		int clusterIntrapairs=0;
		int commonIntrapairs=0;
		for (int i=0; i<clusters.length; i++) {
			for (int k=i+1; k<clusters.length; k++) {
				if (authoritativeClusters[i]==authoritativeClusters[k]) {
					clusterIntrapairs+=1;
					if (clusters[i]==clusters[k]) commonIntrapairs+=1;
				}		
			}
		}
		float recall = (float)commonIntrapairs/clusterIntrapairs;		
		return recall;
	}

}
