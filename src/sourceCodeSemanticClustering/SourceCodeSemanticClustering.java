package sourceCodeSemanticClustering;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import auth.eng.textManager.WordModel;
import clustering.DBSCAN.*;
import clustering.labeling.*;
import dataImport.FileInput;
import dataImport.ProjectInput;
import featureExtraction.*;
import featureExtraction.featureWeight.*;
import featureExtraction.NaiveFeatureExtraction;
import visualization.PrintFile;
import clustering.algorithms.*;
import clustering.distance.*;
import clustering.distance.WekaCosineDistance;
import clustering.evaluation.Evaluation;
import clustering.evaluation.NormalizedEntropy;
import clustering.evaluation.PackagesToClusters;
import clustering.evaluation.Precision;


public class SourceCodeSemanticClustering {

	public static void main(String[] args) throws IOException {
		long startTime = System.nanoTime();
		String fileName = "results\\experiment.txt";
		String projectPath = "C:\\Users\\Aris\\eclipse-workspace\\";
		WeightMethod[] weights = {new TermFrequencyInverseDocumentFrequencyWeight(),new TermFrequencyWeight(),new BinaryWeight()};
		//DistanceFunction[] distance = {new WekaCosineDistance()};
		DistanceFunction[] distance = {new CosineDistance()};
		int maxFileWeight = 21;
		int fileWeightStep = 10;
		int maxFunctionWeight = 5;
		int functionWeightStep = 2;
		int i=0;
		WordModel wordModel = new WordModel.BagOfWords(new auth.eng.textManager.stemmers.InvertibleStemmer(new auth.eng.textManager.stemmers.PorterStemmer()));
		ProjectInput[] projectIn = ProjectInput.createProjectInput(new File(projectPath));
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		Evaluation entropy = new NormalizedEntropy();
		PackagesToClusters pack = new PackagesToClusters();
		Precision precision = new Precision();
		int[] packageClusters = pack.Clusters(new File(projectPath));
		for (int project=0; project<projectIn.length; project++) {
			for (WeightMethod weight:weights) {
				for (int fileWeight=0;fileWeight<maxFileWeight;fileWeight+=fileWeightStep) {
					for (int functionWeight=0;functionWeight<maxFunctionWeight;functionWeight+=functionWeightStep) {
						if (projectIn[project].getInput().length>0) {	
							WordModelFeatureExtraction features = new WordModelFeatureExtractionAddedWeight(projectIn[project].getInput(), weight, wordModel,fileWeight, functionWeight);
							for (DistanceFunction dist:distance) {
								Clustering clusterer = new Kmeans(features.getOccurenceTable(),13, dist);
								int clusters[] = clusterer.returnClusters();				
								writer.write(i+ ":" + projectIn[project].getProjectName() +" " + weight.getClass().getSimpleName() +" "+dist.getClass().getSimpleName() + " " +"file weight:" + fileWeight + " "+ "fun weight:" + functionWeight  + " "  + "precision: " +  precision.evaluate(clusters, packageClusters) + " recall:" + precision.evaluate(packageClusters, clusters));
								writer.newLine();
								String S = i + ".txt";
								PrintFile print = new PrintFile(clusters,features.getIdFiles(),new MostFrequentFeaturesLabeling(new WordModelFeatureExtractionAddedWeight(projectIn[project].getInput(), new NoWeight(),wordModel,40,0),clusters,3,new TermFrequencyInverseDocumentFrequencyWeight()).getLabels());
								print.visualize("C:\\Users\\Aris\\eclipse-workspace\\Ergasia\\results\\" + S);
								i+=1;
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
}