package clustering.labeling;

import featureExtraction.FeatureExtraction;
import featureExtraction.featureWeight.WeightMethod;
import auth.eng.textManager.*;

public class MostFrequentFeaturesLabeling extends Labeling {
	int labelsNumber;
	WeightMethod weightMethod;
	
	public MostFrequentFeaturesLabeling(FeatureExtraction features,int[] assignments, int labelsNumber, WeightMethod weightMethod) {
		super(features,assignments);
		this.labelsNumber = labelsNumber;
		this.weightMethod = weightMethod;
	}
	
	protected String[][] createLabels() {
		String[][] labels = new String[clusterNumber()][labelsNumber];
		float[][] clusterTable = createClusterTable();
		clusterTable = weightMethod.weightOccurenceTable(clusterTable);
		for (int i=0; i<clusterNumber();i++) {
		int[] mostFrequentIds = kLargestElementsIndex(clusterTable[i],labelsNumber);
			for (int j=0; j<labelsNumber; j++) {
				labels[i][j] = features.getIdFeature(mostFrequentIds[j]);
			}
		}
		for (int i=0; i<labels.length; i++) {
			for (int k=0; k<labels[0].length; k++) {
				labels[i][k] = features.unstemFeature(labels[i][k]);		
			}
		}
		return labels;
		
	}
	
	
	
	protected int clusterNumber() {
		int max = clusters[0];
		for (int i=1; i < clusters.length ; i++) {
			if (clusters[i]>max) {
				max=clusters[i];
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
					clusterTable[clusters[i]][k] += occurenceTable[i][k];					
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
