package featureExtraction;

import dataImport.FileInput;


import java.util.HashMap;

abstract class FeatureExtraction {
	protected FileInput[] input;
	protected float occurenceTable[][];
	protected HashMap<String, Integer> fileId;
	protected HashMap<Integer, String> idFile;
	
	
	public FeatureExtraction(FileInput[] input) {
		this.input = input;
	}
	
	abstract HashMap<String, Integer> getFeatureId();
	abstract HashMap<Integer, String> getIdFeature();

	
	private HashMap<String, Integer> createFileId() {
		int fileNumber = input.length;
		HashMap<String, Integer> fileId = new HashMap<String, Integer>();
		
		for (int i=0; i<fileNumber; i++) {
			fileId.put(input[i].getFileName(),i);			
		}
		return fileId;
	}
	
	private HashMap<Integer, String> createIdFile() {
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
	
	public HashMap<String, Integer> getFileId(){
		if (fileId==null) {
			fileId=createFileId();
		}		
		return fileId;
	}
	
	
	public HashMap<Integer, String> getIdFile(){
		if (idFile==null) {
			idFile=createIdFile();
		}
		return idFile;
	}
	

}