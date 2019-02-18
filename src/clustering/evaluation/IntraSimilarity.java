package clustering.evaluation;

import java.util.ArrayList;

import clustering.distance.DistanceFunction;

public class IntraSimilarity extends Evaluation {
	private DistanceFunction distance;
	private float regularize;
	
	public IntraSimilarity(DistanceFunction distance, float regularize) {
		this.distance = distance;
		this.regularize = regularize;
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
			total++;
		}
		if(count==0)
			return Float.NEGATIVE_INFINITY;
		return sum/total;
	}
	
	private float sim(ArrayList<float[]> members) {
		int count = 0;
		float sum = 0;
		for(float[] m1 : members)
			for(float[] m2 : members)
				if(m1!=m2) {
					sum += distance.distance(m1, m2);
					count++;
				}
		if(count==0)
			return 0;//Float.NEGATIVE_INFINITY;
		return (float)Math.exp(-sum/count*regularize);
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
		String string = getClass().getSimpleName()+"Reg"+regularize;
		return string;
	}

}
