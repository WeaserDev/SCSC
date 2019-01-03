package featureExtraction;

import java.util.Arrays;

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
		updatedOccurence = weight.weightOccurenceTable(updatedOccurence);
		if (referenceWeight>0) {
			for (int n=0; n<11; n++) {
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
								updatedOccurence[fileIndex][k] += (countFeatures/features.length)*referenceWeight*oldOccurence[referencedFileIndex][k]/Math.pow(depreciationRate, n)/occurence.length;	
							}
						}
					}	
				}	
			}
		}
		System.out.println(max);

		//for(int i=0;i<updatedOccurence.length;i++)
			//System.out.println(Arrays.toString(updatedOccurence[i]));
		return updatedOccurence;
	}

}
