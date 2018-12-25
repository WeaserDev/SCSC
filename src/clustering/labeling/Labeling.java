package clustering.labeling;

import java.util.ArrayList;
import java.util.HashMap;

import featureExtraction.InputFeatureExtraction;


public abstract class Labeling {
	InputFeatureExtraction features;
	int[] clusters;
	
	Labeling(InputFeatureExtraction features,int[] clusters){
		this.features = features;
		this.clusters = clusters;
	}
	protected abstract HashMap<Integer, ArrayList<String>> createLabels();
	
	public HashMap<Integer, ArrayList<String>> getLabels(){
		return createLabels();
	}
	

}
