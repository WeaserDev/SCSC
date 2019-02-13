package clustering.evaluation;

public class NonExtremityClusterDistribution extends Evaluation {
	int lowerLimit;
	int upperLimit;
	
	public NonExtremityClusterDistribution(int lowerLimit,int upperLimit) {
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}
	
	@Override
	public float evaluate(int[] clusters, float[][] occurenceTable) {
		int[] clusterSize = new int[clusterNumber(clusters)];
		int nonExtremeClusterPopulation = 0;
		for (int i=0; i<clusters.length; i++) {
			clusterSize[clusters[i]]++;
		}
		for (int i=0; i<clusterNumber(clusters);i++) {
			if (clusterSize[i]>lowerLimit&&clusterSize[i]<upperLimit) {
				nonExtremeClusterPopulation += clusterSize[i];
			}
		}
		return (float)nonExtremeClusterPopulation/clusters.length;
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


