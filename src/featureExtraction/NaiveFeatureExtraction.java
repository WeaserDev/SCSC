package featureExtraction;

import java.util.HashMap;

import dataImport.FileInput;

public class NaiveFeatureExtraction extends FeatureExtraction {

	protected HashMap<String, Integer> featureId;
	protected HashMap<Integer, String> idFeature;

	
	public NaiveFeatureExtraction(FileInput[] input) {
		super(input);
	}
	
	
	private HashMap<String, Integer> createFeatureId(){
		int fileNumber = input.length;
		int wordNumber = 0;
		HashMap<String, Integer> featureId = new HashMap<String, Integer>();
		
		for (int i=0; i<fileNumber; i++) {
			String[] words = input[i].getFileCode().split("\\W+");
			for (int k=0; k<words.length; k++) {

				if (!featureId.containsKey(words[k])){
					featureId.put(words[k],wordNumber);
					wordNumber +=1;	
				}
			}
		}
		return featureId;
	}
	
	private HashMap<Integer, String> createIdFeature(){
		int fileNumber = input.length;
		int wordNumber = 0;
		HashMap<Integer, String> idFeature = new HashMap<Integer, String>();
		HashMap<String, Integer> featureId = new HashMap<String, Integer>();

		
		for (int i=0; i<fileNumber; i++) {
			String[] words = input[i].getFileCode().split("\\W+");
			for (int k=0; k<words.length; k++) {

				if (!featureId.containsKey(words[k])){
					featureId.put(words[k],wordNumber);
					idFeature.put(wordNumber, words[k]);
					wordNumber +=1;	
				}
			}
		}
		return idFeature;
	}
	
	protected float[][] createOccurenceTable() {
		int fileNumber = input.length;
		if (featureId==null) {
			featureId=createFeatureId();
		}
		int index = featureId.size();
		float[][] occurence = new float[fileNumber][index];
		
		
		for (int i=0; i<fileNumber; i++) {
			String[] words = input[i].getFileCode().split("\\W+");
			for (int k=0; k<words.length; k++) {
				if (featureId.containsKey(words[k])) {
				index = featureId.get(words[k]);
				occurence[i][index] = 1;
				}
			}	
		}
		return occurence;
	}
	

	
	public HashMap<String, Integer> getFeatureId(){
		if (featureId==null) {
			featureId=createFeatureId();
		}		
		return featureId;
	}
	
	
	public HashMap<Integer, String> getIdFeature(){
		if (idFeature==null) {
			idFeature=createIdFeature();
		}		
		return idFeature;
	}
}
	

