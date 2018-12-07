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
//import weka.core.*;
import clustering.distance.WekaCosineDistance;
import clustering.evaluation.Evaluation;
import clustering.evaluation.NormalizedEntropy;
import clustering.evaluation.PackagesToClusters;
import clustering.evaluation.*;


public class SourceCodeSemanticClustering {

	public static void main(String[] args) throws IOException {
		long startTime = System.nanoTime();
		String fileName = "results\\experiment7.txt";
		String projectPath = "C:\\Users\\Aris\\eclipse-workspace\\";
		WeightMethod[] weights = {new TermFrequencyInverseDocumentFrequencyWeight(),new TermFrequencyWeight(),new BinaryWeight()};
		//DistanceFunction[] distance = {new WekaCosineDistance()};
		DistanceFunction[] distance = {new CosineDistance(), new EuclideanDistance(), new DotDistance()};
		int maxFileWeight = 21;
		int fileWeightStep = 10;
		int maxFunctionWeight = 5;
		int functionWeightStep = 2;
		int i=0;
		WordModel wordModel = new WordModel.BagOfWords(new auth.eng.textManager.stemmers.InvertibleStemmer(new auth.eng.textManager.stemmers.PorterStemmer()));
		ProjectInput[] projectIn = ProjectInput.createProjectInput(new File(projectPath));
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		PackagesToClusters pack = new PackagesToClusters();		
		int[] packageClusters = pack.Clusters(new File(projectPath));
		int[] cluster1 = {1, 1, 1, 1, 2,2,2,2,3,3,3,3,4,4,4,4};
		int[] cluster2 = {1,1,4,2,2,2,1,1,3,3,2,2,2,2,3,3};
		int[] cluster3 = {1, 1, 1, 2, 2, 2,3,3,3};
		Precision precision = new Precision(packageClusters);
		Recall recall = new Recall(packageClusters);
		MojoFM mojo = new MojoFM(packageClusters);
		int clusterNumber = clusterNumber(packageClusters);

		for (int project=0; project<projectIn.length; project++) {
			for (WeightMethod weight:weights) {
				for (int fileWeight=0;fileWeight<maxFileWeight;fileWeight+=fileWeightStep) {
					for (int functionWeight=0;functionWeight<maxFunctionWeight;functionWeight+=functionWeightStep) {
						if (projectIn[project].getInput().length>0) {
							WordModelFeatureExtraction features = new WordModelFeatureExtractionAddedWeight(projectIn[project].getInput(), weight, wordModel,fileWeight, functionWeight);
							for (DistanceFunction dist:distance) {
								int times=200;
								float averagePrecision = 0;
								float averageRecall = 0;
								float averageMojoFM = 0;
								float averageTime = 0;
								for (int repeat=0;repeat<times;repeat++) {
									long startTime2=System.nanoTime();	
									Clustering clusterer = new Kmeans(features.getOccurenceTable(),clusterNumber, dist);
									int clusters[] = clusterer.returnClusters();
									long endTime2 = System.nanoTime();
									//writer.write(i+ ":" + projectIn[project].getProjectName() +" " + weight.getClass().getSimpleName() +" "+dist.getClass().getSimpleName() + " " +"file weight:" + fileWeight + " "+ "fun weight:" + functionWeight  + " "  + "precision: " +  precision.evaluate(clusters, null) + " recall:" + recall.evaluate(clusters, null) + " mojoFM:" + mojo.evaluate(clusters, null) + " time:" + ((endTime2 - startTime2)/1000000) + " ms" );							
									//writer.newLine();
									averagePrecision+=precision.evaluate(clusters, null);
									averageRecall+=recall.evaluate(clusters, null);
									averageMojoFM+=mojo.evaluate(clusters, null);
									averageTime += ((endTime2 - startTime2)/1000000);
									//String S = i + ".txt";
									//WordModelFeatureExtraction features2=new WordModelFeatureExtractionAddedWeight(projectIn[project].getInput(), weight,wordModel,40,0);
									//PrintFile print = new PrintFile(clusters,features2.getIdFiles(),new MostFrequentFeaturesLabeling(features,clusters,5,new TermFrequencyInverseDocumentFrequencyWeight()).getLabels());
									//print.visualize("C:\\Users\\Aris\\eclipse-workspace\\Ergasia\\results\\" + S);
									i+=1;
								}
								writer.newLine();
								writer.write("Total: "+ ":" + projectIn[project].getProjectName() +" " + weight.getClass().getSimpleName() +" "+dist.getClass().getSimpleName() + " " +"file weight:" + fileWeight + " "+ "fun weight:" + functionWeight  + " "  + "repeated "+ times+" times:" + " Average Precision: " +  averagePrecision/times + " Average Recall:" + averageRecall/times + " Average mojoFM:" + averageMojoFM/times + " Average time:" + averageTime/times + " ms" );							
								writer.newLine();
								writer.newLine();
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