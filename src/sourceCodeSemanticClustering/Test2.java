package sourceCodeSemanticClustering;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import auth.eng.textManager.WordModel;
import clustering.algorithms.OccurenceClustering;
import clustering.algorithms.TopicsKmeans;
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

		WordModelFeatureExtraction features = new WordModelFeatureExtractionReferenceAddedWeight(projectIn[0].getInput(), new NoWeight(), wordModel,10,10,0.5f ,2);
		float occurence[][] = features.getOccurenceTable();
		for (int i=0; i< occurence.length;i++) {
			System.out.print("file" + i + " ");
			for (int k=0; k<occurence[0].length;k++) {
				System.out.print(occurence[i][k] + " ");
				
			}
		}		
		OccurenceClustering clusterer = new clustering.algorithms.Kmeans(features.getOccurenceTable(),2, new clustering.distance.CosineDistance());
		int clusters[] = clusterer.returnClusters();
		HashMap<Integer,ArrayList<String>> map =  new clustering.labeling.MostFrequentFeaturesLabeling(features,clusters,2,new NoWeight()).getLabels();
		System.out.println(map.get(clusters[0]));
		System.out.println(features.getIdFile(0));
	}

}
