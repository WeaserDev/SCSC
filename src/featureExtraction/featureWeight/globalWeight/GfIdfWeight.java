package featureExtraction.featureWeight.globalWeight;

public class GfIdfWeight extends GlobalWeightMethod {

	@Override
	public float[] getGlobalWeight(float[][] occurenceTable) {
		int featureNumber = occurenceTable[0].length;
		int fileNumber = occurenceTable.length;
		float featureCount[] = new float[featureNumber];
		float gf[]= new float[featureNumber];
		for (int i=0; i<fileNumber; i++) {
			for (int k=0; k<featureNumber; k++) {
				if (occurenceTable[i][k]>0) {
					featureCount[k] += 1;
					gf[k]+= occurenceTable[i][k];
				}	
			}
		}
		for (int k=0; k<featureNumber; k++) {
			if (featureCount[k]>0){
				gf[k] = gf[k]/featureCount[k];
			}
		}
		return gf;
	}

}
