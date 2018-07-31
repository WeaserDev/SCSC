package clustering.algorithms;

import clustering.distance.DistanceFunction;
import clustering.evaluation.Evaluation;


public class KmeansDynamic extends Kmeans {
	Evaluation evaluation;
	
	public KmeansDynamic(float[][] occurenceTable,Evaluation evaluation, DistanceFunction distanceFunction){
		super(occurenceTable, 0 , distanceFunction);
		this.evaluation = evaluation;
	}
	
	protected int[] createClusters() { 
		int[] result; 
		this.clusterNumber = 10;
		float previousEvaluation = 0; 
		float currentEvaluation = 0; 
		while(true) { 
		result = super.createClusters(); 
		currentEvaluation = evaluation.evaluate(result, occurenceTable); 
		if(previousEvaluation>currentEvaluation) break; 
		previousEvaluation = currentEvaluation; 
		this.clusterNumber++; 
		} 
		return result; 
	}
}
