package sourceCodeSemanticClustering;

import java.io.File;

import auth.eng.textManager.WordModel;
import clustering.algorithms.Clustering;
import clustering.evaluation.Evaluation;
import clustering.evaluation.PackagesToClusters;
import dataImport.FileInput;
import dataImport.ProjectInput;
import featureExtraction.WordModelFeatureExtraction;

public class Test {
	public static void main(String[] args) throws Exception {
		String testProjectPath = "..\\_repos\\smile";
		WordModel wordModel = new WordModel.BagOfWords(new auth.eng.textManager.stemmers.InvertibleStemmer(new auth.eng.textManager.stemmers.PorterStemmer()));
		ProjectInput project = new ProjectInput(FileInput.createFileInput(new File(testProjectPath)), "THIS");
		int[] evaluationClusters = (new PackagesToClusters()).Clusters(new File(testProjectPath));
		
		WordModelFeatureExtraction features = new featureExtraction.WordModelFeatureExtractionReferenceAddedWeight(project.getInput(), new featureExtraction.featureWeight.TermFrequencyInverseDocumentFrequencyWeight(), wordModel, 1, 1,
				0.5f, 2f);
		
		Clustering algorithm = new clustering.algorithms.KmeansDynamic(features.getOccurenceTable(), new clustering.evaluation.IntraSimilarity(new clustering.distance.CosineDistance()), new clustering.distance.CosineDistance());
		//Clustering algorithm = new clustering.algorithms.TopicsKmeans(features.getOccurenceTable(), evaluationClusters.length, 20, new clustering.distance.CosineDistance());
		Evaluation[] metrics = {new clustering.evaluation.Precision(evaluationClusters), new clustering.evaluation.Recall(evaluationClusters), new clustering.evaluation.MojoFM(evaluationClusters)};
		
		int[] clusters = algorithm.returnClusters();
		for(Evaluation metric : metrics) 
			System.out.println(metric.toString()+" : "+metric.evaluate(clusters, null));
		
		(new visualization.JFrameVisualizer(clusters, project)).visualize();
		
	}
}