package featureExtraction;

import java.util.Arrays;

import dataImport.FileInput;
import featureExtraction.featureWeight.*;

public class WordModelFeatureExtractionFileNameAddedWeight extends WordModelFeatureExtraction {
	int fileNameWeight;

	public WordModelFeatureExtractionFileNameAddedWeight(FileInput[] input, WeightMethod weightMethod, String method, int fileNameWeight) {
		super(input, new NoWeight(), method);
		this.weightMethod = weightMethod;
		this.fileNameWeight = fileNameWeight;
	}
	
	protected float[][] createOccurenceTable() {

		float[][] occurence;
		occurence = super.createOccurenceTable();
		int fileNumber = input.length;
		int index = featureIds.size();
		boolean additionalFeatures = false;
		for (int i=0; i<fileNumber; i++) {
			String[] words = wordModel.getSentenceFeatures(input[i].getFileName());
			for (int k=0; k<words.length; k++) {
				if (!featureIds.containsKey(words[k])){
					featureIds.put(words[k],featureIds.size());
					idFeatures.put(idFeatures.size(), words[k]);
					additionalFeatures = true;
				}
			}	
		}
		if (additionalFeatures) {
			for (int i=0 ; i<input.length; i++) {
				System.arraycopy(occurence[i], 0, occurence[i], 0,idFeatures.size());
			}
		}
		
		for (int i=0; i<fileNumber; i++) {
			String[] words = wordModel.getSentenceFeatures(input[i].getFileName());
			for (int k=0; k<words.length; k++) {
				if (featureIds.containsKey(words[k])) {
					index = featureIds.get(words[k]);
					occurence[i][index] += fileNameWeight;
				}
			}	
		}
		occurence = weightMethod.weightOccurenceTable(occurence);
		return occurence;
	}

}
