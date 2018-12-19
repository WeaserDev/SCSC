package clustering.labeling;

import java.util.ArrayList;
import java.util.HashMap;

import featureExtraction.FeatureExtraction;


public abstract class Labeling {
	FeatureExtraction features;
	int[] clusters;
	
	Labeling(FeatureExtraction features,int[] clusters){
		this.features = features;
		this.clusters = clusters;
	}
	protected abstract HashMap<Integer, ArrayList<String>> createLabels();
	
	public HashMap<Integer, ArrayList<String>> getLabels(){
		return createLabels();
	}
	

}
