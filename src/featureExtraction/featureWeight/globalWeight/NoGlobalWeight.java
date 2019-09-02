package featureExtraction.featureWeight.globalWeight;

public class NoGlobalWeight extends GlobalWeightMethod {

	@Override
	public float[] getGlobalWeight(float[][] occurenceTable) {
		float noWeight[]= new float[occurenceTable[0].length];
		for (int j=0;j<occurenceTable[0].length;j++) {
			noWeight[j]=1;
		}
		return noWeight;
	}

}
