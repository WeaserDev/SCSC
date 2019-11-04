package clustering.algorithms;

import clustering.evaluation.Evaluation;
import weka.core.DistanceFunction;
/**
 * Extends the WekaClusteringKmeans class. 
 * Iteratively uses weka's kmeans algorithm for different cluster numbers until it finds the number of clusters that maximizes a given evaluation metric.
 * @author Lefas Aristeidis
 */
public class WekaClusteringKmeansDynamic extends WekaClusteringKmeans {
	Evaluation evaluation;
		
	public WekaClusteringKmeansDynamic(float[][] occurenceTable,Evaluation evaluation, DistanceFunction distanceFunction){
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
