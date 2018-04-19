package featureExtraction;

import dataImport.FileInput;


import java.util.HashMap;

public class FeatureExtraction {

	private int occurenceTable[][];
	private HashMap<String, Integer> fileID = new HashMap<String, Integer>();
	private HashMap<String, Integer> wordID = new HashMap<String, Integer>();
	private HashMap<Integer, String> idFile = new HashMap<Integer, String>();
	private HashMap<Integer, String> idWord = new HashMap<Integer, String>();
	
	
	public FeatureExtraction() {
		
	}
	
	public FeatureExtraction(FileInput[] input) {

		createFileMap(input);
		createWordMap(input);
		occurenceTable = createOccurenceTable(input);
		
	}

	
	private void createWordMap(FileInput[] input){
		int fileNumber = input.length;
		int wordNumber = 0;
		
		
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
	}
	
	private void createFileMap(FileInput[] input) {
		int fileNumber = input.length;

		
		for (int i=0; i<fileNumber; i++) {
			idFile.put(i,input[i].getFileName());
			fileID.put(input[i].getFileName(),i);
			
		}
	}
	
	private int[][] createOccurenceTable(FileInput[] input) {
		int fileNumber = input.length;
		int index = wordID.size();
		int[][] occurence = new int[fileNumber][index];
		
		
		for (int i=0; i<fileNumber; i++) {
			String[] words = input[i].getFileCode().split("\\W+");
			for (int k=0; k<words.length; k++) {
				if (wordID.containsKey(words[k])) {
				index = wordID.get(words[k]);
				occurence[i][index] += 1;
				}
			}	
		}
		return occurence;
	}
	
	public int[][] getOccurenceTable(){
		return occurenceTable;
	}
	
	public HashMap<String, Integer> getFileID(){
		return fileID;
	}
	
	public HashMap<String, Integer> getWordID(){
		return wordID;
	}
	
	public HashMap<Integer, String> getIdFile(){
		return idFile;
	}
	
	public HashMap<Integer, String> getIdWord(){
		return idWord;
	}
}