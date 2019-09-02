package featureExtraction.featureWeight;

import featureExtraction.featureWeight.globalWeight.GlobalWeightMethod;
import featureExtraction.featureWeight.localWeight.LocalWeightMethod;

public class WeightMethod {
	LocalWeightMethod localWeight;
	GlobalWeightMethod globalWeight;
	
	public WeightMethod(LocalWeightMethod localWeight,GlobalWeightMethod globalWeight) {
		this.localWeight = localWeight;
		this.globalWeight = globalWeight;
		if (this.localWeight==null)
			this.localWeight = new featureExtraction.featureWeight.localWeight.NoLocalWeight();
		if (this.globalWeight==null)
			this.globalWeight = new featureExtraction.featureWeight.globalWeight.NoGlobalWeight();
		
	}
	
	public float[][] weightOccurenceTable(float[][] occurenceTable){
		float[][] localWeightOccurenceTable = localWeight.getLocalWeight(occurenceTable);
		float[] globalWeightOccurenceTable = globalWeight.getGlobalWeight(occurenceTable);
		for(int i=0; i<occurenceTable.length;i++) {
			for(int j=0; j<occurenceTable[0].length;j++) {
				localWeightOccurenceTable[i][j] = localWeightOccurenceTable[i][j]*globalWeightOccurenceTable[j];
			}
		}
		return localWeightOccurenceTable;
	}
}
