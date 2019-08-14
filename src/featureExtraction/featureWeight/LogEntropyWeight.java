package featureExtraction.featureWeight;

public class LogEntropyWeight extends WeightMethod {

	@Override
	public float[][] weightOccurenceTable(float[][] occurenceTable) {
		float[] entropyWeights = new float[occurenceTable[0].length];
		float[][] probability = new float[occurenceTable.length][occurenceTable[0].length];
		float rowSum[] = new float[occurenceTable.length];
		float[][] logEntropyWeights = new float[occurenceTable.length][occurenceTable[0].length];
		
		for (int i=0; i<occurenceTable.length; i++) {
			for (int j=0; j<occurenceTable[0].length; j++) {
				rowSum[i] += occurenceTable[i][j];
			}
		}
		
		for (int i=0; i<occurenceTable.length; i++) {
			for (int j=0; j<occurenceTable[0].length; j++) {
				probability[i][j] = occurenceTable[i][j]/rowSum[i];
			}
		}
		for (int j=0; j<occurenceTable[0].length; j++) {
			entropyWeights[j] = 1;
			for (int i=0; i<occurenceTable.length; i++) {
				if(probability[i][j]!=0) {
					entropyWeights[j] += (probability[i][j]*Math.log(probability[i][j]))/Math.log(occurenceTable.length);
				}			
			}
		}
		for (int i=0; i<occurenceTable.length; i++) {
			for (int j=0; j<occurenceTable[0].length; j++) {
				logEntropyWeights[i][j] = entropyWeights[j]*(float)Math.log(occurenceTable[i][j] + 1);
			}
		}
		return logEntropyWeights;
	}

}
