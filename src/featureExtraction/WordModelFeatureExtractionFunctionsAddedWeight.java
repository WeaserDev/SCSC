package featureExtraction;

import dataImport.FileInput;
import featureExtraction.featureWeight.*;

public class WordModelFeatureExtractionFunctionsAddedWeight extends WordModelFeatureExtractionFileNameAddedWeight {
	private int functionWeight;
	
	public WordModelFeatureExtractionFunctionsAddedWeight(FileInput[] input, WeightMethod weightMethod, String method, int fileNameWeight, int functionWeight) {
		super(input, new NoWeight(), method, fileNameWeight);
		this.functionWeight = functionWeight;
	}
	
	protected float[][] createOccurenceTable() {
		float[][] occurence;
		occurence = super.createOccurenceTable();
		for (int i=0; i<input.length; i++) {
			int start = 0;
			int end = 0;
			String fileCode = input[i].getFileCode().replaceAll("\\S*\\("," (");
			while(true){
				end = fileCode.indexOf("(", end);
				if (end == -1) break;
				start = fileCode.lastIndexOf(" ", end-2);
				String functionName = fileCode.substring(start, end);
				String[] words = wordModel.getSentenceFeatures(functionName);
				for (int k=0; k<words.length; k++) {
					if (featureIds.containsKey(words[k])) {
						occurence[i][featureIds.get(words[k])] += functionWeight;
					}
				}
			}
		}	
				
		return occurence;
	}
}
