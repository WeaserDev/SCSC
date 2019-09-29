package featureExtraction;

import java.util.HashMap;

public class RemoveLowOccurences implements FeatureExtraction {
	FeatureExtraction featureExtraction;
	int removeThreshold;
	
	RemoveLowOccurences(FeatureExtraction featureExtraction, int removeThreshold){
		this.featureExtraction = featureExtraction;
		this.removeThreshold = removeThreshold;
	}
	
	@Override
	public float[][] getOccurenceTable() {
		float[][] occurenceTable = featureExtraction.getOccurenceTable();
		HashMap<Integer, Integer> mapper = new HashMap<Integer, Integer>();
		
		for (int i=0; i<occurenceTable.length;i++) {
			int countOccurences = 0;
			for (int j=0; j<occurenceTable[0].length;j++) {
				if(occurenceTable[i][j]>0) {
					countOccurences++;
					if (countOccurences>removeThreshold) {
						mapper.put(mapper.size(), j);
						break;						
					}
				}
			}	
		}
		float[][] reducedOccurenceTable = new float[mapper.size()][occurenceTable[0].length];
		for (int i=0; i<occurenceTable.length;i++) {
			for (int j=0; j<mapper.size();j++) {
				reducedOccurenceTable[i][j] = occurenceTable[i][mapper.get(j)];
			}
		}
		return reducedOccurenceTable;
	}

}
