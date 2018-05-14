package visualization;

import java.util.HashMap;
import featureExtraction.*;

abstract class Visualization {
	protected int[] assignments;
	protected HashMap<Integer, String> idFile;
	
	Visualization(int[] assignments, HashMap<Integer,String> idFile){
		this.assignments = assignments;
		this.idFile = idFile;
	}
	
	abstract void visualize(String description);
	
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
	
	protected double calculateEntropy() {
		int clusterNumber = clusterNumber();
		double entropy=0;
		for (int i=0; i<clusterNumber; i++) {
			int clusterPoints=0;
			for (int k=0 ; k<assignments.length; k++) {
				if (assignments[k]==i) {
					clusterPoints += 1;
				}	
			}
			double temp = (double)clusterPoints/(double)assignments.length;
			entropy += temp * Math.log(temp)/Math.log(assignments.length);
		}
		return -entropy;
	}
	
}

