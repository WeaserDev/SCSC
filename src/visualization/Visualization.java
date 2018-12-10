package visualization;

import java.util.HashMap;

abstract class Visualization {
	protected int[] clusters;
	protected HashMap<Integer, String> idFile;
	protected String[][] labels;
	
	Visualization(int[] clusters, HashMap<Integer,String> idFile, String[][] labels){
		this.clusters = clusters;
		this.idFile = idFile;
		this.labels = labels;
	}
	
	abstract void visualize(String description);
	
}

