package clustering.evaluation;

import java.util.Arrays;

public class MojoFM extends MojoDistance {

	public MojoFM(int[] authoritativeClusters) {
		super(authoritativeClusters);
	}

	public float evaluate(int[] clusters, float[][] occurenceTable) {
		float mojoDistance = super.evaluate(clusters,occurenceTable);
		int[] clusterSize = new int[authoritativeClusterNumber];
		for (int i=0;i<clusters.length;i++) {
			clusterSize[authoritativeClusters[i]]+=1;
		}
		Arrays.sort(clusterSize);
		int nonEmptyGroupsNumber = 0;
		for (int i=0;i<authoritativeClusterNumber;i++) {
			if(clusterSize[i]>nonEmptyGroupsNumber) nonEmptyGroupsNumber++;
		}
		int maxMojoDistance = clusters.length - nonEmptyGroupsNumber;
		return (1-mojoDistance/maxMojoDistance)*100;
	}
}
