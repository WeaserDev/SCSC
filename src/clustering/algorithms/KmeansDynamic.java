package clustering.algorithms;

import java.util.Arrays;

import clustering.distance.DistanceFunction;
import clustering.evaluation.Evaluation;


public class KmeansDynamic extends Kmeans {
	Evaluation evaluation;
	
	public KmeansDynamic(float[][] occurenceTable, Evaluation evaluation, DistanceFunction distanceFunction){
		super(occurenceTable, 0 , distanceFunction);
		this.evaluation = evaluation;
	}
	
	protected int[] createClusters() { 
		int[] bestResult = null; 
		float bestEvaluation = Float.NEGATIVE_INFINITY; 
		int bestNum = 0;
		for(int num=2;num<occurenceTable.length/2;num++){ 
			this.clusterNumber = num;
			int[] result = super.createClusters(); 
			float currentEvaluation = evaluation.evaluate(result, occurenceTable);
			System.out.println(num + " " + currentEvaluation);
			//if(currentEvaluation>bestEvaluation)
				//break;
			if(currentEvaluation>bestEvaluation) {
				bestEvaluation = currentEvaluation;
				bestResult = result;
				bestNum = num;
			}
			if(Float.isInfinite(currentEvaluation))
				break;
		} 
		System.out.println("Selected "+bestNum + " " + bestEvaluation);
		return bestResult; 
	}
}
