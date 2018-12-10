package sourceCodeSemanticClustering;

import java.io.File;

import auth.eng.textManager.WordModel;
import clustering.algorithms.Clustering;
import clustering.algorithms.Kmeans;
import clustering.evaluation.Evaluation;
import clustering.evaluation.PackagesToClusters;
import dataImport.FileInput;
import dataImport.ProjectInput;
import featureExtraction.WordModelFeatureExtraction;
import featureExtraction.WordModelFeatureExtractionAddedWeight;
import featureExtraction.featureWeight.BinaryWeight;
import featureExtraction.featureWeight.TermFrequencyInverseDocumentFrequencyWeight;
import featureExtraction.featureWeight.TermFrequencyWeight;
import featureExtraction.featureWeight.WeightMethod;

public class Test {
	public static void main(String[] args) throws Exception {
		WordModel wordModel = new WordModel.BagOfWords(new auth.eng.textManager.stemmers.InvertibleStemmer(new auth.eng.textManager.stemmers.PorterStemmer()));
		ProjectInput project = new ProjectInput(FileInput.createFileInput(new File(".\\")), "This Project");
		int[] evaluationClusters = (new PackagesToClusters()).Clusters(new File(".\\"));
		
		WordModelFeatureExtraction features = new WordModelFeatureExtractionAddedWeight(project.getInput(), new featureExtraction.featureWeight.TermFrequencyInverseDocumentFrequencyWeight(), wordModel, 0, 0);
		
		Clustering algorithm = new clustering.algorithms.KmeansDynamic(features.getOccurenceTable(), new clustering.evaluation.IntraSimilarity(new clustering.distance.CosineDistance()), new clustering.distance.CosineDistance());
		Evaluation[] metrics = {new clustering.evaluation.Precision(evaluationClusters), new clustering.evaluation.Recall(evaluationClusters)};
		
		int[] clusters = algorithm.returnClusters();
		for(Evaluation metric : metrics) 
			System.out.println(metric.toString()+" : "+metric.evaluate(evaluationClusters, null));
		
		(new visualization.JFrameVisualizer(evaluationClusters, project)).visualize();
		
	}
}