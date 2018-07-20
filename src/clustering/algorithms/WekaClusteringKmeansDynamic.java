package clustering.algorithms;

import clustering.evaluation.Evaluation;
import weka.core.DistanceFunction;

public class WekaClusteringKmeansDynamic extends WekaClusteringKmeans {
	Evaluation evaluation;
		
	WekaClusteringKmeansDynamic(float[][] occurenceTable,Evaluation evaluation, DistanceFunction distanceFunction){
		super(occurenceTable,0 , distanceFunction);
		this.evaluation = evaluation;
	}
	
	protected int[] createClusters() { 
		int[] result; 
		this.clusterNumber = 2;
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
