package sourceCodeSemanticClustering;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import dataImport.FileInput;
import featureExtraction.WordModelFeatureExtraction;
import featureExtraction.NaiveFeatureExtraction;
import visualization.PrintFile;
import clustering.WekaClusteringKmeans;

public class SourceCodeSemanticClustering {

	public static void main(String[] args) {
		File projectDir=new File(("C:\\Users\\Aris\\eclipse-workspace\\Ergasia"));
		FileInput[] fileIn = FileInput.createFileInput(projectDir);
		int size = fileIn.length;
		WordModelFeatureExtraction features = new WordModelFeatureExtraction(fileIn,1);
		//NaiveFeatureExtraction features = new NaiveFeatureExtraction(fileIn);
		int featureNumber = features.getFeatureNumber();
		int fileNumber = features.getFileNumber();
		System.out.println(featureNumber + " x " + fileNumber);
		
		for (int k=0;k<featureNumber;k++) {
			System.out.println("feature: " + features.getIdFeature(k));
		}
		
		
		
		//for (int i=0; i<size; i++) {	
			//System.out.println("file name: " + features.getIdFile().get(i));
			//for (int k=0; k<wordCount ; k++) {
				//System.out.println("word: " + features.getIdWord().get(k) + " :" + features.getOccurenceTable()[i][k]);
				
			//}
		//}
		
		//WekaClusteringKmeans clusterer = new WekaClusteringKmeans(features.getOccurenceTable());
		//int clusters[] = clusterer.returnAssignments();

		//PrintFile print=new PrintFile(clusters, features.getIdFile());
		//print.visualize("output.txt");
	}
	
		
}


	

	


