package featureExtraction.featureWeight;

public class NormalizedWeight extends WeightMethod {
	public float[][] weightOccurenceTable(float[][] occurenceTable) {
		occurenceTable = normalizeTable(occurenceTable);
		return occurenceTable;		
	}
}
