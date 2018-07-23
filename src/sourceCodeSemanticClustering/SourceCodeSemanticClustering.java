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
import weka.core.DistanceFunction;
import weka.core.EuclideanDistance;
import clustering.algorithms.*;
import clustering.distance.*;
import clustering.evaluation.Evaluation;
import clustering.evaluation.NormalizedEntropy;

public class SourceCodeSemanticClustering {

	public static void main(String[] args) throws IOException {
		String fileName = "experiment.txt";
		String projectPath = "C:\\Users\\Aris\\eclipse-workspace";
		WordModel wordModel = new WordModel.BagOfWords(new auth.eng.textManager.stemmers.InvertibleStemmer(new auth.eng.textManager.stemmers.PorterStemmer()));
		WeightMethod[] weights = {new TermFrequencyInverseDocumentFrequencyWeight(),new TermFrequencyWeight()};
		DistanceFunction[] distance = {new WekaCosineDistance(), new WekaModifiedCosineDistance()};
		int maxFileWeight = 50;
		int fileWeightStep = 10;
		int maxFunctionWeight = 10;
		int functionWeightStep = 2;
		File projectInput = new File(projectPath);
		ProjectInput[] projectIn = ProjectInput.createProjectInput(projectInput);
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		Evaluation entropy = new NormalizedEntropy();
		for (int project=0; project<projectIn.length; project++) {
			for (WeightMethod weight:weights) {
				for (int fileWeight=0;fileWeight<maxFileWeight;fileWeight+=fileWeightStep) {
					for (int functionWeight=0;functionWeight<maxFunctionWeight;functionWeight+=functionWeightStep) {
						if (projectIn[project].getInput().length>0) {	
							for (DistanceFunction dist:distance) {
								WordModelFeatureExtraction features = new WordModelFeatureExtractionAddedWeight(projectIn[project].getInput(), weight, wordModel, fileWeight, functionWeight);
								WekaClusteringKmeansDynamic clusterer = new WekaClusteringKmeansDynamic(features.getOccurenceTable(), new NormalizedEntropy(), dist);
								int clusters[] = clusterer.returnClusters();				
								writer.write(projectIn[project].getProjectName() +" " + weight.getClass()+" "+dist.getClass() + " " +"file weight:" + fileWeight + " "+ "fun weight:" + functionWeight  + " "  +  entropy.evaluate(clusters, null));
								writer.newLine();					
							}
						}
					}
				}
			}			
		}	
	writer.close();		
	}	
}