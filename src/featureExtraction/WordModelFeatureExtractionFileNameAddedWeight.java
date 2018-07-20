package featureExtraction;

import java.util.Arrays;

import auth.eng.textManager.WordModel;
import dataImport.FileInput;
import featureExtraction.featureWeight.*;

public class WordModelFeatureExtractionFileNameAddedWeight extends WordModelFeatureExtraction {
	int fileNameWeight;
	private WeightMethod weight;

	public WordModelFeatureExtractionFileNameAddedWeight(FileInput[] input, WeightMethod weightMethod, WordModel wordModel, int fileNameWeight) {
		super(input, new NoWeight(), wordModel);
		this.weight = weightMethod;
		this.fileNameWeight = fileNameWeight;
	}
	
	protected float[][] createOccurenceTable() {
		float[][] occurence = super.createOccurenceTable();
		int fileNumber = input.length;
		int index;
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
		occurence = weight.weightOccurenceTable(occurence);
		return occurence;
	}

}
