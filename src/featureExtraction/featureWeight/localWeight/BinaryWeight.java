package featureExtraction.featureWeight.localWeight;

public class BinaryWeight extends LocalWeightMethod {
	
	@Override
	public float[][] getLocalWeight(float[][] occurenceTable) {
		int featuresNumber = occurenceTable[0].length;
		int filesNumber = occurenceTable.length;
		for (int i=0; i<filesNumber; i++) {
			for (int k=0; k<featuresNumber; k++) {
				if (occurenceTable[i][k]>0) {
					occurenceTable[i][k]=1;
				}
				else {
					occurenceTable[i][k]=0;
				}
			}
		}
		return occurenceTable;
	}
}
