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
import featureExtraction.featureWeight.WeightMethod;

public class ValidateProject {
	public static void main(String[] args) throws Exception {
		String testProjectPath = "./";
		WordModel wordModel = new WordModel.BagOfWords(new auth.eng.textManager.stemmers.InvertibleStemmer(new auth.eng.textManager.stemmers.PorterStemmer()));
		ProjectInput project = new ProjectInput(new File(testProjectPath));
		int[] evaluationClusters = (new PackagesToClusters(new File(testProjectPath))).returnClusters();
		
		WordModelFeatureExtraction features = new featureExtraction.WordModelFeatureExtractionReferenceAddedWeight(project.getInput(), new featureExtraction.featureWeight.WeightMethod(new featureExtraction.featureWeight.localWeight.TermFrequencyWeight(), new featureExtraction.featureWeight.globalWeight.InverseDocumentFrequencyWeight()), wordModel, 1, 1,
				0f, 2f);
		
		//OccurenceClustering algorithm = new clustering.algorithms.KmeansDynamic(features.getOccurenceTable(), new clustering.evaluation.IntraSimilarity(new clustering.distance.CosineDistance(), 5), new clustering.distance.CosineDistance(), new clustering.algorithms.kmeansUtils.KmeansInitializationPlusPlusDeterministic(new clustering.distance.CosineDistance(),100));
		OccurenceClustering algorithm = new clustering.algorithms.Kmeans(features.getOccurenceTable(), clusterNumber(evaluationClusters), new clustering.distance.CosineDistance());
		Evaluation[] metrics = {new clustering.evaluation.Precision(evaluationClusters), new clustering.evaluation.Recall(evaluationClusters), new clustering.evaluation.AdjustedPrecision(new File(testProjectPath))};
		
		int[] clusters = algorithm.returnClusters();
		for(Evaluation metric : metrics) 
			System.out.println(metric.toString()+" : "+metric.evaluate(clusters, null));
		
		Labeling labeling = new clustering.labeling.MostFrequentFeaturesLabeling(features, evaluationClusters, 3, new featureExtraction.featureWeight.WeightMethod(null,null));
		(new visualization.FirefoxVisualizer(evaluationClusters, project, labeling.getLabels(), clusters)).visualize();
		
	}
	private static int clusterNumber(int[] clusters) {
		int max = clusters[0];
		for (int i=1; i < clusters.length ; i++) {
			if (clusters[i]>max) {
				max=clusters[i];
			}
		}
		max+=1;
		return max;
	}
}