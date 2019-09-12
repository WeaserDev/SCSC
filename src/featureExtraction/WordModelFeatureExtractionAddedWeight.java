package featureExtraction;

import auth.eng.textManager.WordModel;
import dataImport.FileInput;
import featureExtraction.featureWeight.WeightMethod;

public class WordModelFeatureExtractionAddedWeight extends WordModelFeatureExtraction {
	private int fileNameWeight;
	private WeightMethod weight;
	private int functionWeight;
	
	public WordModelFeatureExtractionAddedWeight(FileInput[] input, WeightMethod weightMethod, WordModel wordModel, int fileNameWeight, int functionWeight) {
		super(input, new WeightMethod(null,null), wordModel);
		this.weight = weightMethod;
		this.functionWeight = functionWeight;
		this.fileNameWeight = fileNameWeight;
	}
	
	protected void createFeatureIds(){
		super.createFeatureIds();
		for (int i=0; i<input.length; i++) {
			String[] words = wordModel.getSentenceFeatures(input[i].getFileName());
			for (int k=0; k<words.length; k++) {
				if (!featureIds.containsKey(words[k])){
					featureIds.put(words[k],featureIds.size());
					idFeatures.put(idFeatures.size(), words[k]);
				}
			}	
		}
		
	}
	
	protected float[][] createOccurenceTable() {
		float[][] occurence = super.createOccurenceTable();
		if (fileNameWeight>0) {
			for (int i=0; i<input.length; i++) {
				String[] words = wordModel.getSentenceFeatures(input[i].getFileName());
				for (int k=0; k<words.length; k++) {
					if (featureIds.containsKey(words[k])) {
						occurence[i][featureIds.get(words[k])] += fileNameWeight;
					}
				}	
			}
		}
		
		if (functionWeight>0) {
			for (int i=0; i<input.length; i++) {
				int start = 0;
				int end = 0;
				
				String fileCode = input[i].getFileCode();
				fileCode.replaceAll("\\S*\\("," (");
				while(true){
					end = fileCode.indexOf("(", end+1);
					//System.out.println(end);
					if (end == -1) break;
					start = fileCode.lastIndexOf(" ", end-2);
					if (start == -1) break;
					if (fileCode.lastIndexOf("(",end-2)>start) start = fileCode.lastIndexOf("(",end-2);
					if (fileCode.lastIndexOf("\n", end -2)>start) start = fileCode.lastIndexOf("\n", end -2);			
					//System.out.println(start);
					String functionName = fileCode.substring(start, end);
					//System.out.println(functionName);
					String[] words = wordModel.getSentenceFeatures(functionName);
					for (int k=0; k<words.length; k++) {
						if (featureIds.containsKey(words[k])) {
							occurence[i][featureIds.get(words[k])] += functionWeight;
						}
					}
				}
			}
		}
		occurence = weight.weightOccurenceTable(occurence);
		return occurence;
	}

}
