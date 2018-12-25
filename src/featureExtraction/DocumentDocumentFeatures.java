package featureExtraction;

import clustering.distance.DistanceFunction;

public class DocumentDocumentFeatures implements FeatureExtraction {
	float occurenceTable[][];
	DistanceFunction distance;
	
	public DocumentDocumentFeatures(float occurenceTable[][], DistanceFunction distance) {
		this.occurenceTable = occurenceTable;
		this.distance = distance;
	}
	
	public float[][] getOccurenceTable(){
		float[][] documentOccurenceTable= new float[occurenceTable.length][occurenceTable.length];
		for (int i=0;i<occurenceTable.length; i++) {
			for (int k=0; k<occurenceTable.length; k++) {
				documentOccurenceTable[i][k] = (float)distance.distance(occurenceTable[i], occurenceTable[k]); 
			}
		}
		
		return documentOccurenceTable;
	}
}
