package experiments;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import java.util.HashSet;


public class TestBestMethodsToLatexRef extends Experiment {
	
	/**
	 * In this experinment we use Kmeans algorithm with deterministic initialization, k is chosen to be equal to the number of packages of the project.
	 * Features are extracted using WordModelFeatureExtractionReferenceAddedWeight. Testing LSI methods.
	 * @throws IOException 
	 */
	@Override
	public void test(ProjectInput project, WordModel wordModel, String[] extensions) throws IOException {
		long startTime = System.nanoTime();
		String fileName = "results\\" + project.getProjectName() + this.getClass().getSimpleName() + "("+ wordModel.getClass().getSimpleName()+")" + ".txt";
		WeightMethod[] weightMethods = {new WeightMethod(new LogWeight(), new NoGlobalWeight()), new WeightMethod(new LogWeight(), new InverseDocumentFrequencyWeight()), new WeightMethod(new TermFrequencyWeight(), new InverseDocumentFrequencyWeight())};
		DistanceFunction distance = new CosineDistance();
		int[] fileWeights = {0, 0, 1, 3, 3, 3, 5, 0, 0, 1, 3, 5, 3, 3};
		int[] functionWeights = {1, 3, 3, 1, 3, 3, 5, 1, 5 , 1, 1, 3, 1, 1};
		int closestCentroids = 1;
		HashSet<Integer> lsiMethods = new HashSet<Integer>(); 
		lsiMethods.add(new Integer("1"));
		lsiMethods.add(new Integer("2"));
		lsiMethods.add(new Integer("4"));
		lsiMethods.add(new Integer("6"));
		lsiMethods.add(new Integer("7"));
		lsiMethods.add(new Integer("9"));
		lsiMethods.add(new Integer("12"));
		lsiMethods.add(new Integer("14"));
		int i=0;
		List<ArrayList<String>> toPrint = new ArrayList<ArrayList<String>>();
		for (int z=0; z<4;z++) {
			toPrint.add(new ArrayList<String>());
		}
		
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		PackagesToClusters pack = new PackagesToClusters(project.getProjectDirectory(), extensions);
		int[] evaluationClusters = pack.returnClusters();
		int clusterNumber = clusterNumber(evaluationClusters);
		System.out.println(String.valueOf(clusterNumber));
		WeightMethod weight;
		
		for (int k=0; k<fileWeights.length;k++) {
			if(k<7) {
				weight = weightMethods[0];
			} else if(k<12) {
				weight = weightMethods[1];
			} else {
				weight = weightMethods[2];
			}	
			WordModelFeatureExtraction features = new WordModelFeatureExtractionReferenceAddedWeight(project.getInput(), weight, wordModel, fileWeights[k], functionWeights[k],0.25f,2);
			float occurence[][];

			if(lsiMethods.contains((k+1))) {
				float occur[][] = features.getOccurenceTable();
				double rank = Math.pow(occur.length*occur[0].length, 0.2);
				int factors = (int) Math.round(rank);
				LSA lsi = new LSA(occur,factors);
				occurence = lsi.getDocumentConceptTable();
			} else {
				occurence = features.getOccurenceTable();
			}
			OccurenceClustering clusterer = new Kmeans(occurence, clusterNumber, distance, new clustering.algorithms.kmeansUtils.KmeansInitializationPlusPlusDeterministic(distance,closestCentroids));
			int clusters[] = clusterer.returnClusters();
			Evaluation[] metrics = {new clustering.evaluation.MojoFM(evaluationClusters), new clustering.evaluation.AdjustedPrecision(project.getProjectDirectory(),extensions), new clustering.evaluation.Fmeasure(evaluationClusters), new clustering.evaluation.NonExtremityClusterDistribution(5, 100)};	


			for (int j=0; j<metrics.length;j++) {
				float eval;
				if(j==0) {
					eval = metrics[j].evaluate(clusters, occurence);
				} else {
					eval = metrics[j].evaluate(clusters, occurence)*100;
				}
				int roundedEval = Math.round(eval);
				toPrint.get(j).add(String.valueOf(roundedEval));
			}
	
			i+=1;
			System.out.println("clustering complete " + i);		

		}
	for(ArrayList<String> list:toPrint) {
		String print = arrayListToLatexTableRow(list);
		writer.write(print);
		writer.newLine();
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




