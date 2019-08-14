package featureExtraction.featureWeight;

public class LogWeight extends WeightMethod {

	@Override
	public float[][] weightOccurenceTable(float[][] occurenceTable) {
		for (int i=0; i<occurenceTable.length; i++) {
			for (int k=0; k<occurenceTable[0].length; k++) {
				occurenceTable[i][k] = (float) Math.log(1 + occurenceTable[i][k]);
			}
		}
		return occurenceTable;
	}

}
