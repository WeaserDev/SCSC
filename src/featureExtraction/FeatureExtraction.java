package featureExtraction;

import dataImport.FileInput;
import featureExtraction.featureWeight.WeightMethod;

import java.util.HashMap;

public abstract class FeatureExtraction {
	protected FileInput[] input;
	protected float occurenceTable[][];
	protected HashMap<String, Integer> fileIds;
	protected HashMap<Integer, String> idFiles;
	WeightMethod weightMethod;
	
	public FeatureExtraction(FileInput[] input, WeightMethod weightMethod) {
		this.input = input;
		this.weightMethod=weightMethod;
	}
	public abstract String describeFeature(int featureId);
	public abstract int getFeatureId(String feature);
	public abstract String getIdFeature(int featureId);
	public abstract int getFeatureNumber();
	
	private HashMap<String, Integer> createFileIds() {
		int fileNumber = input.length;
		HashMap<String, Integer> fileIds = new HashMap<String, Integer>();
		
		for (int i=0; i<fileNumber; i++) {
			fileIds.put(input[i].getFileName(),i);			
		}
		return fileIds;
	}
	
	private HashMap<Integer, String> createIdFiles() {
		int fileNumber = input.length;
		HashMap<Integer, String> idFiles = new HashMap<Integer, String>();		
		for (int i=0; i<fileNumber; i++) {
			idFiles.put(i,input[i].getFileName());			
		}
		return idFiles;
	}
	
	protected abstract float[][] createOccurenceTable();
	
	public float[][] getOccurenceTable(){
		if (occurenceTable==null) {
			occurenceTable=createOccurenceTable();
		}
		return occurenceTable;
	}
	
	public int getFileId(String name){
		if (fileIds==null) {
			fileIds=createFileIds();
		}		
		return fileIds.get(name);
	}
	
	
	public String getIdFile(int i){
		if (idFiles==null) {
			idFiles=createIdFiles();
		}
		return idFiles.get(i);
	}
	
	public int getFileNumber() {
		if (idFiles==null) {
			idFiles=createIdFiles();
		}
		return idFiles.size();
	}

}