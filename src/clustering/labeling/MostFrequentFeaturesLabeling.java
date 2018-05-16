package clustering.labeling;

import featureExtraction.FeatureExtraction;

public class MostFrequentFeaturesLabeling extends Labeling {
	int labelsNumber;
	
	public MostFrequentFeaturesLabeling(FeatureExtraction features,int[] assignments, int labelsNumber) {
		super(features,assignments);
		this.labelsNumber = labelsNumber;
	}
	
	protected String[][] createLabels() {
		String[][] labels = new String[clusterNumber()][labelsNumber];
		float[][] clusterTable = createClusterTable();
		for (int i=0; i<clusterNumber();i++) {
		int[] mostFrequentIds = kLargestElementsIndex(clusterTable[i],labelsNumber);
			for (int j=0; j<labelsNumber; j++) {
				labels[i][j] = features.getIdFeature(mostFrequentIds[j]);
			}
		}		
		return labels;
		
	}	

}
