package sourceCodeSemanticClustering;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import auth.eng.textManager.WordModel;
import clustering.labeling.*;
import dataImport.FileInput;
import dataImport.ProjectInput;
import dataImport.ProjectInputWithNameExtension;
import featureExtraction.*;
import featureExtraction.dimensionReduction.LSA;
import featureExtraction.featureWeight.*;
import featureExtraction.featureWeight.globalWeight.InverseDocumentFrequencyWeight;
import featureExtraction.featureWeight.globalWeight.NoGlobalWeight;
import featureExtraction.featureWeight.localWeight.LogWeight;
import featureExtraction.featureWeight.localWeight.TermFrequencyWeight;
import visualization.PrintFile;
import clustering.algorithms.*;
import clustering.distance.*;
//import weka.core.*;
import clustering.evaluation.*;

public class SourceCodeSemanticClustering {

	public static void main(String[] args) throws IOException {
		String projectPath = args[0];
		String[] extensions = new String[args.length-1];
		for (int i=1; i<args.length; i++) {
			extensions[i-1] = args[i];
		}
		ProjectInput project;
		int[] bestClusters = null;
		float bestMojo = 0;
		InputFeatureExtraction bestFeatures = null;
		int repeat = 20;
		WordModel wordModel = new WordModel.BagOfWords(new auth.eng.textManager.stemmers.InvertibleStemmer(new auth.eng.textManager.stemmers.PorterStemmer()));
		if(extensions.length>1) {
			project = new ProjectInputWithNameExtension(new File(projectPath), extensions);
		} else {
			project = new ProjectInput(new File(projectPath), extensions);
		}

		int[] evaluationClusters = (new PackagesToClusters(new File(projectPath), extensions)).returnClusters();
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
		
		int clusterNumber = clusterNumber(evaluationClusters);
		WeightMethod weight;
		
		for (int k=0; k<fileWeights.length;k++) {
			if(k<7) {
				weight = weightMethods[0];
			} else if(k<12) {
				weight = weightMethods[1];
			} else {
				weight = weightMethods[2];
			}	
			WordModelFeatureExtraction feature = new WordModelFeatureExtractionReferenceAddedWeight(project.getInput(), weight, wordModel, fileWeights[k], functionWeights[k],0,2);
			RemoveLowOccurences features = new RemoveLowOccurences(feature, 4);
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
			for (int j=0;j<repeat;j++) {
				OccurenceClustering clusterer = new Kmeans(occurence, clusterNumber, distance, new clustering.algorithms.kmeansUtils.KmeansInitializationPlusPlus(distance));
				int clusters[] = clusterer.returnClusters();
				Evaluation MojoFM = new clustering.evaluation.MojoFM(evaluationClusters);
				if (bestMojo<MojoFM.evaluate(clusters, occurence)) {
					bestMojo = MojoFM.evaluate(clusters, occurence);
					bestClusters = clusters;
					bestFeatures = feature;
				}
			}
		}
		Labeling labeling = new clustering.labeling.MostFrequentFeaturesLabeling(bestFeatures, evaluationClusters, 3, new featureExtraction.featureWeight.WeightMethod(null,null));
		(new visualization.FirefoxVisualizer(bestClusters, project, labeling.getLabels(), evaluationClusters)).visualize();
	}
	private static int clusterNumber(int[] clusters) {
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