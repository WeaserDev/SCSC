package featureExtraction;

import dataImport.FileInput;

import java.util.HashMap;

import auth.eng.textManager.*;

public class WordModelFeatureExtraction extends FeatureExtraction {
	WordModel wordModel;
	private HashMap<String, Integer> featureIds;
	private HashMap<Integer, String> idFeatures;
	
	public WordModelFeatureExtraction(FileInput[] input,int methodId) {
		super(input);
		switch(methodId) {
		case 1: wordModel = WordModel.commonBagOfWords;
		break;
		case 2: wordModel = WordModel.commonWordNet;
		break;
		}
	}
	
	private void createFeatureIds(){
		int fileNumber = input.length;
		int wordNumber = 0;
		HashMap<Integer, String> idFeatures = new HashMap<Integer, String>();
		HashMap<String, Integer> featureIds = new HashMap<String, Integer>();
	
		for (int i=0; i<fileNumber; i++) {
			String[] sentences = WordModel.getTextSentences(input[i].getFileCode());
			for (int j=0; j<sentences.length; j++) {		
				String[] words = wordModel.getSentenceFeatures(sentences[j]);
				for (int k=0; k<words.length; k++) {

					if (!featureIds.containsKey(words[k])){
						featureIds.put(words[k],wordNumber);
						idFeatures.put(wordNumber, words[k]);
						wordNumber +=1;	
					}
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
			String[] sentences = WordModel.getTextSentences(input[i].getFileCode());
			for (int j=0; j<sentences.length; j++) {	
				String[] words = wordModel.getSentenceFeatures(sentences[j]);
				for (int k=0; k<words.length; k++) {
					if (featureIds.containsKey(words[k])) {
						index = featureIds.get(words[k]);
						occurence[i][index] = 1;
					}
				}	
			}
		}
		return occurence;
	}

	public int getFeatureNumber() {
		if (featureIds==null) {
			createFeatureIds();
		}		
		return featureIds.size();
	}
}
