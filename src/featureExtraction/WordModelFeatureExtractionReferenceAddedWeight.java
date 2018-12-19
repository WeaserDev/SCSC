package featureExtraction;

import auth.eng.textManager.WordModel;
import dataImport.FileInput;
import featureExtraction.featureWeight.WeightMethod;

public class WordModelFeatureExtractionReferenceAddedWeight extends WordModelFeatureExtractionAddedWeight {
	float referenceWeight;
	float depreciationRate;
	private WeightMethod weight;
	public WordModelFeatureExtractionReferenceAddedWeight(FileInput[] input, WeightMethod weightMethod,WordModel wordModel, int fileNameWeight, int functionWeight, float referenceWeight, float depreciationRate) {
		super(input, new featureExtraction.featureWeight.NoWeight(), wordModel, fileNameWeight, functionWeight);
		this.referenceWeight = referenceWeight;
		this.depreciationRate = depreciationRate;
		this.weight = weightMethod;
	}
	
	protected float[][] createOccurenceTable() {
		float[][] occurence = super.createOccurenceTable();
		float [][] updatedOccurence = occurence.clone();
		float max=0;
		if (referenceWeight>0) {
			for (int n=1; n<11; n++) {
				float [][] oldOccurence = updatedOccurence.clone();
				for (int referencedFileIndex=0; referencedFileIndex<input.length; referencedFileIndex++) {
					String[] features = wordModel.getSentenceFeatures(input[referencedFileIndex].getFileName());		
					for (int fileIndex=0; fileIndex<input.length; fileIndex++) {
						if (fileIndex!=referencedFileIndex) {
							int countFeatures = 0;
							for (int feature=0; feature<features.length; feature++) {
								int featureIndex = featureIds.get(features[feature]);
								if (occurence[fileIndex][featureIndex]>0) {
									countFeatures+=1;
								}
							}
							for (int k=0;k<occurence[0].length;k++) {
								updatedOccurence[fileIndex][k] += (countFeatures/features.length)*referenceWeight*oldOccurence[referencedFileIndex][k]/(Math.pow(depreciationRate, n)*occurence.length);
								if (updatedOccurence[fileIndex][k]>max) max=updatedOccurence[fileIndex][k];
							}
						}
					}	
				}	
			}
		}
		System.out.println(max);

		updatedOccurence = weight.weightOccurenceTable(updatedOccurence);
		return updatedOccurence;
	}

}
