package visualization;

import java.util.HashMap;


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
	
}

