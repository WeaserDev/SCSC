package clustering.evaluation;

import clustering.distance.DistanceFunction;

public class AverageClusterIntraInterDistance extends Evaluation {

	private DistanceFunction distance;
	float intraFactor;
	float interFactor;
	
	public AverageClusterIntraInterDistance (DistanceFunction distance,float intraFactor, float interFactor) {
		this.distance = distance;
		this.interFactor = interFactor;
		this.intraFactor = intraFactor;
	}
	
	@Override
	public float evaluate(int[] clusters, float[][] occurenceTable) {
		int clusterNumber = clusterNumber(clusters);
		double[] intraDistance = new double[clusterNumber];
		double[] interDistance = new double[clusterNumber];
		double averageIntraDistance = 0;
		double averageInterDistance = 0;
		int[] intraPairs = new int[clusterNumber];
		int[] interPairs = new int[clusterNumber];
		for (int i=0;i<clusters.length;i++) {
			for (int k=i+1;k<clusters.length;k++) {
				if (clusters[i]==clusters[k]) {
					intraDistance[clusters[i]] += distance.distance(occurenceTable[i], occurenceTable[k]);
					intraPairs[clusters[i]]++;
				}
				else {
					interDistance[clusters[i]] += distance.distance(occurenceTable[i], occurenceTable[k]);
					interDistance[clusters[k]] += distance.distance(occurenceTable[i], occurenceTable[k]);
					interPairs[clusters[i]]++;
					interPairs[clusters[k]]++;
				}
			}
		}
		for (int i=0;i<clusterNumber;i++) {
			if (intraPairs[i]>0) {
				intraDistance[i] = intraDistance[i]/intraPairs[i];
			}
			if (interPairs[i]>0) {
				interDistance[i] = interDistance[i]/interPairs[i];
			}
			averageIntraDistance += intraDistance[i];
			averageInterDistance += interDistance[i];
			
		}
		
		return (float)(intraFactor*averageIntraDistance/clusterNumber - interFactor*averageInterDistance/clusterNumber);
		
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
	
	@Override
	public String toString() {
		String string = getClass().getSimpleName()+"Intra"+intraFactor+"Inter"+interFactor;
		return string;
	}

}
