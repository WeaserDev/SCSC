package sourceCodeSemanticClustering;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import clustering.DBSCAN.*;
import clustering.labeling.*;
import dataImport.FileInput;
import featureExtraction.WordModelFeatureExtraction;
import featureExtraction.featureWeight.*;
import featureExtraction.NaiveFeatureExtraction;
import visualization.PrintFile;
import clustering.*;

public class SourceCodeSemanticClustering {

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		HashMap<Integer, String> idFiles = new HashMap<Integer, String>();
		
		File projectDir=new File(("C:\\Users\\Aris\\eclipse-workspace\\Ergasia"));
		FileInput[] fileIn = FileInput.createFileInput(projectDir);
		int size = fileIn.length;
		BinaryInverseDocumentFrequencyWeight bwidf = new BinaryInverseDocumentFrequencyWeight();
		BinaryWeight bw = new BinaryWeight();
		WeightMethod ntfidf = new NormalizedTermFrequencyInverseDocumentFrequencyWeight();
		TermFrequencyWeight tf = new TermFrequencyWeight();
		TermFrequencyInverseDocumentFrequencyWeight tfidf = new TermFrequencyInverseDocumentFrequencyWeight();
		WeightMethod nw = new NormalizedWeight();
		WeightMethod nwidf = new NormalizedInverseDocumentFrequencyWeight();
		WordModelFeatureExtraction features = new WordModelFeatureExtraction(fileIn, tfidf , 1);
		//NaiveFeatureExtraction features = new NaiveFeatureExtraction(fileIn,tfidf);
		int featureNumber = features.getFeatureNumber();
		int fileNumber = features.getFileNumber();
		System.out.println(featureNumber + " x " + fileNumber);
		
		//for (int k=0;k<featureNumber;k++) {
		//	System.out.println("feature: " + features.getIdFeature(k));
		//}
		
		for (int i=0;i<fileNumber;i++) {
			idFiles.put(i, features.getIdFile(i));
		}
		
		//for (int i=0; i<size; i++) {	
			//System.out.println("file name: " + features.getIdFile(i));
			//for (int k=0; k<featureNumber ; k++) {
				//System.out.println("word: " + features.getIdFeature(k) + " :" + features.getOccurenceTable()[i][k]);
				
			//}
		//}
		
		//WekaClusteringCanopy clusterer = new WekaClusteringCanopy(features.getOccurenceTable());
		//WekaClusteringHierarchical clusterer = new WekaClusteringHierarchical(features.getOccurenceTable());
		//WekaClusteringKmeans clusterer = new WekaClusteringKmeans(features.getOccurenceTable(),8);
		//WekaClusteringDBSCAN clusterer = new WekaClusteringDBSCAN(features.getOccurenceTable());
		WekaClusteringXmeans clusterer = new WekaClusteringXmeans(features.getOccurenceTable(),12 , 2);

		int clusters[] = clusterer.returnAssignments();
		Labeling labels = new MostFrequentFeaturesLabeling(features,clusters,5);
		
		//for (int i=0; i<labelTable.length; i++) {	
			//for (int k=0; k<labelTable[0].length ; k++) {
				//System.out.println(labelTable[i][k]);
			//}
		//}
		PrintFile print=new PrintFile(clusters, idFiles,labels.getLabels());
		print.visualize("results\\xModCosineC2_12F1tfidfLabel.txt");
		long endTime = System.nanoTime();
		System.out.println("Took "+((endTime - startTime)/1000000) + " ms"); 

	
	
	}	
}
