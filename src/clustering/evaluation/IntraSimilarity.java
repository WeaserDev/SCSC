package clustering.evaluation;

import java.util.ArrayList;

import clustering.distance.DistanceFunction;

public class IntraSimilarity extends Evaluation {
	private DistanceFunction distance;
	
	public IntraSimilarity(DistanceFunction distance) {
		this.distance = distance;
	}

	public float evaluate(int[] clusters, float[][] occurenceTable) {
		int clusterNumber = clusterNumber(clusters);
		float sum=0;
		int count = 0;
		int total = 0;
		for (int i=0; i<clusterNumber; i++) {
			ArrayList<float[]> members = new ArrayList<float[]>();
			for(int j=0;j<clusters.length;j++)
				if(clusters[j]==i)
					members.add(occurenceTable[j]);
			if(members.size()>1) {
				sum += sim(members);
				count++;
			}
			if(!members.isEmpty())
				total++;
		}
		if(count==0)
			return Float.NEGATIVE_INFINITY;
		return sum/count/(float)Math.pow(total, 0.25);
	}
	
	private float sim(ArrayList<float[]> members) {
		int count = 0;
		float sum = 0;
		for(float[] m1 : members)
			for(float[] m2 : members)
				if(m1!=m2) {
					sum -= distance.distance(m1, m2);
					count++;
				}
		if(count==0)
			return -1;//Float.NEGATIVE_INFINITY;
		return count/sum;
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
