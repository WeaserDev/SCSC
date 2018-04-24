package featureExtraction;

import java.util.HashMap;

import dataImport.FileInput;

public class NaiveFeatureExtraction extends FeatureExtraction {

	protected HashMap<String, Integer> wordID;
	protected HashMap<Integer, String> idWord;

	
	public NaiveFeatureExtraction(FileInput[] input) {
		super(input);
	}
	
	
	private HashMap<String, Integer> createWordID(){
		int fileNumber = input.length;
		int wordNumber = 0;
		HashMap<String, Integer> wordID = new HashMap<String, Integer>();
		
		for (int i=0; i<fileNumber; i++) {
			String[] words = input[i].getFileCode().split("\\W+");
			for (int k=0; k<words.length; k++) {

				if (!wordID.containsKey(words[k])){
					wordID.put(words[k],wordNumber);
					wordNumber +=1;	
				}
			}
		}
		return wordID;
	}
	
	private HashMap<Integer, String> createidWord(){
		int fileNumber = input.length;
		int wordNumber = 0;
		HashMap<Integer, String> idWord = new HashMap<Integer, String>();
		HashMap<String, Integer> wordID = new HashMap<String, Integer>();

		
		for (int i=0; i<fileNumber; i++) {
			String[] words = input[i].getFileCode().split("\\W+");
			for (int k=0; k<words.length; k++) {

				if (!wordID.containsKey(words[k])){
					wordID.put(words[k],wordNumber);
					idWord.put(wordNumber, words[k]);
					wordNumber +=1;	
				}
			}
		}
		return idWord;
	}
	
	protected float[][] createOccurenceTable() {
		int fileNumber = input.length;
		if (wordID==null) {
			wordID=createWordID();
		}
		int index = wordID.size();
		float[][] occurence = new float[fileNumber][index];
		
		
		for (int i=0; i<fileNumber; i++) {
			String[] words = input[i].getFileCode().split("\\W+");
			for (int k=0; k<words.length; k++) {
				if (wordID.containsKey(words[k])) {
				index = wordID.get(words[k]);
				occurence[i][index] = 1;
				}
			}	
		}
		return occurence;
	}
	

	
	public HashMap<String, Integer> getWordID(){
		if (wordID==null) {
			wordID=createWordID();
		}		
		return wordID;
	}
	
	
	public HashMap<Integer, String> getIdWord(){
		if (idWord==null) {
			idWord=createidWord();
		}		
		return idWord;
	}
}
	

