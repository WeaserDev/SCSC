package experiments;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import auth.eng.textManager.WordModel;
import clustering.algorithms.Kmeans;
import clustering.algorithms.OccurenceClustering;
import clustering.algorithms.PackagesToClusters;
import clustering.distance.CosineDistance;
import clustering.distance.DistanceFunction;
import clustering.evaluation.Evaluation;
import dataImport.ProjectInput;
import featureExtraction.WordModelFeatureExtraction;
import featureExtraction.WordModelFeatureExtractionReferenceAddedWeight;
import featureExtraction.dimensionReduction.LSA;
import featureExtraction.featureWeight.WeightMethod;
import featureExtraction.featureWeight.globalWeight.*;
import featureExtraction.featureWeight.localWeight.*;

public class TestMethodsKknown extends Experiment {
	
	/**
	 * In this experinment we use Kmeans algorithm with deterministic initialization, k is chosen to be equal to the number of packages of the project.
	 * Features are extracted using WordModelFeatureExtractionReferenceAddedWeight. Testing LSI methods.
	 * @throws IOException 
	 */
	@Override
	public void test(ProjectInput project, WordModel wordModel) throws IOException {
		long startTime = System.nanoTime();
		String fileName = "results\\" + project.getProjectName() + this.getClass().getSimpleName() + "("+ wordModel.getClass().getSimpleName()+")" + ".csv";		
		WeightMethod[] weights = {new WeightMethod(new LogWeight(),new NoGlobalWeight()),new WeightMethod(new TermFrequencyWeight(),new InverseDocumentFrequencyWeight()),new WeightMethod(new NoLocalWeight(),new InverseDocumentFrequencyWeight()),new WeightMethod(new LogWeight(), new InverseDocumentFrequencyWeight())};
		DistanceFunction[] distances = {new CosineDistance()};
		int maxFileWeight = 5;
		int fileWeightStep = 5;
		int maxFunctionWeight = 3;
		int functionWeightStep = 3;
		float referenceWeightStep = 0.5f;
		float maxReferenceWeight = 0;
		int maxClosestCentroids = 1;
		
		
		int i=0;
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		PackagesToClusters pack = new PackagesToClusters(project.getProjectDirectory());
		int[] evaluationClusters = pack.returnClusters();
		int clusterNumber = clusterNumber(evaluationClusters);
	
		

		for (WeightMethod weight:weights) {
			for (float referenceWeight=0; referenceWeight<=maxReferenceWeight; referenceWeight+=referenceWeightStep) {
				for (int fileWeight=0;fileWeight<=maxFileWeight;fileWeight+=fileWeightStep) {
					for (int functionWeight=0;functionWeight<=maxFunctionWeight;functionWeight+=functionWeightStep) {
						WordModelFeatureExtraction features = new WordModelFeatureExtractionReferenceAddedWeight(project.getInput(), weight, wordModel, fileWeight, functionWeight,referenceWeight,2);
						for (DistanceFunction distance:distances) {
							for (int closestCentroids=1; closestCentroids<=maxClosestCentroids;closestCentroids++) {
								float occurence[][] =  features.getOccurenceTable();
								System.out.println("FE complete");
								double rank = Math.pow(occurence.length*occurence[0].length, 0.2);
								int factors = (int) Math.round(rank);
								LSA[] lsis = {new LSA(occurence, factors), new LSA(occurence)};
								System.out.println("Lsis complete");
								float occurences[][][] = {occurence, lsis[0].getDocumentConceptTable(), lsis[0].getLowRankApproximation(), lsis[1].getDocumentConceptTable(),lsis[1].getLowRankApproximation()};
								String occurenceMethods[] = {"initial table", "95% concept", "95% aprox", "n*m^0.2 concept", "n*m^0.2 aprox", "kaiser concept","kaiser aprox"};
								for (int t=0; t<occurences.length;t++) {
									long startTime2=System.nanoTime();	
									OccurenceClustering clusterer = new Kmeans(occurences[t], clusterNumber, distance, new clustering.algorithms.kmeansUtils.KmeansInitializationPlusPlusDeterministic(distance,closestCentroids));
									int clusters[] = clusterer.returnClusters();
									long endTime2 = System.nanoTime();
									Evaluation[] metrics = {new clustering.evaluation.Precision(evaluationClusters), new clustering.evaluation.Recall(evaluationClusters),new clustering.evaluation.Fmeasure(evaluationClusters),new clustering.evaluation.AdjustedPrecision(project.getProjectDirectory()), new clustering.evaluation.AverageClusterPrecision(evaluationClusters),new clustering.evaluation.AverageClusterRecall(evaluationClusters),new clustering.evaluation.AverageClusterAdjustedPrecision(project.getProjectDirectory()), new clustering.evaluation.MojoFM(evaluationClusters),new clustering.evaluation.SilhuetteIndex(new clustering.distance.CosineDistance()),new clustering.evaluation.NonExtremityClusterDistribution(5, 100),new clustering.evaluation.IntraSimilarity(distance, 1),new clustering.evaluation.IntraSimilarity(distance,2),new clustering.evaluation.IntraSimilarity(distance, 3),new clustering.evaluation.IntraSimilarity(distance, 4),new clustering.evaluation.IntraSimilarity(distance, 5), new clustering.evaluation.IntraInterDistance(distance, 1, 1), new clustering.evaluation.AverageClusterIntraInterDistance(distance, 1, 1)};
									if(i==0) {
										ArrayList<String> toPrint = createFirstLine(metrics);
										String print = arrayListToCSVString(toPrint);
										writer.write(print);
										writer.newLine();
									}
									ArrayList<String> toPrint = new ArrayList<String>();
									toPrint.add(String.valueOf(clusterNumber));
									toPrint.add(weight.toString());
									toPrint.add(String.valueOf(referenceWeight));
									toPrint.add(String.valueOf(fileWeight));
									toPrint.add(String.valueOf(functionWeight));
									toPrint.add(distance.getClass().getSimpleName());
									toPrint.add(String.valueOf(closestCentroids));
									toPrint.add(String.valueOf(occurences[t][0].length));
									toPrint.add(occurenceMethods[t]);
									for (Evaluation metric:metrics) {
										toPrint.add(String.valueOf(metric.evaluate(clusters, occurence)));
									}
									toPrint.add(String.valueOf((endTime2-startTime2)/1000000));
									String print = arrayListToCSVString(toPrint);
									writer.write(print);
									writer.newLine();
									
									i+=1;
									System.out.println("clustering complete " + i);
								}
							}
						}
					}
				}			
			}
		}			
			
	writer.close();		
	long endTime = System.nanoTime();
	System.out.println("Took "+((endTime - startTime)/1000000) + " ms");
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
	
	private ArrayList<String> createFirstLine(Evaluation[] metrics){
		ArrayList<String> toPrint = new ArrayList<String>();
		toPrint.add("Cluster Number");
		toPrint.add("Weight Method");
		toPrint.add("Reference Weight (factor 2)");
		toPrint.add("File Weight");
		toPrint.add("Function Weight");
		toPrint.add("Distance Function");
		toPrint.add("Closest Centroids Init");
		toPrint.add("SVD factors");
		toPrint.add("lsi method");
		for (Evaluation metric:metrics) {
			toPrint.add(metric.toString());
		}
		toPrint.add("Time Taken(ms)");
		return toPrint;	
	}
	
}


