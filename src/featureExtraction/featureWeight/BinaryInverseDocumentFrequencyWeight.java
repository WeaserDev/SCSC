package featureExtraction.featureWeight;

public class BinaryInverseDocumentFrequencyWeight extends WeightMethod {

	public float[][] weightOccurenceTable(float[][] occurenceTable) {
		int featuresNumber = occurenceTable[0].length;
		int filesNumber = occurenceTable.length;
		float[] inverseDocumentFrequency = inverseDocumentFrequency(occurenceTable);

		for (int i=0; i<filesNumber; i++) {
			for (int k=0; k<featuresNumber; k++) {
				if (occurenceTable[i][k]>0) {
					occurenceTable[i][k]=inverseDocumentFrequency[k];
				}
				else {
					occurenceTable[i][k]=0;
				}
			}
		}
		return occurenceTable;
	}

}
