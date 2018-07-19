package clustering.labeling;

import featureExtraction.FeatureExtraction;


public abstract class Labeling {
	FeatureExtraction features;
	int[] clusters;
	
	Labeling(FeatureExtraction features,int[] clusters){
		this.features = features;
		this.clusters = clusters;
	}
	protected abstract String[][] createLabels();
	
	public String[][] getLabels(){
		return createLabels();
	}
	

}
