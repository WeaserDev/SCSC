package featureWeight;

public abstract class WeightMethod {
	abstract public float[][] weightOccurenceTable(float[][] occurenceTable);

	protected float[] countFileFeatures(float[][] occurenceTable){
		int featuresNumber = occurenceTable[0].length;
		int filesNumber = occurenceTable.length;
		float featureCount[] = new float[filesNumber];
		for (int i=0; i<filesNumber; i++) {
			for (int k=0; k<featuresNumber; k++) {
				featureCount[i] += occurenceTable[i][k];
			}
		}				
		return featureCount;
	}
	
	protected float[] inverseDocumentFrequency(float[][] occurenceTable) {
		int featuresNumber = occurenceTable[0].length;
		int filesNumber = occurenceTable.length;
		float featureCount[] = new float[featuresNumber];
		float inverseDocumentFrequency[]= new float[featuresNumber];
		for (int i=0; i<filesNumber; i++) {
			for (int k=0; k<featuresNumber; k++) {
				if (occurenceTable[i][k]>0) {
					featureCount[k] += 1;
				}	
			}
		}
		for (int k=0; k<featuresNumber; k++) {
			inverseDocumentFrequency[k] = (float)Math.log(filesNumber/featureCount[k]);
		}
		return inverseDocumentFrequency;
	}
}
