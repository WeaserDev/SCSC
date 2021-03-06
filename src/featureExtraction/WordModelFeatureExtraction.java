package featureExtraction;

import dataImport.FileInput;
import featureExtraction.featureWeight.WeightMethod;

import java.util.HashMap;

import auth.eng.textManager.*;

public class WordModelFeatureExtraction extends InputFeatureExtraction {
	WordModel wordModel;
	protected HashMap<String, Integer> featureIds;
	protected HashMap<Integer, String> idFeatures;
	
	public WordModelFeatureExtraction(FileInput[] input,WeightMethod weightMethod, WordModel wordModel) {
		super(input,weightMethod);
		this.wordModel = wordModel;
	}
	
	protected void createFeatureIds(){
		int fileNumber = input.length;
		int wordNumber = 0;
		HashMap<Integer, String> idFeatures = new HashMap<Integer, String>();
		HashMap<String, Integer> featureIds = new HashMap<String, Integer>();
	
		for (int i=0; i<fileNumber; i++) {
			String[] words = wordModel.getSentenceFeatures(input[i].getFileCode());
			for (int k=0; k<words.length; k++) {
				if (!featureIds.containsKey(words[k])){
					featureIds.put(words[k],wordNumber);
					idFeatures.put(wordNumber, words[k]);
					wordNumber +=1;	
				}
			}
			
		}
		this.featureIds = featureIds;
		this.idFeatures = idFeatures;
	}
	

	public int getFeatureId(String feature) {
		if (featureIds==null) {
			createFeatureIds();
		}		
		return featureIds.get(feature);
	}

	public String getIdFeature(int i) {
		if (idFeatures==null) {
			createFeatureIds();
		}		
		return idFeatures.get(i);
	}

	protected float[][] createOccurenceTable() {
		int fileNumber = input.length;
		if (featureIds==null) {
			createFeatureIds();
		}
		int index = featureIds.size();
		float[][] occurence = new float[fileNumber][index];
			
		for (int i=0; i<fileNumber; i++) {
			String[] words = wordModel.getSentenceFeatures(input[i].getFileCode());
			for (int k=0; k<words.length; k++) {
				if (featureIds.containsKey(words[k])) {
					index = featureIds.get(words[k]);
					occurence[i][index] += 1;
				}
			}	
		}
		occurence = weightMethod.weightOccurenceTable(occurence);
		return occurence;
	}

	public int getFeatureNumber() {
		if (featureIds==null) {
			createFeatureIds();
		}		
		return featureIds.size();
	}
	
	public String describeFeature(int featureId){		
		return ((auth.eng.textManager.stemmers.InvertibleStemmer) wordModel.getStemmer()).getBestInterpretation(this.getIdFeature(featureId));
	}
}
