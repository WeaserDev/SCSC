package featureWeight;

public class TermFrequencyWeight extends WeightMethod {

	public float[][] weightOccurenceTable(float[][] occurenceTable) {
		float[] fileFeaturesCount = countFileFeatures(occurenceTable);		
		int featuresNumber = occurenceTable[0].length;
		int filesNumber = occurenceTable.length;
		for (int i=0; i<filesNumber; i++) {
			for (int k=0; k<featuresNumber; k++) {
				occurenceTable[i][k] = occurenceTable[i][k]/fileFeaturesCount[i];
			}
		}		
		return occurenceTable;
	}

}
