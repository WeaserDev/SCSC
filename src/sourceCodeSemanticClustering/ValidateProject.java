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
import featureExtraction.dimensionReduction.LSA;
import featureExtraction.featureWeight.WeightMethod;
import featureExtraction.featureWeight.globalWeight.InverseDocumentFrequencyWeight;
import featureExtraction.featureWeight.globalWeight.NoGlobalWeight;

public class ValidateProject {
	public static void main(String[] args) throws Exception {
		String testProjectPath = "C:\\projectpy\\keras-2.3.0";
		String[] extensions = {"py"};
		WordModel wordModel = new WordModel.BagOfWords(new auth.eng.textManager.stemmers.InvertibleStemmer(new auth.eng.textManager.stemmers.PorterStemmer()));
		ProjectInput project = new ProjectInput(new File(testProjectPath), extensions);
		int[] evaluationClusters = (new PackagesToClusters(new File(testProjectPath), extensions)).returnClusters();
		
		WordModelFeatureExtraction features = new featureExtraction.WordModelFeatureExtractionReferenceAddedWeight(project.getInput(), new featureExtraction.featureWeight.WeightMethod(new featureExtraction.featureWeight.localWeight.LogWeight(), new featureExtraction.featureWeight.globalWeight.InverseDocumentFrequencyWeight()), wordModel, 1, 1,
				0f, 0f);
//		float occur[][] = features.getOccurenceTable();
//		double rank = Math.pow(occur.length*occur[0].length, 0.2);
//		int factors = (int) Math.round(rank);
//		LSA lsi = new LSA(occur,factors);
		System.out.println(features.getOccurenceTable()[0].length);

		//OccurenceClustering algorithm = new clustering.algorithms.KmeansDynamic(features.getOccurenceTable(), new clustering.evaluation.IntraSimilarity(new clustering.distance.CosineDistance(), 5), new clustering.distance.CosineDistance(), new clustering.algorithms.kmeansUtils.KmeansInitializationPlusPlusDeterministic(new clustering.distance.CosineDistance(),100));
		OccurenceClustering algorithm = new clustering.algorithms.Kmeans(features.getOccurenceTable(), 10, new clustering.distance.CosineDistance());
		Evaluation[] metrics = {new clustering.evaluation.Precision(evaluationClusters), new clustering.evaluation.Recall(evaluationClusters), new clustering.evaluation.AdjustedPrecision(new File(testProjectPath),extensions)};
		
		int[] clusters = algorithm.returnClusters();
		for(Evaluation metric : metrics) 
			System.out.println(metric.toString()+" : "+metric.evaluate(clusters, null));
		
		Labeling labeling = new clustering.labeling.MostFrequentFeaturesLabeling(features, evaluationClusters, 2, new featureExtraction.featureWeight.WeightMethod(null,null));
		(new visualization.FirefoxVisualizer(clusters, project, labeling.getLabels(), evaluationClusters)).visualize();
		
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