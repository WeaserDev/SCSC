package featureExtraction;

import dataImport.FileInput;


import java.util.HashMap;

abstract class FeatureExtraction {
	protected FileInput[] input;
	protected float occurenceTable[][];
	protected HashMap<String, Integer> fileID;
	protected HashMap<Integer, String> idFile;
	
	
	public FeatureExtraction(FileInput[] input) {
		this.input = input;
	}
		
	private HashMap<String, Integer> createFileID() {
		int fileNumber = input.length;
		HashMap<String, Integer> fileID = new HashMap<String, Integer>();
		
		for (int i=0; i<fileNumber; i++) {
			fileID.put(input[i].getFileName(),i);			
		}
		return fileID;
	}
	
	private HashMap<Integer, String> createidFile() {
		int fileNumber = input.length;
		HashMap<Integer, String> idFile = new HashMap<Integer, String>();		
		for (int i=0; i<fileNumber; i++) {
			idFile.put(i,input[i].getFileName());			
		}
		return idFile;
	}
	
	protected abstract float[][] createOccurenceTable();
	
	public float[][] getOccurenceTable(){
		if (occurenceTable==null) {
			occurenceTable=createOccurenceTable();
		}
		return occurenceTable;
	}
	
	public HashMap<String, Integer> getFileID(){
		if (fileID==null) {
			fileID=createFileID();
		}		
		return fileID;
	}
	
	
	public HashMap<Integer, String> getIdFile(){
		if (idFile==null) {
			idFile=createidFile();
		}
		return idFile;
	}
	

}