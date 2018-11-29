package clustering.evaluation;

public class Precision extends SupervisedEvaluation{

	public Precision(int[] authoritativeClusters) {
		super(authoritativeClusters);
	}

	public float evaluate(int[] clusters, float occurenceTable[][]) {
		int clusterIntrapairs=0;
		int commonIntrapairs=0;
		for (int i=0; i<clusters.length; i++) {
			for (int k=i+1; k<clusters.length; k++) {
				if (clusters[i]==clusters[k]) {
					clusterIntrapairs+=1;
					if (authoritativeClusters[i]==authoritativeClusters[k]) commonIntrapairs+=1;
				}		
			}
		}
		float precision = (float)commonIntrapairs/clusterIntrapairs;		
		return precision;
	}
}
