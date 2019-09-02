package featureExtraction.featureWeight.globalWeight;


public class InverseDocumentFrequencyWeight extends GlobalWeightMethod {
	
	@Override
	public float[] getGlobalWeight(float[][] occurenceTable) {
		int featureNumber = occurenceTable[0].length;
		int fileNumber = occurenceTable.length;
		float featureCount[] = new float[featureNumber];
		float inverseDocumentFrequency[]= new float[featureNumber];
		for (int i=0; i<fileNumber; i++) {
			for (int k=0; k<featureNumber; k++) {
				if (occurenceTable[i][k]>0) {
					featureCount[k] += 1;
				}	
			}
		}
		for (int k=0; k<featureNumber; k++) {
			if (featureCount[k]>0){
			inverseDocumentFrequency[k] = (float)((Math.log(fileNumber/featureCount[k]))/Math.log(2.0));
			}
		}
		return inverseDocumentFrequency;		
	}
}
