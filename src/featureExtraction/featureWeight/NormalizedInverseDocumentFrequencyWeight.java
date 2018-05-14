package featureExtraction.featureWeight;

public class NormalizedInverseDocumentFrequencyWeight extends WeightMethod{
	public float[][] weightOccurenceTable(float[][] occurenceTable) {
		float[] inverseDocumentFrequency = inverseDocumentFrequency(occurenceTable);	
		int featuresNumber = occurenceTable[0].length;
		int filesNumber = occurenceTable.length;
		for (int i=0; i<filesNumber; i++) {
			for (int k=0; k<featuresNumber; k++) {
				occurenceTable[i][k] = occurenceTable[i][k]*inverseDocumentFrequency[k];
			}
		}
		occurenceTable = normalizeTable(occurenceTable);
		return occurenceTable;		
	}
}
