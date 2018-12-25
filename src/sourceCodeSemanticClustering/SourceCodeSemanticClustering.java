package sourceCodeSemanticClustering;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import auth.eng.textManager.WordModel;
import clustering.labeling.*;
import dataImport.ProjectInput;
import featureExtraction.*;
import featureExtraction.featureWeight.*;
import visualization.PrintFile;
import clustering.algorithms.*;
import clustering.distance.*;
//import weka.core.*;
import clustering.evaluation.*;

public class SourceCodeSemanticClustering {

	public static void main(String[] args) throws IOException {
		long startTime = System.nanoTime();
		String fileName = "results\\ergasiaNewInit.txt";
		String projectPath = "C:\\Users\\Aris\\eclipse-workspace";
		WeightMethod[] weights = {new TermFrequencyInverseDocumentFrequencyWeight(), new TermFrequencyWeight()};
		//DistanceFunction[] distance = {new WekaCosineDistance()};
		DistanceFunction[] distance = {new CosineDistance()};
		int maxFileWeight = 1;
		int fileWeightStep = 5;
		int maxFunctionWeight = 1;
		int functionWeightStep = 5;
		int maxTopics = 70;
		int i=0;
		WordModel wordModel = new WordModel.BagOfWords(new auth.eng.textManager.stemmers.InvertibleStemmer(new auth.eng.textManager.stemmers.PorterStemmer()));
		ProjectInput[] projectIn = ProjectInput.createProjectInput(new File(projectPath));
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		PackagesToClusters pack = new PackagesToClusters(new File(projectPath));		
		int[] packageClusters = pack.returnClusters();
		Precision precision = new Precision(packageClusters);
		Recall recall = new Recall(packageClusters);
		MojoFM mojo = new MojoFM(packageClusters);
		int clusterNumber = clusterNumber(packageClusters);
		

		for (int project=0; project<projectIn.length; project++) {
			for (WeightMethod weight:weights) {
				for (int fileWeight=0;fileWeight<maxFileWeight;fileWeight+=fileWeightStep) {
					for (int functionWeight=0;functionWeight<maxFunctionWeight;functionWeight+=functionWeightStep) {
						if (projectIn[project].getInput().length>0) {
							WordModelFeatureExtraction features = new WordModelFeatureExtractionReferenceAddedWeight(projectIn[project].getInput(), weight, wordModel,fileWeight, functionWeight, 0, 2);
							for (DistanceFunction dist:distance) {
							DocumentDocumentFeatures doc = new featureExtraction.DocumentDocumentFeatures(features.getOccurenceTable(),dist);
							float occurence[][] =  doc.getOccurenceTable();
							
								//for (int topicsNumber=5; topicsNumber < maxTopics; topicsNumber+=5) {
									int times=30;
									float averagePrecision = 0;
									float averageRecall = 0;
									float averageMojoFM = 0;
									float averageTime = 0;
									for (int repeat=0;repeat<times;repeat++) {
										long startTime2=System.nanoTime();	
										OccurenceClustering clusterer = new Kmeans(occurence,clusterNumber, dist, new clustering.algorithms.kmeansUtils.KmeansInitializationPlusPlusDeterministic(dist,100));
										//Clustering clusterer = new TopicsKmeans(features.getOccurenceTable(),topicsNumber,clusterNumber,dist);
										int clusters[] = clusterer.returnClusters();
										long endTime2 = System.nanoTime();
										//writer.write(i+ ":" + projectIn[project].getProjectName() +" " + weight.getClass().getSimpleName() +" "+dist.getClass().getSimpleName() + " " +"file weight:" + fileWeight + " "+ "fun weight:" + functionWeight  + " "  + "precision: " +  precision.evaluate(clusters, null) + " recall:" + recall.evaluate(clusters, null) + " mojoFM:" + mojo.evaluate(clusters, null) + " time:" + ((endTime2 - startTime2)/1000000) + " ms" );							
										//writer.newLine();
										averagePrecision+=precision.evaluate(clusters, null);
										averageRecall+=recall.evaluate(clusters, null);
										averageMojoFM+=mojo.evaluate(clusters, null);
										averageTime += ((endTime2 - startTime2)/1000000);
										//String S = i + ".txt";
										//PrintFile print = new PrintFile(clusters,features.getIdFiles(),new MostFrequentFeaturesLabeling(features,clusters,5,new NoWeight()).getLabels());
										//print.visualize("C:\\Users\\Aris\\eclipse-workspace\\Ergasia\\results\\" + "project60topics" + S);
										i+=1;
									}
									writer.newLine();
									writer.write("Total: "+ ":" + projectIn[project].getProjectName() +" " + weight.getClass().getSimpleName() +" "+dist.getClass().getSimpleName() + " " +"file weight:" + fileWeight + " "+ "fun weight:" + functionWeight  + " topics number: " + "no topics" + " repeated "+ times+" times:" + " Average Precision: " +  averagePrecision/times + " Average Recall:" + averageRecall/times + " Average mojoFM:" + averageMojoFM/times + " Average time:" + averageTime/times + " ms" );							
									writer.newLine();
									writer.newLine();
								//}
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