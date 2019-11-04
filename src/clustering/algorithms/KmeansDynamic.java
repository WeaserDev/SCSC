package clustering.algorithms;

import java.util.Arrays;

import clustering.algorithms.kmeansUtils.KmeansInitialization;
import clustering.distance.DistanceFunction;
import clustering.evaluation.Evaluation;

/**
 * Extends the Kmeans class. 
 * Iteratively uses the kmeans algorithm for different cluster numbers until it finds the number of clusters that maximizes a given evaluation metric.
 * @author Lefas Aristeidis
 */
public class KmeansDynamic extends Kmeans {
	Evaluation evaluation;
	/**
	 * A simplified constructor for the initialization of kmeans class.
	 * The initialization is done with the deterministic version of kmeans++ initialization.
	 * @param occurenceTable The document-term table. occurenceTable(i,j) value represents the relevant importance of term j for document i.
	 * @param evaluation The evaluation metric we want to maximize.
	 * @param distance The distance function that will be used to calculate the distance between entities.
	 */
	public KmeansDynamic(float[][] occurenceTable, Evaluation evaluation, DistanceFunction distanceFunction){
		super(occurenceTable, 0 , distanceFunction);
		this.evaluation = evaluation;
	}
	/**
	 * The basic constructor for the initialization of KmeansDynamic class.
	 * @param occurenceTable The document-term table. occurenceTable(i,j) value represents the relevant importance of term j for document i.
	 * @param evaluation The evaluation metric we want to maximize.
	 * @param distance The distance function that will be used to calculate the distance between entities.
	 * @param initialize The k-means initialization object is used to initialize the algorithm
	 * @see KmeansInitialization
	 */
	public KmeansDynamic(float[][] occurenceTable, Evaluation evaluation, DistanceFunction distanceFunction, KmeansInitialization initialize) {
		super(occurenceTable, 0, distanceFunction, initialize);
		this.evaluation = evaluation;
	}
	
	protected int[] createClusters() { 
		int[] bestResult = null; 
		float bestEvaluation = Float.NEGATIVE_INFINITY;
		float prevEvaluation = bestEvaluation;
		int bestNum = 0;
		for(int num=2;num<occurenceTable.length/2;num++){ 
			this.clusterNumber = num;
			int[] result = super.createClusters(); 
			float currentEvaluation = evaluation.evaluate(result, occurenceTable);
			System.out.println(num + " " + currentEvaluation);
			//if(currentEvaluation>bestEvaluation)
				//break;
//			if(Float.isInfinite(currentEvaluation) || (num>5 && Math.abs(currentEvaluation-prevEvaluation)<=0))
//				break;
			prevEvaluation = currentEvaluation;
			if(currentEvaluation>bestEvaluation) {
				bestEvaluation = currentEvaluation;
				bestResult = result;
				bestNum = num;
			}
		} 
		System.out.println("Selected "+bestNum + " " + bestEvaluation);
		return bestResult; 
	}
}
