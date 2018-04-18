package featureExtraction;

import dataImport.FileInput;


import java.util.HashMap;

public class FeatureExtraction {

	public int occurenceTable[][];
	public HashMap<String, Integer> fileID = new HashMap<String, Integer>();
	public HashMap<String, Integer> wordID = new HashMap<String, Integer>();
	public HashMap<Integer, String> idFile = new HashMap<Integer, String>();
	public HashMap<Integer, String> idWord = new HashMap<Integer, String>();
	
	
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
			String[] words = input[i].fileCode.split("\\W+");
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
			idFile.put(i,input[i].fileName);
			fileID.put(input[i].fileName,i);
			
		}
	}
	
	private int[][] createOccurenceTable(FileInput[] input) {
		int fileNumber = input.length;
		int index = wordID.size();
		int[][] occurence = new int[fileNumber][index];
		
		
		for (int i=0; i<fileNumber; i++) {
			String[] words = input[i].fileCode.split("\\W+");
			for (int k=0; k<words.length; k++) {
				if (wordID.containsKey(words[k])) {
				index = wordID.get(words[k]);
				occurence[i][index] += 1;
				}
			}	
		}
		return occurence;
	}
}