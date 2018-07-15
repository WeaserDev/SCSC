package clustering.evaluation;

public class NormalizedEntropy extends Evaluation {

	
	public float evaluate(int[] clusters, float[][] occurenceTable) {
		int clusterNumber = clusterNumber(clusters);
		double entropy=0;
		for (int i=0; i<clusterNumber; i++) {
			int clusterPoints=0;
			for (int k=0 ; k<clusters.length; k++) {
				if (clusters[k]==i) {
					clusterPoints += 1;
				}	
			}
			double temp = (double)clusterPoints/(double)clusters.length;
			if (temp!=0) {
				entropy += temp * Math.log(temp)/Math.log(1/(double)clusterNumber);
			}
		}
		return (float)entropy;
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
