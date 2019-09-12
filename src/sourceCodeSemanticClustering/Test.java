package sourceCodeSemanticClustering;

import java.io.File;

import auth.eng.textManager.WordModel;
import clustering.algorithms.MergePackagesToClusters;
import clustering.algorithms.OccurenceClustering;
import clustering.algorithms.PackagesToClusters;
import clustering.evaluation.Evaluation;
import clustering.labeling.Labeling;
import dataImport.FileInput;
import dataImport.ProjectInput;
import featureExtraction.DocumentDocumentFeatures;
import featureExtraction.WordModelFeatureExtraction;
import featureExtraction.dimensionReduction.LSA;
import featureExtraction.dimensionReduction.LSI;
import featureExtraction.featureWeight.localWeight.TermFrequencyWeight;

public class Test {
	public static void main(String[] args) throws Exception {
		String testProjectPath = "..\\";
		WordModel wordModel = new WordModel.BagOfWords(new auth.eng.textManager.stemmers.InvertibleStemmer(new auth.eng.textManager.stemmers.PorterStemmer()));
		ProjectInput project = new ProjectInput(new File(testProjectPath));
		int[] evaluationClusters = (new MergePackagesToClusters(new File(testProjectPath),5)).returnClusters();
		
		WordModelFeatureExtraction features = new featureExtraction.WordModelFeatureExtractionReferenceAddedWeight(project.getInput(), new featureExtraction.featureWeight.WeightMethod(new featureExtraction.featureWeight.localWeight.LogWeight(), new featureExtraction.featureWeight.globalWeight.NoGlobalWeight()), wordModel, 10, 3,
				0f, 2f);
		DocumentDocumentFeatures doc = new featureExtraction.DocumentDocumentFeatures(features.getOccurenceTable(),new clustering.distance.CosineDistance());
		LSA svd = new LSA(features.getOccurenceTable());
		LSI svd2 = new LSI(features.getOccurenceTable());
		float regularizeIntraSimilarity = 1;//1 to split to fewer clusters, 5 to split to more clusters
		//OccurenceClustering algorithm = new clustering.algorithms.KmeansDynamic(svd.getDocumentConceptTable(), new clustering.evaluation.IntraSimilarity(new clustering.distance.CosineDistance(),regularizeIntraSimilarity), new clustering.distance.ModifiedCosineDistance(), new clustering.algorithms.kmeansUtils.KmeansInitializationPlusPlusDeterministic(new clustering.distance.CosineDistance(),1));
		//OccurenceClustering algorithm = new clustering.algorithms.TopicsKmeans(features.getOccurenceTable(), 10, 20, new clustering.distance.CosineDistance());
		OccurenceClustering algorithm = new clustering.algorithms.Kmeans(svd.getDocumentConceptTable(), 11, new clustering.distance.ModifiedCosineDistance(),new clustering.algorithms.kmeansUtils.KmeansInitializationPlusPlusDeterministic(new clustering.distance.CosineDistance(), 1));
		Evaluation[] metrics = {new clustering.evaluation.Precision(evaluationClusters), new clustering.evaluation.Recall(evaluationClusters), new clustering.evaluation.AdjustedPrecision(new File(testProjectPath)),new clustering.evaluation.MojoFM(evaluationClusters),new clustering.evaluation.SilhuetteIndex(new clustering.distance.ModifiedCosineDistance())};
		System.out.println(svd.getCutoffPoint());
		int[] clusters = algorithm.returnClusters();
		for(Evaluation metric : metrics) 
			System.out.println(metric.toString()+" : "+metric.evaluate(clusters, features.getOccurenceTable()));
		
		Labeling labeling = new clustering.labeling.MostFrequentFeaturesLabeling(features, clusters, 3, new featureExtraction.featureWeight.WeightMethod(null,null));
		(new visualization.FirefoxVisualizer(clusters, project, labeling.getLabels(), null)).visualize();//show discovered thing
		//(new visualization.JFrameVisualizer(clusters, project)).visualize();//show discovered thing
	}
}