package sourceCodeSemanticClustering;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import auth.eng.textManager.WordModel;
import clustering.algorithms.OccurenceClustering;
import clustering.algorithms.TopicsKmeans;
import clustering.evaluation.*;
import dataImport.ProjectInput;
import dataImport.ProjectInputWithNameExtension;
import experiments.*;
import featureExtraction.DocumentDocumentFeatures;
import featureExtraction.WordModelFeatureExtraction;
import featureExtraction.WordModelFeatureExtractionAddedWeight;
import featureExtraction.WordModelFeatureExtractionReferenceAddedWeight;
import featureExtraction.featureWeight.*;

public class Test2 {

	public static void main(String[] args) throws IOException {
		String projectPath = "C:\\projectC";
		String[] extensions = {"c", "h", "cpp", "hh", "cc", "hpp"};
		
		ProjectInputWithNameExtension[] projects = ProjectInputWithNameExtension.createProjectInput(new File(projectPath), extensions);
		WordModel wordModel = new WordModel.BagOfWords(new auth.eng.textManager.stemmers.InvertibleStemmer(new auth.eng.textManager.stemmers.PorterStemmer()));
		
		for(ProjectInput project: projects) {
			Experiment exp = new TestMethodsToLatex();
			exp.test(project, wordModel, extensions);

		}

	}

}
