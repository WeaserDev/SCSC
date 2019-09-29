package experiments;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import auth.eng.textManager.WordModel;
import clustering.algorithms.Kmeans;
import clustering.algorithms.MergePackagesToClusters;
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

public class TestMethodsToLatex extends Experiment {
	
	/**
	 * In this experinment we use Kmeans algorithm with deterministic initialization, k is chosen to be equal to the number of packages of the project.
	 * Features are extracted using WordModelFeatureExtractionReferenceAddedWeight. Testing LSI methods.
	 * @throws IOException 
	 */
	@Override
	public void test(ProjectInput project, WordModel wordModel, String[] extensions) throws IOException {
		long startTime = System.nanoTime();
		String fileName = "results\\" + project.getProjectName() + this.getClass().getSimpleName() + "("+ wordModel.getClass().getSimpleName()+")" + ".txt";
		LocalWeightMethod[] localWeights = {new LogWeight(), new TermFrequencyWeight(), new NoLocalWeight()};
		String[] localNames = {"log-tf", "tf", "None"};
		GlobalWeightMethod[] globalWeights = {new InverseDocumentFrequencyWeight(), new NoGlobalWeight()};
		String[] globalNames = {"idf", "None"};
		DistanceFunction distance = new CosineDistance();
		int[] fileWeights = {0, 1, 3, 5};
		int[] functionWeights = {0, 1, 3, 5};
		int closestCentroids = 1;
		int i=0;
		String occurenceMethods[] = {"document-term", "document-concept", "low rank approx"};
		
		
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		PackagesToClusters pack = new PackagesToClusters(project.getProjectDirectory(), extensions);
		int[] evaluationClusters = pack.returnClusters();
		int clusterNumber = clusterNumber(evaluationClusters);
	
		

		for (int k=0; k<localWeights.length;k++) {
			for (int j=0; j<globalWeights.length;j++) {
				WeightMethod weight = new WeightMethod(localWeights[k], globalWeights[j]);
				for (int fileWeight : fileWeights) {
					for (int functionWeight : functionWeights) {
						WordModelFeatureExtraction features = new WordModelFeatureExtractionReferenceAddedWeight(project.getInput(), weight, wordModel, fileWeight, functionWeight,0,2);
						float occurence[][] =  features.getOccurenceTable();
						double rank = Math.pow(occurence.length*occurence[0].length, 0.2);
						int factors = (int) Math.round(rank);
						LSA lsi = new LSA(occurence,factors);
						float occurences[][][] = {occurence, lsi.getDocumentConceptTable(), lsi.getLowRankApproximation()};	
						for (int t=0; t<occurences.length; t++) {						
							OccurenceClustering clusterer = new Kmeans(occurences[t], clusterNumber, distance, new clustering.algorithms.kmeansUtils.KmeansInitializationPlusPlusDeterministic(distance,closestCentroids));
							int clusters[] = clusterer.returnClusters();
							Evaluation[] metrics = {new clustering.evaluation.MojoFM(evaluationClusters), new clustering.evaluation.AdjustedPrecision(project.getProjectDirectory(),extensions), new clustering.evaluation.Fmeasure(evaluationClusters), new clustering.evaluation.NonExtremityClusterDistribution(5, 100)};	
							ArrayList<String> toPrint = new ArrayList<String>();
							toPrint.add(localNames[k]);
							toPrint.add(globalNames[j]);
							toPrint.add(String.valueOf(fileWeight));
							toPrint.add(String.valueOf(functionWeight));
							toPrint.add(occurenceMethods[t]);
							for (Evaluation metric:metrics) {
								float eval;
								if(metric.toString().equals("MojoFM")) {
									eval = metric.evaluate(clusters, occurence);
								} else {
									eval = metric.evaluate(clusters, occurence)*100;
								}
								int roundedEval = Math.round(eval);
								toPrint.add(String.valueOf(roundedEval));
							}
							String print = arrayListToLatexTableRow(toPrint);
							writer.write(print);
							writer.newLine();	
							writer.flush();
							i+=1;
							System.out.println("clustering complete " + i);		
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
	
}



