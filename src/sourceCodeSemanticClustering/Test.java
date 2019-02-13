package sourceCodeSemanticClustering;

import java.io.File;

import auth.eng.textManager.WordModel;
import clustering.algorithms.OccurenceClustering;
import clustering.algorithms.PackagesToClusters;
import clustering.evaluation.Evaluation;
import clustering.labeling.Labeling;
import dataImport.FileInput;
import dataImport.ProjectInput;
import featureExtraction.WordModelFeatureExtraction;

public class Test {
	public static void main(String[] args) throws Exception {
		String testProjectPath = "./";
		WordModel wordModel = new WordModel.BagOfWords(new auth.eng.textManager.stemmers.InvertibleStemmer(new auth.eng.textManager.stemmers.PorterStemmer()));
		ProjectInput project = new ProjectInput(FileInput.createFileInput(new File(testProjectPath)), "THIS");
		int[] evaluationClusters = (new PackagesToClusters(new File(testProjectPath))).returnClusters();
		
		WordModelFeatureExtraction features = new featureExtraction.WordModelFeatureExtractionReferenceAddedWeight(project.getInput(), new featureExtraction.featureWeight.TermFrequencyInverseDocumentFrequencyWeight(), wordModel, 1, 1,
				0f, 2f);
		
		float regularizeIntraSimilarity = 5;//1 to split to fewer clusters, 5 to split to more clusters
		OccurenceClustering algorithm = new clustering.algorithms.KmeansDynamic(features.getOccurenceTable(), new clustering.evaluation.IntraSimilarity(new clustering.distance.CosineDistance(), regularizeIntraSimilarity), new clustering.distance.CosineDistance(), new clustering.algorithms.kmeansUtils.KmeansInitializationPlusPlusDeterministic(new clustering.distance.CosineDistance(),100));
		//OccurenceClustering algorithm = new clustering.algorithms.TopicsKmeans(features.getOccurenceTable(), 10, 20, new clustering.distance.CosineDistance());
		//OccurenceClustering algorithm = new clustering.algorithms.Kmeans(features.getOccurenceTable(), 20, new clustering.distance.CosineDistance());
		Evaluation[] metrics = {new clustering.evaluation.Precision(evaluationClusters), new clustering.evaluation.Recall(evaluationClusters), new clustering.evaluation.AdjustedPrecision(new File(testProjectPath))};

		int[] clusters = algorithm.returnClusters();
		for(Evaluation metric : metrics) 
			System.out.println(metric.toString()+" : "+metric.evaluate(clusters, null));
		
		Labeling labeling = new clustering.labeling.MostFrequentFeaturesLabeling(features, clusters, 3, new featureExtraction.featureWeight.NoWeight());
		(new visualization.FirefoxVisualizer(clusters, project, labeling.getLabels(), null)).visualize();//show discovered thing
		
	}
}