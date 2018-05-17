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
	
	protected int clusterNumber() {
		int max = assignments[0];
		for (int i=1; i < assignments.length ; i++) {
			if (assignments[i]>max) {
				max=assignments[i];
			}
		}
		max+=1;
		return max;
	}
	
	protected float[][] createClusterTable(){
		float [][] occurenceTable = features.getOccurenceTable();
		float[][] clusterTable = new float[clusterNumber()][features.getFeatureNumber()];

		for (int cluster=0; cluster<clusterNumber();cluster++) {
			for (int i=0; i<features.getFileNumber();i++) {
				for (int k=0; k<features.getFeatureNumber();k++) {
					clusterTable[assignments[i]][k] += occurenceTable[i][k];					
				}								
			}
		}
		return clusterTable;
	}
	
	protected int[] kLargestElementsIndex(float[] array, int k) {
		float [] temp = new float[k];
		int minIndex;
		int [] tempIndex = new int[k];
		for (int i=0; i<k; i++) {
			temp[i] = array[i];
			tempIndex[i] = i;
		}
		for (int i=k;i<array.length;i++) {
			minIndex = arrayMinimum(temp);
			if (temp[minIndex] < array[i]) {
				temp[minIndex] = array[i];
				tempIndex[minIndex] = i;
			}			
		}
		
		return tempIndex;
	}

	private int arrayMinimum(float[] temp) {
		float min = temp[0];
		int index = 0;
		
		for (int i=1; i<temp.length; i++) {
			if (min>temp[i]) {
				min = temp[i];
				index = i;
			}
		}
		return index;
	}
}
