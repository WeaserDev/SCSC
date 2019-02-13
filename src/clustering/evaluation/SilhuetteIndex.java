package clustering.evaluation;

import clustering.distance.DistanceFunction;

public class SilhuetteIndex extends Evaluation {
	private DistanceFunction distance;
	
	public SilhuetteIndex(DistanceFunction distance) {
		this.distance = distance;
	}
	
	@Override
	public float evaluate(int[] clusters, float[][] occurenceTable) {
		float silhuette = 0;
		for (int i=0;i<clusters.length;i++) {
			float[] clusterDistance = new float[clusterNumber(clusters)];
			int[] count = new int[clusterNumber(clusters)];
			for (int k=0;k<clusters.length;k++) {
				if(i!=k) {
					clusterDistance[clusters[k]] += distance.distance(occurenceTable[i], occurenceTable[k]);
					count[clusters[k]]++;
				}
			}	
			for (int k=0; k<clusterNumber(clusters);k++) {
				if (count[k]>0) {
					clusterDistance[k] = clusterDistance[k]/count[k];
				} else {
					clusterDistance[k] = Float.POSITIVE_INFINITY;
				}
			}
				int minDistanceIndex = minIndex(clusterDistance, i);
				
				if (count[clusters[i]]>0) {
					if (clusterDistance[clusters[i]]>clusterDistance[minDistanceIndex]) {
						silhuette += (clusterDistance[minDistanceIndex] - clusterDistance[clusters[i]])/clusterDistance[clusters[i]];
					} else {
						silhuette += (clusterDistance[minDistanceIndex] - clusterDistance[clusters[i]])/clusterDistance[minDistanceIndex];
					}
				}		 
			
		}
		return silhuette/clusters.length;
	}
	
	private int minIndex(float[] array, int excludedIndex) {
		float min = Float.POSITIVE_INFINITY;
		int index = -1;
		for (int i=0; i<array.length;i++) {
			if (i!=excludedIndex) {
				if (min>array[i]) {
					min = array[i];
					index = i;
				}	
			}
			
		}
		return index;
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
