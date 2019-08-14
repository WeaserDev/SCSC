package sourceCodeSemanticClustering;

import java.io.File;

import auth.eng.textManager.WordModel;
import clustering.algorithms.OccurenceClustering;
import clustering.algorithms.PackagesToClusters;
import clustering.evaluation.Evaluation;
import clustering.labeling.Labeling;
import dataImport.FileInput;
import dataImport.ProjectInput;
import featureExtraction.DocumentDocumentFeatures;
import featureExtraction.WordModelFeatureExtraction;
import featureExtraction.dimensionReduction.LSA;

public class Test {
	public static void main(String[] args) throws Exception {
		String testProjectPath = "C:\\projects\\Jedit";
		WordModel wordModel = new WordModel.BagOfWords(new auth.eng.textManager.stemmers.InvertibleStemmer(new auth.eng.textManager.stemmers.PorterStemmer()));
		ProjectInput project = new ProjectInput(new File(testProjectPath));
		int[] evaluationClusters = (new PackagesToClusters(new File(testProjectPath))).returnClusters();
		
		WordModelFeatureExtraction features = new featureExtraction.WordModelFeatureExtractionReferenceAddedWeight(project.getInput(), new featureExtraction.featureWeight.TermFrequencyInverseDocumentFrequencyWeight(), wordModel, 1, 1,
				0f, 2f);
		DocumentDocumentFeatures doc = new featureExtraction.DocumentDocumentFeatures(features.getOccurenceTable(),new clustering.distance.CosineDistance());
		LSA svd = new LSA(features.getOccurenceTable());
		float regularizeIntraSimilarity = 1;//1 to split to fewer clusters, 5 to split to more clusters
		//OccurenceClustering algorithm = new clustering.algorithms.KmeansDynamic(svd.getDocumentConceptTable(), new clustering.evaluation.IntraSimilarity(new clustering.distance.CosineDistance(),regularizeIntraSimilarity), new clustering.distance.ModifiedCosineDistance(), new clustering.algorithms.kmeansUtils.KmeansInitializationPlusPlusDeterministic(new clustering.distance.CosineDistance(),1));
		//OccurenceClustering algorithm = new clustering.algorithms.TopicsKmeans(features.getOccurenceTable(), 10, 20, new clustering.distance.CosineDistance());
		OccurenceClustering algorithm = new clustering.algorithms.Kmeans(svd.getLowRankApproximation(), 70, new clustering.distance.ModifiedCosineDistance(),new clustering.algorithms.kmeansUtils.KmeansInitializationPlusPlusDeterministic(new clustering.distance.CosineDistance(), 1));
		Evaluation[] metrics = {new clustering.evaluation.Precision(evaluationClusters), new clustering.evaluation.Recall(evaluationClusters), new clustering.evaluation.AdjustedPrecision(new File(testProjectPath)),new clustering.evaluation.MojoFM(evaluationClusters),new clustering.evaluation.SilhuetteIndex(new clustering.distance.ModifiedCosineDistance())};

		int[] clusters = algorithm.returnClusters();
		for(Evaluation metric : metrics) 
			System.out.println(metric.toString()+" : "+metric.evaluate(clusters, features.getOccurenceTable()));
		
		Labeling labeling = new clustering.labeling.MostFrequentFeaturesLabeling(features, clusters, 3, new featureExtraction.featureWeight.NoWeight());
		(new visualization.FirefoxVisualizer(clusters, project, labeling.getLabels(), null)).visualize();//show discovered thing
		//(new visualization.JFrameVisualizer(clusters, project)).visualize();//show discovered thing
	}
}