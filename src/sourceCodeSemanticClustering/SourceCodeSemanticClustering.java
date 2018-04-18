package sourceCodeSemanticClustering;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import dataImport.FileInput;

import featureExtraction.FeatureExtraction;

public class SourceCodeSemanticClustering {

	public static void main(String[] args) {
		File projectDir=new File(("c:\\project"));
		FileInput[] fileIn = FileInput.createFileInput(projectDir);
		int size = fileIn.length;
		FeatureExtraction features = new FeatureExtraction(fileIn);
		int wordCount = features.wordID.size();
		for (int i=0; i<size; i++) {	
			System.out.println("file name: " + features.idFile.get(i));
			for (int k=0; k<wordCount ; k++) {
				System.out.println("word: " + features.idWord.get(k) + " :" + features.occurenceTable[i][k]);
				
			}

			
			
		
		}
	}
	
		
}


	

	


