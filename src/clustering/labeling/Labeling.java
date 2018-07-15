package clustering.labeling;

import featureExtraction.FeatureExtraction;


public abstract class Labeling {
	FeatureExtraction features;
	int[] assignments;
	
	Labeling(FeatureExtraction features,int[] assignments){
		this.features = features;
		this.assignments = assignments;
	}
	protected abstract String[][] createLabels();
	
	public String[][] getLabels(){
		return createLabels();
	}
	

}
