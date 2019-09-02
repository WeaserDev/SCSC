package featureExtraction.featureWeight.localWeight;

public class LogWeight extends LocalWeightMethod {

	@Override
	public float[][] getLocalWeight(float[][] occurenceTable) {
		for (int i=0; i<occurenceTable.length; i++) {
			for (int k=0; k<occurenceTable[0].length; k++) {
				occurenceTable[i][k] = (float) (Math.log(1 + occurenceTable[i][k])/Math.log(2));
			}
		}
		return occurenceTable;
	}

}
