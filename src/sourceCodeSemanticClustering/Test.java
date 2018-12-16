package sourceCodeSemanticClustering;

import java.io.File;

import auth.eng.textManager.WordModel;
import clustering.algorithms.Clustering;
import clustering.evaluation.Evaluation;
import clustering.evaluation.PackagesToClusters;
import dataImport.FileInput;
import dataImport.ProjectInput;
import featureExtraction.WordModelFeatureExtraction;
import featureExtraction.WordModelFeatureExtractionAddedWeight;
import weka.core.pmml.jaxbbindings.Cluster;

public class Test {
	public static void main(String[] args) throws Exception {
		WordModel wordModel = new WordModel.BagOfWords(new auth.eng.textManager.stemmers.InvertibleStemmer(new auth.eng.textManager.stemmers.PorterStemmer()));
		ProjectInput project = new ProjectInput(FileInput.createFileInput(new File("../_repos/smile")), "THIS");
		int[] evaluationClusters = (new PackagesToClusters()).Clusters(new File("../_repos/smile"));
		
		WordModelFeatureExtraction features = new WordModelFeatureExtractionAddedWeight(project.getInput(), new featureExtraction.featureWeight.TermFrequencyInverseDocumentFrequencyWeight(), wordModel, 1, 1);
		
		//Clustering algorithm = new clustering.algorithms.KmeansDynamic(features.getOccurenceTable(), new clustering.evaluation.IntraSimilarity(new clustering.distance.CosineDistance()), new clustering.distance.CosineDistance());
		Clustering algorithm = new clustering.algorithms.TopicsKmeans(features.getOccurenceTable(), 10, 20, new clustering.distance.CosineDistance());
		Evaluation[] metrics = {new clustering.evaluation.Precision(evaluationClusters), new clustering.evaluation.Recall(evaluationClusters)};
		
		int[] clusters = algorithm.returnClusters();
		for(Evaluation metric : metrics) 
			System.out.println(metric.toString()+" : "+metric.evaluate(clusters, null));
		
		(new visualization.JFrameVisualizer(clusters, project)).visualize();
		
	}
}