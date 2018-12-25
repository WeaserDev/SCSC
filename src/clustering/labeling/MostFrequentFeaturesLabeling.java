package clustering.labeling;

import featureExtraction.InputFeatureExtraction;
import featureExtraction.featureWeight.WeightMethod;

import java.util.ArrayList;
import java.util.HashMap;

import auth.eng.textManager.*;

public class MostFrequentFeaturesLabeling extends Labeling {
	int labelsNumber;
	WeightMethod weightMethod;
	
	public MostFrequentFeaturesLabeling(InputFeatureExtraction features,int[] assignments, int labelsNumber, WeightMethod weightMethod) {
		super(features,assignments);
		this.labelsNumber = labelsNumber;
		this.weightMethod = weightMethod;
	}
	
	protected HashMap<Integer, ArrayList<String>> createLabels() {
		float[][] clusterTable = createClusterTable();
		HashMap<Integer, ArrayList<String>> labelsMap = new HashMap<Integer, ArrayList<String>>();
		clusterTable = weightMethod.weightOccurenceTable(clusterTable);
		for (int i=0; i<clusterNumber();i++) {
		int[] mostFrequentIds = kLargestElementsIndex(clusterTable[i],labelsNumber);
		ArrayList<String> labels = new ArrayList<String>(labelsNumber);
			for (int j=0; j<labelsNumber; j++) {
				labels.add(j,features.describeFeature(mostFrequentIds[j]));
			}
		labelsMap.put(i, labels);
		}
		return labelsMap;
		
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


		for (int i=0; i<features.getFileNumber();i++) {
			for (int k=0; k<features.getFeatureNumber();k++) {
				clusterTable[clusters[i]][k] += occurenceTable[i][k];					
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
