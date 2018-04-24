package sourceCodeSemanticClustering;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import dataImport.FileInput;

import featureExtraction.NaiveFeatureExtraction;
import visualization.PrintFile;
import clustering.WekaClusteringKmeans;

public class SourceCodeSemanticClustering {

	public static void main(String[] args) {
		File projectDir=new File(("C:\\Users\\Aris\\eclipse-workspace\\Ergasia"));
		FileInput[] fileIn = FileInput.createFileInput(projectDir);
		int size = fileIn.length;
		NaiveFeatureExtraction features = new NaiveFeatureExtraction(fileIn);
		int wordCount = features.getWordID().size();
		for (int i=0; i<size; i++) {	
			System.out.println("file name: " + features.getIdFile().get(i));
			for (int k=0; k<wordCount ; k++) {
				System.out.println("word: " + features.getIdWord().get(k) + " :" + features.getOccurenceTable()[i][k]);
				
			}
		}
		
		WekaClusteringKmeans clusterer = new WekaClusteringKmeans(features.getOccurenceTable());
		int clusters[] = clusterer.returnAssignments();
		for (int i=0; i<clusters.length ; i++) {
			System.out.println(clusters[i]);
			System.out.println(features.getIdFile().get(i));
		}
		PrintFile print=new PrintFile(clusters, features.getIdFile());
		print.printClusters();
	}
	
		
}


	

	


