package sourceCodeSemanticClustering;

import java.io.File;

import auth.eng.textManager.WordModel;
import dataImport.ProjectInput;
import featureExtraction.WordModelFeatureExtraction;
import featureExtraction.WordModelFeatureExtractionAddedWeight;
import featureExtraction.WordModelFeatureExtractionReferenceAddedWeight;
import featureExtraction.featureWeight.*;

public class Test2 {

	public static void main(String[] args) {
		String projectPath = "C:\\test\\";
		ProjectInput[] projectIn = ProjectInput.createProjectInput(new File(projectPath));
		WordModel wordModel = new WordModel.BagOfWords(new auth.eng.textManager.stemmers.InvertibleStemmer(new auth.eng.textManager.stemmers.PorterStemmer()));

		WordModelFeatureExtraction features = new WordModelFeatureExtractionReferenceAddedWeight(projectIn[0].getInput(), new TermFrequencyInverseDocumentFrequencyWeight(), wordModel,0,0, 0.5f ,2);
		float occurence[][] = features.getOccurenceTable();
		for (int i=0; i< occurence.length;i++) {
			System.out.print("file" + i + " ");
			for (int k=0; k<occurence[0].length;k++) {
				System.out.print(occurence[i][k] + " ");
				
			}
		}

	}

}
