package featureExtraction.featureWeight.localWeight;

public class TermFrequencyWeight extends LocalWeightMethod {
	
	@Override
	public float[][] getLocalWeight(float[][] occurenceTable) {
		float[] fileFeaturesCount = countFileFeatures(occurenceTable);		
		int featuresNumber = occurenceTable[0].length;
		int filesNumber = occurenceTable.length;
		for (int i=0; i<filesNumber; i++) {
			if(fileFeaturesCount[i]!=0) {
				for (int k=0; k<featuresNumber; k++) {
					occurenceTable[i][k] = occurenceTable[i][k]/fileFeaturesCount[i];
				}
			}
		}	
		return occurenceTable;
	}
	
	protected float[] countFileFeatures(float[][] occurenceTable){
		int featuresNumber = occurenceTable[0].length;
		int filesNumber = occurenceTable.length;
		float featureCount[] = new float[filesNumber];
		for (int i=0; i<filesNumber; i++) {
			for (int k=0; k<featuresNumber; k++) {
				featureCount[i] += occurenceTable[i][k];
			}
		}				
		return featureCount;
	}
}
