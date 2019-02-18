package clustering.evaluation;

import clustering.distance.DistanceFunction;

public class IntraInterDistance extends Evaluation {
	private DistanceFunction distance;
	float intraFactor;
	float interFactor;
	
	public IntraInterDistance (DistanceFunction distance,float intraFactor, float interFactor) {
		this.distance = distance;
		this.interFactor = interFactor;
		this.intraFactor = intraFactor;
	}
	
	@Override
	public float evaluate(int[] clusters, float[][] occurenceTable) {
		double intraDistance = 0;
		double interDistance = 0;
		int intraPairs = 0;
		int interPairs = 0;
		for (int i=0;i<clusters.length;i++) {
			for (int k=i+1;k<clusters.length;k++) {
				if (clusters[i]==clusters[k]) {
					intraDistance += distance.distance(occurenceTable[i], occurenceTable[k]);
					intraPairs++;
				}
				else {
					interDistance += distance.distance(occurenceTable[i], occurenceTable[k]);
					interPairs++;
				}
			}
		}
		return (float)(intraFactor*intraDistance/intraPairs - interFactor*interDistance/interPairs);
	}
	
	@Override
	public String toString() {
		String string = getClass().getSimpleName()+"Intra"+intraFactor+"Inter"+interFactor;
		return string;
	}
}
