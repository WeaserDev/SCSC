package sourceCodeSemanticClustering;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import dataImport.FileInput;
import featureExtraction.WordModelFeatureExtraction;
import featureExtraction.NaiveFeatureExtraction;
import visualization.PrintFile;
import clustering.*;

public class SourceCodeSemanticClustering {

	public static void main(String[] args) {
		HashMap<Integer, String> idFiles = new HashMap<Integer, String>();
		
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
		
		for (int i=0;i<fileNumber;i++) {
			idFiles.put(i, features.getIdFile(i));
		}
		
		//for (int i=0; i<size; i++) {	
			//System.out.println("file name: " + features.getIdFile().get(i));
			//for (int k=0; k<wordCount ; k++) {
				//System.out.println("word: " + features.getIdWord().get(k) + " :" + features.getOccurenceTable()[i][k]);
				
			//}
		//}
		
		WekaClusteringCanopy clusterer = new WekaClusteringCanopy(features.getOccurenceTable());
		int clusters[] = clusterer.returnAssignments();

		PrintFile print=new PrintFile(clusters, idFiles);
		print.visualize("CanC5F1.txt");
	}
	
		
}


	

	


