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
import clustering.distance.ModifiedCosineDistance;
import clustering.evaluation.Evaluation;
import dataImport.ProjectInput;
import featureExtraction.WordModelFeatureExtraction;
import featureExtraction.WordModelFeatureExtractionReferenceAddedWeight;
import featureExtraction.dimensionReduction.LSA;
import featureExtraction.featureWeight.WeightMethod;
import featureExtraction.featureWeight.globalWeight.*;
import featureExtraction.featureWeight.localWeight.*;

public class TestFinalMethodsKknown extends Experiment {
	
	/**
	 * In this experinment we use Kmeans algorithm with deterministic initialization, k is chosen to be equal to the number of packages of the project.
	 * Features are extracted using WordModelFeatureExtractionReferenceAddedWeight. Testing LSI methods.
	 * @throws IOException 
	 */
	@Override
	public void test(ProjectInput project, WordModel wordModel, String[] extensions) throws IOException {
		long startTime = System.nanoTime();
		String fileName = "results\\" + project.getProjectName() + this.getClass().getSimpleName() + "("+ wordModel.getClass().getSimpleName()+")" + ".csv";		
		WeightMethod[] weights = {new WeightMethod(new LogWeight(),new InverseDocumentFrequencyWeight()),new WeightMethod(new LogWeight(),new NoGlobalWeight()), new WeightMethod(new LogWeight(),new NoGlobalWeight()), new WeightMethod(new TermFrequencyWeight(),new InverseDocumentFrequencyWeight()),new WeightMethod(new LogWeight(), new NoGlobalWeight()),new WeightMethod(new NoLocalWeight(),new InverseDocumentFrequencyWeight()),new WeightMethod(new LogWeight(),new InverseDocumentFrequencyWeight()),new WeightMethod(new LogWeight(),new InverseDocumentFrequencyWeight())};
		DistanceFunction[] distances = {new CosineDistance(), new CosineDistance(),new CosineDistance(),new CosineDistance(),new ModifiedCosineDistance(), new CosineDistance(),new CosineDistance()};
		int[] fileWeights = {0, 5, 10, 5, 10, 5, 5, 1};
		int[] functionWeights = {0, 3, 0, 0, 3, 0,0,1};
		float[] referenceWeights = {0f, 0f, 0f, 0f, 0.5f, 0f,0f,0f};
		int closestCentroids = 1;
		int i=0;
		String occurenceMethods[] = {"kaiser concept", "n*m^0.2 concept", "95% concept", "initial table", "n*m^0.2 concept", "initial table","95% concept","kaiser concept(m2)"};
		
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		PackagesToClusters pack = new PackagesToClusters(project.getProjectDirectory(), extensions);
		int[] evaluationClusters = pack.returnClusters();
		int clusterNumber = clusterNumber(evaluationClusters);
	
		

		for (int k=0; k<weights.length;k++) {
			WordModelFeatureExtraction features = new WordModelFeatureExtractionReferenceAddedWeight(project.getInput(), weights[k], wordModel, fileWeights[k], functionWeights[k],referenceWeights[k],2);
			float occurence[][] =  features.getOccurenceTable();
			if(k==0) {
				LSA lsi = new LSA(occurence);
				occurence = lsi.getDocumentConceptTable();
			} else if(k==1) {
				double rank = Math.pow(occurence.length*occurence[0].length, 0.2);
				int factors = (int) Math.round(rank);
				LSA lsi = new LSA(occurence,factors);
				occurence = lsi.getDocumentConceptTable();
			} else if(k==2) {
				LSA lsi = new LSA(occurence, 95.0f);
				occurence = lsi.getDocumentConceptTable();
			} else if(k==4) {
				double rank = Math.pow(occurence.length*occurence[0].length, 0.2);
				int factors = (int) Math.round(rank);
				LSA lsi = new LSA(occurence, factors);
				occurence = lsi.getLowRankApproximation();
			} else if (k==6) {
				LSA lsi = new LSA(occurence, 95.0f);
				occurence = lsi.getDocumentConceptTable();
			} else if(k==7) {
				LSA lsi = new LSA(occurence);
				occurence = lsi.getDocumentConceptTable();
			}
			long startTime2=System.nanoTime();	
			OccurenceClustering clusterer = new Kmeans(occurence, clusterNumber, distances[k], new clustering.algorithms.kmeansUtils.KmeansInitializationPlusPlusDeterministic(distances[k],closestCentroids));
				int clusters[] = clusterer.returnClusters();
				long endTime2 = System.nanoTime();
				Evaluation[] metrics = {new clustering.evaluation.Precision(evaluationClusters), new clustering.evaluation.Recall(evaluationClusters),new clustering.evaluation.Fmeasure(evaluationClusters),new clustering.evaluation.AdjustedPrecision(project.getProjectDirectory(),extensions), new clustering.evaluation.AverageClusterPrecision(evaluationClusters),new clustering.evaluation.AverageClusterRecall(evaluationClusters),new clustering.evaluation.AverageClusterAdjustedPrecision(project.getProjectDirectory(), extensions), new clustering.evaluation.MojoFM(evaluationClusters),new clustering.evaluation.SilhuetteIndex(new clustering.distance.CosineDistance()),new clustering.evaluation.NonExtremityClusterDistribution(5, 100),new clustering.evaluation.IntraSimilarity(distances[k], 1),new clustering.evaluation.IntraSimilarity(distances[k],2),new clustering.evaluation.IntraSimilarity(distances[k], 3),new clustering.evaluation.IntraSimilarity(distances[k], 4),new clustering.evaluation.IntraSimilarity(distances[k], 5), new clustering.evaluation.IntraInterDistance(distances[k], 1, 1), new clustering.evaluation.AverageClusterIntraInterDistance(distances[k], 1, 1)};
				if(i==0) {
					ArrayList<String> toPrint = createFirstLine(metrics);
					String print = arrayListToCSVString(toPrint);
					writer.write(print);
					writer.newLine();
				}
				ArrayList<String> toPrint = new ArrayList<String>();
				toPrint.add(String.valueOf(clusterNumber));
				toPrint.add(weights[k].toString());
				toPrint.add(String.valueOf(referenceWeights[k]));
				toPrint.add(String.valueOf(fileWeights[k]));
				toPrint.add(String.valueOf(functionWeights[k]));
				toPrint.add(distances[k].getClass().getSimpleName());
				toPrint.add(String.valueOf(closestCentroids));
				toPrint.add(String.valueOf(occurence[0].length));
				toPrint.add(occurenceMethods[k]);
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


