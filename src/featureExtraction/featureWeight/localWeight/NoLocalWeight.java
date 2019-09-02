package featureExtraction.featureWeight.localWeight;

public class NoLocalWeight extends LocalWeightMethod {

	@Override
	public float[][] getLocalWeight(float[][] occurenceTable) {
		return occurenceTable;
	}

}
