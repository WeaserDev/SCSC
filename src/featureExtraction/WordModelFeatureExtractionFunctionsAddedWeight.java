package featureExtraction;

import auth.eng.textManager.WordModel;
import dataImport.FileInput;
import featureExtraction.featureWeight.*;

public class WordModelFeatureExtractionFunctionsAddedWeight extends WordModelFeatureExtractionFileNameAddedWeight {
	private int functionWeight;
	private WeightMethod weight;
	
	public WordModelFeatureExtractionFunctionsAddedWeight(FileInput[] input, WeightMethod weightMethod, WordModel wordModel, int fileNameWeight, int functionWeight) {
		super(input, new NoWeight(), wordModel, fileNameWeight);
		this.weight = weightMethod;
		this.functionWeight = functionWeight;
	}
	
	protected float[][] createOccurenceTable() {
		float[][] occurence = super.createOccurenceTable();
		for (int i=0; i<input.length; i++) {
			int start = 0;
			int end = 0;
			
			String fileCode = input[i].getFileCode();
			fileCode.replaceAll("\\S*\\("," (");
			while(true){
				end = fileCode.indexOf("(", end+1);
				System.out.println(end);
				if (end == -1) break;
				start = fileCode.lastIndexOf(" ", end-2);
				System.out.println(start);
				String functionName = fileCode.substring(start, end);
				System.out.println(functionName);
				String[] words = wordModel.getSentenceFeatures(functionName);
				for (int k=0; k<words.length; k++) {
					if (featureIds.containsKey(words[k])) {
						occurence[i][featureIds.get(words[k])] += functionWeight;
					}
				}
			}
		}	
	
		occurence = weight.weightOccurenceTable(occurence);	
		return occurence;
	}
}
