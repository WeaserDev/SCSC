package featureExtraction.featureWeight.globalWeight;


public class NormalWeight extends GlobalWeightMethod {
	
	@Override
	public float[] getGlobalWeight(float[][] occurenceTable) {
		float[] normalWeight = new float[occurenceTable[0].length];
		for (int i=0; i<occurenceTable.length; i++) {
			for (int j=0; j<occurenceTable[0].length; j++) {
				normalWeight[j] += (float)Math.pow(occurenceTable[i][j],2);
				}	
		}		
		return normalWeight;		
	}
}
