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
import clustering.evaluation.AccumulativeEvaluation;
import clustering.evaluation.Evaluation;
import dataImport.ProjectInput;
import featureExtraction.WordModelFeatureExtraction;
import featureExtraction.WordModelFeatureExtractionReferenceAddedWeight;
import featureExtraction.featureWeight.TermFrequencyInverseDocumentFrequencyWeight;
import featureExtraction.featureWeight.TermFrequencyWeight;
import featureExtraction.featureWeight.WeightMethod;

public class KmeansRandomInitKKnown extends Experiment {

	/**
	 * In this experinment we use Kmeans algorithm with random initialization, k is chosen to be equal to the number of packages of the project.
	 * Features are extracted using WordModelFeatureExtractionReferenceAddedWeight. For each setup clustering is repeated a number of times.
	 * @throws IOException 
	 */
	@Override
	public void test(ProjectInput project, WordModel wordModel) throws IOException {
		long startTime = System.nanoTime();
		String fileName = "results\\" + project.getProjectName() + this.getClass().getSimpleName() + "("+ wordModel.getClass().getSimpleName()+")" + ".csv";
		WeightMethod[] weights = {new TermFrequencyInverseDocumentFrequencyWeight(), new TermFrequencyWeight()};
		DistanceFunction[] distances = {new CosineDistance()};
		int maxFileWeight = 2;
		int fileWeightStep = 1;
		int maxFunctionWeight = 2;
		int functionWeightStep = 1;
		float referenceWeightStep = 0.25f;
		float maxReferenceWeight = 0.5f;
		int repeated = 10;
		
		
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
							float occurence[][] =  features.getOccurenceTable();
							Evaluation[] metrics = {new clustering.evaluation.Precision(evaluationClusters), new clustering.evaluation.Recall(evaluationClusters),new clustering.evaluation.Fmeasure(evaluationClusters),new clustering.evaluation.AdjustedPrecision(project.getProjectDirectory()), new clustering.evaluation.AverageClusterPrecision(evaluationClusters),new clustering.evaluation.AverageClusterRecall(evaluationClusters),new clustering.evaluation.AverageClusterAdjustedPrecision(project.getProjectDirectory()), new clustering.evaluation.MojoFM(evaluationClusters),new clustering.evaluation.SilhuetteIndex(new clustering.distance.CosineDistance()),new clustering.evaluation.NonExtremityClusterDistribution(5, 100),new clustering.evaluation.IntraSimilarity(distance, 1),new clustering.evaluation.IntraSimilarity(distance,2),new clustering.evaluation.IntraSimilarity(distance, 3),new clustering.evaluation.IntraSimilarity(distance, 4),new clustering.evaluation.IntraSimilarity(distance, 5), new clustering.evaluation.IntraInterDistance(distance, 1, 1), new clustering.evaluation.AverageClusterIntraInterDistance(distance, 1, 1)};
							AccumulativeEvaluation[] acMetrics = new AccumulativeEvaluation[metrics.length];
							for (int ev=0;ev<metrics.length;ev++) {
								acMetrics[ev] = new AccumulativeEvaluation(metrics[ev]);
							}
							if(i==0) {
								ArrayList<String> toPrint = createFirstLine(metrics);
								String print = arrayListToCSVString(toPrint);
								writer.write(print);
								writer.newLine();
							}
							long startTime2=System.nanoTime();	
							for (int times=0;times<repeated;times++) {
								OccurenceClustering clusterer = new Kmeans(occurence,clusterNumber, distance, new clustering.algorithms.kmeansUtils.KmeansInitializationPlusPlus(distance));
								int clusters[] = clusterer.returnClusters();
								for (AccumulativeEvaluation metric:acMetrics) {
									metric.evaluate(clusters, occurence);
								}
							}
							long endTime2 = System.nanoTime();
							
							ArrayList<String> toPrint = new ArrayList<String>();
							toPrint.add(String.valueOf(clusterNumber));
							toPrint.add(weight.getClass().getSimpleName());
							toPrint.add(String.valueOf(referenceWeight));
							toPrint.add(String.valueOf(fileWeight));
							toPrint.add(String.valueOf(functionWeight));
							toPrint.add(distance.getClass().getSimpleName());
							toPrint.add(String.valueOf(repeated));
							for (AccumulativeEvaluation metric:acMetrics) {
								toPrint.add(String.valueOf(metric.getCurrentResults()));
								toPrint.add(String.valueOf(metric.getBestResults()) + " (" + String.valueOf(metric.getBestScoreRun()) + ")");
							}
							toPrint.add(String.valueOf((endTime2-startTime2)/1000000/repeated));
							String print = arrayListToCSVString(toPrint);
							writer.write(print);
							writer.newLine();
							System.out.println("Completed " + i + "th experiment");
							
							i+=1;
							
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
		toPrint.add("Times Repeated");
		for (Evaluation metric:metrics) {
			toPrint.add(metric.toString());
			toPrint.add("Best " + metric.toString());
		}
		toPrint.add("Time Taken(ms)");
		return toPrint;	
	}
	
}
