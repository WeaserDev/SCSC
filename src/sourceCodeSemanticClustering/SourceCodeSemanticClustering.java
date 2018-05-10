package sourceCodeSemanticClustering;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import dataImport.FileInput;
import featureExtraction.WordModelFeatureExtraction;
import featureWeight.*;
import featureExtraction.NaiveFeatureExtraction;
import visualization.PrintFile;
import clustering.*;

public class SourceCodeSemanticClustering {

	public static void main(String[] args) {
		HashMap<Integer, String> idFiles = new HashMap<Integer, String>();
		
		File projectDir=new File(("C:\\Users\\Aris\\eclipse-workspace\\Ergasia"));
		FileInput[] fileIn = FileInput.createFileInput(projectDir);
		int size = fileIn.length;
		BinaryInverseDocumentFrequencyWeight bwidf = new BinaryInverseDocumentFrequencyWeight();
		BinaryWeight bw = new BinaryWeight();
		TermFrequencyWeight tf = new TermFrequencyWeight();
		TermFrequencyInverseDocumentFrequencyWeight tfidf = new TermFrequencyInverseDocumentFrequencyWeight();
		//WordModelFeatureExtraction features = new WordModelFeatureExtraction(fileIn, tfidf , 1);
		NaiveFeatureExtraction features = new NaiveFeatureExtraction(fileIn,tfidf);
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
		WekaClusteringKmeans clusterer = new WekaClusteringKmeans(features.getOccurenceTable());

		int clusters[] = clusterer.returnAssignments();

		PrintFile print=new PrintFile(clusters, idFiles);
		print.visualize("C:\\results\\kCosineC7Fntfidf.txt");
	}
	
		
}
